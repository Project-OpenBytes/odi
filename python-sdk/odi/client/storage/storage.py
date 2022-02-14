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

import math
from abc import ABCMeta, abstractmethod
from typing import Any, Optional

_SIZE_MAP = {
    "B": 1,
    "KB": 2,
    "MB": 3,
    "GB": 4,
    "TB": 5
}


class _StorageInterface(metaclass=ABCMeta):
    @abstractmethod
    def upload(self) -> Any:
        raise NotImplementedError

    @abstractmethod
    def download(self, **kwargs) -> Any:
        raise NotImplementedError


class Storage(_StorageInterface):
    def upload(self) -> Any:
        return None

    def download(self, **kwargs) -> Any:
        return None

    @classmethod
    def _size_convert(cls, *, size: int, origin: str = "B", target: str = "MB") -> float:
        origin, target = origin.upper(), target.upper()
        if (origin or target) not in _SIZE_MAP or origin == target:
            return size

        coefficient = _SIZE_MAP[target] - _SIZE_MAP[origin]
        val = 1024 ** coefficient

        return size / val
