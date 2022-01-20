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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@ToString
public class UserToken {
    private final static String TOKEN_PREFIX = "odi";

    private final static int TOKEN_LENGTH = 36;

    // token with expired Time will expire in 30 days
    private final static int TOKEN_EXPIRED_IN_SECONDS = 30 * 24 * 60 * 60;
    private String id;
    private String userId;
    private String userToken;
    private Type tokenType;
    private Instant refreshTime; // last refresh time
    private Integer expiredInSeconds;

    public UserToken(String userId) {
        this(userId, Type.BEARER);
    }

    public UserToken(String userId, Type tokenType) {
        this(userId, tokenType, TOKEN_EXPIRED_IN_SECONDS);
    }

    public UserToken(String userId, Type tokenType, Integer expiredInSeconds) {
        this.userId = userId;
        this.tokenType = tokenType;
        this.userToken = generateTokenString();
        this.refreshTime = Instant.now();
        this.expiredInSeconds = expiredInSeconds;
    }

    public String generateTokenString() {
        return String.format("%s_%s", TOKEN_PREFIX, RandomUtil.randomString(TOKEN_LENGTH));
    }

    @Getter
    @AllArgsConstructor
    public enum Type {
        BEARER(1);

        Integer code;

        public static Type of(Integer code) {
            for (Type type : Type.values()) {
                if (type.getCode() == code) {
                    return type;
                }
            }

            return null;
        }
    }
}
