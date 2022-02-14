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

# pylint: disable=arguments-differ
# pylint: disable=invalid-name
# pylint: disable=fixme
# pylint: disable=unspecified-encoding

"""File cache."""

import os
import pathlib
from typing import Any, Iterable, Optional

from odi.cache.cache import _CacheInterface, Item


class FileCache(_CacheInterface):
    """`FileCache` provides functions for get, rewrite file content and remove file."""

    _ODI_FILE_PREFIX = os.path.join(pathlib.Path.home(), ".config", "odi")

    def get(self, *args: str) -> Optional[Iterable[Item]]:
        """
        Get multi files content from a number of file paths and save the content to `Item` object.

        :param args: a number of file paths
        :return: a list of `Item`, append `Item` if read file successfully, append `None` otherwise
        """

        res = []
        for file in args:
            try:
                with open(file, "r", encoding="utf-8") as f:
                    # todo: fix out of memory
                    data = f.read()
                    res.append(Item(value=data))
            except OSError as e:
                print(f"Failed to {self.get.__name__} {__class__.__name__} '{file}': {format(e)}")
                res.append(None)

        return res

    def put(self, key: str, value: Any, mode: str = "w") -> Optional[Item]:
        try:
            dirname = os.path.dirname(key)
            if not os.path.exists(dirname) and len(dirname) > 0:
                os.makedirs(dirname)
            with open(key, mode) as f:
                # todo: fix out of memory
                data = f.write(str(value))
                return Item(value=data)
        except OSError as e:
            print(f"Failed to {self.put.__name__} {__class__.__name__}: {format(e)}")
            return None

    def remove(self, key: str) -> bool:
        try:
            os.remove(key)
            return True
        except OSError as e:
            print(f"Failed to {self.remove.__name__} {__class__.__name__}: {format(e)}")
            return False

    def get_odi_file_prefix(self) -> str:
        return self._ODI_FILE_PREFIX
