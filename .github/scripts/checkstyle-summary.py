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
"""Renders the Checkstyle XML reports of all modules into the GitHub Actions job summary.

Replaces the Jenkins 'recordIssues' step. The Maven build is run with
'-Dcheckstyle.failOnViolation=false' so that the tests still run when a rule is violated;
this script is what turns the collected violations into a failure.
"""

import os
import sys
import xml.etree.ElementTree as ET
from pathlib import Path

# GitHub renders at most 10 error annotations per step; the rest stay in the table
MAX_ANNOTATIONS = 10
# keeps the summary well below GitHub's 1 MiB per-step budget
MAX_ROWS = 100

SEVERITY_ICONS = {"error": "❌", "warning": "⚠️", "info": "ℹ️"}


def collect_violations(root):
    """Parses every module's 'checkstyle-result.xml' into a flat, sorted violation list."""
    violations = []
    for report in sorted(root.glob("**/target/checkstyle-result.xml")):
        for file_element in ET.parse(report).getroot().iter("file"):
            path = Path(file_element.get("name", ""))
            # annotations need a repo-relative path; Checkstyle reports absolute ones
            file = str(path.relative_to(root)) if path.is_absolute() and path.is_relative_to(root) else str(path)
            for error in file_element.iter("error"):
                violations.append(
                    {
                        "file": file,
                        "line": error.get("line", "0"),
                        "column": error.get("column"),
                        "severity": error.get("severity", "error"),
                        "message": error.get("message", ""),
                        "rule": (error.get("source", "").rsplit(".", 1)[-1].removesuffix("Check")),
                    }
                )
    violations.sort(key=lambda v: (v["file"], int(v["line"]), int(v["column"] or 0)))
    return violations


def annotate(violations):
    """Emits inline PR annotations for the first few violations."""
    for violation in violations[:MAX_ANNOTATIONS]:
        level = "error" if violation["severity"] == "error" else "warning"
        location = f"file={violation['file']},line={violation['line']}"
        if violation["column"]:
            location += f",col={violation['column']}"
        print(f"::{level} {location},title=Checkstyle: {violation['rule']}::{violation['message']}")


def escape(text):
    """Keeps Checkstyle messages (which contain '|' and '<') from breaking the table."""
    return " ".join(text.split()).replace("|", "\\|").replace("<", "&lt;").replace(">", "&gt;")


def render(violations):
    lines = ["## Checkstyle", ""]
    if not violations:
        lines += ["✅ No violations."]
        return "\n".join(lines) + "\n"

    by_severity = {}
    for violation in violations:
        by_severity[violation["severity"]] = by_severity.get(violation["severity"], 0) + 1
    counts = " · ".join(
        f"{SEVERITY_ICONS.get(severity, '•')} {count} {severity}" for severity, count in sorted(by_severity.items())
    )
    file_count = len({violation["file"] for violation in violations})
    lines += [f"{counts} — {len(violations)} violations in {file_count} files", ""]
    lines += ["| File | Line | Rule | Message |", "| --- | --- | --- | --- |"]
    for violation in violations[:MAX_ROWS]:
        lines.append(
            f"| `{violation['file']}` | {violation['line']} | {violation['rule']} | {escape(violation['message'])} |"
        )
    if len(violations) > MAX_ROWS:
        lines += ["", f"_… and {len(violations) - MAX_ROWS} more; see the build log for the full list._"]
    return "\n".join(lines) + "\n"


def main():
    root = Path(os.environ.get("GITHUB_WORKSPACE", ".")).resolve()
    violations = collect_violations(root)

    summary = render(violations)
    summary_file = os.environ.get("GITHUB_STEP_SUMMARY")
    if summary_file:
        with open(summary_file, "a", encoding="utf-8") as file:
            file.write(summary)
    else:
        sys.stdout.write(summary)

    annotate(violations)
    errors = [violation for violation in violations if violation["severity"] == "error"]
    if errors:
        print(f"::error::Checkstyle reported {len(errors)} violations")
        return 1
    return 0


if __name__ == "__main__":
    sys.exit(main())
