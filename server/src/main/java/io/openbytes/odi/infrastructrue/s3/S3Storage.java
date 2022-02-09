/*
 * Copyright 2021 The OpenBytes Team. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.openbytes.odi.infrastructrue.s3;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.json.JSONUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import io.openbytes.odi.domain.DatasetFile;
import io.openbytes.odi.domain.storage.ListFilesResponse;
import io.openbytes.odi.domain.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class S3Storage implements Storage {

    @Resource
    private AmazonS3 amazonS3;

    @Resource
    private AWSConfiguration awsConfiguration;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.expiredInSeconds}")
    private int expiredInSeconds;

    @Override
    public ListFilesResponse ListDatasetFiles(String prefix, String marker, int maxKeys) {
        ListFilesResponse resp = new ListFilesResponse();

        // list files from s3
        ObjectListing objectListing = amazonS3.listObjects(
                new ListObjectsRequest().withBucketName(bucket)
                        .withPrefix(prefix)
                        .withMarker(marker)
                        .withMaxKeys(maxKeys)
        );


        resp.setTruncated(objectListing.isTruncated());
        resp.setFiles(objectListing.getObjectSummaries().parallelStream().
                map(objectSummary -> new DatasetFile(FileNameUtil.getName(objectSummary.getKey()), objectSummary.getKey(),
                        objectSummary.getSize(), GetUrl(objectSummary.getKey()))).
                filter(x -> !x.getFullPath().endsWith("/")). // only file
                collect(Collectors.toList()));

        return resp;
    }

    @Override
    public String GetUrl(String key) {
        //todo maybe set bucket to public?
        return amazonS3.generatePresignedUrl(bucket, key, new Date(Instant.now().toEpochMilli() + 1000 * expiredInSeconds)).toExternalForm(); // Non-encrypted permanent download link
    }

    @Override
    public Map<String, String> GetUrls(List<String> keys) {
        return keys.stream().collect(
                Collectors.toMap(key -> key, this::GetUrl));
    }

    @Override
    public S3PutPolicy GetPutPermission(String datasetName) {
        // file will upload under datasets/datasetName/ prefix
        String prefix = String.format("%s/%s/", "datasets", datasetName);
        return generatePutPolicy(bucket, prefix, awsConfiguration.getAccessKey(), awsConfiguration.getSecretKey(), awsConfiguration.getRegion(), Instant.now().plusSeconds(600l));
    }

    /**
     * generate put policy; use AWS4 signature version
     * <p>
     * 1. calculate StringToSign(policy)
     * see how to calculate policy: https://docs.aws.amazon.com/zh_cn/AmazonS3/latest/API/sigv4-authentication-HTTPPOST.html
     * 2. calculate SigningKey
     * see how to calculate signing key: https://docs.aws.amazon.com/zh_cn/AmazonS3/latest/API/sigv4-authentication-HTTPPOST.html
     * 3. calculate Signature
     * Hex(SHA256(SigningKey, StringToSign))
     * </p>
     *
     * @param bucket      @NotEmpty `tutu` .etc
     * @param fileName    @NotEmpty `label-platform/user/test.png` `avatar1.png` .etc
     * @param expiredTime @NotNull  time when the policy expired
     */
    public S3PutPolicy generatePutPolicy(String bucket, String fileName, String accessKeyID, String accessSecret, String region, Instant expiredTime) {
        Date currentTime = new Date();
        String currentTimePureStr = DateUtil.format(currentTime, DatePattern.PURE_DATE_PATTERN);
        String currentTimeISO8601SimpleStr = DateUtil.format(currentTime, PolicyConditionsConst.ISO8601_SIMPLE_PATTERN);
        String credential = String.format("%s/%s/%s/%s/%s", accessKeyID, currentTimePureStr, region, SERVICE, AWS4_REQUEST);
        PolicyConditionsConst policyConditionsConst = new PolicyConditionsConst(expiredTime);
        policyConditionsConst.addEq(PolicyConditionsConst.KEY_X_AMZ_DATE, currentTimeISO8601SimpleStr);
        policyConditionsConst.addEq(PolicyConditionsConst.KEY_X_AMZ_ALGORITHM, Algorithm.SHA256);
        policyConditionsConst.addEq(PolicyConditionsConst.KEY_X_AMZ_CREDENTIAL, credential);
        policyConditionsConst.addEq(PolicyConditionsConst.KEY_BUCKET, bucket);
        policyConditionsConst.addEq(PolicyConditionsConst.KEY_SUCCESS_ACTION_STATUS, HTTP_SUCCESS_CODE);
        policyConditionsConst.addStartsWith(PolicyConditionsConst.KEY_FILE_NAME_LIKE, fileName);
        String policyConditionsJSON = JSONUtil.toJsonStr(policyConditionsConst);

        // get policy
        String policyBase64 = Base64.encode(policyConditionsJSON.getBytes(StandardCharsets.UTF_8));

        // get signing key
        byte[] signingKey = calculateSigningKey(currentTimePureStr, region, accessSecret);

        // get signature
        String signature = calculateSignature(signingKey, policyBase64);

        // https://dev-odi-bucket-211231.s3.us-west-1.amazonaws.com
        String host = String.format("%s://%s.%s.%s.%s/", PROTOCOL, bucket, SERVICE, region, ENDPOINT_SUFFIX);
        return new S3PutPolicy(Algorithm.SHA256, policyBase64, signature, host, fileName, currentTimeISO8601SimpleStr, credential, Acl.PUBLIC_READ, HTTP_SUCCESS_CODE);
    }


    interface Acl {
        String PUBLIC_READ = "public-read";
    }

    interface Algorithm {
        String SHA256 = "AWS4-HMAC-SHA256";
    }

    // PolicyConditionsConst: description of the conditions of policy
    // Example JSON:
    // { "expiration": "2007-12-01T12:00:00.000Z",
    //   "conditions": [
    //     ["eq", "$acl": "public-read" ],
    //     ["eq", "$bucket": "tutu" ],
    //     ["starts-with", "$key", "user/"],
    //   ]
    //  }
    // see help from https://docs.aws.amazon.com/AmazonS3/latest/API/sigv4-HTTPPOSTConstructPolicy.html
    private static final class PolicyConditionsConst {

        /**
         * The expiration element specifies the expiration date and time of the POST policy in ISO8601 GMT date format.
         * For example, 2013-08-01T12:00:00.000Z specifies that the POST policy is not valid after midnight GMT on August 1, 2013.
         */
        private final String expiration;

        /**
         * The conditions in a POST policy is an array of objects,
         * each of which is used to validate the request.
         * You can use these conditions to restrict what is allowed in the request.
         * For example, the preceding policy conditions require the following:
         * 1. Request must specify the `tutu` bucket name.
         * 2. Object key name must have the `user/` prefix.
         * 3. Object ACL must be set to `public-read`.
         */
        private final List<List<String>> conditions;

        private final String EQ = "eq";
        private final String STARTS_WITH = "starts-with";

        public final static String KEY_X_AMZ_DATE = "x-amz-date";
        public final static String KEY_X_AMZ_ALGORITHM = "x-amz-algorithm";
        public final static String KEY_X_AMZ_CREDENTIAL = "x-amz-credential";
        public final static String KEY_BUCKET = "bucket";
        public final static String KEY_ACL = "acl";
        public final static String KEY_SUCCESS_ACTION_STATUS = "success_action_status";
        public final static String KEY_FILE_NAME_LIKE = "key";

        public final static String ISO8601_SIMPLE_PATTERN = "yyyyMMdd'T'HHmmss'Z'";

        PolicyConditionsConst(Instant expiration) {
            this.expiration = expiration.toString();
            this.conditions = new ArrayList<>();
        }

        public void addEq(String key, String value) {
            conditions.add(Arrays.asList(EQ, String.format("$%s", key), value));
        }

        public void addStartsWith(String key, String value) {
            conditions.add(Arrays.asList(STARTS_WITH, String.format("$%s", key), value));
        }

        public String getExpiration() {
            return expiration;
        }

        public List<List<String>> getConditions() {
            return conditions;
        }
    }

    private static final String AWS_SIGNATURE_VERSION = "AWS4";
    private static final String AWS4_REQUEST = "aws4_request";
    private static final String PROTOCOL = "https";
    private static final String SERVICE = "s3";
    private static final String ENDPOINT_SUFFIX = "amazonaws.com/";
    private static final String HTTP_SUCCESS_CODE = "200";

    /**
     * sha256
     *
     * @param key  signingKey
     * @param data stringToSign
     */
    private byte[] sha256(byte[] key, String data) {
        return SecureUtil.hmac(HmacAlgorithm.HmacSHA256, key).digest(data);
    }

    /**
     * how to calculate signing key
     * see https://docs.aws.amazon.com/zh_cn/AmazonS3/latest/API/sigv4-authentication-HTTPPOST.html
     *
     * @param currentTimePureStr like `yyyyMMdd`
     * @param region             like `us-east-1`
     */
    private byte[] calculateSigningKey(String currentTimePureStr, String region, String accessKeySecret) {
        byte[] dateKey = sha256((AWS_SIGNATURE_VERSION + accessKeySecret).getBytes(StandardCharsets.UTF_8), currentTimePureStr);
        byte[] dateRegionKey = sha256(dateKey, region);
        byte[] dateRegionServiceKey = sha256(dateRegionKey, SERVICE);
        byte[] signingKey = sha256(dateRegionServiceKey, AWS4_REQUEST);
        return signingKey;
    }

    /**
     * how to calculate signature
     * see https://docs.aws.amazon.com/zh_cn/AmazonS3/latest/API/sigv4-authentication-HTTPPOST.html
     *
     * @param signingKey
     * @param policyBase64
     */
    private String calculateSignature(byte[] signingKey, String policyBase64) {
        return HexUtil.encodeHexStr(sha256(signingKey, policyBase64));
    }
}
