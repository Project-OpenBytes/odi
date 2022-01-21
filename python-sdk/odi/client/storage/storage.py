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
from typing import Any


class _StorageInterface(metaclass=ABCMeta):
    @abstractmethod
    def upload(self) -> Any:
        raise NotImplementedError

    @abstractmethod
    def download(self) -> Any:
        raise NotImplementedError


class Storage(_StorageInterface):
    def upload(self) -> Any:
        return None

    def download(self) -> Any:
        return None
