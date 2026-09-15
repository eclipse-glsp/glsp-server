#!/usr/bin/env python3
# ********************************************************************************
# Copyright (c) 2026 EclipseSource and others.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Public License v. 2.0 which is available at
# https://www.eclipse.org/legal/epl-2.0.
#
# This Source Code may also be made available under the following Secondary
# Licenses when the conditions for such availability set forth in the Eclipse
# Public License v. 2.0 are satisfied: GNU General Public License, version 2
# with the GNU Classpath Exception which is available at
# https://www.gnu.org/software/classpath/license.html.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
# ********************************************************************************
"""Renders the Surefire JUnit reports into the GitHub Actions job summary.

Replaces the Jenkins 'junit' publisher. Surefire already fails the build on a failing
test, so this script only reports; it always exits successfully.
"""

import os
import sys
import xml.etree.ElementTree as ET
from pathlib import Path

# GitHub renders at most 10 error annotations per step
MAX_ANNOTATIONS = 10
# a stack trace is far too long for a summary; the build log has the full one
MAX_TRACE_LINES = 12


def collect_suites(root):
    """Parses every module's Surefire reports into per-suite totals plus failure details."""
    suites, failures = [], []
    for report in sorted(root.glob("**/target/surefire-reports/TEST-*.xml")):
        suite = ET.parse(report).getroot()
        suites.append(
            {
                "name": suite.get("name", report.stem),
                "tests": int(suite.get("tests", 0)),
                "failures": int(suite.get("failures", 0)),
                "errors": int(suite.get("errors", 0)),
                "skipped": int(suite.get("skipped", 0)),
                "time": float(suite.get("time", 0) or 0),
            }
        )
        for case in suite.iter("testcase"):
            for result in case:
                if result.tag not in ("failure", "error"):
                    continue
                failures.append(
                    {
                        "name": f"{case.get('classname', '')}.{case.get('name', '')}",
                        "kind": result.tag,
                        "message": (result.get("message") or result.get("type") or "").strip(),
                        "trace": (result.text or "").strip(),
                    }
                )
    suites.sort(key=lambda suite: suite["name"])
    return suites, failures


def annotate(failures):
    """Surfaces the first few failures as annotations on the run."""
    for failure in failures[:MAX_ANNOTATIONS]:
        # annotations are single-line; newlines have to be encoded
        message = failure["message"].replace("\r", "").replace("\n", "%0A")
        print(f"::error title=Test {failure['kind']}: {failure['name']}::{message}")


def escape(text):
    """Collapses whitespace and neutralizes characters that would break the markdown table."""
    return " ".join(text.split()).replace("|", "\\|").replace("<", "&lt;").replace(">", "&gt;")


def render(suites, failures):
    lines = ["## Tests", ""]
    if not suites:
        lines += ["⚠️ No test reports found."]
        return "\n".join(lines) + "\n"

    total = sum(suite["tests"] for suite in suites)
    failed = sum(suite["failures"] for suite in suites)
    errored = sum(suite["errors"] for suite in suites)
    skipped = sum(suite["skipped"] for suite in suites)
    passed = total - failed - errored - skipped
    duration = sum(suite["time"] for suite in suites)

    status = "✅ All tests passed" if not (failed or errored) else "❌ Test failures"
    lines += [
        f"{status} — {passed} passed · {failed} failed · {errored} errors · {skipped} skipped "
        f"({total} tests in {duration:.1f}s)",
        "",
    ]

    lines += ["| Suite | Tests | Failures | Errors | Skipped | Time |", "| --- | ---: | ---: | ---: | ---: | ---: |"]
    for suite in suites:
        icon = "❌ " if suite["failures"] or suite["errors"] else ""
        lines.append(
            f"| {icon}`{suite['name']}` | {suite['tests']} | {suite['failures']} | "
            f"{suite['errors']} | {suite['skipped']} | {suite['time']:.2f}s |"
        )

    if failures:
        lines += ["", "<details><summary>Failure details</summary>", ""]
        for failure in failures:
            trace = "\n".join(failure["trace"].splitlines()[:MAX_TRACE_LINES])
            lines += [f"**{escape(failure['name'])}** — {escape(failure['message'])}", "", "```", trace, "```", ""]
        lines += ["</details>"]
    return "\n".join(lines) + "\n"


def main():
    root = Path(os.environ.get("GITHUB_WORKSPACE", ".")).resolve()
    suites, failures = collect_suites(root)

    summary = render(suites, failures)
    summary_file = os.environ.get("GITHUB_STEP_SUMMARY")
    if summary_file:
        with open(summary_file, "a", encoding="utf-8") as file:
            file.write(summary)
    else:
        sys.stdout.write(summary)

    annotate(failures)
    return 0


if __name__ == "__main__":
    sys.exit(main())
