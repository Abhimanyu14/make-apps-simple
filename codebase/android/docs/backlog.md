# Backlog

1. [ ] **Architecture**: Simplify package structure in the `barcodes` module. The `common` package has a nested `common` package inside `ui` which can be flattened (`com.makeappssimple.abhimanyu.barcodes.android.common.ui.common`).
2. [ ] **Architecture**: Flatten redundant package nesting in features, e.g., `feature/home/home` should be `feature/home`.
3. [x] **Code Quality**: Refactor `BarcodeGeneratorImpl` to replace magic numbers for barcode formats with named constants or an Enum mapping.
4. [x] **Code Quality**: Remove unused private method `getBarcodeFormatInt` in `BarcodeGeneratorImpl`.
5. [ ] **Naming Convention**: Rename use cases in the `barcodes` module to follow the `*UseCase` suffix convention.
6. [ ] **Naming Convention**: Rename ViewModels in the `barcodes` module to follow the `*ViewModel` suffix convention.
7. [ ] **Naming Convention**: Rename screen Composables in the `barcodes` module to follow the `*UI` suffix convention.
8. [ ] **Naming Convention**: Rename data, domain, and UI models in the `barcodes` module to have consistent suffixes (`*DataModel`, `*DomainModel`, `*UiModel`).
9. [ ] **Naming Convention**: Rename mappers in the `barcodes` module to follow the `SourceToDestinationMapper` naming convention.
10. [ ] **DI**: Reorganize DI modules in the `barcodes` module by feature where appropriate, or consolidate if they are too scattered.
11. [ ] **DI**: Consolidate redundant `di` packages in the `data` and `presentation` layers of the `barcodes` module.
12. [ ] **Code Conventions - Function Body Style**: Replace expression body functions (using `=`) with block bodies in test files. Found 44 instances in test files (e.g., `HomeScreenViewModelTest.kt`, `BarcodeRepositoryTest.kt`, `BarcodeDaoTest.kt`, `CreateBarcodeScreenUITest.kt`, `NavigationKitTest.kt`).
13. [ ] **Code Conventions - Braces and Blocks**: Add curly braces to all if statements. Found 6 instances without braces (e.g., `WebView.kt` line 59, `BarcodesApp.kt` lines 74, 78, 98, 108, `BarcodeDetailsScreenUITest.kt` line 168).
14. [ ] **Code Conventions - Visibility Modifiers**: Change `public` visibility modifiers to `private` or `internal`. Found 3 instances: `BarcodesAppModule.kt` (line 54), `BarcodesActivity.kt` (line 25), `TestTags.kt` (line 19).
15. [ ] **Code Conventions - Visibility Modifiers**: Review and restrict `internal` visibility modifiers to `private` or `internal` with `@VisibleForTesting` where appropriate. Found 150+ instances across the module.
16. [ ] **Code Conventions - Trailing Commas**: Add trailing commas to listOf calls and other applicable places. Found missing trailing comma in `HomeScreenViewModelTest.kt` line 175.
17. [ ] **Code Conventions - Chained Method Calls**: Break chained method calls onto separate lines when there are at least 2 method calls. Found instance in `ScanBarcodeScreen.kt` line 183: `Preview.Builder().build().apply`.
18. [ ] **Code Conventions - End of Files**: Ensure all files end with an empty line. Need to verify all files in the `barcodes` module.
