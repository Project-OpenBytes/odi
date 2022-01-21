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

from typing import Any, Dict, Optional, Type
from urllib.parse import urljoin

from requests import Session
from requests.models import Response


class _HTTPMethod:
    GET = "GET"
    POST = "POST"
    PUT = "PUT"


class _BaseRequest:
    _API_VERSION = "v1"
    _REQUEST_PREFIX = f"/api/{_API_VERSION}"

    METHOD = _HTTPMethod.GET

    @classmethod
    def make_url(cls, **kwargs: Any) -> str:
        raise NotImplementedError

    @classmethod
    def api_version(cls) -> str:
        return cls._API_VERSION


class _UserRequest(_BaseRequest):
    _REQUEST_PREFIX = f"{_BaseRequest._REQUEST_PREFIX}/users"

    @classmethod
    def make_url(cls, **kwargs: Any) -> str:
        raise NotImplementedError


class _DatasetRequest(_BaseRequest):
    _REQUEST_PREFIX = f"{_BaseRequest._REQUEST_PREFIX}/datasets"

    @classmethod
    def make_url(cls, **kwargs: Any) -> str:
        raise NotImplementedError


class _HealthCheckRequest(_BaseRequest):
    METHOD = _HTTPMethod.GET

    @classmethod
    def make_url(cls, **kwargs: Any) -> str:
        return "/health/check/"


class _GithubLoginDeviceCodeRequest(_BaseRequest):
    METHOD = _HTTPMethod.GET

    @classmethod
    def make_url(cls, **kwargs: Any) -> str:
        return f"{cls._REQUEST_PREFIX}/login/github/device/code/"


class _RegisterByGithubDeviceCodeRequest(_UserRequest):
    METHOD = _HTTPMethod.PUT

    @classmethod
    def make_url(cls, **kwargs: Any) -> str:
        return f"{cls._REQUEST_PREFIX}/register/github/device_code/"


class _PullDatasetByNameRequest(_DatasetRequest):
    METHOD = _HTTPMethod.POST

    @classmethod
    def make_url(cls, **kwargs: Any) -> str:
        return f"{cls._REQUEST_PREFIX}/{kwargs['dataset']}/files/"


class Request:
    # ignore the camel style

    # base
    HealthCheck = _HealthCheckRequest
    GithubLoginDeviceCode = _GithubLoginDeviceCodeRequest

    # user
    RegisterByGithubDeviceCode = _RegisterByGithubDeviceCodeRequest

    # dataset
    PullDatasetByName = _PullDatasetByNameRequest


class Client:
    def __init__(
            self,
            protocol: Optional[str] = "http",
            host: Optional[str] = "47.103.90.26",
            port: Optional[int] = 9876
    ) -> None:
        self._protocol = protocol
        self._host = host
        self._port = port
        self._request_prefix = f"{protocol}://{host}:{port}/" if port else f"{protocol}://{host}/"

    @property
    def protocol(self) -> str:
        return self._protocol

    @property
    def host(self) -> str:
        return self._host

    @property
    def port(self) -> int:
        return self._port

    @property
    def request_prefix(self) -> str:
        return self._request_prefix

    def _make_url(self, req: Type[_BaseRequest], **kwargs: Any) -> str:
        return urljoin(self._request_prefix, req.make_url(**kwargs))

    def do(self, req: Type[_BaseRequest], data: Optional[Dict[str, Any]] = None, **kwargs: Any) -> Response:
        url = self._make_url(req, **kwargs)
        resp = Session().request(method=req.METHOD, url=url, data=data)
        return resp


__all__ = ["Client", "Request"]
