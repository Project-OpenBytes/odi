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

package io.openbytes.odi.interfaces.ro;

import io.swagger.annotations.ApiModelProperty;

import java.util.HashSet;
import java.util.Set;

public class CreateDatasetRO {
    @ApiModelProperty(value = "dataset name", required = true)
    public String name = "";
    @ApiModelProperty(value = "dataset short desc", required = false)
    public String description = "";
    @ApiModelProperty(value = "dataset homepage", required = false)
    public String homepage = "";
    @ApiModelProperty(value = "dataset github readme link", required = false)
    public String readmeLink = "";
    @ApiModelProperty(value = "dataset owner name", required = false)
    public String ownerName = "";
    @ApiModelProperty(value = "dataset creator user id", required = false)
    public String creatorUserId = "";
    @ApiModelProperty(value = "dataset creator token", required = false)
    public String odiToken = "";
    @ApiModelProperty(value = "dataset related tags", required = false)
    public Set<String> tags = new HashSet<>();
}
