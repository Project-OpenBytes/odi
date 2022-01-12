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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class GithubOauthTokenResult {

    @JSONField(name = "success")
    private boolean success;

    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "message")
    private String message;

    @Data
    public class OauthTokenSuccess {
        @JSONField(name = "access_token")
        private String accessToken;

        @JSONField(name = "token_type")
        private String tokenType;

        @JSONField(name = "scope")
        private String scope;
    }

    @Getter
    @AllArgsConstructor
    public enum OauthFailed {
        // this is a github auth token error response enum
        authorizationPending("authorization_pending", "The authorization request is still pending.", "error_uri");

        @JSONField(name = "error")
        private final String error;

        @JSONField(name = "error_description")
        private final String errorDescription;

        @JSONField(name = "error_uri")
        private final String errorUri;
    }

}
