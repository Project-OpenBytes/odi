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

package io.openbytes.odi.infrastructrue.repository;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@TableName("dataset_tag")
@Getter
@Setter
public class TagPO extends BasePO {
    private String id;
    private String datasetId;
    private String tagName;

    public TagPO() {
    }

    public TagPO(String datasetId, String tagName) {
        this.datasetId = datasetId;
        this.tagName = tagName;
    }

    public TagPO(String id, String datasetId, String tagName) {
        this.id = id;
        this.datasetId = datasetId;
        this.tagName = tagName;
    }

}
