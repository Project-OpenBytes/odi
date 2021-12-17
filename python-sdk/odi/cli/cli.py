import click

from odi import __version__


@click.group(context_settings={"help_option_names": ("-h", "--help")})
@click.pass_context
def cli(ctx: click.Context) -> None:
    pass


@cli.command()
@click.pass_obj
def download(ctx: str) -> None:
    print("download")


@cli.command()
@click.pass_obj
def get(ctx: str) -> None:
    print("get")


@cli.command()
@click.pass_obj
def init(ctx: str) -> None:
    print("init")


@cli.command()
@click.pass_obj
def login(ctx: str) -> None:
    print("login")


@cli.command()
@click.pass_obj
def search(ctx: str) -> None:
    print("search")


@cli.command()
@click.pass_obj
def upload(ctx: str) -> None:
    print("upload")


@cli.command()
@click.pass_obj
def version(ctx: str) -> None:
    print(__version__)


@cli.command()
@click.pass_obj
def wai(ctx: str) -> None:
    print("who am i")


if __name__ == "__main__":
    cli()
