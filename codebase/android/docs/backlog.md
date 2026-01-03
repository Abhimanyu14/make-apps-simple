# Backlog

- [ ] **Architecture**: Simplify package structure in the `barcodes` module. The `common` package has a nested `common` package inside `ui` which can be flattened (`com.makeappssimple.abhimanyu.barcodes.android.common.ui.common`).
- [ ] **Architecture**: Flatten redundant package nesting in features, e.g., `feature/home/home` should be `feature/home`.
- [x] **Code Quality**: Refactor `BarcodeGeneratorImpl` to replace magic numbers for barcode formats with named constants or an Enum mapping.
- [x] **Code Quality**: Remove unused private method `getBarcodeFormatInt` in `BarcodeGeneratorImpl`.
- [ ] **Naming Convention**: Rename use cases in the `barcodes` module to follow the `*UseCase` suffix convention.
- [ ] **Naming Convention**: Rename ViewModels in the `barcodes` module to follow the `*ViewModel` suffix convention.
- [ ] **Naming Convention**: Rename screen Composables in the `barcodes` module to follow the `*UI` suffix convention.
- [ ] **Naming Convention**: Rename data, domain, and UI models in the `barcodes` module to have consistent suffixes (`*DataModel`, `*DomainModel`, `*UiModel`).
- [ ] **Naming Convention**: Rename mappers in the `barcodes` module to follow the `SourceToDestinationMapper` naming convention.
- [ ] **DI**: Reorganize DI modules in the `barcodes` module by feature where appropriate, or consolidate if they are too scattered.
- [ ] **DI**: Consolidate redundant `di` packages in the `data` and `presentation` layers of the `barcodes` module.
