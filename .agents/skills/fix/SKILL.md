---
name: fix
description: Use after completing any code changes (new features, bug fixes, refactors) before reporting completion
---

Run the auto-fix and validation suite for the Eclipse GLSP Server from the repository root.

`$ARGUMENTS` selects the build profile:

- (empty) — `m2` + `fatjar`, the default
- `m2` — Maven build, checkstyle and tests
- `fatjar` — same as `m2`, plus the shaded Workflow example jar
- `p2` — Eclipse/Tycho build

1. Auto-fix the copyright headers of the files touched by the current change. Independent of the build and fast, so it runs first and its fixes are covered by the build that follows:

```bash
npx @eclipse-glsp/cli checkHeaders . -t changes -f java -e "**/src-gen/**" -a
```

2. Compile, run checkstyle, the tests and the fatjar packaging in one pass:

```bash
mvn clean verify -Pm2 -Pfatjar -B -Dcheckstyle.failOnViolation=false
```

For the `p2` profile run `mvn clean verify -Pp2 -B` instead.

3. Turn the collected checkstyle violations into a verdict. Run this even when the Maven build failed — the reports of the modules that were built are still worth reading:

```bash
.github/scripts/checkstyle-summary.py
```

Then:

- If the Maven build failed, fix the compile errors or failing tests and re-run this skill.
- If the checkstyle summary reported violations, fix them and re-run this skill.
- Otherwise everything is clean (headers are corrected in place, the build and the tests pass, checkstyle has no violations) — report completion.

Notes:

- `-t changes` restricts the header check to the files of the current change. Without it the check runs repository-wide and bumps the copyright end year of untouched files — a release chore, not part of a code change.
- Checkstyle runs in the `validate` phase, so letting it fail the build would skip the tests entirely. `-Dcheckstyle.failOnViolation=false` collects the violations instead and step 3 turns them into the failure — the same split the CI workflow uses.
- The e2e suites are deliberately not part of this skill: they start the Workflow server and drive Playwright suites from a `glsp-core` checkout, which takes minutes. They run in `.github/workflows/e2e.yml`.
