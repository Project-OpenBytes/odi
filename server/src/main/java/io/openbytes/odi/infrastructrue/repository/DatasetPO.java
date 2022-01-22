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

@TableName("dataset")
@Getter
@Setter
public class DatasetPO extends BasePO {
    private String id;
    private String name;
    private String homepage;
    private String description;
    private String readmeLink;
    private String ownerName;
    private String creatorUserId;
    private String creatorOrgId;
    private Integer viewCount;
    private Integer starCount;
    private Integer downloadCount;


    public DatasetPO(String id, String name, String homepage, String description, String ownerName, String creatorUserId, String creatorOrgId) {
        this.id = id;
        this.name = name;
        this.homepage = homepage;
        this.description = description;
        this.ownerName = ownerName;
        this.creatorUserId = creatorUserId;
        this.creatorOrgId = creatorOrgId;
    }

}
