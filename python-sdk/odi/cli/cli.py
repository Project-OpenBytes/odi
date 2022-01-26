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

from odi.client import ODI
from odi.util import ui


@click.group(cls=ui.CLIGroup, context_settings={"help_option_names": ("-h", "--help")})
@click.option(
    "-t", "--token", type=str, is_flag=True, default="", help="Specify ODI user token file"
)
@click.option(
    "-c", "--config", type=str, is_flag=True, default="", help="Specify ODI config file"
)
@click.pass_context
def cli(ctx: ui.Context, token: str, config: str) -> None:
    """ODI command line tool."""

    from odi.cli.command import implement_cli

    implement_cli(ctx, token, config)


command = partial(cli.command, cls=ui.Command)


@cli.group(cls=ui.CLIGroup, short_help="Login, logout, show user info")
@click.pass_obj
def auth(obj: ODI) -> None:
    """Login, logout, and show current user information."""
    pass


auth_command = partial(auth.command, cls=ui.Command)


@auth_command(short_help="Login to ODI")
@click.pass_obj
def login(obj: ODI) -> None:
    """Login to ODI through GitHub."""

    from odi.cli.command import implement_login

    implement_login()


@auth_command(short_help="Logout from ODI")
@click.pass_obj
def logout(odj: ODI) -> None:
    """Logout from ODI and remove the local cache."""

    from odi.cli.command import implement_logout

    implement_logout()


@auth_command(short_help="User info")
@click.pass_obj
def info(obj: ODI) -> None:
    """Show current user information."""

    from odi.cli.command import implement_info

    implement_info()


@command(short_help="Initialize empty dataset")
@click.pass_obj
def init(obj: ODI) -> None:
    """Initialize an empty dataset from template."""

    print("init")


@command(short_help="Pull dataset to local")
@click.argument("dataset", type=str)
@click.option(
    "-p", "--path", type=str, default="", help="Path to pull the dataset"
)
@click.pass_obj
def pull(obj: ODI, dataset: str, path: str) -> None:
    """Pull a dataset from ODI to local."""

    from odi.cli.command import implement_pull

    implement_pull(dataset, path)


@command(short_help="Submit dataset to ODI")
@click.pass_obj
def submit(obj: ODI) -> None:
    """Push a local dataset to ODI and submit a new pull request."""

    print("submit")


@command(short_help="Search dataset")
@click.argument("keyword", type=str)
@click.pass_obj
def search(obj: ODI, keyword: str) -> None:
    """Search a dataset from ODI."""

    from odi.cli.command import implement_search

    implement_search(obj, keyword)


@command(short_help="CLI version")
def version() -> None:
    """ODI CLI and SDK version."""

    from odi.cli.command import implement_version

    implement_version()


if __name__ == "__main__":
    cli(prog_name="odi")
