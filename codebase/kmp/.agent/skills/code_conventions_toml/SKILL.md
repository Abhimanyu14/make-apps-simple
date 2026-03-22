---
name: code_conventions_toml
description: TOML coding conventions for the project.
---

# TOML Code Conventions

## Naming

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

## Ordering

1. **Section Order:**
    - `[versions]` (app, SDK, library, and plugin versions)
    - `[libraries]` (dependencies)
    - `[plugins]` (plugin definitions)
    - `[bundles]` (dependency bundles)
2. **Within Sections:**
    - Alphabetize keys within each section for readability and consistency.
    - Group related items (e.g., all `androidx-` libraries together, all
      `koin-` libraries together).
    - For bundles, list dependencies in logical or usage order (e.g., camera-related libraries together).

### Example

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
