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

package io.openbytes.odi.domain.common;


import cn.hutool.core.util.StrUtil;
import io.openbytes.odi.BizException;
import io.openbytes.odi.CodeAndMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.net.MalformedURLException;

@Getter
@ToString
@EqualsAndHashCode(of = "url")
public class HttpURL {

    private static final String HTTPS_PREFIX = "https://";

    private static final String HTTP_PREFIX = "http://";

    private final static int HTTPS_URL_LENGTH = 1024;

    private final String url;

    public HttpURL(String url) {
        if (!this.isUrl(url)) {
            throw new BizException(CodeAndMessage.argsIllegal, "Not URL.");
        }
        if (!url.startsWith(HTTPS_PREFIX) && !url.startsWith(HTTP_PREFIX)) {
            throw new BizException(CodeAndMessage.argsIllegal, "Not HTTP URL.");
        }
        if (url.length() > HTTPS_URL_LENGTH) {
            throw new BizException(CodeAndMessage.argsIllegal, "`url` too long.");
        }
        this.url = url;
    }

    public boolean isUrl(CharSequence value) {
        if (StrUtil.isBlank(value)) {
            return false;
        }
        try {
            new java.net.URL(StrUtil.str(value));
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }

}
