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
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor
public class User {
    private String id;


    /**
     * user nick name
     */
    private String nickname;

    /**
     * user unique name
     */
    private String userName;

    /**
     * user unique email
     */
    private String email;

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

        public static Optional<Status> of(int code) {
            for (Status status : Status.values()) {
                if (status.code == code) {
                    return Optional.of(status);
                }
            }
            return Optional.empty();
        }
    }

    public User() {
    }
}
