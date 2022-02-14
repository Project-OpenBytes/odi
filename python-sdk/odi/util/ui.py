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

from enum import Enum
from gettext import gettext
from typing import Any, Optional

import click
from tqdm import tqdm

_OPTION_HELP_MESSAGE = "Show help for command and exit"


class CustomFormatter(click.HelpFormatter):
    def __init__(
            self,
            indent_increment: int = 2,
            width: int = 100,
            max_width: Optional[int] = None
    ) -> None:
        super().__init__(indent_increment, width, max_width)

    @staticmethod
    def _bold_style(text) -> str:
        return click.style(gettext(text), blink=True, bold=True)

    def write_heading(self, heading: str) -> None:
        self.write(self._bold_style(f"{'':>{self.current_indent}}{heading}:\n"))

    def write_usage(self, prog: str, args: str = "", prefix: Optional[str] = None) -> None:
        prefix = self._bold_style(prefix) if prefix else self._bold_style("Usage: ")
        super().write_usage(prog, args, prefix)


class Context(click.Context):
    formatter_class = CustomFormatter

    def make_formatter(self) -> CustomFormatter:
        return self.formatter_class(max_width=self.max_content_width)


class Command(click.Command):
    context_class = Context

    def __init__(self, **kwargs: Any) -> None:
        self.manual = kwargs.pop("manual", ())
        super().__init__(**kwargs)

    def get_help_option(self, ctx: Context) -> Optional[click.Option]:
        opt = super().get_help_option(ctx)
        opt.help = _OPTION_HELP_MESSAGE
        return opt

    def format_help(self, ctx: Context, formatter: CustomFormatter) -> None:
        self.format_help_text(ctx, formatter)
        self.format_usage(ctx, formatter)
        self.format_manual(formatter)
        self.format_options(ctx, formatter)
        self.format_epilog(ctx, formatter)

    def format_manual(self, formatter: CustomFormatter) -> None:
        if not self.manual:
            return

        with formatter.section("manual"):
            for example in self.manual:
                formatter.write_text(example)

    def format_help_text(self, ctx: Context, formatter: CustomFormatter) -> None:
        text = self.help or ""

        if self.deprecated:
            text = gettext("(Deprecated) {text}").format(text=text)
        if text:
            formatter.write_text(text)
            formatter.write_text("\n")


class CLIGroup(click.Group):
    context_class = Context

    def get_help_option(self, ctx: Context) -> Optional[click.Option]:
        opt = super().get_help_option(ctx)
        opt.help = _OPTION_HELP_MESSAGE
        return opt

    def format_options(self, ctx: Context, formatter: CustomFormatter) -> None:
        opts = []
        for param in self.get_params(ctx):
            rv = param.get_help_record(ctx)
            if rv is not None:
                opts.append(rv)

        if opts:
            with formatter.section("Options"):
                formatter.write_dl(opts)

    def format_help_text(self, ctx: Context, formatter: CustomFormatter) -> None:
        text = self.help or ""

        if self.deprecated:
            text = gettext("(Deprecated) {text}").format(text=text)
        if text:
            formatter.write_text(text)
            formatter.write_text("\n")

    def format_help(self, ctx: Context, formatter: CustomFormatter) -> None:
        self.format_help_text(ctx, formatter)
        self.format_usage(ctx, formatter)
        self.format_commands(ctx, formatter)
        self.format_options(ctx, formatter)
        self.format_epilog(ctx, formatter)


class Color(Enum):
    BLUE = "\033[94m"
    BOLD = "\033[1m"
    DARKCYAN = "\033[36m"
    END = "\033[0m"
    PINK = "\033[95m"
    RED = "\033[91m"
    UNDERLINE = "\033[4m"
    YELLOW = "\033[93m"


def format_long_str_with_ellipsis(text: str, limit: int) -> str:
    if len(text) <= limit:
        return text

    if len(text) <= 3 or limit <= 3:
        return text

    return f"{text[0: limit - 3]}..."


class Tqdm(tqdm):
    def __init__(
            self,
            *,
            desc: str,
            total: int,
            leave: bool,
            position: int,
            bar_format: str
    ) -> None:
        super().__init__(desc=desc, total=total, leave=leave, position=position, bar_format=bar_format)
