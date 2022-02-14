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

import os
from concurrent.futures import as_completed, ThreadPoolExecutor
from typing import Any, Dict, List

import requests
from tqdm import tqdm

from odi.client.storage.storage import Storage


class _S3Obj:
    def __init__(self, name: str, path: str, size: int, url: str) -> None:
        self._name = name
        self._path = path
        self._size = size
        self._url = url

    @property
    def name(self) -> str:
        return self._name

    @property
    def path(self) -> str:
        return self._path

    @property
    def size(self) -> int:
        return self._size

    @property
    def url(self) -> str:
        return self._url


class S3(Storage):
    @classmethod
    def _load_obj(cls, data: List[Dict[str, Any]]) -> List[_S3Obj]:
        objs = []
        for d in data:
            objs.append(_S3Obj(name=d["name"], path=d["fullPath"], size=d["size"], url=d["url"]))
        return objs

    def upload(self) -> Any:
        pass

    def download(self, data: List[Dict[str, Any]], path="") -> Any:
        # done, not done
        objs = self._load_obj(data)
        size = self._size_convert(size=sum(o.size for o in objs), origin="b", target="mb")
        total = len(objs)
        print(f"Total: {total}, {size}MB.")
        with tqdm(total=total) as pbar:
            with ThreadPoolExecutor(max_workers=total) as executor:
                futures = [executor.submit(self._download_obj, obj) for obj in objs]
                for future in as_completed(futures):
                    result = future.result()
                    pbar.update(1)

    @classmethod
    def _download_obj(cls, obj: _S3Obj) -> None:
        if not os.path.exists(os.path.dirname(obj.path)):
            os.makedirs(os.path.dirname(obj.path))
        with requests.get(obj.url, stream=True) as r:
            with open(obj.path, "wb") as file:
                for chunk in r.iter_content(chunk_size=1024):
                    file.write(chunk)
