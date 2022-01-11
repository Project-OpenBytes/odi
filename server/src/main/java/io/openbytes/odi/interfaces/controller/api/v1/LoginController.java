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

package io.openbytes.odi.interfaces.controller.api.v1;

import io.openbytes.odi.application.GithubService;
import io.openbytes.odi.domain.user.DeviceCode;
import io.openbytes.odi.interfaces.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

@Api(tags = "[web] Login")
@RequestMapping("/api/v1/login")
@RestController
public class LoginController {

    @Resource
    private GithubService githubService;

    @ApiOperation(value = "Get an github device code")
    @GetMapping("/github/device/code")
    public Result<Optional<DeviceCode>> getGithubLoginDeviceCode() {
        return Result.ok(githubService.getDeviceCode());
    }
}
