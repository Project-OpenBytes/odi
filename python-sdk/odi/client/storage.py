import os

import requests
from tqdm import tqdm


class S3Bucket:
    def __init__(self, name: str) -> None:
        self.name = name


class S3Object:
    def __init__(self, name: str, path: str, size: int, url: str) -> None:
        self.name = name
        self.path = path
        self.size = size
        self.url = url


class S3:
    def __init__(self) -> None:
        pass

    @staticmethod
    def download_obj(obj: S3Object, position: int, path: str = "./"):
        block_size = 1024
        response = requests.get(obj.url, stream=True)
        with tqdm(
                desc=obj.path,
                total=obj.size,
                unit="iB",
                unit_scale=True,
                leave=None,
                position=position
        ) as bar:
            file_path = os.path.join(path, obj.path)
            if not os.path.exists(os.path.dirname(file_path)):
                os.makedirs(os.path.dirname(file_path))
            with open(file_path, "wb") as file:
                for data in response.iter_content(block_size):
                    bar.update(len(data))
                    file.write(data)
