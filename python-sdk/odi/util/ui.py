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

from typing import Any, Optional

import click


class HelpFormatter(click.HelpFormatter):
    def __init__(
            self,
            indent_increment: Optional[int] = 2,
            width: Optional[int] = 100,
            max_width: Optional[int] = None
    ) -> None:
        super().__init__(indent_increment, width, max_width)

    @staticmethod
    def _bold_style(text) -> str:
        return click.style(text, blink=True, bold=True)
    # from gettext import gettext as _


class Command(click.Command):
    def __init__(self, **kwargs: Any) -> None:
        self.manual = kwargs.pop("manual", ())
        super().__init__(**kwargs)

    def format_help(self, ctx: click.Context, formatter: HelpFormatter) -> None:
        self.format_usage(ctx, formatter)
        self.format_help_text(ctx, formatter)
        self.format_manual(formatter)
        self.format_options(ctx, formatter)
        self.format_epilog(ctx, formatter)

    def format_manual(self, formatter: HelpFormatter) -> None:
        if not self.manual:
            return

        with formatter.section("manual"):
            for example in self.manual:
                formatter.write_text(example)


class CLIGroup(click.Group):
    def format_options(self, ctx: click.Context, formatter: HelpFormatter) -> None:
        opts = []
        for param in self.get_params(ctx):
            rv = param.get_help_record(ctx)
            if rv is not None:
                opts.append(rv)

        if opts:
            with formatter.section("Options"):
                formatter.write_dl(opts)

    def format_help(self, ctx: click.Context, formatter: HelpFormatter) -> None:
        self.format_usage(ctx, formatter)
        self.format_help_text(ctx, formatter)
        self.format_commands(ctx, formatter)
        self.format_options(ctx, formatter)
        self.format_epilog(ctx, formatter)
