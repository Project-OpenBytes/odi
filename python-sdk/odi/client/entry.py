import os.path
from concurrent.futures import FIRST_EXCEPTION, ThreadPoolExecutor, wait
from warnings import warn

from odi.client.request import Client, Request
from odi.client.storage import S3, S3Object


class ODI:
    def __init__(self):
        self._client = Client()

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
