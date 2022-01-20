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
import io.openbytes.odi.CodeAndMessage;
import io.openbytes.odi.domain.UserToken;
import io.openbytes.odi.domain.repository.UserTokenRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TokenService {

    @Resource
    private UserTokenRepository tokenRepository;

    /**
     * generate a token by user id
     *
     * @param userId
     * @return
     */
    // optional, 返回的值是一个 可选， 针对于查询
    // update 操作返回 error
    public UserToken generateToken(String userId) {
        if (StrUtil.isEmpty(userId)) {
            throw new BizException(CodeAndMessage.argsIllegal, "TokenService.userId is empty");
        }

        UserToken oAuthToken = new UserToken(userId);
        tokenRepository.save(oAuthToken);
        return oAuthToken;
    }
}
