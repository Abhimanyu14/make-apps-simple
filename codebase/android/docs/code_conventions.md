# Naming

1. Prefer American english words over British english words.

   **Context:** Many programming terms use American English, so it’s easier to follow that convention.
   e.g. `color`.

---

# Package

1. Prefer multiple words separated by hyphens or underscores over concatenating them into a single word.

   e.g. `finance_manager` vs `financemanager`.

Refer `dictionary.md` for more details.

---

# Dependency Injection

1. DI methods should always have `provides` as the prefix (not `provide`).

---

# Kotlin Files

## Naming Conventions

- **Classes and Objects:**
  - Use PascalCase for class and object names (e.g., `MainActivity`, `UserRepository`).
  - Use descriptive names that clearly indicate their purpose.
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
  6. Functions – public first, then internal, followed by private. Order the methods alphabetically within each visibility modifier section.
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
  - Use the `@VisibleForTesting` annotation for internal functions that need to be tested.

## Code Style

- **Indentation:**
  - Use 4 spaces for indentation (not tabs).
- **Line Length:**
  - Keep lines under 120 characters when possible.
  - Break long lines at logical points.
- **Spacing:**
  - Use single spaces around operators and after commas.
  - Do not use spaces before colons in type declarations.
- **Line Separator:**
  - Put each named parameter in a separate line for every kotlin method calls.
  - Put parameters in single line for Java method calls if they file within a line, if not put each parameter in a separate line.
  - For every function declaration, always place each parameter in separate line. Do NOT add empty lines when there are not parameters.
- **Functions:**
- Do NOT use function expressions.
- **Braces:**
  - Use K&R style (opening brace on the same line).
  - Always use braces for control structures, even for single-line blocks.
- **Named Parameters:**
  - Always use named parameters when calling pure Kotlin functions except when the parameter has vararg.
  - Always put each named argument on a separate line even for a single named argument.
  - Do not use named parameters for Java method calls.
- **Trailing Commas:**
  - Use trailing commas wherever applicable.
- **End of files:**
  - Files should always end with an empty line.

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
  - Always use the safe call operator (`?.`) and Elvis operator (`?:`) appropriately.
  - Never use `!!`.
  - Always use safe type casting (`as?`).
  - Never use forced casting (`as`).

## Documentation

- **KDoc Comments:**
  - Use KDoc for public APIs and complex functions.
  - Include `@param`, `@return`, and `@throws` tags where appropriate.
- **Inline Comments:**
  - Use comments to explain "why," not "what."
  - Keep comments up to date with code changes.

## Testing

- **Test Naming:**
  - Name test classes with the suffix `Test` (e.g., `UserRepositoryTest`).
  - Name test functions in the format `functionUnderTest_condition_expectedResult` (e.g., `login_withInvalidCredentials_returnsError`).
  - Use backticks for descriptive test names (e.g., `fun `login fails with invalid credentials``).
- **Test File Location:**
  - Place unit tests in the corresponding `test/` directory, mirroring the main source structure.
  - Place instrumented tests in the `androidTest/` directory.
- **Test Structure:**
  - Use the Arrange-Act-Assert (AAA) pattern for organizing test logic.
  - Use `@Before` and `@After` for setup and teardown logic.
- **Spacing:**
  - Within each test method, separate arrange, act, and assert sections with empty lines.
  - Do not add unnecessary comments in tests to segregate arrange, act and assert sections.
- **Visibility and Annotations:**
  - Use `@Test` for test methods.
  - Use `@VisibleForTesting` for internal members that need to be accessed in tests.
  - Prefer `internal` or `private` visibility for test helpers within test files.
  - Use `private` or `internal` for test classes.
- **Mocking and Dependency Injection:**
  - Use dependency injection to provide test doubles (mocks, fakes, stubs).
  - Prefer using fakes over mocks.
  - Use mock libraries for mocking dependencies only when required, such as at the last level of application interaction (e.g., database, files, network, etc.).
- **Best Practices:**
  - Each test should verify a single behavior or outcome.
  - Avoid shared state between tests; use setup methods to initialize state.
  - Use constants for test data to avoid magic values.
  - Keep tests fast and isolated from external dependencies.
  - Prefer using test-specific coroutine dispatchers for coroutine-based code.
- **Assertions:**
  - Use expressive assertion libraries (e.g., Truth, AssertJ, JUnit assertions).
  - Prefer `assertThat` over `assertEquals` for readability.
  - Always use `com.google.common.truth.Truth.assertThat` for assertions.

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
