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
import io.openbytes.odi.domain.user.GithubOauthTokenResult;
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

    private static final String DEVICE_CODE_URL = "https://github.com/login/device/code";

    private static final String OAUTH_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";

    private static final MediaType MediaType_JSON = MediaType.get("application/json; charset=utf-8");

    private static final String DEVICE_CODE_SCOPE = "user";

    private static final String OAUTH_GRANT_TYPE = "urn:ietf:params:oauth:grant-type:device_code";

    private final String clientId;

    public GithubService(@Value("${github.account.clientId}") @NotNull String clientId) {
        this.clientId = clientId;
    }

    /**
     * <p>
     * https://docs.github.com/en/developers/apps/building-oauth-apps/authorizing-oauth-apps#step-1-app-requests-the-device-and-user-verification-codes-from-github
     * </p>
     *
     * @return get device code & user code from github workflow
     */
    public Optional<DeviceCode> getDeviceCode() {
        if (StrUtil.isEmpty(clientId)) {
            throw new BizException(CodeAndMessage.doFailed, "clientId is empty");
        }

        Map<String, String> params = new LinkedHashMap<>();
        params.put("client_id", clientId);
        params.put("scope", DEVICE_CODE_SCOPE);

        RequestBody body = RequestBody.create(JSON.toJSONString(params), MediaType_JSON);
        Request request = new Request.Builder().header("Accept", "application/json").url(DEVICE_CODE_URL).post(body).build();
        // 2. get response
        String res = null;
        try (Response response = okHttpClient.newCall(request).execute()) {
            // if can not get response, return a error
            if (!response.isSuccessful() || HttpStatus.HTTP_OK != response.code()) {
                throw new BizException(CodeAndMessage.doFailed, String.format("get response from github error, code = %s, successful = %s", response.code(), response.isSuccessful()));
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

    /**
     * <p>
     * https://docs.github.com/en/developers/apps/building-oauth-apps/authorizing-oauth-apps#device-flow
     * </p>
     * <p>
     * get access token by client id & device code & grant type
     * todo : use a token bucket to limit this request
     *
     * @param deviceCode github get device code response
     * @return
     */
    public Optional<GithubOauthTokenResult> getOAuthAccessToken(String deviceCode) {
        if (StrUtil.isEmpty(clientId)) {
            throw new BizException(CodeAndMessage.doFailed, "clientId is empty");
        }

        if (StrUtil.isEmpty(deviceCode)) {
            throw new BizException(CodeAndMessage.argsIllegal, "device code is empty");
        }

        Map<String, String> params = new LinkedHashMap<>();
        params.put("client_id", clientId);
        params.put("device_code", deviceCode);
        params.put("grant_type", OAUTH_GRANT_TYPE);

        RequestBody body = RequestBody.create(JSON.toJSONString(params), MediaType_JSON);
        Request request = new Request.Builder().header("Accept", "application/json").url(OAUTH_ACCESS_TOKEN_URL).post(body).build();
        // 2. get response
        String res = null;
        try (Response response = okHttpClient.newCall(request).execute()) {
            // if can not get response, return a error
            if (!response.isSuccessful() || HttpStatus.HTTP_OK != response.code()) {
                throw new BizException(CodeAndMessage.doFailed, String.format("get response from github error, code = %s, successful = %s", response.code(), response.isSuccessful()));
            }
            res = response.body().string();
        } catch (Exception e) {
            log.warn("get github device code, catch error: {}", e.getMessage());
            throw new BizException(CodeAndMessage.doFailed, e.getMessage());
        }

        // check if it's not login
        if (JSON.parseObject(res).containsKey("error")) {
            if (GithubOauthTokenResult.OauthFailed.authorizationPending.getError().equals(JSON.parseObject(res).getString("error"))) {
                return Optional.of(new GithubOauthTokenResult(false, "", GithubOauthTokenResult.OauthFailed.authorizationPending.getErrorDescription()));
            } else { // is not pending, just throw exceptions
                throw new BizException(CodeAndMessage.doFailed, String.format("get response from github access token error, res = %s", res));
            }
        }

        // 3. deserialize access token
        GithubOauthTokenResult.OauthTokenSuccess oauthTokenSuccess = JSON.parseObject(res, GithubOauthTokenResult.OauthTokenSuccess.class);


        // return a success and odi token
        return Optional.of(new GithubOauthTokenResult(true, oauthTokenSuccess.getAccessToken(), ""));
    }

}
