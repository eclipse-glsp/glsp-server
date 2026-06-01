# CLAUDE.md

## Validation

- After completing any code changes, always run the `/verify` skill before reporting completion

## Commenting Style

- **Javadoc (`/** ... */`) on the public API**: document `public`/`protected` interfaces, classes, methods, and notable fields. Describe intent and behavior, not the obvious signature. Don't document plain getters/setters.
- **Cross-reference with `{@link Symbol}`** instead of writing bare type/method names in prose.
- **Document non-trivial methods** with `@param`/`@return` (and `@throws` where relevant). Skip them for self-explanatory one-liners.
- **Deprecations** use the fixed form `/** @deprecated Use {@link Replacement} instead */` and pair it with the `@Deprecated` annotation.
- **Inline `//` comments explain _why_, not _what_** — keep them short and lowercase, and reserve them for non-obvious decisions or rationale.
- **Mark known limitations** with `// FIXME:` / `// TODO:`, and justify warning suppressions with `@SuppressWarnings("...")` (e.g. `@SuppressWarnings("checkstyle:...")`).
- Don't restate code in comments; let clear naming carry the _what_.
- Copyright headers are required on every file but are handled by `/verify` — don't add them manually.
