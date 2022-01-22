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

package io.openbytes.odi.application;

import cn.hutool.core.util.StrUtil;
import io.openbytes.odi.domain.storage.ListFilesResponse;
import io.openbytes.odi.domain.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j(topic = "metricsLog")
@Service
public class DatasetFileService {

    private final Storage storage;

    public DatasetFileService(Storage storage) {
        this.storage = storage;
    }

    public Optional<ListFilesResponse> listFiles(String datasetName, String marker, int maxKeys) {
        // todo check dataset
        if (StrUtil.isEmpty(datasetName)) {
            return Optional.of(ListFilesResponse.emptyResponse());
        }

        // append suffix
        datasetName = StrUtil.addSuffixIfNot(datasetName, "/");
        return Optional.of(storage.ListDatasetFiles(datasetName, marker, maxKeys));
    }
}
