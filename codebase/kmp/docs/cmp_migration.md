# CMP Migration Plan

Last updated: 2026-04-26

## Goal
Migrate this repository from Android-first modules to Compose Multiplatform (CMP) modules, while keeping Android release stability.

## Apps In This Repo

1. `:app-barcodes` (Android app wrapper for `:barcodes`)
2. `:app-cosmos-design-system-catalog` (Android app wrapper for `:cosmos-design-system:catalog`)
3. `:app-finance-manager` (Android app wrapper for `:finance-manager`)
4. `:app-make-apps-simple` (Android launcher/umbrella app)
5. `:portfolio` (already a CMP app module with Android, iOS, JVM, JS, WASM)
6. `portfolio/iosApp` (native iOS host app for `:portfolio` framework)

## Module Inventory (from `settings.gradle.kts`)

Total Gradle modules: **19**

- CMP/KMP modules: **13**
- Android-only modules: **6**

### Current Module Status

| Module | Type | Current state | Migration status |
|---|---|---|---|
| `:portfolio` | CMP app | `commonMain + androidMain + iosMain + jvmMain + js/wasm` | High maturity |
| `:app-barcodes` | Android app | Thin wrapper around `:barcodes` | Wrapper only |
| `:app-cosmos-design-system-catalog` | Android app | Thin wrapper around `:cosmos-design-system:catalog` | Wrapper only |
| `:app-finance-manager` | Android app | Thin wrapper around `:finance-manager` | Wrapper only |
| `:app-make-apps-simple` | Android app | Android launcher app, links multiple modules | Android-only |
| `:barcode-generator` | Android library | `src/main` only | Not started |
| `:barcodes` | Mixed (KMP + Android source) | `commonMain` exists but Android code still in `src/main/java` | In progress |
| `:finance-manager` | Android library | Large Android-only codebase (`src/main`) | Not started |
| `:cosmos-design-system:library` | CMP library | `commonMain` heavy, `androidMain` minimal, iOS/JS configured | High maturity |
| `:cosmos-design-system:catalog` | CMP library/app-content | `commonMain + androidMain + ios/jvm/js` | High maturity |
| `:core:clipboard-kit` | KMP shell, Android impl only | `androidMain` only | Low maturity |
| `:core:coroutines` | CMP core | `commonMain` only | High maturity |
| `:core:date-time` | CMP core | `commonMain + androidMain + ios/jvm/js` | High maturity |
| `:core:app-version-kit` | KMP shell, Android-focused | `commonMain + androidMain` | Medium maturity |
| `:core:build-config-kit` | KMP shell, Android impl only | `androidMain` only | Low maturity |
| `:core:json-kit` | KMP shell, Android impl only | `androidMain` only | Low maturity |
| `:core:kotlin` | CMP core | `commonMain + androidMain` | Medium maturity |
| `:core:log-kit` | CMP core with Android adapter | `commonMain + androidMain` | Medium maturity |
| `:core:uri-kit` | KMP shell, Android impl only | `androidMain` only | Low maturity |

## Migration Principles

1. Keep Android wrappers (`:app-*`) thin; move all business/UI logic to CMP modules.
2. Move pure domain/use-case/state logic to `commonMain` first.
3. Introduce `expect/actual` for platform APIs (camera, clipboard, documents, notifications, build config, URI handling, analytics, review/update APIs).
4. For storage, keep SQLDelight or multiplatform abstraction in shared code, and isolate Room-specific code as Android `actual` until replaced.
5. Add iOS/JVM/JS/WASM targets only after module compiles cleanly with Android + shared code.
6. Maintain parity tests: shared unit tests in `commonTest`, platform tests in `androidUnitTest`/`androidInstrumentedTest`.

## Step-By-Step Action Items Per Module

### 1) `:portfolio`
1. Keep as reference module for CMP conventions and folder structure.
2. Extract reusable patterns (navigation, resources, app host wiring) into docs/templates.
3. Add CI checks to guarantee all declared targets continue compiling.

### 2) `:app-barcodes`
1. Keep only Android entry point (`Application`/`Activity`) and DI bootstrapping.
2. Remove feature/business logic from app module if any appears.
3. Add iOS/Desktop/Web hosts later (outside this wrapper) consuming shared `:barcodes` UI.

### 3) `:app-cosmos-design-system-catalog`
1. Keep Android launcher-only responsibilities.
2. Ensure all screen/content logic remains in `:cosmos-design-system:catalog`.
3. Add additional platform hosts that launch the shared catalog module.

### 4) `:app-finance-manager`
1. Keep Android shell only.
2. Move remaining app-level logic into shared `:finance-manager` module before non-Android hosts.
3. Add iOS host app once shared module reaches feature parity.

### 5) `:app-make-apps-simple`
1. Split launcher state/navigation model into a new shared CMP module (recommended: `:make-apps-simple` shared feature module).
2. Replace Android-only navigation/events with shared navigation contracts.
3. Keep `:app-make-apps-simple` as Android shell that calls shared launcher UI.

### 6) `:barcode-generator`
1. Convert to KMP module (`kotlin { androidTarget(); iosX64/iosArm64/iosSimulatorArm64; ... }`).
2. Move pure barcode format/domain logic to `commonMain`.
3. Isolate ZXing or Android platform dependencies behind `expect/actual` or adapter interfaces.
4. Add `commonTest` vectors for deterministic barcode generation rules.

### 7) `:barcodes`
1. Move code from `src/main/java` into `src/androidMain/kotlin` (first cleanup pass).
2. Audit `commonMain` and extract remaining pure domain/use-case/viewmodel state from Android code.
3. Introduce platform interfaces for camera, MLKit scanning, Play Services, in-app review/update, Firebase.
4. Add non-Android targets (start with iOS) once shared layer compiles without Android APIs.
5. Replace Room-only persistence boundaries with multiplatform repository contracts.
6. Expand `commonTest` coverage for shared reducers, validators, mappers, and use-cases.

### 8) `:finance-manager`
1. Convert module to KMP plugin setup (or create `:finance-manager-shared` and migrate incrementally).
2. Move domain models, use-cases, validation, formatting, and screen state/event contracts to `commonMain`.
3. Move UI to CMP composables in `commonMain`; keep Android-only integrations in `androidMain`.
4. Abstract Android-specific pieces: notifications, alarms, broadcast receivers, documents, Firebase, Play APIs.
5. Plan storage migration path (Room adapter first, SQLDelight or shared abstraction next).
6. Add iOS target after feature slices are migrated (accounts, categories, transactions, settings).
7. Add shared tests per feature slice before deleting Android-only implementations.

### 9) `:cosmos-design-system:library`
1. Continue reducing Android-specific resource/provider usage in `androidMain`.
2. Validate components across Android/iOS/JVM/JS screenshots or golden tests.
3. Harden public API docs for cross-platform usage guarantees.

### 10) `:cosmos-design-system:catalog`
1. Ensure all demo screens run from shared content first.
2. Keep platform demos in platform source sets only when necessary.
3. Add CI matrix for catalog targets to prevent regressions.

### 11) `:core:clipboard-kit`
1. Add `commonMain` API contracts (`expect` clipboard API).
2. Keep Android implementation in `androidMain` as `actual`.
3. Add iOS/JVM/JS implementations.
4. Add shared behavioral tests using fake clipboard adapters.

### 12) `:core:coroutines`
1. Keep as shared baseline module.
2. Verify no Android imports leak into shared APIs.
3. Expand shared test utilities for dispatcher/scope control.

### 13) `:core:date-time`
1. Keep date/time logic in `commonMain`.
2. Review formatter behavior parity across Android/iOS/JVM/JS.
3. Add locale/timezone edge-case tests to `commonTest`.

### 14) `:core:app-version-kit`
1. Move version model/query interfaces to `commonMain`.
2. Keep Android package-manager lookup as `androidMain actual`.
3. Add iOS/JVM implementations or graceful no-op fallbacks per platform.

### 15) `:core:build-config-kit`
1. Define shared config contract in `commonMain`.
2. Implement Android `actual` using BuildConfig.
3. Add non-Android actuals via compile-time constants/environment providers.

### 16) `:core:json-kit`
1. Move JSON reader/writer interfaces to `commonMain`.
2. Keep Android implementation as one adapter; add multiplatform default adapter (`kotlinx.serialization`).
3. Replace Android-only call sites with shared abstraction.

### 17) `:core:kotlin`
1. Keep pure helpers in `commonMain`.
2. Move any Android-specific extensions to `androidMain`.
3. Add missing `commonTest` cases for all helper utilities.

### 18) `:core:log-kit`
1. Define logging facade in `commonMain` with platform-agnostic levels/events.
2. Keep Android Logcat logger in `androidMain`.
3. Add iOS/JVM/JS logger actuals and testable fake logger.

### 19) `:core:uri-kit`
1. Define URI parser/encoder contract in `commonMain`.
2. Keep Android URI implementation in `androidMain`.
3. Add iOS/JVM/JS implementations using platform URL/URI primitives.
4. Add shared URI conformance tests.

## Suggested Execution Order

1. **Phase 1 (Foundation)**: `:core:*` low/medium maturity modules (`clipboard-kit`, `json-kit`, `uri-kit`, `build-config-kit`, `app-version-kit`, `log-kit`).
2. **Phase 2 (Feature core)**: `:barcode-generator`, then `:barcodes` migration from `src/main` to `androidMain` + shared extraction.
3. **Phase 3 (Largest app)**: `:finance-manager` incremental feature-slice migration.
4. **Phase 4 (Hosts)**: keep `:app-*` wrappers thin; add non-Android hosts after shared modules stabilize.
5. **Phase 5 (Quality gates)**: multi-target CI compile + shared test coverage thresholds.

## Definition Of Done (Per Module)

1. Module compiles for all intended targets.
2. No Android imports in `commonMain`.
3. Platform APIs isolated via `expect/actual` or interfaces.
4. Shared unit tests cover migrated logic.
5. Android wrapper modules contain only platform bootstrap code.
