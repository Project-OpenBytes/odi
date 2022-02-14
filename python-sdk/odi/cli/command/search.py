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

import click

from odi.util import ui

from odi.client import ODI


def implement_search(obj: ODI, keyword: str) -> None:
    result = obj.search(keyword)
    if not result:
        click.echo("Try another keyword!")

    titles = [
        "NAME",
        "DESCRIPTION",
        "STAR",
        "DOWNLOAD"
    ]

    datasets = [titles]
    for r in result:
        datasets.append(
            [
                ui.format_long_str_with_ellipsis(r["name"], 32),
                ui.format_long_str_with_ellipsis(r["description"], 64),
                "0" if r["starCount"] is None else str(r["starCount"]),
                "0" if r["downloadCount"] is None else str(r["downloadCount"])
            ]
        )

    col_widths = []
    col_space = 3
    for i in range(0, len(titles)):
        col_widths.append(max(len(row[i]) for row in datasets) + col_space)

    for loc, row in enumerate(datasets):
        text = ""
        for i in range(0, len(row)):
            text += "".join(row[i].ljust(col_widths[i]))
        if loc == 0:
            text = ui.Color.YELLOW.value + \
                   ui.Color.BOLD.value + \
                   text + \
                   ui.Color.END.value
        click.echo(text)
