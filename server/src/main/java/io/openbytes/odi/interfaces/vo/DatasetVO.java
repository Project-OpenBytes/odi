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

package io.openbytes.odi.interfaces.vo;

import io.openbytes.odi.domain.Dataset;
import io.openbytes.odi.domain.DatasetTag;

import java.time.Instant;
import java.util.Set;

public class DatasetVO {
    public String id;
    public String name;
    public String homepage;
    public String description;
    public String readmeLink;
    public Instant insertTime;
    public Instant updatedTime;
    public String ownerName;
    public String creatorUserId;
    public String creatorOrgId;
    public Integer starCount;
    public Integer viewCount;
    public Integer downloadCount;
    public Set<DatasetTag> tags;

    public static DatasetVO from(Dataset dataset) {
        DatasetVO vo = new DatasetVO();
        vo.id = dataset.getId();
        vo.name = dataset.getName();
        if (dataset.getHomepage() != null) {
            vo.homepage = dataset.getHomepage().getUrl();
        }
        vo.description = dataset.getDescription();
        if (dataset.getReadmeLink() != null) {
            vo.readmeLink = dataset.getReadmeLink().getUrl();
        }
        vo.insertTime = dataset.getInsertTime();
        vo.updatedTime = dataset.getUpdateTime();
        vo.ownerName = dataset.getOwnerName();
        vo.creatorUserId = dataset.getCreatorUserId();
        vo.creatorOrgId = dataset.getCreatorOrgId();
        vo.tags = dataset.getTags();
        return vo;
    }
}
