# Architecture Guidelines

This document outlines the architectural patterns and coding standards followed in the project. It serves as a reference for developers and coding agents to ensure consistency and maintainability.

## 1. Top-Level Structure (KMP Readiness)

The codebase is structured to facilitate potential future migration to Kotlin Multiplatform (KMP).

* **`platform`**: Contains Android-specific implementations that rely on the Android SDK (e.g.,
  `activity`, `application`, system services, intended `actual` declarations).
* **`common`**: Contains platform-agnostic code (intended
  `commonMain`), following Clean Architecture principles. This is where the majority of the application logic and UI resides.

## 2. Clean Architecture Layers (inside `common`)

The `common` package is further divided into the standard Clean Architecture layers.
**Dependency Rule**: `UI → Presentation → Domain ← Data`

### a. Domain (`domain`)

* **Responsibility**: The core business logic of the application.
* **Dependencies
  **: Pure Kotlin modules. No dependencies on Android framework or other layers (Data, Presentation, UI).
* **Contents**:
    * `model`: Domain models (suffix: `DomainModel`).
    * `use_case`: Business logic units (suffix: `UseCase`).
    * `repository`: Repository interfaces defining data operations.

### b. Data (`data`)

* **Responsibility**: Managing data persistence and retrieval (Single Source of Truth).
* **Dependencies**: Depends on `domain`.
* **Contents**:
    * `repository`: Implementation of domain repository interfaces.
    * `source`: Data sources (e.g., `database`, `network`).
    * `model`: Data entities (suffix: `DataModel`).
    * `mapper`: Mappers to convert between `DataModel` and `DomainModel` to ensure decoupling.

### c. Presentation (`presentation`)

* **Responsibility**: State management, navigation, and preparing data for the UI.
* **Dependencies**: Depends on `domain`.
* **Interaction**: ViewModels interact with the Domain layer exclusively via **Use Cases**.
* **Contents**:
    * `view_model`: ViewModels (suffix: `ViewModel`).
    * `model`: UI state models (suffix: `UiModel`).
    * `mapper`: Mappers to convert between `DomainModel` and `UiModel`.
    * `navigation`: Navigation logic and definitions.

### d. UI (`ui`)

* **Responsibility**: Rendering the user interface as a **Passive View**.
* **Dependencies**: Depends on `presentation`.
* **Contents**:
    * `feature`: Composable screens and feature-specific components.
    * `theme`: Design system and theming.

### e. DI (`di`)

* **Responsibility**: Dependency Injection configuration (using Koin).

## 3. Naming Conventions

* **Models**: Suffix classes with their layer.
    * Data Layer: `*DataModel`
    * Domain Layer: `*DomainModel`
    * Presentation/UI Layer: `*UiModel`
* **Use Cases**: Suffix with `UseCase` (e.g., `GetBarcodesUseCase`).
* **ViewModels**: Suffix with `ViewModel` (e.g., `HomeScreenViewModel`).
* **Screens**: Suffix top-level screen Composables with `UI` (e.g., `HomeScreenUI`).
* **Mappers**: Format `SourceToDestinationMapper` (e.g., `BarcodeUiToDomainMapper`,
  `BarcodeDataToDomainMapper`).

## 4. Feature Directory Structure

Features are organized hierarchically within `presentation` and
`ui` packages to keep related files together.

**Presentation Layer** (`common/presentation/feature/<feature>/<screen>/`)

* `view_model/`: Contains the `ViewModel`.
* `state/`:
    * `*UIState`: Data class representing the screen state.
    * `*UIStateEvents`: Interface/class for event callbacks handled by the ViewModel.
* `event/`: `*UIEvent` (Sealed interface) for events triggered from the UI.

**UI Layer** (`common/ui/feature/<feature>/<screen>/`)

* `screen/`: Contains the main screen Composable (e.g., `HomeScreenUI.kt`).
* `components/`: Sub-components specific to the screen.
* `bottom_sheet/`, `dialog/`: specific UI elements.

## 5. State Management Pattern

* **Unidirectional Data Flow**:
    * The `ViewModel` exposes a `uiState` (usually a `StateFlow`) and `uiStateEvents`.
    * The **UI** observes `uiState` to render the interface.
    * The **UI** triggers events (via function callbacks or an
      `handleUIEvent` lambda) which are processed by the `ViewModel`.

## 6. References

* Based on "Clean Architecture for Android" concepts (e.g., by Eran Boudjnah), adapted for KMP and Jetpack Compose.
