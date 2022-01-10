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

import io.openbytes.odi.domain.Dataset;
import io.openbytes.odi.domain.repository.DatasetRepository;
import io.openbytes.odi.interfaces.vo.DatasetVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Slf4j(topic = "metricsLog")
@Service
public class DatasetService {

    @Resource
    private DatasetRepository datasetRepository;

    public Optional<DatasetVO> getByName(String name) {
        Optional<Dataset> optionalDataset = datasetRepository.getByName(name);
        return assembleVo(optionalDataset);
    }

    public Optional<DatasetVO> getById(String id) {
        Optional<Dataset> optionalDataset = datasetRepository.get(id);
        return assembleVo(optionalDataset);
    }

    private Optional<DatasetVO> assembleVo(Optional<Dataset> dataset) {
        if (dataset.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(DatasetVO.fromDO(dataset.get()));
    }


}
