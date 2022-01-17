#  Copyright 2021 The OpenBytes Team. All rights reserved.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
import json
import os
import pathlib
import time
import webbrowser
from abc import ABCMeta, abstractmethod
from concurrent.futures import ALL_COMPLETED, ThreadPoolExecutor, wait
from typing import Any, Optional

from odi.client.auth.user import User
from odi.client.request import Client, Request


class _AuthenticationInterface(metaclass=ABCMeta):
    @abstractmethod
    def auth(self) -> Any:
        raise NotImplementedError


class Authentication(_AuthenticationInterface):
    def __init__(self, client: Optional[Client] = None) -> None:
        self._client = client if client and isinstance(client, Client) else Client()

    def auth(self) -> Any:
        pass

    def _get_odi_user_info(self, **kwargs: Any) -> (User, str):
        pass

    def _cache_token(self, **kwargs: Any) -> Any:
        pass


class GithubAuth(Authentication):
    def auth(self) -> bool:
        device_code, expire_time, request_interval = self._get_device_code()
        with ThreadPoolExecutor(10) as executor:
            all_task = [executor.submit(self._get_odi_user_info, device_code, expire_time, request_interval)]

        done, not_done = wait(all_task, return_when=ALL_COMPLETED)
        if not_done:
            return False
        for future in done:
            user, _ = future.result()
            if user:
                self._cache_token(user)
        print("Login succeeded!")
        return True

    def _cache_token(self, token: Any) -> Any:
        path = os.path.join(pathlib.Path.home(), ".odi")
        if not os.path.exists(os.path.join(pathlib.Path.home(), ".odi")):
            os.makedirs(path)
        with open(os.path.join(path, "odi_token"), "w") as file:
            file.write(json.dumps(token, indent=2))

    def _get_device_code(self) -> (str, int, int):
        resp = self._client.do(Request.GithubLoginDeviceCode).json()
        try:
            data = resp["data"]
            url = data["verificationUrl"]
            expire_time = data["expiresIn"]
            request_interval = data["interval"]
            user_code = data["userCode"]
            device_code = data["deviceCode"]
        except KeyError:
            # raise format_exc()
            print(resp)
            print("Failed.")
        else:
            print(f"Your Github authentication code: {user_code}")
            print(f"Please enter the code on: {url}")
            time.sleep(2)
            webbrowser.open(url)
            print("Waiting for ODI response...")

            return device_code, expire_time, request_interval

    def _get_odi_user_info(self, device_code: str, expire_time: int, request_interval: int) -> (Any, str):
        start = time.time()
        while time.time() - start < expire_time:
            resp = self._client.do(Request.RegisterByGithubDeviceCode, data={"device_code": device_code}).json()
            time.sleep(request_interval)
            if resp["status"] == "SUCCESS":
                return resp["user"], ""
        return None, ""


__all__ = [
    "Authentication",
    "GithubAuth"
]
