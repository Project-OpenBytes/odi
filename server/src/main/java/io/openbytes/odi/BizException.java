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

public class BizException extends RuntimeException {

    private final CodeAndMessage codeAndMessage;

    private final String exceptionMessage;

    public BizException(CodeAndMessage codeAndMessage) {
        this.codeAndMessage = codeAndMessage;
        this.exceptionMessage = codeAndMessage.getMessage();
    }

    public BizException(CodeAndMessage codeAndMessage, String exceptionMessage) {
        this.codeAndMessage = codeAndMessage;
        this.exceptionMessage = exceptionMessage;
    }

    public CodeAndMessage getCodeAndMessage() {
        return codeAndMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

}
