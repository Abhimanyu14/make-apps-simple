# Finance Manager CMP Migration Plan

Last updated: 2026-09-01

## Goal
Copy the finance manager app from `codebase/kmp` into `codebase/cmp`, make it build and run on Android first, and leave non-Android targets for later phases.

## Working Assumptions

1. Android-only is acceptable for the first pass.
2. The CMP repo should own the migrated module(s), even if most code still lives in Android source sets.
3. The migration should be resumable by other agents without re-discovering the layout.

## Target Layout

1. `codebase/cmp/finance-manager` for the main app/library code.
2. `codebase/cmp/app-finance-manager` for the Android launcher wrapper.
3. `codebase/cmp/docs/finance-manager-cmp-migration-plan.md` as the living plan.

## Phases

### Phase 1: Copy and Wire

Status: completed

1. Copy the finance manager module and its Android app wrapper from `codebase/kmp`.
2. Register the copied modules in `codebase/cmp/settings.gradle.kts`.
3. Update module dependencies to use the CMP repo’s module names.
4. Preserve Android resources, manifest entries, and tests so the app still launches.

### Phase 2: CMP Compatibility Pass

Status: completed

1. Move Android-only source to the CMP Android module layout.
2. Replace KMP package/module references with CMP module/API names.
3. Add explicit Android Koin bindings for core implementations that cannot export generated KSP module values across CMP project boundaries.
4. Keep non-Android source sets deferred for a later migration.

### Phase 3: Build and Fix

Status: completed

1. Run `./gradlew --no-daemon --no-configuration-cache --console=plain :app-finance-manager:assembleDebug` from `codebase/cmp`.
2. Fix Gradle, package, namespace, dependency, Koin, and resource compatibility issues.
3. Verify the debug APK at `app-finance-manager/build/outputs/apk/debug/app-finance-manager-debug.apk`.
4. Result: `BUILD SUCCESSFUL`; debug APK generated successfully.

### Phase 4: Resume Notes

Status: completed

1. Keep Android-only parity as the current completed milestone.
2. Future agents should migrate non-Android source sets only when required.
3. Future agents should remove the temporary AGP 9 compatibility properties `android.builtInKotlin=false` and `android.newDsl=false` after migrating the project to built-in Kotlin/modern DSL.
4. The build emits deprecation warnings for those properties and for the legacy Kotlin Android plugin; these do not block the Android APK.

## Completion Criteria

1. The finance manager app sources live under `codebase/cmp`.
2. The Android app wrapper launches successfully from the CMP tree.
3. The plan file reflects the current phase and next resume point.
4. Remaining non-Android work is explicitly deferred rather than implied.

## Resume Checklist

1. Start in `codebase/cmp`.
2. Read this file before changing module wiring.
3. Re-run `./gradlew --no-daemon --no-configuration-cache --console=plain :app-finance-manager:assembleDebug`.
4. Treat `app-finance-manager/build/outputs/apk/debug/app-finance-manager-debug.apk` as the current Android verification artifact.
