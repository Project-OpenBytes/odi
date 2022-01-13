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

from functools import partial

import click

from odi.util import ui


@click.group(cls=ui.CLIGroup, context_settings={"help_option_names": ("-h", "--help")})
@click.pass_context
def cli(ctx: click.Context) -> None:
    pass


command = partial(cli.command, cls=ui.Command)


@cli.command()
@click.pass_obj
def auth(ctx: str) -> None:
    """Login, logout, and get user info"""
    print("auth")


@cli.command()
@click.pass_obj
def init(ctx: str) -> None:
    print("init")


@cli.command()
@click.argument("dataset", type=str)
@click.option(
    "-p", "--path", type=str, default="", help="Path to pull the dataset"
)
@click.pass_obj
def pull(ctx: str, dataset: str, path: str) -> None:
    from odi.cli.command.pull import implement_pull
    implement_pull(dataset, path)


@cli.command()
@click.pass_obj
def push(ctx: str) -> None:
    print("push")


@cli.command()
@click.pass_obj
def search(ctx: str) -> None:
    print("search")


@cli.command()
def version() -> None:
    """ODI version"""

    from odi.cli.command.version import implement_version

    implement_version()


if __name__ == "__main__":
    cli(prog_name="odi")
