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

import io.openbytes.odi.domain.storage.ListFilesResponse;
import io.openbytes.odi.domain.storage.Storage;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DatasetFileServiceTest {

    @InjectMocks
    DatasetFileService datasetFileService;

    @Mock
    Storage storage;

    @Test
    public void testList() {

        // test list empty dataset file name
        Optional<ListFilesResponse> listFilesResponse = Assertions.assertDoesNotThrow(() -> datasetFileService.list("", "", 0));
        Assertions.assertEquals(listFilesResponse.get().isTruncated(), ListFilesResponse.emptyResponse().isTruncated());
        Assertions.assertEquals(listFilesResponse.get().getFiles(), ListFilesResponse.emptyResponse().getFiles());

        String datasetName = "test-dataset-name";
        // test list by marker & maxKeys
        //  when(storage.ListDatasetFiles())

    }


}