from typing import Optional


class ODIException(Exception):
    def __init__(self, message: Optional[str] = None):
        super().__init__()
        self._message = message

    def __str__(self) -> str:
        return self._message if self._message else ""


class DatabaseError(ODIException):

    def __init__(
            self,
            message: Optional[str] = None,
    ) -> None:
        super().__init__(message)


if __name__ == "__main__":
    raise DatabaseError("shit")
