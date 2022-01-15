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

package io.openbytes.odi.infrastructrue.repository.user;

import io.openbytes.odi.domain.repository.UserRepository;
import io.openbytes.odi.domain.user.User;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserChannelMapper channelMapper;

    @Override
    public void save(User user) {
        UserPO userPO = toPO(user);
        userPO.setInsertFields();
        userMapper.insert(userPO);
        user.setId(userPO.getId()); // this id should be return
    }

    @Override
    public void saveChannelUser(ChannelUserPO channelUserPO) {
        channelUserPO.setInsertFields();
        channelMapper.insert(channelUserPO);
    }

    @Override
    public Optional<User> getById(String userId) {
        UserPO user = userMapper.selectById(userId);
        return Optional.ofNullable(fromPO(user));
    }

    public Optional<ChannelUserPO> getUserByChannelTypeAndChannelUserId(String channelType, String channelUserId) {
        return Optional.ofNullable(channelMapper.selectByChannelTypeAndChannelUserId(channelType, channelUserId));
    }


    /**
     * @param user
     * @return
     */
    public UserPO toPO(User user) {
        Integer status = null;
        if (user.getStatus() != null) {
            status = user.getStatus().getCode();
        }
        return new UserPO(user.getUserName(), user.getNickname(), user.getEmail(), user.getAvatarUrl(), status);
    }


    /**
     * @param userPO
     * @return
     */
    public User fromPO(UserPO userPO) {
        return new User(userPO.getId(), userPO.getUserName(), userPO.getNickname(), userPO.getEmail(), userPO.getAvatarUrl(), User.Status.of(userPO.getStatus()).get());
    }
}
