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

package io.openbytes.odi.interfaces.controller.api;

import io.openbytes.odi.application.DatasetFileService;
import io.openbytes.odi.application.DatasetService;
import io.openbytes.odi.domain.storage.ListFilesResponse;
import io.openbytes.odi.interfaces.Result;
import io.openbytes.odi.interfaces.vo.DatasetVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

@Api(tags = "[web] Dataset")
@RequestMapping("/api/v1/datasets")
@RestController
public class DatasetController {

    @Resource
    private DatasetService service;

    @Resource
    private DatasetFileService datasetFileService;


    @ApiOperation(value = "Get an dataset by id", notes = "When id not exist, return empty.")
    @GetMapping("/{id}")
    public Result<Optional<DatasetVO>> getById(String id) {
        if (id == null) {
            id = "";
        }

        return Result.ok(service.getById(id));
    }


    /**
     * @param dataset the dataset name you want to query.
     * @param marker  marker is for page for query, if given marker, server will query files after this marker
     * @param maxKeys maxKeys is the current page size you want to query.
     * @return
     */
    @ApiOperation(value = "Get dataset files", notes = "Get dataset files.")
    @GetMapping("/{dataset}/files")
    public Result<Optional<ListFilesResponse>> list(@PathVariable String dataset, @RequestParam(name = "marker", required = false) String marker, @RequestParam(name = "maxKeys", defaultValue = "10", required = false) int maxKeys) {
        if (dataset == null) {
            dataset = "";
        }

        return Result.ok(datasetFileService.list(dataset, marker, maxKeys));
    }

}
