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
from warnings import warn

from odi.client.request import Client, Request

from odi.client.storage.s3 import S3, S3Object


class ODI:
    def __init__(self):
        self._client = Client()
        self._storage = None

    def pull(self, *, dataset: str, path: str = "") -> bool:
        if path and not os.path.exists(path):
            print("No such path.")
            return False

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

        s3objs = []
        for f in files:
            s3objs.append(S3Object(f["name"], f["fullPath"], f["size"], f["url"]))
        print(f"Pulling {dataset} (Total: {len(s3objs)})...")
        self._download_from_storage(s3objs, path)

    def _download_from_storage(self, files, path: str):
        max_workers = len(files)
        with ThreadPoolExecutor(max_workers) as executor:
            futures = [executor.submit(S3.download_obj, file, pos, path) for pos, file in enumerate(files)]

            done, not_done = wait(futures, return_when=FIRST_EXCEPTION)

    def login_from_github(self) -> bool:
        resp = Client().do(Request.GithubLoginDeviceCode).json()
        try:
            data = resp["data"]
            url = data["verificationUrl"]
            expire = data["expiresIn"]
            interval = data["interval"]
            user_code = data["userCode"]
            device_code = data["deviceCode"]
            print(user_code)
            print(url)
            time.sleep(2)
            print("Plz enter the code...")
        except KeyError:
            raise format_exc()
        else:
            import webbrowser

            webbrowser.open(url)
            print("please enter the code..")

            def get_odi_token(exp, poke, dc) -> str:
                # todo 改成真正计时 end-start
                i = 0
                while i < exp:
                    print("requesting")
                    r = self._client.do(Request.RegisterByGithubDeviceCode, data={"device_code": dc}).json()
                    time.sleep(poke)
                    i += poke
                    print(r)
                    if r["status"] == "SUCCESS":
                        return r["user"]
                return ""

            with ThreadPoolExecutor(10) as executor:
                all_task = [executor.submit(get_odi_token, expire, interval, device_code)]

            done, not_done = wait(all_task, return_when=ALL_COMPLETED)
            for future in done:
                print(future.result())

            print("main ")
        return True


if __name__ == "__main__":
    ODI().login_from_github()
