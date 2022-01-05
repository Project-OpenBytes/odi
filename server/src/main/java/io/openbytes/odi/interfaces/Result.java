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

package io.openbytes.odi.interfaces;

import io.openbytes.odi.CodeAndMessage;
import lombok.Data;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

@Data
public class Result<T> {

    private Boolean success;

    private Integer httpCode;

    private String bizCode;

    private String message;

    private String traceId;

    private T data;

    private Result() {
    }

    public static <T> Result<T> ok() {
        return newInstance(CodeAndMessage.doSuccess);
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = newInstance(CodeAndMessage.doSuccess);
        result.data = data;
        return result;
    }

    public static <T> Result<T> error(CodeAndMessage codeAndMessage) {
        return newInstance(codeAndMessage);
    }

    public static <T> Result<T> error(CodeAndMessage codeAndMessage, String message) {
        Result<T> result = newInstance(codeAndMessage);
        result.message = message;
        return result;
    }

    private static <T> Result<T> newInstance(CodeAndMessage codeAndMessage) {
        Result<T> result = new Result<>();
        result.success = codeAndMessage.getSuccess();
        result.httpCode = codeAndMessage.getHttpCode();
        result.bizCode = codeAndMessage.getBizCode();
        result.message = codeAndMessage.getMessage();
        result.traceId = TraceContext.traceId();
        return result;
    }

}
