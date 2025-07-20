# Kotlin Files

## Naming Conventions

- **Classes and Objects:**
  - Use PascalCase for class and object names (e.g., `MainActivity`, `UserRepository`).
  - Use descriptive names that clearly indicate the purpose.
- **Functions and Variables:**
  - Use camelCase for function and variable names (e.g., `getUserData()`, `userName`).
  - Use descriptive names that explain what the function does or what the variable contains.
- **Constants:**
  - Use UPPER_SNAKE_CASE for constants (e.g., `MAX_RETRY_COUNT`, `DEFAULT_TIMEOUT`).
- **Package Names:**
  - Use lowercase with dots as separators (e.g., `com.makeappssimple.abhimanyu.app`).
- **File Names:**
  - Use PascalCase for file names, matching the primary class name (e.g., `MainActivity.kt`).

## Code Organization

- **File Structure:**
  1. Package declaration
  2. Import statements (alphabetically ordered)
  3. Class/object declaration
  4. Properties
  5. Constructor
  6. Functions - public first, then internal followed by private.
  7. Companion object (if any)
- **Import Organization:**
  - Group imports: standard library, third-party libraries, project-specific imports.
  - Use explicit imports instead of wildcard imports.
  - Remove unused imports.

## Visibility Modifiers

- **Default to Private:**
  - Use `private` as the default visibility modifier.
  - Use `internal` for module-level visibility when needed.
  - Use `public` only when the API is intentionally exposed.
- **Testing:**
  - Use `@VisibleForTesting` annotation for internal functions that need to be tested.

## Code Style

- **Indentation:**
  - Use 4 spaces for indentation (not tabs).
- **Line Length:**
  - Keep lines under 120 characters when possible.
  - Break long lines at logical points.
- **Spacing:**
  - Use single spaces around operators and after commas.
  - No spaces before colons in type declarations.
- **Braces:**
  - Use K&R style (opening brace on the same line).
  - Always use braces for control structures, even single-line blocks.
- **Named Parameters:**
  - Always use named parameters when calling pure Kotlin functions.
  - Always put each named argument in separate lines.
- **Trailing commas:**
  - Use trailing commas wherever applicable.

## Function Design

- **Single Responsibility:**
  - Each function should have a single, clear purpose.
  - Keep functions small and focused.
- **Parameters:**
  - Use meaningful parameter names.
  - Consider using data classes for multiple related parameters.
- **Return Types:**
  - Always specify return types for public functions.
  - Use nullable types (`Type?`) when the function can return null.

## Error Handling

- **Exceptions:**
  - Use specific exception types rather than generic `Exception`.
  - Always document exceptions that can be thrown by any function.
- **Null Safety:**
  - Prefer non-nullable types when possible.
  - Always use safe call operator (`?.`) and Elvis operator (`?:`) appropriately.
  - Never use `!!`.
  - Always use safe type casting `as?`.
  - Never use forced casting `as`.

## Documentation

- **KDoc Comments:**
  - Use KDoc for public APIs and complex functions.
  - Include `@param`, `@return`, and `@throws` tags where appropriate.
- **Inline Comments:**
  - Use comments to explain "why" not "what".
  - Keep comments up to date with code changes.

---

# TOML Files

## Naming Conventions

- **Section Names:**
  - Use `[versions]`, `[libraries]`, `[plugins]`, and `[bundles]` as top-level sections.
- **Version Keys:**
  - Use lowercase, hyphen-separated keys (e.g., `compile-sdk`, `kotlinx-coroutines-swing`).
  - For app versions, use the format: `app-<module>-version-code` and `app-<module>-version-name`.
  - For plugin versions, use the format: `plugin-<plugin-name>`.
- **Library Keys:**
  - Use the format `<group>-<artifact>` (e.g., `androidx-activity-compose`, `kotlinx-coroutines-core`).
  - For bundles, use a single word representing the group (e.g., `camera`, `compose`).
  - For test libraries, prefix with `test-` (e.g., `test-ext-junit`).
- **Reference Keys:**
  - Use `module` for specifying the full Maven coordinate.
  - Use `version.ref` for referencing version keys (e.g., `version.ref = "kotlinx-coroutines"`).

## Ordering Conventions

- **Section Order:**
  1. `[versions]` (app, SDK, library, and plugin versions)
  2. `[libraries]` (dependencies)
  3. `[plugins]` (plugin definitions)
  4. `[bundles]` (dependency bundles)
- **Within Sections:**
  - Alphabetize keys within each section for readability and consistency.
  - Group related items (e.g., all `androidx-` libraries together, all `koin-` libraries together).
  - For bundles, list dependencies in logical or usage order (e.g., camera-related libraries together).

## Example

```toml
[versions]
kotlinx-coroutines = "1.10.2"
kotlin = "2.1.21"

[libraries]
kotlinx-coroutines-core = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-core", version.ref = "kotlinx-coroutines" }
kotlin = { module = "org.jetbrains.kotlin:kotlin", version.ref = "kotlin" }

[plugins]
plugin-kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "plugin-kotlin" }

[bundles]
compose = [
    "androidx-activity-compose",
    "androidx-compose-foundation",
    "androidx-compose-runtime",
    "androidx-compose-material3",
]
```
