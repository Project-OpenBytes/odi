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
    def _size_convert(cls, *, size: int, origin: Optional[str] = "B", target: str) -> int:
        size_map = {
            "B": 1,
            "KB": 2,
            "MB": 3,
            "GB": 4,
            "TB": 5
        }

        origin, target = origin.upper(), target.upper()
        if (origin or target) not in size_map or origin == target:
            return size

        coefficient = size_map[target] - size_map[origin]
        val = 1024 ** abs(coefficient)
        new_size = size / val if coefficient > 0 else size * val

        return math.ceil(new_size)
