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

import cn.hutool.core.io.file.FileNameUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import io.openbytes.odi.domain.DatasetFile;
import io.openbytes.odi.domain.storage.ListFilesResponse;
import io.openbytes.odi.domain.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class S3Storage implements Storage {

    @Resource
    private AmazonS3 amazonS3;

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
}
