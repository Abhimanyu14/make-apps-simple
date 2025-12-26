- [Lint](#lint)
    - [To run lint checks](#to-run-lint-checks)
- [Detekt](#detekt)
    - [To run detekt checks](#to-run-detekt-checks)
- [Testing](#testing)
    - [To generate test coverage report](#to-generate-test-coverage-report)
- [Screenshot Testing](#screenshot-testing)
    - [To update screenshots](#to-update-screenshots)
    - [To run screenshot tests](#to-run-screenshot-tests)
- [Kotlin Binary Compatibility Validator](#kotlin-binary-compatibility-validator)
    - [To update public API](#to-update-public-api)
    - [To check public API stability](#to-check-public-api-stability)
- [Dokka](#dokka)
    - [To generate docs](#to-generate-docs)
- [Release](#release)
    - [To publish to maven central](#to-publish-to-maven-central)

# Lint

## To run lint checks

```
./gradlew lint
```

# Detekt

## To run detekt checks

```
./gradlew detekt
```

# Testing

## To generate test coverage report

```
./gradlew koverHtmlReportDebug
```

# Screenshot Testing

## To update screenshots

```
./gradlew :cosmos-design-system:updateDebugScreenshotTest
```

## To run screenshot tests

```
./gradlew :cosmos-design-system:validateDebugScreenshotTest
```

# Kotlin Binary Compatibility Validator

## To update public API

```
./gradlew apiDump
```

## To check public API stability

```
./gradlew apiCheck
```

# Dokka

## To generate docs

```
./gradlew :dokkaGenerateModuleHtml
```

# Release

## To publish to maven central

```
./gradlew publishAndReleaseToMavenCentral --no-configuration-cache
```
