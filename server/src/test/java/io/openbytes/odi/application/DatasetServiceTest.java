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
import io.openbytes.odi.domain.common.HttpURL;
import io.openbytes.odi.domain.repository.DatasetRepository;
import io.openbytes.odi.interfaces.vo.DatasetVO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DatasetServiceTest {

    @InjectMocks
    DatasetService datasetService;

    @Mock
    DatasetRepository datasetRepository;

    @Test
    public void testGetByName() {
        Dataset dataset1 = new Dataset("test_dataset", new HttpURL("http://baidu.com"),
                "desc", new HttpURL("http://baidu.com"), Instant.now(), Instant.now(), "ownerName", "1", "1");

        // query none
        Optional<DatasetVO> vo = Assertions.assertDoesNotThrow(() -> datasetService.getByName("")); // empty name
        Assertions.assertTrue(vo.isEmpty());

        // query dataset1
        when(datasetRepository.getByName(dataset1.getName())).then(invocation -> Optional.of(dataset1));

        vo = Assertions.assertDoesNotThrow(() -> datasetService.getByName(dataset1.getName()));
        Assertions.assertEquals(DatasetVO.fromDO(dataset1), vo.get());
    }

    @Test
    public void testGetById() {
        Dataset dataset1 = new Dataset("test_dataset", new HttpURL("http://baidu.com"),
                "desc", new HttpURL("http://baidu.com"), Instant.now(), Instant.now(), "ownerName", "1", "1");

        // query none
        Optional<DatasetVO> vo = Assertions.assertDoesNotThrow(() -> datasetService.getById("")); // empty name
        Assertions.assertTrue(vo.isEmpty());

        // query dataset1
        when(datasetRepository.get(dataset1.getId())).then(invocation -> Optional.of(dataset1));

        vo = Assertions.assertDoesNotThrow(() -> datasetService.getById(dataset1.getId()));
        Assertions.assertEquals(DatasetVO.fromDO(dataset1), vo.get());
    }
}