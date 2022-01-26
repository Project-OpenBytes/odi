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
from typing import Any, Dict, List, Optional
from warnings import warn

from odi.client.auth import GithubAuth
from odi.client.request import Client, Request
from odi.client.storage import S3


class ODI:
    def __init__(
            self,
            user_token: Optional[str] = None,
            user_token_file: Optional[str] = None,
            config: Optional[Dict[str, Any]] = None,
            config_file: Optional[str] = None
    ) -> None:
        self._client = Client()
        self._storage = None
        self._user = None

    def init(self) -> Any:
        pass

    def user_info(self) -> Any:
        pass

    def submit(self) -> Any:
        pass

    def pull(self, *, dataset: str, path: Optional[str] = "") -> bool:
        if path and not os.path.exists(path):
            print(f"Failed to {self.pull.__name__} dataset '{dataset}': No such directory: '{path}'.")
            return False

        return self._download_from_s3(dataset, path)

    def _download_from_s3(self, dataset: str, path: str) -> bool:
        resp = self._client.do(Request.PullDatasetByName, dataset=dataset).json()
        if not resp["success"] or resp["httpCode"] != 200:
            print(resp["message"])
            return False

        data = resp["data"]
        files = data["files"]
        if not files:
            print(f"Cannot find the dataset [{dataset}].")
            return False

        if data["truncated"]:
            warn(f"Dataset [{dataset}] is truncated.")

        self._storage = S3()
        self._storage.download(files)

    def search(
            self,
            keyword: str,
            index: Optional[int] = 1,
            size: Optional[int] = 128
    ) -> Optional[List[Dict[str, Any]]]:
        if not keyword or index < 1 or size < 1:
            return None
        res = self._client.do(Request.ListDataset, keyword=keyword, index=1, size=size).json()
        return res["data"]["records"]

    def login(self) -> bool:
        return GithubAuth(self._client).auth()
