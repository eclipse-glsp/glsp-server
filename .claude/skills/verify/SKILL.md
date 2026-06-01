---
name: verify
description: Build, checkstyle, and test the project. Accepts an optional profile argument (m2, p2, fatjar). Defaults to m2 + fatjar. IMPORTANT - Proactively invoke this skill after completing any code changes (new features, bug fixes, refactors) before reporting completion to the user.
---

# Verify

Run build verification for the Eclipse GLSP server project.

## Arguments

`$ARGUMENTS` can be one of:
- `m2` — plain Maven build + checkstyle + tests
- `p2` — Eclipse P2/Tycho build
- `fatjar` — Maven build + fatjar packaging (example workflow)
- (empty) — defaults to fatjar

## Steps

1. **Determine profile** from `$ARGUMENTS`. If empty, use fatjar.

2. **For m2 profile**, run in sequence:
   ```bash
   mvn clean verify -Pm2 -Pfatjar -B 
   ```
   This runs compile, checkstyle, tests and building of the fatjar together.

3. **For p2 profile**, run:
   ```bash
   mvn clean verify -Pp2 -B
   ```

4. **For copyright header check**, run:
   ```bash
   npx @eclipse-glsp/cli checkHeaders . -f java -e "**/src-gen/**" -a
   ```
   This checks that all Java source files have the correct copyright header, excluding generated sources.
   Any missing or incorrect headers will be fixed automatically.
   
5. **Report results**: summarize pass/fail for each phase (compile, checkstyle, tests). If any step fails, show the relevant error output.
