# AGENTS.md

- Use Maven. The build needs a profile: `-Pm2` for the Maven artifacts, `-Pp2` for the Eclipse/Tycho build, `-Pfatjar` for the shaded Workflow example jar. Java 21 builds, the bundles run on Java 17.
- Consult `README.md` for the plugin layout and the Workflow example setup.
- Document public APIs with Javadoc and use `{@link Symbol}` for cross-references. Explain behavior and non-obvious decisions rather than restating signatures.
- After code changes, run the /fix skill. Resolve failures and repeat until headers, build, checkstyle, and tests pass.
- The e2e suites are not part of /fix: they run the Playwright suites of `glsp-core` against the Workflow server. See `.github/workflows/e2e.yml`.
- `CHANGELOG.md` is generated from the merged PRs before a release. Do not add or bump entries manually.
