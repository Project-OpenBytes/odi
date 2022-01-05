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

package io.openbytes.odi;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * http code & biz code
 */
@Getter
@AllArgsConstructor
public enum CodeAndMessage {
    doSuccess(true, 200, "odi-1000000", "Success."),

    doFailed(false, 200, "odi-1000001", "Failed."),

    argsIllegal(false, 200, "odi-10000011", "Args illegal."),
    ;

    private final Boolean success;

    private final Integer httpCode;

    private final String bizCode;

    private final String message;

}
