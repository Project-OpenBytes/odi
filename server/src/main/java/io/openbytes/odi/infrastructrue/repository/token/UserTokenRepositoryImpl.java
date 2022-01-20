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

package io.openbytes.odi.infrastructrue.repository.token;

import io.openbytes.odi.domain.UserToken;
import io.openbytes.odi.domain.repository.UserTokenRepository;
import io.openbytes.odi.infrastructrue.repository.user.UserTokenPO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserTokenRepositoryImpl implements UserTokenRepository {

    @Resource
    private UserTokenMapper userTokenMapper;

    @Override
    public void save(UserToken userToken) {
        UserTokenPO userTokenPO = toPO(userToken);
        userTokenPO.setInsertFields();
        userTokenMapper.insert(userTokenPO);
    }


    public static UserTokenPO toPO(UserToken userToken) {
        Integer tokenType = null;
        if (null != userToken.getTokenType()) {
            tokenType = userToken.getTokenType().getCode();
        }
        return new UserTokenPO(userToken.getUserId(), userToken.getUserToken(), tokenType, userToken.getRefreshTime(), userToken.getExpiredInSeconds());
    }
}
