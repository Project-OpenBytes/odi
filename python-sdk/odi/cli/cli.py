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
@click.option(
    "-t", "--token", type=str, default="", help="ODI user oauth token file path"
)
@click.option(
    "-c", "--config", type=str, default="", help="ODI config file path"
)
@click.pass_context
def cli(ctx: ui.Context, token: str, config: str) -> None:
    """ODI command line tool."""

    from odi.cli.command import implement_cli


command = partial(cli.command, cls=ui.Command)


@cli.group(cls=ui.CLIGroup, short_help="Login, logout, show user info")
@click.pass_obj
def auth(ctx: str) -> None:
    """Login, logout, and show current user information."""
    pass


auth_command = partial(auth.command, cls=ui.Command)


@auth_command(short_help="Login to ODI")
@click.pass_obj
def login(ctx: str) -> None:
    """Login to ODI through GitHub."""

    from odi.cli.command import implement_login

    implement_login()


@auth_command(short_help="Logout from ODI")
@click.pass_obj
def logout(ctx: str) -> None:
    """Logout from ODI and remove the local cache."""

    from odi.cli.command import implement_logout

    implement_logout()


@auth_command(short_help="User info")
@click.pass_obj
def info(ctx: str) -> None:
    """Show current user information."""

    from odi.cli.command import implement_info

    implement_info()


@command(short_help="Create empty dataset")
@click.pass_obj
def create(ctx: str) -> None:
    """Create an empty dataset from template."""

    print("create")


@command(short_help="Pull dataset to local")
@click.argument("dataset", type=str)
@click.option(
    "-p", "--path", type=str, default="", help="Path to pull the dataset"
)
@click.pass_obj
def pull(ctx: str, dataset: str, path: str) -> None:
    """Pull a dataset from ODI to local."""

    from odi.cli.command import implement_pull

    implement_pull(dataset, path)


@command(short_help="Push dataset to ODI")
@click.pass_obj
def push(ctx: str) -> None:
    """Push a local dataset to ODI."""

    print("push")


@command(short_help="Search dataset")
@click.argument("keyword", type=str)
@click.pass_obj
def search(ctx: str) -> None:
    """Search a dataset from ODI."""
    print("search")


@command(short_help="CLI version")
def version() -> None:
    """ODI CLI and SDK version."""

    from odi.cli.command import implement_version

    implement_version()


if __name__ == "__main__":
    cli(prog_name="odi")
