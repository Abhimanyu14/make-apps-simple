# Natural Language

1. Prefer American english words over British english words.

   **Context:** Many programming terms use American English, so it’s easier to follow that
   convention.
   e.g. `color`.

**References:**

- For word split ups - [Dictionary](dictionary.md)

---

# Package

1. Prefer multiple words separated by hyphens or underscores over concatenating them into a single
   word.

   e.g. `finance_manager` vs `financemanager`.

   Refer [Dictionary](dictionary.md) for more details.

---

# Kotlin Files

## Braces and Blocks

1. Always use curly braces `{}` for blocks, even for single-line statements.

   **Context:** This improves readability and maintains consistency.
   e.g.
   Use

   ```kotlin
   if (condition) {
     doSomething()
   }
   ```

   instead of

   ```kotlin
   if (condition) doSomething()
   ```

2. Use K&R style (opening brace on the same line).

## Function Body Style

1. Mandatory Block Bodies
   Always use **block bodies** with curly braces `{}` for function declarations.
   **Expression body syntax** (using `=`) is prohibited, even for simple one-line returns.

   **Context:** To ensure code consistency and simplify future logic extensions.
   e.g.
   Use

   ```kotlin
   fun doSomething() {
       // implementation
   }
   ```

   instead of

   ```kotlin
   fun doSomething() = /* implementation */
   ```

2. Lambda Assignments
   This rule does not apply to properties assigned to a lambda expression.
   Since lambdas require curly braces by definition, they should remain as-is.

   e.g.

   ```kotlin
   val doSomething = {
       /* implementation */
   }
   ```

## Line Breaks

1. Brackets
   Always put a new line after the opening brace `{` and before the closing brace `}`.

   **Context:** This improves readability and makes it easier to visually parse code blocks.
   e.g.
   Use

   ```kotlin
   if (condition) {
       doSomething()
   }
   ```

   instead of

   ```kotlin
   if (condition) { doSomething() }
   ```

2. Named parameter
   Place each named parameter on a separate line for every Kotlin method call.

   **Context:** This enhances readability, especially when dealing with functions that have multiple
   parameters.
   e.g.
   Use

   ```kotlin
   someFunction(
       param1 = value1,
       param2 = value2
   )
   ```

   instead of

   ```kotlin
   someFunction(param1 = value1, param2 = value2)
   ```

3. Lambda expressions
   Always use new lines for lambda expressions.

   **Context:** This improves readability and makes it easier to understand the structure of the
   lambda.
   e.g.
   Use

   ```kotlin
   list.filter {
       it.isValid()
   }
   ```

   instead of

   ```kotlin
   list.filter { it.isValid() }
   ```

4. Chained method calls
   Always put chained method calls on separate lines when there are at least 2 method calls chained.

   **Context:** This improves readability, especially when dealing with long chains of method calls.
   e.g.
   Use

   ```kotlin
   someObject
       .methodOne()
       .methodTwo()
       .methodThree()
   ```

   instead of

   ```kotlin
   someObject.methodOne().methodTwo().methodThree()
   ```

5. Function parameters
    - Put parameters in single line for Java method calls if they fit within a line, if not put
      each parameter in a separate line.
    - For every function declaration, always place each parameter in separate line. Do NOT add empty
      lines when there are not parameters.

## Trailing Commas

1. Use trailing commas wherever applicable.

   **Context:** Trailing commas can make version control diffs cleaner and reduce the need for
   additional changes when adding new items to lists or function calls.
   e.g.
   Use

   ```kotlin
   val list = listOf(
       item1,
       item2,
       item3,
   )
   ```

   instead of

   ```kotlin
   val list = listOf(
       item1,
       item2,
       item3
   )
   ```

## Naming Conventions

1. **Classes and Objects:**

    - Use PascalCase for class and object names (e.g., `MainActivity`, `UserRepository`).
    - Use descriptive names that clearly indicate their purpose.

2. **Functions and Variables:**

    - Use camelCase for function and variable names (e.g., `getUserData()`, `userName`).
    - Use descriptive names that explain what the function does or what the variable contains.

3. **Constants:**
    - Use UPPER_SNAKE_CASE for constants (e.g., `MAX_RETRY_COUNT`, `DEFAULT_TIMEOUT`).
4. **Package Names:**
    - Use lowercase with dots as separators (e.g., `com.makeappssimple.abhimanyu.app`).
5. **File Names:**
    - Use PascalCase for file names, matching the primary class name (e.g., `MainActivity.kt`).

## Code Organization

1. **File Structure:**
    1. Package declaration
    2. Import statements (alphabetically ordered)
    3. Class/object declaration
    4. Properties
    5. Constructor
    6. Functions – public first, then internal, followed by private. Order the methods
       alphabetically within each visibility modifier section.
    7. Companion object (if any)
2. **Import Organization:**
    - Group imports: standard library, third-party libraries, project-specific imports.
    - Use explicit imports instead of wildcard imports.
    - Remove unused imports.

## Visibility Modifiers

1. **Default to Private:**
    - Use `private` as the default visibility modifier.
    - Use `internal` for module-level visibility when needed.
    - Use `public` only when the API is intentionally exposed.
2. **Testing:**
    - Use the `@VisibleForTesting` annotation for internal functions that need to be tested.

## Indentation:

1. Use 4 spaces for indentation (not tabs).

## Line Length:

1. Keep lines under 120 characters when possible.
2. Break long lines at logical points.

## Spacing:

1. Use single spaces around operators and after commas.
2. Do not use spaces before colons in type declarations.

## Named Parameters:

1. Always use named parameters when calling pure Kotlin functions except when the parameter has
   vararg.
2. Always put each named argument on a separate line even for a single named argument.
3. Do NOT use named parameters for Java method calls.

## Trailing Lambda:

1. Avoid using trailing lambdas. Prefer using named paramters instead.

## End of files:

1. Files should always end with an empty line.

## Method Ordering

1. **Order of Methods:**
    - Public methods first, followed by internal methods, and then private methods.
    - Within each visibility modifier, order methods alphabetically.

## Function Design

1. **Single Responsibility:**
    - Each function should have a single, clear purpose.
    - Keep functions small and focused.
2. **Parameters:**
    - Use meaningful parameter names.
    - Consider using data classes for multiple related parameters.
3. **Return Types:**
    - Always specify return types for public functions.
    - Use nullable types (`Type?`) when the function can return null.

## Error Handling

1. **Exceptions:**
    - Use specific exception types rather than generic `Exception`.
    - Always document exceptions that can be thrown by any function.
2. **Null Safety:**
    - Prefer non-nullable types when possible.
    - Always use the safe call operator (`?.`) and Elvis operator (`?:`) appropriately.
    - Never use `!!`.
    - Always use safe type casting (`as?`).
    - Never use forced casting (`as`).

## Documentation

1. **KDoc Comments:**
    - Use KDoc for public APIs and complex functions.
    - Include `@param`, `@return`, and `@throws` tags where appropriate.
2. **Inline Comments:**
    - Use comments to explain "why," not "what."
    - Keep comments up to date with code changes.

## Testing

1. **Test Naming:**
    - Name test classes with the suffix `Test` (e.g., `UserRepositoryTest`).
    - Name test functions in the format `functionUnderTest_condition_expectedResult` (e.g.,
      `login_withInvalidCredentials_returnsError`).
    - Use backticks for descriptive test names (e.g., `fun `login fails with invalid credentials``).
2. **Test File Location:**
    - Place unit tests in the corresponding `test/` directory, mirroring the main source structure.
    - Place instrumented tests in the `androidTest/` directory.
3. **Test Structure:**
    - Use the Arrange-Act-Assert (AAA) pattern for organizing test logic.
    - Use `@Before` and `@After` for setup and teardown logic.
4. **Spacing:**
    - Within each test method, separate arrange, act, and assert sections with empty lines.
    - Do not add unnecessary comments in tests to segregate arrange, act and assert sections.
5. **Visibility and Annotations:**
    - Use `@Test` for test methods.
    - Use `@VisibleForTesting` for internal members that need to be accessed in tests.
    - Prefer `internal` or `private` visibility for test helpers within test files.
    - Use `private` or `internal` for test classes.
6. **Mocking and Dependency Injection:**
    - Use dependency injection to provide test doubles (mocks, fakes, stubs).
    - Prefer using fakes over mocks.
    - Use mock libraries for mocking dependencies only when required, such as at the last level of
      application interaction (e.g., database, files, network, etc.).
7. **Best Practices:**
    - Each test should verify a single behavior or outcome.
    - Avoid shared state between tests; use setup methods to initialize state.
    - Use constants for test data to avoid magic values.
    - Keep tests fast and isolated from external dependencies.
    - Prefer using test-specific coroutine dispatchers for coroutine-based code.
8. **Assertions:**
    - Use Kotest for assertions.

---

## Dependency Injection

1. DI methods should always have `provides` as the prefix (not `provide`).

---

# XML Files

## Naming Conventions

1. **File Names:**
    - Use snake_case for file names (e.g., `barcodes_strings.xml`, `barcodes_ic_launcher.xml`).
    - Prefix resource files with the module name to avoid conflicts (e.g., `barcodes_`).

2. **Resource IDs and Names:**
    - Use snake_case for resource IDs and names (e.g., `barcodes_app_name`,
      `barcodes_launcher_background`).
    - Prefix string, color, and dimen resources with the module name.
    - Use PascalCase for style names (e.g., `BarcodesTheme`).

## Formatting

1. **Indentation:**
    - Use 4 spaces for indentation.

2. **Attributes:**
    - Place attributes on separate lines for complex elements (e.g., `<vector>`,
      `<path>`) or when there are multiple attributes.
    - Indent attributes by 4 spaces relative to the element tag.
    - Place the closing tag `/>` on a new line for multi-line elements.
    - Single-line elements are acceptable for simple resources (e.g., `<string>`, `<color>`).

## Organization

1. **Region Comments:**
    - Use `<!-- region section_name -->` and
      `<!-- endregion -->` to group related resources in large files (e.g., `strings.xml`).

2. **License Header:**
    - Include the project's copyright license header at the top of every XML file.

3. **Ordering:**
    - Group resources logically (e.g., by screen or feature).
    - Sort resources alphabetically within groups where applicable.

---

# TOML Files

## Naming Conventions

1. **Section Names:**
    - Use `[versions]`, `[libraries]`, `[plugins]`, and `[bundles]` as top-level sections.
2. **Version Keys:**
    - Use lowercase, hyphen-separated keys (e.g., `compile-sdk`, `kotlinx-coroutines-swing`).
    - For app versions, use the format: `app-<module>-version-code` and `app-<module>-version-name`.
    - For plugin versions, use the format: `plugin-<plugin-name>`.
3. **Library Keys:**
    - Use the format `<group>-<artifact>` (e.g., `androidx-activity-compose`,
      `kotlinx-coroutines-core`).
    - For bundles, use a single word representing the group (e.g., `camera`, `compose`).
    - For test libraries, prefix with `test-` (e.g., `test-ext-junit`).
4. **Reference Keys:**
    - Use `module` for specifying the full Maven coordinate.
    - Use `version.ref` for referencing version keys (e.g., `version.ref = "kotlinx-coroutines"`).

## Ordering Conventions

1. **Section Order:**
    - `[versions]` (app, SDK, library, and plugin versions)
    - `[libraries]` (dependencies)
    - `[plugins]` (plugin definitions)
    - `[bundles]` (dependency bundles)
2. **Within Sections:**

    - Alphabetize keys within each section for readability and consistency.
    - Group related items (e.g., all `androidx-` libraries together, all `koin-` libraries
      together).
    - For bundles, list dependencies in logical or usage order (e.g., camera-related libraries
      together).

   **Example:**

   ```
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
