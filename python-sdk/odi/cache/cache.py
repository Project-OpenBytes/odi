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

from abc import ABCMeta, abstractmethod
from typing import Any, Optional


class Item:
    def __init__(
            self,
            value: Any = None,
            size: Optional[int] = -1
    ) -> None:
        self._value = value
        self._size = size

    @property
    def value(self) -> Any:
        return self._value

    @property
    def size(self) -> int:
        return self._size


class CacheInterface(metaclass=ABCMeta):
    @abstractmethod
    def get(self, *args: str) -> Item or None:
        raise NotImplementedError

    @abstractmethod
    def put(self, key: str, value: Any) -> Item or None:
        raise NotImplementedError

    @abstractmethod
    def remove(self, key: str) -> bool:
        raise NotImplementedError


class Cache(CacheInterface):
    def get(self, *args: str) -> Item or None:
        return None

    def put(self, key: str, value: Any) -> Item or None:
        return None

    def remove(self, key: str) -> bool:
        return False
