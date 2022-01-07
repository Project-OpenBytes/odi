
import os.path
from enum import Enum, unique
from urllib.parse import urljoin

from requests import Session
from requests.models import Response


class _HTTPMethod:
    GET = "GET"
    POST = "POST"


@unique
class Request(Enum):
    HealthCheck = "health_check"
    PullDatasetByName = "pull_dataset_by_name"


class _URLBuilder:
    _API_VERSION = "v1"

    def __init__(self) -> None:
        self._common_request_prefix = "/api/" + self._API_VERSION + "/"
        self._dataset_request_prefix = self._common_request_prefix + "datasets/"

        self._builder = {
            Request.HealthCheck: self._health_check,
            Request.PullDatasetByName: self._pull_dataset_by_name
        }

    @staticmethod
    def _health_check(**kwargs) -> str:
        return "/health/check/"

    def _pull_dataset_by_name(self, **kwargs) -> str:
        return urljoin(self._dataset_request_prefix, os.path.join(kwargs["dataset"], "files/"))

    def build(self, req: Request, **kwargs) -> str:
        return self._builder[req](**kwargs)

    def version(self) -> str:
        return self._API_VERSION


class Client:

    def __init__(self) -> None:
        self._url_builder = _URLBuilder()

    def _make_url(self, req: Request, **kwargs) -> str:
        return self._url_builder.build(req, **kwargs)

    def do(self, req: Request, **kwargs) -> Response:
        url = urljoin(self._HOME, self._make_url(req, **kwargs))
        resp = Session().request(method=_HTTPMethod.GET, url=url)
        return resp
