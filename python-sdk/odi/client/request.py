from typing import Dict, Callable, List, Any

from requests.models import Response


class _HTTPMethod:
    GET = "GET"
    POST = "POST"


class _Request:
    pass

class HealthCheckRequest(_Request):
    pass

class HealthCheckRequest2(_Request):
    pass


class URLBuilder:
    def __init__(self):
        self._builder = {
            _Request: print,
            HealthCheckRequest: self._build_health_check_url,
            HealthCheckRequest2: self._build_health_check_url,
        }

    def _build_health_check_url(self) -> str:
        return "3333"

    def build(self, req: Any) -> str:
        return self._builder[req]()


class Client:
    _DEFAULT_URL = "https://gas.graviti.cn/"

    def __init__(self) -> None:
        self._url_builder = URLBuilder()

    def _make_url(self) -> str:
        return ""

    def request_to_url(self):
        return self._url_builder.build(HealthCheckRequest)

    def do(self, req, **kwargs) -> Response:
        return None


if __name__ == "__main__":
    c = Client()
    print(c.request_to_url())