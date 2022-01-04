from odi.client.request import Client


class ODI:
    def __init__(self):
        self._client = Client()

    def pull(self, dataset: str) -> None:
        pass
