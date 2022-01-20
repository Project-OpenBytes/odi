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

import io.openbytes.odi.domain.UserToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class BindByGithubDeviceCodeResult {

    private Status status;

    /**
     * odi bearer token
     */
    private UserToken userToken;

    private User user;

    public BindByGithubDeviceCodeResult(Status status) {
        this.status = status;
    }

    public BindByGithubDeviceCodeResult(Status status, UserToken userToken, User user) {
        this.status = status;
        this.userToken = userToken;
        this.user = user;
    }

    @AllArgsConstructor
    public enum Status {

        /**
         * user has not sign in, pending
         */
        SUCCESS(0),

        ERROR(1),

        TOKEN_EXPIRED(2),

        PENDING(3);

        @Getter
        public final int status;

        public static Status of(GithubOauthTokenResult.Status oauthTokenStatus) {
            switch (oauthTokenStatus) {
                case SUCCESS:
                    return SUCCESS;
                case ERROR_PENDING:
                    return PENDING;
                case ERROR_TOKEN_EXPIRED:
                    return TOKEN_EXPIRED;
                default:
                    return ERROR;
            }
        }
    }
}
