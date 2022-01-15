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
import io.openbytes.odi.domain.user.GithubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
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

    private static final String GITHUB_USER_URL = "https://api.github.com/user";

    private static final MediaType MediaType_JSON = MediaType.get("application/json; charset=utf-8");

    private static final String DEVICE_CODE_SCOPE = "user";

    private static final String OAUTH_GRANT_TYPE = "urn:ietf:params:oauth:grant-type:device_code";

    private final String clientId;

    public GithubService(@Value("${github.account.clientId}") String clientId) {
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
    public GithubOauthTokenResult getOAuthAccessToken(String deviceCode) {
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
            switch (GithubOauthTokenResult.OauthFailed.of(JSON.parseObject(res).getString("error"))) {
                case authorizationPending:
                    return new GithubOauthTokenResult(GithubOauthTokenResult.Status.ERROR_PENDING);
                case tokenExpired:
                    return new GithubOauthTokenResult(GithubOauthTokenResult.Status.ERROR_TOKEN_EXPIRED);

                default:
                    return new GithubOauthTokenResult(GithubOauthTokenResult.Status.ERROR);
            }
        }

        // 3. deserialize access token
        GithubOauthTokenResult.OauthTokenSuccess oauthTokenSuccess = JSON.parseObject(res, GithubOauthTokenResult.OauthTokenSuccess.class);

        // return a success and odi token
        return new GithubOauthTokenResult(GithubOauthTokenResult.Status.SUCCESS, oauthTokenSuccess.getAccessToken());
    }

    /**
     * get a user from github by it's oauth bearer token
     *
     * @param oauthToken oauth bearer token
     * @return if oauthToken is empty, return a exception
     * if oauthToken get error or code is not 200, return a exception
     * else, return decoded user
     */
    public GithubUser getUserByOauthToken(String oauthToken) {
        if (StrUtil.isEmpty(oauthToken)) {
            throw new BizException(CodeAndMessage.argsIllegal, "OAuthToken is empty");
        }

        Request request = new Request.Builder().header("Authorization", "token " + oauthToken).url(GITHUB_USER_URL).get().build();
        String res = null;
        try (Response response = okHttpClient.newCall(request).execute()) {
            // if can not get response, return a error
            if (!response.isSuccessful() || HttpStatus.HTTP_OK != response.code()) {
                throw new BizException(CodeAndMessage.doFailed, String.format("get user by oauth token from github error, code = %s, successful = %s", response.code(), response.isSuccessful()));
            }
            res = response.body().string();
        } catch (Exception e) {
            log.warn("get github user by oauth token failed, catch error: {}", e.getMessage());
            throw new BizException(CodeAndMessage.doFailed, e.getMessage());
        }

        // 3. deserialize access token
        return JSON.parseObject(res, GithubUser.class);
    }

}
