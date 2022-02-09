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

package io.openbytes.odi.domain.storage;

import io.openbytes.odi.infrastructrue.s3.S3PutPolicy;

import java.util.List;
import java.util.Map;

public interface Storage {
    /**
     * list odi dataset files
     *
     * @param prefix  prefix you want to query, eg: abc/
     * @param marker
     * @param maxKeys
     * @return todo we should update resp, add isTruncated info...
     */
    ListFilesResponse ListDatasetFiles(String prefix, String marker, int maxKeys);

    /**
     * get file url
     *
     * @param key
     * @return url
     */
    String GetUrl(String key);

    /**
     * get file urls
     *
     * @param keys
     * @return
     */
    Map<String, String> GetUrls(List<String> keys);

    S3PutPolicy GetPutPermission(String datasetName);
}
