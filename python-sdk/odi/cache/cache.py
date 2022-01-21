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

"""Base cache definition."""

from abc import ABCMeta, abstractmethod
from typing import Any, Iterable, Optional


class Item:
    """Item for cache."""

    def __init__(
            self,
            value: Any = None,
            size: Optional[int] = -1
    ) -> None:
        self._value = value
        self._size = size

    @property
    def value(self) -> Any:
        """`Item` value."""
        return self._value

    @property
    def size(self) -> int:
        """`Item` size."""
        return self._size


class _CacheInterface(metaclass=ABCMeta):
    @abstractmethod
    def get(self, *args: str) -> Optional[Iterable[Item]]:
        """
        Get a number of cache items with cache keys.

        :param args: a number of cache keys
        :return: a list of `Item`, append `Item` if found a cache, append `None` otherwise
        """
        raise NotImplementedError

    @abstractmethod
    def put(self, key: str, value: Any) -> Optional[Item]:
        """
        Set a new cache with key and value.

        :param key: cache key
        :param value: cache value
        :return: an `Item` if set succeeded, `None` otherwise
        """
        raise NotImplementedError

    @abstractmethod
    def remove(self, key: str) -> bool:
        """
        Remove a cache.

        :param key: key of cache to be removed
        :return: `True` if remove succeeded, `False` otherwise
        """
        raise NotImplementedError


class Cache(_CacheInterface):
    """Base cache."""

    def get(self, *args: str) -> Optional[Iterable[Item]]:
        return None

    def put(self, key: str, value: Any) -> Optional[Item]:
        return None

    def remove(self, key: str) -> bool:
        return False
