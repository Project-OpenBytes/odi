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
from abc import ABCMeta
from typing import Any, Dict, Type
from urllib.parse import urljoin

from requests import Session
from requests.models import Response


class _HTTPMethod:
    GET = "GET"
    POST = "POST"
    PUT = "PUT"


class _BaseRequest(metaclass=ABCMeta):
    _API_VERSION = "v1"
    _REQUEST_PREFIX = f"/api/{_API_VERSION}"

    METHOD = _HTTPMethod.GET

    @classmethod
    def make_url(cls, **kwargs) -> str:
        raise NotImplementedError

    @classmethod
    def api_version(cls) -> str:
        return cls._API_VERSION


class _UserRequest(_BaseRequest):
    _REQUEST_PREFIX = f"{_BaseRequest._REQUEST_PREFIX}/users"

    @classmethod
    def make_url(cls, **kwargs) -> str:
        raise NotImplementedError


class _DatasetRequest(_BaseRequest):
    _REQUEST_PREFIX = f"{_BaseRequest._REQUEST_PREFIX}/datasets"

    @classmethod
    def make_url(cls, **kwargs) -> str:
        raise NotImplementedError


class _HealthCheckRequest(_BaseRequest):
    METHOD = _HTTPMethod.GET

    @classmethod
    def make_url(cls, **kwargs) -> str:
        return "/health/check/"


class _PullDatasetByNameRequest(_DatasetRequest):
    METHOD = _HTTPMethod.POST

    @classmethod
    def make_url(cls, **kwargs) -> str:
        return f"{cls._REQUEST_PREFIX}/{kwargs['dataset']}/files/"


class _GithubLoginDeviceCodeRequest(_BaseRequest):
    METHOD = _HTTPMethod.GET

    @classmethod
    def make_url(cls, **kwargs) -> str:
        return f"{cls._REQUEST_PREFIX}/login/github/device/code/"


class _RegisterByGithubDeviceCodeRequest(_UserRequest):
    METHOD = _HTTPMethod.PUT

    @classmethod
    def make_url(cls, **kwargs) -> str:
        return f"{cls._REQUEST_PREFIX}/register/github/device_code/"


class Request:
    # ignore the camel style
    HealthCheck = _HealthCheckRequest
    PullDatasetByName = _PullDatasetByNameRequest
    GithubLoginDeviceCode = _GithubLoginDeviceCodeRequest
    RegisterByGithubDeviceCode = _RegisterByGithubDeviceCodeRequest


class Client:
    _SERVER = "http://127.0.0.1/"

    def _make_url(self, req: Type[_BaseRequest], **kwargs) -> str:
        return urljoin(self._SERVER, req.make_url(**kwargs))

    def do(self, req: Type[_BaseRequest], data: Dict[str, Any] = None, **kwargs) -> Response:
        url = self._make_url(req, **kwargs)
        resp = Session().request(method=req.METHOD, url=url, data=data)
        return resp


__all__ = ["Client", "Request"]
