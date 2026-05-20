---
name: commands
description: Useful Gradle commands for linting, testing, and deployment.
---

# Project Commands

## Linting and Code Quality
- **Lint**: Run full Android lint checks.
  ```bash
  ./gradlew lint
  ```
- **Detekt**: Run Kotlin static analysis.
  ```bash
  ./gradlew detekt
  ```

## Testing and Reports
- **Coverage**: Generate HTML test coverage report (using Kover).
  ```bash
  ./gradlew koverHtmlReportDebug
  ```
- **Screenshot Updates**: Update baseline screenshots for visual regression tests.
  ```bash
  ./gradlew :cosmos-design-system:updateDebugScreenshotTest
  ```
- **Screenshot Validation**: Validate current UI against baseline screenshots.
  ```bash
  ./gradlew :cosmos-design-system:validateDebugScreenshotTest
  ```

## API and Documentation
- **API Dump**: Generate or update the public API surface area file.
  ```bash
  ./gradlew apiDump
  ```
- **API Check**: Verify public API stability and binary compatibility.
  ```bash
  ./gradlew apiCheck
  ```
- **Dokka**: Generate HTML documentation using Dokka.
  ```bash
  ./gradlew :dokkaGenerateModuleHtml
  ```

## Release and Publishing
- **Publish**: Publish and release libraries to Maven Central.
  ```bash
  ./gradlew publishAndReleaseToMavenCentral --no-configuration-cache
  ```
