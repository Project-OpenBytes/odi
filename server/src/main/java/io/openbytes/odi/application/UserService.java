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
import io.openbytes.odi.domain.repository.UserRepository;
import io.openbytes.odi.domain.user.BindByGithubDeviceCodeResult;
import io.openbytes.odi.domain.user.GithubOauthTokenResult;
import io.openbytes.odi.domain.user.GithubUser;
import io.openbytes.odi.domain.user.User;
import io.openbytes.odi.infrastructrue.repository.user.ChannelUserPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Slf4j(topic = "metricsLog")
@Service
public class UserService {

    @Resource
    private GithubService githubService;

    @Resource
    private UserRepository userRepository;

    public BindByGithubDeviceCodeResult bindByGithubDeviceCode(String githubDeviceCode) {
        GithubOauthTokenResult oauthAccessToken = githubService.getOAuthAccessToken(githubDeviceCode);


        BindByGithubDeviceCodeResult.Status bindStatus = BindByGithubDeviceCodeResult.Status.of(oauthAccessToken.getStatus());
        //if it's not success, direct return it's status
        if (!BindByGithubDeviceCodeResult.Status.SUCCESS.equals(bindStatus)) {
            return new BindByGithubDeviceCodeResult(bindStatus);
        }

        //get github user
        //will return exception if githubUser is not correct
        GithubUser githubUser = githubService.getUserByOauthToken(oauthAccessToken.getAccessToken());

        // check user exist?
        Optional<ChannelUserPO> channelUserPO = userRepository.getUserByChannelTypeAndChannelUserId("github", githubUser.getId());
        Optional<User> odiUser = channelUserPO.filter(user -> StrUtil.isNotEmpty(user.getUserId())).map(user -> userRepository.getById(user.getUserId())).filter(Optional::isPresent).map(Optional::get);
        if (odiUser.isPresent()) {
            return new BindByGithubDeviceCodeResult(BindByGithubDeviceCodeResult.Status.SUCCESS, odiUser.get());
        }

        // not exist, create a new user
        User newUser = new User();
        newUser.setUserName(githubUser.getLoginName());
        newUser.setNickname(githubUser.getNickName());
        newUser.setEmail(githubUser.getEmail());
        newUser.setAvatarUrl(githubUser.getAvatarUrl());
        newUser.setStatus(User.Status.enable);
        userRepository.save(newUser);

        ChannelUserPO userChannelPO = new ChannelUserPO();
        userChannelPO.setChannelName("github"); //todo set as enum
        userChannelPO.setChannelAccessToken(oauthAccessToken.getAccessToken());
        userChannelPO.setChannelUserId(githubUser.getId());
        userChannelPO.setTokenType("bearer"); //todo set as enum
        userChannelPO.setChannelUserLogin(githubUser.getLoginName());
        userChannelPO.setUserId(newUser.getId());
        userRepository.saveChannelUser(userChannelPO);

        return new BindByGithubDeviceCodeResult(BindByGithubDeviceCodeResult.Status.SUCCESS, newUser);

    }
}
