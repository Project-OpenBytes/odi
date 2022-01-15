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
import io.openbytes.odi.BizException;
import io.openbytes.odi.domain.user.DeviceCode;
import io.openbytes.odi.domain.user.GithubOauthTokenResult;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
public class GithubServiceTest {

    @Value("${github.account.clientId}")
    private String clientId;

    private GithubService githubService;


    @Test
    public void testGetDeviceCode() {
        //1.1 test empty clientId
        githubService = new GithubService("");
        Assertions.assertThrows(BizException.class, () -> githubService.getDeviceCode());


        //1.2 test a wrong clientId
        githubService = new GithubService("wrong clientId");
        Assertions.assertThrows(BizException.class, () -> githubService.getDeviceCode());

        //1.3 test get client id
        githubService = new GithubService(clientId);
        Optional<DeviceCode> dc = Assertions.assertDoesNotThrow(() -> githubService.getDeviceCode());
        Assertions.assertFalse(dc.isEmpty());
        Assertions.assertTrue(StrUtil.isNotEmpty(dc.get().getUserCode()));

        //2.1 test get access token with empty client id
        githubService = new GithubService("");
        Assertions.assertThrows(BizException.class, () -> githubService.getOAuthAccessToken(""));

        //2.2 test a wrong clientId
        githubService = new GithubService("wrong clientId");
        Assertions.assertThrows(BizException.class, () -> githubService.getOAuthAccessToken("test device code"));

        //2.3 test get access token with right device code, but not login in web
        githubService = new GithubService(clientId);
        GithubOauthTokenResult gotr = Assertions.assertDoesNotThrow(() -> githubService.getOAuthAccessToken(dc.get().getDeviceCode()));
        Assertions.assertEquals(gotr.getStatus(), GithubOauthTokenResult.Status.ERROR_PENDING);
        // login in
        //2.4 check again
        //gotr = Assertions.assertDoesNotThrow(() -> githubService.getOAuthAccessTokenAndRegister(dc.get().getDeviceCode()));
        //Assertions.assertTrue(StrUtil.isNotEmpty(gotr.get().getAccess_token()));
    }
}