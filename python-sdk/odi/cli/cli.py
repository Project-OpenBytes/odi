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

from odi import __version__


@click.group(context_settings={"help_option_names": ("-h", "--help")})
@click.pass_context
def cli(ctx: click.Context) -> None:
    pass


@cli.command()
@click.pass_obj
def auth(ctx: str) -> None:
    print("login")


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
    print("get")


@cli.command()
@click.pass_obj
def search(ctx: str) -> None:
    print("search")


@cli.command()
@click.pass_obj
def version(ctx: str) -> None:
    print(__version__)


if __name__ == "__main__":
    cli(prog_name="odi")
