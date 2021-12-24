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
