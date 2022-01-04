class S3:
    pass


class S3Obj:
    def __init__(self, key, size) -> None:
        self._key = key
        self._size = size