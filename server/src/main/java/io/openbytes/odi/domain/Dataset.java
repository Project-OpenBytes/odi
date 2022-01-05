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

package io.openbytes.odi.domain;

import cn.hutool.core.util.RandomUtil;
import io.openbytes.odi.domain.common.HttpURL;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@ToString
public class Dataset {
    private final static int ID_LENGTH = 14;

    private final String id;

    private String name;

    private HttpURL homepage;

    private String description;

    private HttpURL readmeLink;

    private Instant createdAt;

    private Instant updatedAt;

    private String OwnerName;

    private String creatorUserId;

    private String creatorOrgId;

    private Integer viewCount;

    private Integer starCount;

    private Integer downloadCount;

    public Dataset(String name, HttpURL homepage, String description, HttpURL readmeLink, Instant createdAt, Instant updatedAt,
                   String ownerName, String creatorUserId, String creatorOrgId) {
        this.id = RandomUtil.randomString(ID_LENGTH).toUpperCase();
        this.name = name;
        this.homepage = homepage;
        this.description = description;
        this.readmeLink = readmeLink;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.OwnerName = ownerName;
        this.creatorUserId = creatorUserId;
        this.creatorOrgId = creatorOrgId;
    }
}
