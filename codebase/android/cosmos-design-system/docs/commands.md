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

# Dokka

## To generate docs

```
./gradlew :dokkaGenerateModuleHtml
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

# Screenshot Testing

## To update screenshots

```
./gradlew :cosmos-design-system:updateDebugScreenshotTest
```

## To run screenshot tests

```
./gradlew :cosmos-design-system:validateDebugScreenshotTest
```

# Release

## To publish to maven central

```
./gradlew publishAndReleaseToMavenCentral --no-configuration-cache
```
