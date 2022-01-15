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

package io.openbytes.odi.infrastructrue.repository.user;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.openbytes.odi.infrastructrue.repository.BasePO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 *
 */
@TableName("channel_user")
@AllArgsConstructor
@Setter
@Getter
public class ChannelUserPO extends BasePO {

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * eg: github
     */
    private String channelName;
    private String channelUserId;
    private String channelUserLogin;
    private String channelAccessToken;
    private String channelRefreshToken;
    private String TokenType;
    private Instant tokenExpire;
    /**
     * odi user id
     */
    private String userId;

    public ChannelUserPO() {

    }
}
