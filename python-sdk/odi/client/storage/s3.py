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
