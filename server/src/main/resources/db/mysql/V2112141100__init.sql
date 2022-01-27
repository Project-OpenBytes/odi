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

CREATE TABLE IF NOT EXISTS `dataset`
(
    `id` VARCHAR
(
    128
) NOT NULL,

    `insert_time` DATETIME
(
    3
),
    `update_time` DATETIME
(
    3
),
    `name` VARCHAR
(
    512
) NOT NULL,
    `homepage` VARCHAR
(
    1024
) NOT NULL,
    `description` TEXT NOT NULL,
    `readme_link` VARCHAR
(
    1024
) NOT NULL,
    `owner_name` TEXT,
    `creator_user_id` VARCHAR
(
    128
) ,
    `creator_org_id` VARCHAR
(
    128
),
    `view_count` INT,
    `star_count` INT,
    `download_count` INT,
    PRIMARY KEY
(
    `id`
) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    ROW_FORMAT = Dynamic;
alter table dataset change id id int AUTO_INCREMENT;
alter table dataset
    add unique index dataset_name (name);