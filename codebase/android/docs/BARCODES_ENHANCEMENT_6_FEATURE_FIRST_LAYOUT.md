# Enhancement 6: Feature-First Directory Layout — Implementation Plan

**Source:** [BARCODES_ARCHITECTURE_ENHANCEMENTS.md](./BARCODES_ARCHITECTURE_ENHANCEMENTS.md) (Enhancement 6)  
**Package:** `com.makeappssimple.abhimanyu.barcodes.android`  
**Date:** March 15, 2026

---

## 1. Overview

This document details the steps to refactor the **barcodes** module from a **layer-first** layout to a **feature-first** layout. The goal is to colocate all code belonging to a feature (home, barcode_details, create_barcode, scan_barcode, settings, web_view) under a single feature directory, while keeping **shared core** (domain, data, presentation base, navigation, DI) in a dedicated `core/` area to avoid duplication and respect Clean Architecture.

### 1.1 Design Choice: Core + Feature-First

- **Core:** Shared domain (Barcode models, BarcodeRepository, use cases), data (DAO, repository impl, database, mappers), presentation base (ScreenViewModel, ScreenUIState, etc.), app-level presentation (BarcodesActivityViewModel), navigation (NavigationKit, Screen, BarcodesNavGraph, etc.), presentation models/mappers, and DI live under `core/`.
- **Features:** Each feature has `features/<feature_name>/presentation/` and `features/<feature_name>/ui/`. Feature-specific ViewModels, state, events, screens, and UI components live here.
- **Shared UI:** Cross-feature UI (analytics, app shell, common components, constants, icons, permissions, play_store_review, testing) lives under `shared/ui/`.
- **Platform:** Activity and Application remain under `platform/`.

This keeps a single source of truth for barcode domain/data while making feature boundaries explicit and improving scalability (e.g. future Gradle module split per feature).

---

## 2. Target Directory Structure

Base path for all paths below (unless stated otherwise):

`barcodes/src/main/java/com/makeappssimple/abhimanyu/barcodes/android/`

```
android/
├── core/
│   ├── data/
│   │   ├── barcode/
│   │   │   ├── BarcodeDao.kt
│   │   │   └── fake/
│   │   │       └── FakeBarcodeDao.kt
│   │   ├── database/
│   │   │   ├── constants/
│   │   │   │   └── DatabaseConstants.kt
│   │   │   ├── converters/
│   │   │   │   └── BarcodeSourceConverter.kt
│   │   │   ├── di/
│   │   │   │   └── RoomModule.kt
│   │   │   ├── local/
│   │   │   │   └── BarcodesRoomDatabase.kt
│   │   │   └── migrations/
│   │   │       ├── AutoDatabaseMigration.kt
│   │   │       └── ManualDatabaseMigration.kt
│   │   ├── di/
│   │   │   └── DaosModule.kt
│   │   ├── mapper/
│   │   │   ├── BarcodeDataToDomainMapper.kt
│   │   │   └── BarcodeDomainToDataMapper.kt
│   │   ├── model/
│   │   │   ├── BarcodeDataModel.kt
│   │   │   ├── BarcodeFormatDataModel.kt
│   │   │   └── BarcodeSourceDataModel.kt
│   │   └── repository/
│   │       └── barcode/
│   │           └── BarcodeRepositoryImpl.kt
│   ├── di/
│   │   ├── BarcodesAppModule.kt
│   │   ├── FirebaseModule.kt
│   │   └── KoinModule.kt
│   ├── domain/
│   │   ├── model/
│   │   │   ├── BarcodeDomainModel.kt
│   │   │   ├── BarcodeFormatDomainModel.kt
│   │   │   └── BarcodeSourceDomainModel.kt
│   │   ├── repository/
│   │   │   └── BarcodeRepository.kt
│   │   └── use_case/
│   │       └── barcode/
│   │           ├── DeleteBarcodesUseCase.kt
│   │           ├── GetAllBarcodesFlowUseCase.kt
│   │           ├── GetBarcodeByIdUseCase.kt
│   │           ├── InsertBarcodesUseCase.kt
│   │           └── UpdateBarcodesUseCase.kt
│   └── presentation/
│       ├── app/
│       │   └── BarcodesActivityViewModel.kt
│       ├── base/
│       │   ├── ScreenArgs.kt
│       │   ├── ScreenSnackbarType.kt
│       │   ├── ScreenUIEvent.kt
│       │   ├── ScreenUIState.kt
│       │   ├── ScreenUIStateEvents.kt
│       │   └── ScreenViewModel.kt
│       ├── mapper/
│       │   ├── BarcodeDomainToUiMapper.kt
│       │   └── BarcodeUiToDomainMapper.kt
│       ├── model/
│       │   ├── BarcodeFormatUiModel.kt
│       │   ├── BarcodeSourceUiModel.kt
│       │   └── BarcodeUiModel.kt
│       └── navigation/
│           ├── BarcodesNavGraph.kt
│           ├── BarcodesNavHost.kt
│           ├── BarcodesNavigationDirections.kt
│           ├── NavigationCommand.kt
│           ├── NavigationKit.kt
│           ├── NavigationKitImpl.kt
│           ├── Screen.kt
│           ├── constants/
│           │   ├── DeeplinkUrl.kt
│           │   └── NavigationArguments.kt
├── features/
│   ├── barcode_details/
│   │   ├── presentation/
│   │   │   ├── barcode_details/
│   │   │   │   ├── event/
│   │   │   │   │   ├── BarcodeDetailsScreenUIEvent.kt
│   │   │   │   │   └── BarcodeDetailsScreenUIEventHandler.kt
│   │   │   │   ├── snackbar/
│   │   │   │   │   └── BarcodeDetailsScreenSnackbarType.kt
│   │   │   │   ├── state/
│   │   │   │   │   ├── BarcodeDetailsScreenUIState.kt
│   │   │   │   │   └── BarcodeDetailsScreenUIStateEvents.kt
│   │   │   │   └── view_model/
│   │   │   │       └── BarcodeDetailsScreenViewModel.kt
│   │   │   └── navigation/
│   │   │       ├── BarcodeDetailsNavGraph.kt
│   │   │       └── BarcodeDetailsScreenArgs.kt
│   │   └── ui/
│   │       └── barcode_details/
│   │           └── screen/
│   │               ├── BarcodeDetailsScreen.kt
│   │               └── BarcodeDetailsScreenUI.kt
│   ├── create_barcode/
│   │   ├── presentation/
│   │   │   ├── create_barcode/
│   │   │   │   ├── event/
│   │   │   │   │   ├── CreateBarcodeScreenUIEvent.kt
│   │   │   │   │   └── CreateBarcodeScreenUIEventHandler.kt
│   │   │   │   ├── snackbar/
│   │   │   │   │   └── CreateBarcodeScreenSnackbarType.kt
│   │   │   │   ├── state/
│   │   │   │   │   ├── CreateBarcodeScreenUIState.kt
│   │   │   │   │   └── CreateBarcodeScreenUIStateEvents.kt
│   │   │   │   └── view_model/
│   │   │   │       └── CreateBarcodeScreenViewModel.kt
│   │   │   └── navigation/
│   │   │       ├── CreateBarcodeNavGraph.kt
│   │   │       └── CreateBarcodeScreenArgs.kt
│   │   └── ui/
│   │       └── create_barcode/
│   │           └── screen/
│   │               ├── CreateBarcodeScreen.kt
│   │               └── CreateBarcodeScreenUI.kt
│   ├── home/
│   │   ├── presentation/
│   │   │   ├── home/
│   │   │   │   ├── event/
│   │   │   │   │   ├── HomeScreenUIEvent.kt
│   │   │   │   │   └── HomeScreenUIEventHandler.kt
│   │   │   │   ├── snackbar/
│   │   │   │   │   └── HomeScreenSnackbarType.kt
│   │   │   │   ├── state/
│   │   │   │   │   ├── HomeScreenUIState.kt
│   │   │   │   │   └── HomeScreenUIStateEvents.kt
│   │   │   │   └── view_model/
│   │   │   │       └── HomeScreenViewModel.kt
│   │   │   └── navigation/
│   │   │       └── HomeNavGraph.kt
│   │   └── ui/
│   │       └── home/
│   │           ├── bottom_sheet/
│   │           │   ├── HomeCosmosBottomSheetType.kt
│   │           │   └── HomeMenuBottomSheet.kt
│   │           ├── dialog/
│   │           │   └── HomeDeleteBarcodeDialog.kt
│   │           └── screen/
│   │               ├── HomeScreen.kt
│   │               └── HomeScreenUI.kt
│   ├── scan_barcode/
│   │   ├── presentation/
│   │   │   ├── navigation/
│   │   │   │   ├── ScanBarcodeNavGraph.kt
│   │   │   │   └── ScanBarcodeScreenArgs.kt
│   │   │   └── scan_barcode/
│   │   │       ├── event/
│   │   │       │   ├── ScanBarcodeScreenUIEvent.kt
│   │   │       │   └── ScanBarcodeScreenUIEventHandler.kt
│   │   │       ├── snackbar/
│   │   │       │   └── ScanBarcodeScreenSnackbarType.kt
│   │   │       ├── state/
│   │   │       │   └── ScanBarcodeScreenUIState.kt
│   │   │       └── view_model/
│   │   │           └── ScanBarcodeScreenViewModel.kt
│   │   └── ui/
│   │       ├── barcode_scanner/
│   │       │   ├── barcode_scanner/
│   │       │   │   └── BarcodeAnalyser.kt
│   │       │   ├── camera/
│   │       │   │   └── BarcodeScannerPreview.kt
│   │       │   └── di/
│   │       │       └── BarcodeScannerModule.kt
│   │       └── scan_barcode/
│   │           └── screen/
│   │               ├── ScanBarcodeScreen.kt
│   │               └── ScanBarcodeScreenUI.kt
│   ├── settings/
│   │   ├── presentation/
│   │   │   ├── credits/
│   │   │   │   ├── event/
│   │   │   │   │   ├── CreditsScreenUIEvent.kt
│   │   │   │   │   └── CreditsScreenUIEventHandler.kt
│   │   │   │   └── view_model/
│   │   │   │       └── CreditsScreenViewModel.kt
│   │   │   ├── navigation/
│   │   │   │   └── SettingsNavGraph.kt
│   │   │   └── settings/
│   │   │       ├── event/
│   │   │       │   ├── SettingsScreenUIEvent.kt
│   │   │       │   └── SettingsScreenUIEventHandler.kt
│   │   │       └── view_model/
│   │   │           └── SettingsScreenViewModel.kt
│   │   └── ui/
│   │       ├── credits/
│   │       │   └── screen/
│   │       │       ├── CreditsScreen.kt
│   │       │       └── CreditsScreenUI.kt
│   │       └── settings/
│   │           └── screen/
│   │               ├── SettingsScreen.kt
│   │               └── SettingsScreenUI.kt
│   └── web_view/
│       ├── presentation/
│       │   ├── navigation/
│       │   │   ├── WebViewNavGraph.kt
│       │   │   └── WebViewScreenArgs.kt
│       │   └── web_view/
│       │       ├── event/
│       │       │   ├── WebViewScreenUIEvent.kt
│       │       │   └── WebViewScreenUIEventHandler.kt
│       │       ├── state/
│       │       │   ├── WebViewScreenUIState.kt
│       │       │   └── WebViewScreenUIStateEvents.kt
│       │       └── view_model/
│       │           └── WebViewScreenViewModel.kt
│       └── ui/
│           └── web_view/
│               └── screen/
│                   ├── WebViewScreen.kt
│                   └── WebViewScreenUI.kt
├── shared/
│   └── ui/
│       ├── analytics/
│       │   ├── AnalyticsKit.kt
│       │   └── FirebaseAnalyticsKitImpl.kt
│       ├── app/
│       │   ├── BarcodesApp.kt
│       │   └── BarcodesAppUI.kt
│       ├── common/
│       │   ├── CommonScreenUIState.kt
│       │   ├── error_screen/
│       │   │   └── ErrorScreenUI.kt
│       │   └── web_view/
│       │       └── WebView.kt
│       ├── components/
│       │   └── CameraPermissionPermanentlyDeniedDialog.kt
│       ├── constants/
│       │   ├── AppConstants.kt
│       │   ├── ClipboardConstants.kt
│       │   ├── DeeplinkConstants.kt
│       │   └── TestTags.kt
│       ├── icons/
│       │   └── BarcodesIcons.kt
│       ├── permissions/
│       │   ├── CameraPermissionStatus.kt
│       │   ├── PermissionRequest.kt
│       │   ├── PermissionStatus.kt
│       │   └── RequiredPermissionStatus.kt
│       ├── play_store_review/
│       │   └── PlayStoreReviewHandler.kt
│       └── testing/
│           └── TestData.kt
└── platform/
    ├── activity/
    │   └── BarcodesActivity.kt
    └── application/
        └── BarcodesApplication.kt
```

Note: Under `features/settings/`, the `presentation/settings/view_model/SettingsScreenViewModel.kt` path is used to match the current split (settings vs credits). Adjust subpackages to match your existing `settings` vs `credits` structure (e.g. if Credits and Settings ViewModels are in different packages, keep that under `features/settings/presentation/`).

---

## 3. Complete File Relocation Map

Base: `barcodes/src/main/java/com/makeappssimple/abhimanyu/barcodes/android/`

| Current path | New path |
|--------------|----------|
| **Core — Data** | |
| `common/data/barcode/BarcodeDao.kt` | `core/data/barcode/BarcodeDao.kt` |
| `common/data/barcode/fake/FakeBarcodeDao.kt` | `core/data/barcode/fake/FakeBarcodeDao.kt` |
| `common/data/database/constants/DatabaseConstants.kt` | `core/data/database/constants/DatabaseConstants.kt` |
| `common/data/database/converters/BarcodeSourceConverter.kt` | `core/data/database/converters/BarcodeSourceConverter.kt` |
| `common/data/database/di/RoomModule.kt` | `core/data/database/di/RoomModule.kt` |
| `common/data/database/local/BarcodesRoomDatabase.kt` | `core/data/database/local/BarcodesRoomDatabase.kt` |
| `common/data/database/migrations/AutoDatabaseMigration.kt` | `core/data/database/migrations/AutoDatabaseMigration.kt` |
| `common/data/database/migrations/ManualDatabaseMigration.kt` | `core/data/database/migrations/ManualDatabaseMigration.kt` |
| `common/data/di/DaosModule.kt` | `core/data/di/DaosModule.kt` |
| `common/data/mapper/BarcodeDataToDomainMapper.kt` | `core/data/mapper/BarcodeDataToDomainMapper.kt` |
| `common/data/mapper/BarcodeDomainToDataMapper.kt` | `core/data/mapper/BarcodeDomainToDataMapper.kt` |
| `common/data/model/BarcodeDataModel.kt` | `core/data/model/BarcodeDataModel.kt` |
| `common/data/model/BarcodeFormatDataModel.kt` | `core/data/model/BarcodeFormatDataModel.kt` |
| `common/data/model/BarcodeSourceDataModel.kt` | `core/data/model/BarcodeSourceDataModel.kt` |
| `common/data/repository/barcode/BarcodeRepositoryImpl.kt` | `core/data/repository/barcode/BarcodeRepositoryImpl.kt` |
| **Core — DI** | |
| `common/di/BarcodesAppModule.kt` | `core/di/BarcodesAppModule.kt` |
| `common/di/FirebaseModule.kt` | `core/di/FirebaseModule.kt` |
| `common/di/KoinModule.kt` | `core/di/KoinModule.kt` |
| **Core — Domain** | |
| `common/domain/model/BarcodeDomainModel.kt` | `core/domain/model/BarcodeDomainModel.kt` |
| `common/domain/model/BarcodeFormatDomainModel.kt` | `core/domain/model/BarcodeFormatDomainModel.kt` |
| `common/domain/model/BarcodeSourceDomainModel.kt` | `core/domain/model/BarcodeSourceDomainModel.kt` |
| `common/domain/repository/BarcodeRepository.kt` | `core/domain/repository/BarcodeRepository.kt` |
| `common/domain/use_case/barcode/DeleteBarcodesUseCase.kt` | `core/domain/use_case/barcode/DeleteBarcodesUseCase.kt` |
| `common/domain/use_case/barcode/GetAllBarcodesFlowUseCase.kt` | `core/domain/use_case/barcode/GetAllBarcodesFlowUseCase.kt` |
| `common/domain/use_case/barcode/GetBarcodeByIdUseCase.kt` | `core/domain/use_case/barcode/GetBarcodeByIdUseCase.kt` |
| `common/domain/use_case/barcode/InsertBarcodesUseCase.kt` | `core/domain/use_case/barcode/InsertBarcodesUseCase.kt` |
| `common/domain/use_case/barcode/UpdateBarcodesUseCase.kt` | `core/domain/use_case/barcode/UpdateBarcodesUseCase.kt` |
| **Core — Presentation** | |
| `common/presentation/app/BarcodesActivityViewModel.kt` | `core/presentation/app/BarcodesActivityViewModel.kt` |
| `common/presentation/base/ScreenArgs.kt` | `core/presentation/base/ScreenArgs.kt` |
| `common/presentation/base/ScreenSnackbarType.kt` | `core/presentation/base/ScreenSnackbarType.kt` |
| `common/presentation/base/ScreenUIEvent.kt` | `core/presentation/base/ScreenUIEvent.kt` |
| `common/presentation/base/ScreenUIState.kt` | `core/presentation/base/ScreenUIState.kt` |
| `common/presentation/base/ScreenUIStateEvents.kt` | `core/presentation/base/ScreenUIStateEvents.kt` |
| `common/presentation/base/ScreenViewModel.kt` | `core/presentation/base/ScreenViewModel.kt` |
| `common/presentation/mapper/BarcodeDomainToUiMapper.kt` | `core/presentation/mapper/BarcodeDomainToUiMapper.kt` |
| `common/presentation/mapper/BarcodeUiToDomainMapper.kt` | `core/presentation/mapper/BarcodeUiToDomainMapper.kt` |
| `common/presentation/model/BarcodeFormatUiModel.kt` | `core/presentation/model/BarcodeFormatUiModel.kt` |
| `common/presentation/model/BarcodeSourceUiModel.kt` | `core/presentation/model/BarcodeSourceUiModel.kt` |
| `common/presentation/model/BarcodeUiModel.kt` | `core/presentation/model/BarcodeUiModel.kt` |
| `common/presentation/navigation/BarcodesNavGraph.kt` | `core/presentation/navigation/BarcodesNavGraph.kt` |
| `common/presentation/navigation/BarcodesNavHost.kt` | `core/presentation/navigation/BarcodesNavHost.kt` |
| `common/presentation/navigation/BarcodesNavigationDirections.kt` | `core/presentation/navigation/BarcodesNavigationDirections.kt` |
| `common/presentation/navigation/NavigationCommand.kt` | `core/presentation/navigation/NavigationCommand.kt` |
| `common/presentation/navigation/NavigationKit.kt` | `core/presentation/navigation/NavigationKit.kt` |
| `common/presentation/navigation/NavigationKitImpl.kt` | `core/presentation/navigation/NavigationKitImpl.kt` |
| `common/presentation/navigation/Screen.kt` | `core/presentation/navigation/Screen.kt` |
| `common/presentation/navigation/constants/DeeplinkUrl.kt` | `core/presentation/navigation/constants/DeeplinkUrl.kt` |
| `common/presentation/navigation/constants/NavigationArguments.kt` | `core/presentation/navigation/constants/NavigationArguments.kt` |
| **Feature: barcode_details** | |
| `common/presentation/feature/barcode_details/barcode_details/event/BarcodeDetailsScreenUIEvent.kt` | `features/barcode_details/presentation/barcode_details/event/BarcodeDetailsScreenUIEvent.kt` |
| `common/presentation/feature/barcode_details/barcode_details/event/BarcodeDetailsScreenUIEventHandler.kt` | `features/barcode_details/presentation/barcode_details/event/BarcodeDetailsScreenUIEventHandler.kt` |
| `common/presentation/feature/barcode_details/barcode_details/snackbar/BarcodeDetailsScreenSnackbarType.kt` | `features/barcode_details/presentation/barcode_details/snackbar/BarcodeDetailsScreenSnackbarType.kt` |
| `common/presentation/feature/barcode_details/barcode_details/state/BarcodeDetailsScreenUIState.kt` | `features/barcode_details/presentation/barcode_details/state/BarcodeDetailsScreenUIState.kt` |
| `common/presentation/feature/barcode_details/barcode_details/state/BarcodeDetailsScreenUIStateEvents.kt` | `features/barcode_details/presentation/barcode_details/state/BarcodeDetailsScreenUIStateEvents.kt` |
| `common/presentation/feature/barcode_details/barcode_details/view_model/BarcodeDetailsScreenViewModel.kt` | `features/barcode_details/presentation/barcode_details/view_model/BarcodeDetailsScreenViewModel.kt` |
| `common/presentation/feature/barcode_details/navigation/BarcodeDetailsNavGraph.kt` | `features/barcode_details/presentation/navigation/BarcodeDetailsNavGraph.kt` |
| `common/presentation/feature/barcode_details/navigation/BarcodeDetailsScreenArgs.kt` | `features/barcode_details/presentation/navigation/BarcodeDetailsScreenArgs.kt` |
| `common/ui/feature/barcode_details/barcode_details/screen/BarcodeDetailsScreen.kt` | `features/barcode_details/ui/barcode_details/screen/BarcodeDetailsScreen.kt` |
| `common/ui/feature/barcode_details/barcode_details/screen/BarcodeDetailsScreenUI.kt` | `features/barcode_details/ui/barcode_details/screen/BarcodeDetailsScreenUI.kt` |
| **Feature: create_barcode** | |
| `common/presentation/feature/create_barcode/create_barcode/event/CreateBarcodeScreenUIEvent.kt` | `features/create_barcode/presentation/create_barcode/event/CreateBarcodeScreenUIEvent.kt` |
| `common/presentation/feature/create_barcode/create_barcode/event/CreateBarcodeScreenUIEventHandler.kt` | `features/create_barcode/presentation/create_barcode/event/CreateBarcodeScreenUIEventHandler.kt` |
| `common/presentation/feature/create_barcode/create_barcode/snackbar/CreateBarcodeScreenSnackbarType.kt` | `features/create_barcode/presentation/create_barcode/snackbar/CreateBarcodeScreenSnackbarType.kt` |
| `common/presentation/feature/create_barcode/create_barcode/state/CreateBarcodeScreenUIState.kt` | `features/create_barcode/presentation/create_barcode/state/CreateBarcodeScreenUIState.kt` |
| `common/presentation/feature/create_barcode/create_barcode/state/CreateBarcodeScreenUIStateEvents.kt` | `features/create_barcode/presentation/create_barcode/state/CreateBarcodeScreenUIStateEvents.kt` |
| `common/presentation/feature/create_barcode/create_barcode/view_model/CreateBarcodeScreenViewModel.kt` | `features/create_barcode/presentation/create_barcode/view_model/CreateBarcodeScreenViewModel.kt` |
| `common/presentation/feature/create_barcode/navigation/CreateBarcodeNavGraph.kt` | `features/create_barcode/presentation/navigation/CreateBarcodeNavGraph.kt` |
| `common/presentation/feature/create_barcode/navigation/CreateBarcodeScreenArgs.kt` | `features/create_barcode/presentation/navigation/CreateBarcodeScreenArgs.kt` |
| `common/ui/feature/create_barcode/create_barcode/screen/CreateBarcodeScreen.kt` | `features/create_barcode/ui/create_barcode/screen/CreateBarcodeScreen.kt` |
| `common/ui/feature/create_barcode/create_barcode/screen/CreateBarcodeScreenUI.kt` | `features/create_barcode/ui/create_barcode/screen/CreateBarcodeScreenUI.kt` |
| **Feature: home** | |
| `common/presentation/feature/home/home/event/HomeScreenUIEvent.kt` | `features/home/presentation/home/event/HomeScreenUIEvent.kt` |
| `common/presentation/feature/home/home/event/HomeScreenUIEventHandler.kt` | `features/home/presentation/home/event/HomeScreenUIEventHandler.kt` |
| `common/presentation/feature/home/home/snackbar/HomeScreenSnackbarType.kt` | `features/home/presentation/home/snackbar/HomeScreenSnackbarType.kt` |
| `common/presentation/feature/home/home/state/HomeScreenUIState.kt` | `features/home/presentation/home/state/HomeScreenUIState.kt` |
| `common/presentation/feature/home/home/state/HomeScreenUIStateEvents.kt` | `features/home/presentation/home/state/HomeScreenUIStateEvents.kt` |
| `common/presentation/feature/home/home/view_model/HomeScreenViewModel.kt` | `features/home/presentation/home/view_model/HomeScreenViewModel.kt` |
| `common/presentation/feature/home/navigation/HomeNavGraph.kt` | `features/home/presentation/navigation/HomeNavGraph.kt` |
| `common/ui/feature/home/home/bottom_sheet/HomeCosmosBottomSheetType.kt` | `features/home/ui/home/bottom_sheet/HomeCosmosBottomSheetType.kt` |
| `common/ui/feature/home/home/bottom_sheet/HomeMenuBottomSheet.kt` | `features/home/ui/home/bottom_sheet/HomeMenuBottomSheet.kt` |
| `common/ui/feature/home/home/dialog/HomeDeleteBarcodeDialog.kt` | `features/home/ui/home/dialog/HomeDeleteBarcodeDialog.kt` |
| `common/ui/feature/home/home/screen/HomeScreen.kt` | `features/home/ui/home/screen/HomeScreen.kt` |
| `common/ui/feature/home/home/screen/HomeScreenUI.kt` | `features/home/ui/home/screen/HomeScreenUI.kt` |
| **Feature: scan_barcode** | |
| `common/presentation/feature/scan_barcode/scan_barcode/event/ScanBarcodeScreenUIEvent.kt` | `features/scan_barcode/presentation/scan_barcode/event/ScanBarcodeScreenUIEvent.kt` |
| `common/presentation/feature/scan_barcode/scan_barcode/event/ScanBarcodeScreenUIEventHandler.kt` | `features/scan_barcode/presentation/scan_barcode/event/ScanBarcodeScreenUIEventHandler.kt` |
| `common/presentation/feature/scan_barcode/scan_barcode/snackbar/ScanBarcodeScreenSnackbarType.kt` | `features/scan_barcode/presentation/scan_barcode/snackbar/ScanBarcodeScreenSnackbarType.kt` |
| `common/presentation/feature/scan_barcode/scan_barcode/state/ScanBarcodeScreenUIState.kt` | `features/scan_barcode/presentation/scan_barcode/state/ScanBarcodeScreenUIState.kt` |
| `common/presentation/feature/scan_barcode/scan_barcode/view_model/ScanBarcodeScreenViewModel.kt` | `features/scan_barcode/presentation/scan_barcode/view_model/ScanBarcodeScreenViewModel.kt` |
| `common/presentation/feature/scan_barcode/navigation/ScanBarcodeNavGraph.kt` | `features/scan_barcode/presentation/navigation/ScanBarcodeNavGraph.kt` |
| `common/presentation/feature/scan_barcode/navigation/ScanBarcodeScreenArgs.kt` | `features/scan_barcode/presentation/navigation/ScanBarcodeScreenArgs.kt` |
| `common/ui/barcode_scanner/barcode_scanner/BarcodeAnalyser.kt` | `features/scan_barcode/ui/barcode_scanner/barcode_scanner/BarcodeAnalyser.kt` |
| `common/ui/barcode_scanner/camera/BarcodeScannerPreview.kt` | `features/scan_barcode/ui/barcode_scanner/camera/BarcodeScannerPreview.kt` |
| `common/ui/barcode_scanner/di/BarcodeScannerModule.kt` | `features/scan_barcode/ui/barcode_scanner/di/BarcodeScannerModule.kt` |
| `common/ui/feature/scan_barcode/scan_barcode/screen/ScanBarcodeScreen.kt` | `features/scan_barcode/ui/scan_barcode/screen/ScanBarcodeScreen.kt` |
| `common/ui/feature/scan_barcode/scan_barcode/screen/ScanBarcodeScreenUI.kt` | `features/scan_barcode/ui/scan_barcode/screen/ScanBarcodeScreenUI.kt` |
| **Feature: settings** | |
| `common/presentation/feature/settings/credits/event/CreditsScreenUIEvent.kt` | `features/settings/presentation/credits/event/CreditsScreenUIEvent.kt` |
| `common/presentation/feature/settings/credits/event/CreditsScreenUIEventHandler.kt` | `features/settings/presentation/credits/event/CreditsScreenUIEventHandler.kt` |
| `common/presentation/feature/settings/credits/view_model/CreditsScreenViewModel.kt` | `features/settings/presentation/credits/view_model/CreditsScreenViewModel.kt` |
| `common/presentation/feature/settings/navigation/SettingsNavGraph.kt` | `features/settings/presentation/navigation/SettingsNavGraph.kt` |
| `common/presentation/feature/settings/settings/event/SettingsScreenUIEvent.kt` | `features/settings/presentation/settings/event/SettingsScreenUIEvent.kt` |
| `common/presentation/feature/settings/settings/event/SettingsScreenUIEventHandler.kt` | `features/settings/presentation/settings/event/SettingsScreenUIEventHandler.kt` |
| `common/presentation/feature/settings/settings/view_model/SettingsScreenViewModel.kt` | `features/settings/presentation/settings/view_model/SettingsScreenViewModel.kt` |
| `common/ui/feature/settings/credits/screen/CreditsScreen.kt` | `features/settings/ui/credits/screen/CreditsScreen.kt` |
| `common/ui/feature/settings/credits/screen/CreditsScreenUI.kt` | `features/settings/ui/credits/screen/CreditsScreenUI.kt` |
| `common/ui/feature/settings/settings/screen/SettingsScreen.kt` | `features/settings/ui/settings/screen/SettingsScreen.kt` |
| `common/ui/feature/settings/settings/screen/SettingsScreenUI.kt` | `features/settings/ui/settings/screen/SettingsScreenUI.kt` |
| **Feature: web_view** | |
| `common/presentation/feature/web_view/web_view/event/WebViewScreenUIEvent.kt` | `features/web_view/presentation/web_view/event/WebViewScreenUIEvent.kt` |
| `common/presentation/feature/web_view/web_view/event/WebViewScreenUIEventHandler.kt` | `features/web_view/presentation/web_view/event/WebViewScreenUIEventHandler.kt` |
| `common/presentation/feature/web_view/web_view/state/WebViewScreenUIState.kt` | `features/web_view/presentation/web_view/state/WebViewScreenUIState.kt` |
| `common/presentation/feature/web_view/web_view/state/WebViewScreenUIStateEvents.kt` | `features/web_view/presentation/web_view/state/WebViewScreenUIStateEvents.kt` |
| `common/presentation/feature/web_view/web_view/view_model/WebViewScreenViewModel.kt` | `features/web_view/presentation/web_view/view_model/WebViewScreenViewModel.kt` |
| `common/presentation/feature/web_view/navigation/WebViewNavGraph.kt` | `features/web_view/presentation/navigation/WebViewNavGraph.kt` |
| `common/presentation/feature/web_view/navigation/WebViewScreenArgs.kt` | `features/web_view/presentation/navigation/WebViewScreenArgs.kt` |
| `common/ui/feature/web_view/web_view/screen/WebViewScreen.kt` | `features/web_view/ui/web_view/screen/WebViewScreen.kt` |
| `common/ui/feature/web_view/web_view/screen/WebViewScreenUI.kt` | `features/web_view/ui/web_view/screen/WebViewScreenUI.kt` |
| **Shared UI** | |
| `common/ui/analytics/AnalyticsKit.kt` | `shared/ui/analytics/AnalyticsKit.kt` |
| `common/ui/analytics/FirebaseAnalyticsKitImpl.kt` | `shared/ui/analytics/FirebaseAnalyticsKitImpl.kt` |
| `common/ui/app/BarcodesApp.kt` | `shared/ui/app/BarcodesApp.kt` |
| `common/ui/app/BarcodesAppUI.kt` | `shared/ui/app/BarcodesAppUI.kt` |
| `common/ui/common/CommonScreenUIState.kt` | `shared/ui/common/CommonScreenUIState.kt` |
| `common/ui/common/error_screen/ErrorScreenUI.kt` | `shared/ui/common/error_screen/ErrorScreenUI.kt` |
| `common/ui/common/web_view/WebView.kt` | `shared/ui/common/web_view/WebView.kt` |
| `common/ui/components/CameraPermissionPermanentlyDeniedDialog.kt` | `shared/ui/components/CameraPermissionPermanentlyDeniedDialog.kt` |
| `common/ui/constants/AppConstants.kt` | `shared/ui/constants/AppConstants.kt` |
| `common/ui/constants/ClipboardConstants.kt` | `shared/ui/constants/ClipboardConstants.kt` |
| `common/ui/constants/DeeplinkConstants.kt` | `shared/ui/constants/DeeplinkConstants.kt` |
| `common/ui/constants/TestTags.kt` | `shared/ui/constants/TestTags.kt` |
| `common/ui/icons/BarcodesIcons.kt` | `shared/ui/icons/BarcodesIcons.kt` |
| `common/ui/permissions/CameraPermissionStatus.kt` | `shared/ui/permissions/CameraPermissionStatus.kt` |
| `common/ui/permissions/PermissionRequest.kt` | `shared/ui/permissions/PermissionRequest.kt` |
| `common/ui/permissions/PermissionStatus.kt` | `shared/ui/permissions/PermissionStatus.kt` |
| `common/ui/permissions/RequiredPermissionStatus.kt` | `shared/ui/permissions/RequiredPermissionStatus.kt` |
| `common/ui/play_store_review/PlayStoreReviewHandler.kt` | `shared/ui/play_store_review/PlayStoreReviewHandler.kt` |
| `common/ui/testing/TestData.kt` | `shared/ui/testing/TestData.kt` |
| **Platform** | |
| `platform/activity/BarcodesActivity.kt` | `platform/activity/BarcodesActivity.kt` (unchanged) |
| `platform/application/BarcodesApplication.kt` | `platform/application/BarcodesApplication.kt` (unchanged) |

---

## 4. Package Name Updates

After moving files, update the `package` declaration in each file to match its new path. The package is derived from the path under `.../barcodes/android/` with path segments joined by `.`.

Examples:

| New path (under `.../android/`) | New package |
|----------------------------------|-------------|
| `core/domain/repository/BarcodeRepository.kt` | `com.makeappssimple.abhimanyu.barcodes.android.core.domain.repository` |
| `features/home/presentation/home/view_model/HomeScreenViewModel.kt` | `com.makeappssimple.abhimanyu.barcodes.android.features.home.presentation.home.view_model` |
| `features/scan_barcode/ui/barcode_scanner/di/BarcodeScannerModule.kt` | `com.makeappssimple.abhimanyu.barcodes.android.features.scan_barcode.ui.barcode_scanner.di` |
| `shared/ui/analytics/AnalyticsKit.kt` | `com.makeappssimple.abhimanyu.barcodes.android.shared.ui.analytics` |

Apply this rule to every moved file and update all `import` statements across the module to use the new packages.

---

## 5. Implementation Steps (Detailed)

### Step 1: Create new directory structure

- Create all directories under `barcodes/src/main/java/com/makeappssimple/abhimanyu/barcodes/android/` as in Section 2 (core, features/*, shared/ui, platform unchanged).
- Do not delete `common/` yet.

### Step 2: Move Core files

- Move every file listed in Section 3 under **Core — Data**, **Core — DI**, **Core — Domain**, and **Core — Presentation** to its **New path**.
- After each move (or batch), update the file’s `package` and fix any internal imports so that core compiles.

### Step 3: Move Feature files (one feature at a time)

- For each feature (e.g. **barcode_details**, then **create_barcode**, **home**, **scan_barcode**, **settings**, **web_view**):
  - Move all presentation and UI files for that feature to the paths in Section 3.
  - Update `package` in each moved file.
  - Update imports in those files to point to `core.*`, `shared.ui.*`, and other features as needed.
  - Ensure that feature does not depend on `common.*`; only on `core.*`, `shared.ui.*`, or other feature packages where allowed.

### Step 4: Move Shared UI files

- Move all files under **Shared UI** in Section 3 to `shared/ui/...`.
- Update packages and imports. Ensure `core` and `features` reference `shared.ui` where appropriate (e.g. analytics, app, common components, constants).

### Step 5: Update DI (Koin) modules

- In `core/di/BarcodesAppModule.kt`, `KoinModule.kt`, `FirebaseModule.kt`, and in `core/data/di/`, `core/data/database/di/`, and `features/scan_barcode/ui/barcode_scanner/di/BarcodeScannerModule.kt`:
  - Replace any `common.*` package references with `core.*`, `features.*`, or `shared.ui.*`.
  - Ensure all ViewModels, repositories, use cases, and UI bindings are registered with correct class names and packages.
- Re-run KSP/annotation processors if the project uses Koin generated modules so that generated code points to the new packages.

### Step 6: Update BarcodesNavGraph and navigation references

- `core/presentation/navigation/BarcodesNavGraph.kt` (and `BarcodesNavHost.kt` if it references composables) must reference:
  - Screen composables from `features.*.ui.*` (e.g. `features.home.ui.home.screen.HomeScreen`, `features.barcode_details.ui.barcode_details.screen.BarcodeDetailsScreen`).
  - Nav graphs from feature presentation (e.g. `features.home.presentation.navigation.HomeNavGraph`, `features.barcode_details.presentation.navigation.BarcodeDetailsNavGraph`).
- Update all navigation-related imports and route/screen references to use the new packages.

### Step 7: Update platform and app entrypoints

- In `platform/activity/BarcodesActivity.kt` and `platform/application/BarcodesApplication.kt`, update imports that pointed to `common.*` to `core.*`, `shared.ui.*`, or `features.*` as appropriate (e.g. `BarcodesAppUI`, `BarcodesApp`).
- In `shared/ui/app/BarcodesApp.kt` and `BarcodesAppUI.kt`, update imports to use `core.presentation.navigation.*`, `core.presentation.app.*`, and feature UI packages.

### Step 8: Remove old `common/` directory

- After the project builds and all tests pass, delete the entire `common/` directory under `barcodes/src/main/java/com/makeappssimple/abhimanyu/barcodes/android/`.
- Ensure no remaining references to `common.*` remain (search for `com.makeappssimple.abhimanyu.barcodes.android.common` in the barcodes module).

### Step 9: Update tests

- **Unit tests** (`barcodes/src/test/`): Move and/or update packages so they mirror the new layout, e.g.:
  - `.../core/data/repository/BarcodeRepositoryTest.kt` → keep under a `core`-aligned package.
  - `.../feature/home/.../HomeScreenViewModelTest.kt` → e.g. `.../features/home/presentation/home/view_model/HomeScreenViewModelTest.kt`.
  - `.../core/navigation/NavigationKitTest.kt` → keep under core.
- Update all test imports to use `core.*`, `features.*`, `shared.ui.*`.

- **Android tests** (`barcodes/src/androidTest/`): Move and update packages similarly, e.g.:
  - `.../feature/home/HomeScreenTest.kt` → `.../features/home/HomeScreenTest.kt` (or under `features/home/ui/...` as appropriate).
  - `.../feature/barcode_details/.../BarcodeDetailsScreenUITest.kt`, `CreateBarcodeScreenUITest.kt`, etc. → under `features/barcode_details/...`.
  - `.../core/database/dao/BarcodeDaoTest.kt` → under core.
  - `.../integration/NavigationTest.kt`, `.../test/*` (KoinTestRule, TestApplication, etc.): update imports to new packages.

### Step 10: Resources and manifests

- If any resource (layout, drawable, string) or manifest references a class by full name (e.g. in a theme or component), update those to the new class packages.
- Verify `AndroidManifest.xml` and any ProGuard/R8 rules that reference barcodes packages still match the new structure.

### Step 11: Lint and build

- Run the project’s lint task and fix any remaining path/import issues.
- Build the barcodes module and the app that depends on it; run unit and instrumented tests.

---

## 6. Other Changes to Apply

### 6.1 Imports

- Every file that referenced `com.makeappssimple.abhimanyu.barcodes.android.common.*` must be updated to:
  - `...android.core.*` for domain, data, presentation base, navigation, DI.
  - `...android.features.<feature>.*` for feature presentation and UI.
  - `...android.shared.ui.*` for shared UI.
- Use IDE “Optimize Imports” and a project-wide search for `android.common` to catch leftovers.

### 6.2 Koin / DI

- All module `org.koin.core.module.Module` definitions that use `viewModel()`, `single()`, etc. with classes from the old packages must use the new class names (packages will change automatically once files are moved and packages updated).
- If modules are loaded by string or reflection, ensure they still resolve (e.g. Koin module loading from `core.di` and feature DI modules).

### 6.3 Feature-to-feature and feature-to-core references

- Enforce that:
  - Features do not import other features’ UI or presentation details unless necessary (prefer navigation/events via core).
  - Features depend on `core` for domain, data, and shared presentation/navigation.
  - Shared UI lives in `shared.ui` and is used by core and features.

### 6.4 Settings feature structure

- The current codebase has both “settings” and “credits” under the same feature. In Section 2, credits is under `features/settings/presentation/credits/` and `features/settings/ui/credits/`. If your existing structure differs (e.g. separate packages for settings vs credits), keep the same logical split under `features/settings/` and adjust the table in Section 3 accordingly.

### 6.5 Web_view view_model path

- In Section 2, `WebViewScreenViewModel.kt` is under `features/web_view/presentation/web_view/view_model/`. The table in Section 3 reflects that. Ensure `WebViewScreenUIState` and `WebViewScreenUIStateEvents` stay next to the ViewModel in the same feature.

---

## 7. Verification Checklist

- [ ] All files from Section 3 have been moved to the listed **New path**.
- [ ] Every moved file has a `package` declaration matching its new path.
- [ ] No file in the barcodes module imports `com.makeappssimple.abhimanyu.barcodes.android.common`.
- [ ] Core compiles (domain, data, presentation base, navigation, DI).
- [ ] Each feature compiles (presentation + ui).
- [ ] Shared UI compiles and is referenced only via `shared.ui.*`.
- [ ] Platform and app entrypoints use new packages.
- [ ] Koin/DI modules load and resolve all beans.
- [ ] BarcodesNavGraph and navigation use new feature UI and nav graph packages.
- [ ] Unit and Android tests updated and passing.
- [ ] `common/` directory removed.
- [ ] Lint passes; release build succeeds.

---

## 8. References

- [BARCODES_ARCHITECTURE_ENHANCEMENTS.md](./BARCODES_ARCHITECTURE_ENHANCEMENTS.md) — Enhancement 6 and overall barcodes architecture.
- Project architecture guidelines (if any) under `docs/`.
