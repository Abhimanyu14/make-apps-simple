---
name: code_conventions_kotlin
description: Kotlin coding conventions for the project.
---

# Kotlin Code Conventions

## Package Naming

1. Prefer multiple words separated by hyphens or underscores over concatenating them into a single word.
   e.g. `finance_manager` vs `financemanager`.
   Refer [Dictionary](../dictionary/SKILL.md) for more details.

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

3. **When statements**: Each statement in a when should be within a block using curly braces.
   **Context:** This improves readability and maintains consistency.
   e.g.
   Use
   ```kotlin
   when (value) {
       1 -> {
           doSomething()
       }
       else -> {
           doSomethingElse()
       }
   }
   ```
   instead of
   ```kotlin
   when (value) {
       1 -> doSomething()
       else -> doSomethingElse()
   }
   ```

## Function Body Style

1. **Mandatory Block Bodies**: Always use **block bodies** with curly braces
   `{}` for function declarations. **Expression body syntax** (using
   `=`) is prohibited, even for simple one-line returns.
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

2. **Lambda Assignments
   **: This rule does not apply to properties assigned to a lambda expression. Since lambdas require curly braces by definition, they should remain as-is.
   e.g.
   ```kotlin
   val doSomething = {
       /* implementation */
   }
   ```

## Line Breaks

1. **Brackets**: Always put a new line after the opening brace `{` and before the closing brace `}`.
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

2. **Named parameter**: Place each named parameter on a separate line for every Kotlin method call.
   **Context:
   ** This enhances readability, especially when dealing with functions that have multiple parameters.
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

3. **Lambda expressions**: Always use new lines for lambda expressions.
   **Context:
   ** This improves readability and makes it easier to understand the structure of the lambda.
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

4. **Chained method calls
   **: Always put chained method calls on separate lines when there are at least 2 method calls chained.
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

5. **Function parameters**:
    - Put parameters in single line for Java method calls if they fit within a line, if not put each parameter in a separate line.
    - For every function declaration, always place each parameter in separate line. Do NOT add empty lines when there are not parameters.

## Trailing Commas

1. Use trailing commas wherever applicable.
   **Context:
   ** Trailing commas can make version control diffs cleaner and reduce the need for additional changes when adding new items to lists or function calls.
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
    6. Functions – public first, then internal, followed by private. Order the methods alphabetically within each visibility modifier section.
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

## Formatting

1. **Indentation:** Use 4 spaces for indentation (not tabs).
2. **Line Length:
   ** Keep lines under 120 characters when possible. Break long lines at logical points.
3. **Spacing:
   ** Use single spaces around operators and after commas. Do not use spaces before colons in type declarations.

## Parameters and Returns

1. **Named Parameters:**
    - Always use named parameters when calling pure Kotlin functions except when the parameter has vararg.
    - Always put each named argument on a separate line even for a single named argument.
    - Do NOT use named parameters for Java method calls.

2. **Trailing Lambda:** Avoid using trailing lambdas. Prefer using named parameters instead.

3. **End of files:** All files should always end with exactly a single empty line.

## Method Ordering

1. **Order of Methods:**
    - Public methods first, followed by internal methods, and then private methods.
    - Within each visibility modifier, order methods alphabetically.

## Function Design

1. **Single Responsibility:
   ** Each function should have a single, clear purpose. Keep functions small and focused.
2. **Parameters:
   ** Use meaningful parameter names. Consider using data classes for multiple related parameters.
3. **Return Types:** Always specify return types for public functions. Use nullable types (
   `Type?`) when the function can return null.

## Error Handling

1. **Exceptions:** Use specific exception types rather than generic
   `Exception`. Always document exceptions that can be thrown by any function.
2. **Null Safety:** Prefer non-nullable types when possible. Always use safe call (`?.`) and Elvis (
   `?:`) operators appropriately. Never use `!!`. Always use safe type casting (
   `as?`). Never use forced casting (`as`).

## Documentation

1. **KDoc Comments:** Use KDoc for public APIs and complex functions. Include `@param`,
   `@return`, and `@throws` tags where appropriate.
2. **Inline Comments:** Use comments to explain "why," not "what." Keep comments up to date.

## Testing

1. **Test Naming:** Suffix with `Test`. Name functions as
   `functionUnderTest_condition_expectedResult` or use backticks for descriptive names.
2. **Test File Location:** Place unit tests in the corresponding
   `test/` directory, mirroring the source structure. Place instrumented tests in `androidTest/`.
3. **Test Structure:** Use the Arrange-Act-Assert (AAA) pattern. Use `@Before` and
   `@After` for setup/teardown.
4. **Spacing:
   ** Separate AAA sections with empty lines. Do not add redundant comments for these sections.
5. **Visibility and Annotations:** Use `@Test` for test methods. Use
   `@VisibleForTesting` where needed. Prefer `internal` or `private` for test classes and helpers.
6. **Mocking and Dependency Injection:
   ** Use DI for test doubles. Prefer fakes over mocks. Use mock libraries only when required (e.g., at boundaries like DB/Network).
7. **Best Practices:
   ** Verify a single behavior per test. Avoid shared state. Use constants for test data. Keep tests fast and isolated. Use test-specific coroutine dispatchers.
8. **Assertions:** Use Kotest for assertions.

## Dependency Injection

1. DI methods should always have `provides` as the prefix (not `provide`).
