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

import lombok.Getter;
import lombok.ToString;

/**
 * this is the abstraction of remote object info.
 */

@Getter
@ToString
public class DatasetFile {

    /**
     * eg: c.txt
     */
    private String name;

    /**
     * eg: a/b/c.txt
     */
    private String fullPath;

    private Long size;

    /*
    file downloaded url
     */
    private String url;

    public DatasetFile(String name, String fullPath, Long size, String url) {
        this.name = name;
        this.fullPath = fullPath;
        this.size = size;
        this.url = url;
    }

}
