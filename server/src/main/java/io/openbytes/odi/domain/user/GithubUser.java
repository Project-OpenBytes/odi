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

package io.openbytes.odi.domain.user;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class GithubUser {

    @JSONField(name = "id")
    private String Id;

    /**
     * this is the unique name
     */
    @JSONField(name = "login")
    private String loginName;

    @JSONField(name = "avatar_url")
    private String avatarUrl;

    /**
     * this is the nickname
     */
    @JSONField(name = "name")
    private String nickName;

    @JSONField(name = "email")
    private String email;
}
