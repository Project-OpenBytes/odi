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
from typing import Type
from urllib.parse import urljoin

from requests import Session
from requests.models import Response


class _HTTPMethod:
    GET = "GET"
    POST = "POST"


class _BaseRequest(metaclass=ABCMeta):
    _API_VERSION = "v1"

    _URL = ""

    _REQUEST_PREFIX = f"/api/{_API_VERSION}/"
    _DATASET_REQUEST_PREFIX = f"{_REQUEST_PREFIX}datasets/"

    METHOD = _HTTPMethod.GET

    @classmethod
    def make_url(cls, **kwargs) -> str:
        raise NotImplementedError

    @classmethod
    def api_version(cls) -> str:
        return cls._API_VERSION


class _HealthCheckRequest(_BaseRequest):
    _URL = "/health/check/"
    METHOD = _HTTPMethod.GET

    @classmethod
    def make_url(cls, **kwargs) -> str:
        return cls._URL


class _PullDatasetByNameRequest(_BaseRequest):
    METHOD = _HTTPMethod.POST

    @classmethod
    def make_url(cls, **kwargs) -> str:
        return urljoin(cls._DATASET_REQUEST_PREFIX, f"{kwargs['dataset']}files/")


class Request:
    # ignore the camel style
    HealthCheck = _HealthCheckRequest
    PullDatasetByName = _PullDatasetByNameRequest


class Client:
    _SERVER = "http://127.0.0.1/"

    def __init__(self) -> None:
        pass

    def _make_url(self, req: Type[_BaseRequest], **kwargs) -> str:
        return urljoin(self._SERVER, req.make_url(**kwargs))

    def do(self, req: Type[_BaseRequest], **kwargs) -> Response:
        url = self._make_url(req, **kwargs)
        resp = Session().request(method=req.METHOD, url=url)
        return resp


__all__ = ["Client", "Request"]

if __name__ == "__main__":
    print(Client().do(Request.HealthCheck))
