# Clean Architecture Issues Report

**Codebase:** make-apps-simple  
**Date:** March 15, 2026  
**Reference:** [Architecture Guidelines](../codebase/android/docs/architecture_guidelines.md)

---

## Executive Summary

The codebase documents a **Clean Architecture** structure with the dependency rule `UI → Presentation → Domain ← Data`. While many patterns are correctly implemented (repository interfaces in domain, use cases, mappers, Koin DI), several violations of the dependency rule and layer boundaries were identified. The most significant issues involve **Domain depending on Data and Android**, **Presentation depending on UI**, and **common modules using Android APIs**.

---

## 1. Domain Layer Violations

### 1.1 Domain Use Cases Depending on Data Layer Models

**Severity:** High  
**Principle Violated:** Domain must not depend on Data. Per the architecture guidelines, Domain has "No dependencies on Android framework or other layers (Data, Presentation, UI)."

#### BackupDataUseCase

**File:** `codebase/android/finance-manager/src/main/java/com/makeappssimple/abhimanyu/finance/manager/android/common/domain/use_case/common/BackupDataUseCase.kt`

| Line | Violation |
|------|-----------|
| 19 | `import android.net.Uri` — Android framework in domain |
| 22–24 | Imports `BackupData`, `DatabaseData`, `DatastoreData` — Data layer models |
| 47 | Uses `Uri` in `invoke(uri: Uri)` |
| 50–54 | Constructs `BackupData` and `DatabaseData` directly |
| 66–90 | Returns `DatabaseData` and `DatastoreData` from private methods |

The use case orchestrates backup by:
- Creating `BackupData`, `DatabaseData`, and `DatastoreData` (Data models)
- Passing `Uri` to `JsonWriterKit`
- Serializing Data models with `Json.encodeToString(BackupData)`

**Recommendation:** Introduce domain models (e.g. `BackupPayload`, `DatabasePayload`) and interfaces for file I/O that abstract `Uri`. Implement a Data-layer service that maps domain payloads to `BackupData`/`DatabaseData` and performs file writes.

---

#### RestoreDataUseCase

**File:** `codebase/android/finance-manager/src/main/java/com/makeappssimple/abhimanyu/finance/manager/android/common/domain/use_case/common/RestoreDataUseCase.kt`

| Line | Violation |
|------|-----------|
| 19 | `import android.net.Uri` |
| 25–33 | Imports Data layer: `AccountEntity`, `TransactionEntity`, `asEntity`, `asExternalModel`, `sanitizeAccounts`, `sanitizeTransactions`, `BackupData`, `DatabaseData`, `DatastoreData` |
| 50–51 | Uses `Uri` in `invoke(uri: Uri)` |
| 62 | Decodes `BackupData` (Data model) via `Json.decodeFromString<BackupData>` |
| 77–106 | Works directly with `DatabaseData` and `DatastoreData` |
| 87–100 | Uses `AccountEntity`, `TransactionEntity`, `sanitizeAccounts`, `sanitizeTransactions` (Data layer) |

The use case mixes:
- JSON parsing of Data models
- Data-layer sanitization and entity mapping
- Direct calls to `TransactionRepository.restoreData()` with domain types (correct) but only after converting from Data models inside the domain layer

**Recommendation:** Move restore orchestration into the Data layer. Domain should expose only high-level operations like `RestoreBackupUseCase(in: InputStream)` or `RestoreBackupUseCase(backupBytes: ByteArray)`. A Data layer component should handle `Uri`, JSON parsing, `BackupData`/`DatabaseData`/`DatastoreData`, sanitization, and repository calls.

---

### 1.2 Common Core Abstractions Using Android APIs

**Severity:** Medium (blocks KMP readiness)  
**Principle:** The `common` package is intended to be platform-agnostic for KMP.

| File | Violation |
|------|-----------|
| `common/core/json_reader/JsonReaderKit.kt` | Interface uses `android.net.Uri` in `readJsonFromFile(uri: Uri)` |
| `common/core/json_writer/JsonWriterKit.kt` | Interface uses `android.net.Uri` in `writeJsonToFile(uri: Uri, ...)` |
| `JsonReaderKitImpl.kt`, `JsonWriterKitImpl.kt` | Implementations use `android.net.Uri` |
| `UriDecoderImpl.kt`, `UriEncoderImpl.kt` | Use `android.net.Uri` |

Because Domain use cases depend on `JsonReaderKit`/`JsonWriterKit`, Domain is transitively tied to Android.

**Recommendation:** Introduce platform-agnostic abstractions:
- Use `InputStream`/`OutputStream` or `ByteArray` (or KMP-compatible equivalents)
- Platform-specific implementations in `platform` resolve `Uri` to streams and call the shared kit

---

## 2. Presentation Layer Violations

### 2.1 Presentation Depends on UI Types (Reversed Dependency)

**Severity:** High  
**Principle:** Per guidelines, `UI` depends on `Presentation`. Presentation must not depend on UI.

#### ViewModels and State Using UI Component Types

| File | UI Types Used |
|------|---------------|
| `ViewTransactionScreenViewModel.kt` | `TransactionListItemData`, `toTransactionListItemData` |
| `HomeScreenViewModel.kt` | `TransactionListItemData`, `toTransactionListItemData`, `OverviewCardViewModelData`, `OverviewCardAction`, `OverviewTabOption`, `orDefault` |
| `CategoriesScreenViewModel.kt` | `CategoriesGridItemData` |
| `AccountsScreenViewModel.kt` | `AccountsListItemData` |
| `TransactionsScreenViewModel.kt` | `TransactionListItemData`, `toTransactionListItemData` |
| `TransactionForValuesScreenViewModel.kt` | `TransactionForListItemData` |

#### UIState Data Classes

| File | UI Types |
|------|----------|
| `HomeScreenUIState.kt` | `TransactionListItemData`, `OverviewCardViewModelData` |
| `AccountsScreenUIState.kt` | `AccountsListItemData` |
| `CategoriesScreenUIState.kt` | `CategoriesGridItemData` |
| `TransactionsScreenUIState.kt` | `TransactionListItemData` |
| `ViewTransactionScreenUIState.kt` | `TransactionListItemData` |
| `TransactionForValuesScreenUIState.kt` | `TransactionForListItemData` |

#### Presentation Use Cases Depending on UI

| File | Violation |
|------|-----------|
| `GetAllAccountsListItemDataListUseCase.kt` | Returns `ImmutableList<AccountsListItemData>`, uses `AccountsListItemContentData`, `AccountsListItemHeaderData`, `iconResource` (UI extension) |

These types (`TransactionListItemData`, `OverviewCardViewModelData`, `CategoriesGridItemData`, `AccountsListItemData`, `TransactionForListItemData`) live in `common.ui.ui.component.*` and describe UI layout. Presentation should work with its own models (e.g. `*UiModel` or `*PresentationModel`), and the UI layer should map them to component-specific types.

**Recommendation:** Introduce Presentation-layer models (e.g. `TransactionListPresentationModel`) and mappers. ViewModels and state should only use these. The UI layer should map `*PresentationModel` → `*ListItemData` / `*GridItemData` inside Composables or dedicated UI mappers.

---

### 2.2 Presentation Event Handlers Using Android APIs

**Severity:** Medium

| File | Violation |
|------|-----------|
| `SettingsScreenUIEventHandler.kt` | `android.net.Uri`, `android.os.Build` (line 69: `Build.VERSION.SDK_INT`) |

**Recommendation:** Move SDK version checks and URI handling behind platform/abstraction layers or inject platform-specific behavior via interfaces.

---

### 2.3 Presentation Use Cases Depending on UI Extensions

**Severity:** Medium

| File | Violation |
|------|-----------|
| `GetAllAccountsListItemDataListUseCase.kt` | `import ...ui.ui.extensions.iconResource` — maps domain `AccountType` to `CosmosIconResource` |
| `AddAccountScreenViewModel.kt` | `iconResource` extension for `AccountType` |
| `EditAccountScreenViewModel.kt` | Same |
| `EditAccountScreenViewModel.kt` | `EditAccountScreenUIVisibilityData` (UI type) |

`iconResource` converts domain types to Compose/design-system icon resources. That mapping belongs in the UI or in a UI-bound adapter, not in presentation logic or use cases.

**Recommendation:** Use domain identifiers (e.g. `AccountType.name`) in presentation models; the UI layer maps them to `CosmosIconResource` via its own extension or mapper.

---

### 2.4 Presentation Depending on UI for Navigation

**Severity:** Low (often pragmatic for Compose)

Presentation `NavGraph` files import UI screens:

- `CategoriesNavGraph.kt` → `AddCategoryScreen`, `CategoriesScreen`, `EditCategoryScreen`
- `TransactionForNavGraph.kt` → `AddTransactionForScreen`, `EditTransactionForScreen`, `TransactionForValuesScreen`
- `TransactionsNavGraph.kt` → `AddTransactionScreen`, `EditTransactionScreen`, etc.
- Similar patterns in Barcodes and other features

This creates a formal Presentation → UI dependency. In Compose apps, navigation is often defined close to the screens, so this is common but still a deviation from strict layer rules.

**Recommendation:** Consider defining routes/contracts in Presentation and wiring actual Composables at the app/UI entry point, or accept this as a controlled, documented exception.

---

## 3. Barcodes-Specific Issues

### 3.1 Presentation Depending on UI

| File | Violation |
|------|-----------|
| `BarcodeDetailsScreenViewModel.kt` | `AnalyticsKit` from `common.ui.analytics` |
| `ScanBarcodeScreenViewModel.kt` | Same |
| `WebViewScreenViewModel.kt` | Same |
| `SettingsScreenViewModel.kt` | Same |
| `ScreenViewModel.kt` (base) | Same |
| `HomeScreenViewModel.kt` | `HomeCosmosBottomSheetType` (UI type) |
| `HomeScreenUIEventHandler.kt` | `HomeCosmosBottomSheetType`, `HomeMenuBottomSheetEvent` |
| `HomeScreenUIState.kt` | `HomeCosmosBottomSheetType` |
| `BarcodeDetailsScreenUIEventHandler.kt` | `BARCODE_VALUE_CLIPBOARD_LABEL` (UI constant) |

**Recommendation:** Move analytics into a domain or platform service and inject it. Move bottom sheet types and clipboard labels to presentation or shared constants modules that UI can depend on, not the other way around.

---

## 4. Structure and Module Boundaries

### 4.1 Layers in a Single Module

All layers (Domain, Data, Presentation, UI) live inside the same Gradle modules (`finance-manager`, `barcodes`) and are separated only by packages. This makes it easy for dependencies to cross layers without build-time enforcement.

**Recommendation:** Consider splitting into Gradle modules (e.g. `finance-manager-domain`, `finance-manager-data`, `finance-manager-presentation`, `finance-manager-ui`) so the dependency rule is enforced by the build.

### 4.2 Web Project

The `codebase/web/` project (Kotlin/JS, Compose for Web) has no explicit Clean Architecture layering. It is primarily UI/presentation with `content/`, `style/`, and `components/`.

**Recommendation:** If web and Android will share logic, introduce a shared module (e.g. KMP) with domain and possibly data, and have both platforms depend on it.

---

## 5. What Is Done Well

| Area | Implementation |
|------|----------------|
| Repository pattern | Interfaces in Domain, implementations in Data |
| Use cases | Most domain use cases depend only on repositories and other domain types |
| Mappers | Consistent naming (`BarcodeDataToDomainMapper`, etc.) and separation |
| DI | Koin with interface binding, modular composition |
| Platform vs common | Some separation (e.g. `AlarmKitModule`, `NotificationKitModule` in platform) |
| Data → Domain | Data layer correctly depends on Domain and uses domain models in repository APIs |
| State flow | Unidirectional flow: `uiState`, `uiStateEvents`, `*UIEvent` |
| Barcodes domain | Largely free of Android/Data leakage (except backup/restore where used) |

---

## 6. Prioritized Recommendations

### High priority

1. **Domain purity**
   - Move backup/restore orchestration out of Domain or introduce domain-only models and interfaces.
   - Remove `BackupData`, `DatabaseData`, `DatastoreData`, and `Uri` from domain use cases.

2. **Presentation → UI dependency**
   - Introduce presentation-only models for lists, grids, and overview cards.
   - Move UI component types (`*ListItemData`, `*GridItemData`, etc.) so they are used only in the UI layer.
   - Ensure ViewModels and UIState use presentation models; UI maps to component types.

### Medium priority

3. **Platform-agnostic I/O**
   - Replace `Uri` in `JsonReaderKit`/`JsonWriterKit` with stream- or byte-based abstractions.
   - Implement platform-specific adapters that resolve `Uri` and call the shared kit.

4. **Presentation vs UI boundaries**
   - Move `iconResource` and similar extensions to the UI layer or a UI-bound adapter.
   - Move analytics, bottom sheet types, and UI constants so Presentation does not depend on UI packages.

### Lower priority

5. **Module structure**
   - Split features into domain/data/presentation/ui modules for build-time dependency enforcement.
   - Document navigation as an accepted Presentation → UI dependency if it is kept for pragmatic reasons.

---

## 7. File-Level Reference

### Domain files with violations

- `finance-manager/.../domain/use_case/common/BackupDataUseCase.kt`
- `finance-manager/.../domain/use_case/common/RestoreDataUseCase.kt`

### Common core files with Android APIs

- `common/core/json_reader/JsonReaderKit.kt`
- `common/core/json_writer/JsonWriterKit.kt`
- `common/core/json_reader/JsonReaderKitImpl.kt`
- `common/core/json_writer/JsonWriterKitImpl.kt`
- `common/core/uri_decoder/UriDecoderImpl.kt`
- `common/core/uri_encoder/UriEncoderImpl.kt`

### Presentation files depending on UI

- `HomeScreenViewModel.kt`, `HomeScreenUIState.kt`, `HomeScreenUIStateEvents.kt`
- `ViewTransactionScreenViewModel.kt`, `ViewTransactionScreenUIState.kt`
- `CategoriesScreenViewModel.kt`, `CategoriesScreenUIState.kt`
- `AccountsScreenViewModel.kt`, `AccountsScreenUIState.kt`, `GetAllAccountsListItemDataListUseCase.kt`
- `TransactionsScreenViewModel.kt`, `TransactionsScreenUIState.kt`
- `TransactionForValuesScreenViewModel.kt`, `TransactionForValuesScreenUIState.kt`
- `EditAccountScreenViewModel.kt`, `EditAccountScreenUIState.kt`
- `SettingsScreenUIEventHandler.kt`
- Barcodes: `BarcodeDetailsScreenViewModel.kt`, `ScanBarcodeScreenViewModel.kt`, `WebViewScreenViewModel.kt`, `SettingsScreenViewModel.kt`, `ScreenViewModel.kt`, `HomeScreenViewModel.kt`, `HomeScreenUIEventHandler.kt`, `HomeScreenUIState.kt`, `BarcodeDetailsScreenUIEventHandler.kt`

---

*Report generated from analysis of the make-apps-simple codebase.*
