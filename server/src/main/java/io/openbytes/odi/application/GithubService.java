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

package io.openbytes.odi.application;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import io.openbytes.odi.BizException;
import io.openbytes.odi.CodeAndMessage;
import io.openbytes.odi.domain.user.DeviceCode;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j(topic = "metricsLog")
@Service
public class GithubService {

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private static final String GET_DEVICE_CODE_URL = "https://github.com/login/device/code";

    private static final MediaType MediaType_JSON = MediaType.get("application/json; charset=utf-8");

    private String clientId;

    public GithubService(@Value("${github.account.clientId}") @NotNull String clientId) {
        this.clientId = clientId;
    }

    public Optional<DeviceCode> getDeviceCode() {
        if (StrUtil.isEmpty(clientId)) {
            throw new BizException(CodeAndMessage.doFailed, "clientId is empty");
        }

        Map<String, String> params = new LinkedHashMap<>();
        params.put("client_id", clientId);

        RequestBody body = RequestBody.create(JSON.toJSONString(params), MediaType_JSON);
        Request request = new Request.Builder().header("Accept", "application/json").url(GET_DEVICE_CODE_URL).post(body).build();
        // 2. get response
        String res = null;
        try (Response response = okHttpClient.newCall(request).execute()) {
            // if can not get response, return a error
            if (!response.isSuccessful() || HttpStatus.HTTP_OK != response.code()) {
                throw new BizException(CodeAndMessage.doFailed, String.format("get response from github error, code = %s, successful = %v", response.code(), response.isSuccessful()));
            }
            res = response.body().string();
        } catch (Exception e) {
            log.warn("get github device code, catch error: {}", e.getMessage());
            throw new BizException(CodeAndMessage.doFailed, e.getMessage());
        }

        // 3. deserialize access token
        DeviceCode dc = JSON.parseObject(res, DeviceCode.class);
        return Optional.of(dc);
    }

}
