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

import io.openbytes.odi.domain.common.HttpURL;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@ToString
public class Dataset {
    private final static int ID_LENGTH = 14;

    private String id;

    private Instant insertTime;

    private Instant updatedTime;

    private String name;

    private HttpURL homepage;

    private String description;

    private HttpURL readmeLink;


    private String OwnerName;

    private String creatorUserId;

    private String creatorOrgId;

    private Integer viewCount;

    private Integer starCount;

    private Integer downloadCount;

    public Dataset(String name, HttpURL homepage, String description, HttpURL readmeLink, Instant insertTime, Instant updatedTime,
                   String ownerName, String creatorUserId, String creatorOrgId) {
        this.name = name;
        this.homepage = homepage;
        this.description = description;
        this.readmeLink = readmeLink;
        this.insertTime = insertTime;
        this.updatedTime = updatedTime;
        this.OwnerName = ownerName;
        this.creatorUserId = creatorUserId;
        this.creatorOrgId = creatorOrgId;
    }

    public Dataset(String id, String name, HttpURL homepage, String description, HttpURL readmeLink, Instant insertTime, Instant updatedTime,
                   String ownerName, String creatorUserId, String creatorOrgId) {
        this.id = id;
        this.name = name;
        this.homepage = homepage;
        this.description = description;
        this.readmeLink = readmeLink;
        this.insertTime = insertTime;
        this.updatedTime = updatedTime;
        this.OwnerName = ownerName;
        this.creatorUserId = creatorUserId;
        this.creatorOrgId = creatorOrgId;
    }


}
