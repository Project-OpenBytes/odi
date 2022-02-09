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

public final class S3PutPolicy {

    /**
     * example: AWS4-HMAC-SHA256
     */
    private final String algorithm;

    private final String policy;

    private final String signature;

    /**
     * example: https://tutu.s3.cn-northwest-1.amazonaws.com.cn
     */
    private final String host;

    /**
     * example: label-platform/exquisite-helper/test1.json
     */
    private final String key;

    /**
     * example: 20210704T121212Z
     */
    private final String date;

    /**
     * example: <accessKeyID>/20210704/cn-northwest-1/s3/aws4_request
     */
    private final String credential;

    /**
     * example: public read
     */
    private final String acl;

    /**
     * example: 200
     */
    private final String status;

    public S3PutPolicy(String algorithm, String policy, String signature, String host, String key, String date, String credential, String acl, String status) {
        this.algorithm = algorithm;
        this.policy = policy;
        this.signature = signature;
        this.host = host;
        this.key = key;
        this.date = date;
        this.credential = credential;
        this.acl = acl;
        this.status = status;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getPolicy() {
        return policy;
    }

    public String getSignature() {
        return signature;
    }

    public String getHost() {
        return host;
    }

    public String getKey() {
        return key;
    }

    public String getDate() {
        return date;
    }

    public String getCredential() {
        return credential;
    }

    public String getAcl() {
        return acl;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "AwsPutPolicy{" +
                "algorithm='" + algorithm + '\'' +
                ", policy='" + policy + '\'' +
                ", signature='" + signature + '\'' +
                ", host='" + host + '\'' +
                ", key='" + key + '\'' +
                ", date='" + date + '\'' +
                ", credential='" + credential + '\'' +
                ", acl='" + acl + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}