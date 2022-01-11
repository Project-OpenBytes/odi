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

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(of = "id", callSuper = false)
public class User {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;


    /**
     * user nick name
     */
    private String nickname;


    /**
     * user encrypt password
     */
    private String password;

    /**
     * user unique name
     */
    private String userName;

    /**
     * user avatar link
     */
    private String avatarUrl;


    /**
     * user status
     */
    private Status status;

    public enum Status {
        enable(1),
        disable(0),
        delete(2);

        @EnumValue
        @Getter
        private final int code;

        Status(int code) {
            this.code = code;
        }

        public static Optional<Status> get(int code) {
            for (Status status : Status.values()) {
                if (status.code == code) {
                    return Optional.of(status);
                }
            }
            return Optional.empty();
        }
    }
}
