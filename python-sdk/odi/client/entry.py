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
import time
from concurrent.futures import ALL_COMPLETED, FIRST_EXCEPTION, ThreadPoolExecutor, wait
from traceback import format_exc
from typing import Any, Dict, Optional
from warnings import warn

from odi.client.request import Client, Request
from odi.client.auth.auth import GithubAuth


# from odi.client.storage.s3 import S3, S3Object


class ODI:
    def __init__(
            self,
            oauth_token: Optional[str] = None,
            oauth_token_file: Optional[str] = None,
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

    def push(self) -> Any:
        pass

    # def pull(self, *, dataset: str, path: str = "") -> bool:
    #     if path and not os.path.exists(path):
    #         print(f"Failed to {self.pull.__name__} dataset '{dataset}': "
    #               f"No such path or directory: '{path}'.")
    #         return False
    #
    #     resp = self._client.do(Request.PullDatasetByName, dataset=dataset).json()
    #     if not resp["success"] or resp["httpCode"] != 200:
    #         print(resp["message"])
    #         return False
    #
    #     data = resp["data"]
    #     files = data["files"]
    #     if not files:
    #         print(f"Cannot find the dataset [{dataset}].")
    #         return False
    #
    #     if data["truncated"]:
    #         warn(f"Dataset [{dataset}] is truncated.")
    #
    #     s3objs = []
    #     for f in files:
    #         s3objs.append(S3Object(f["name"], f["fullPath"], f["size"], f["url"]))
    #     print(f"Pulling {dataset} (Total: {len(s3objs)})...")
    #     self._download_from_storage(s3objs, path)
    #
    # def _download_from_storage(self, files, path: str):
    #     max_workers = len(files)
    #     with ThreadPoolExecutor(max_workers) as executor:
    #         futures = [executor.submit(S3.download_obj, file, pos, path) for pos, file in enumerate(files)]
    #
    #         done, not_done = wait(futures, return_when=FIRST_EXCEPTION)

    def search(self, dataset: str, tag: Optional[str] = None) -> Any:
        pass

    def login(self) -> bool:
        return GithubAuth(self._client).auth()


if __name__ == "__main__":
    ODI().login()
