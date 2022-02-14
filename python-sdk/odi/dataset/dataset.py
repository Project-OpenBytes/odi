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

import re


class Dataset:
    @classmethod
    def is_valid_dataset_name(cls, dataset: str) -> bool:
        """
        Check if a dataset name is valid.

        Name rule:
            1. start with alphabet
            2. only contains alphabets, numbers, _ and -
            3. 2 <= length <= 18

        :param dataset: dataset name
        :return: `True` if it is valid, `False` otherwise
        """
        res = re.search(r"^[A-Za-z][A-Za-z0-9-_]{1,17}$", dataset)
        return True if res else False
