---
name: verification
description: Verification steps to ensure code changes are correct and maintainable.
---

# Verification Checklist

1. [ ] **Build Successfully**: Build the app (recursively if needed) after all changes to ensure no compilation errors.
2. [ ] **Lint and Checks**: Run `./gradlew lint` and `./gradlew detekt`.
3. [ ] **Test Execution**: Run all relevant unit tests (e.g., `./gradlew testDebugUnitTest`).
