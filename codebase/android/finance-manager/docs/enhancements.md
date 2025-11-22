# Finance‑Manager Architectural Enhancements

## Overview
This document captures the architectural observations and improvement recommendations for the **finance‑manager** module. It is intended for developers onboarding the project and for planning refactors.

---

## 1️⃣ Current State
| Layer | Packages / Key Files | Observations |
|------|----------------------|--------------|
| **Presentation** | `presentation/feature/*/view_model`, `presentation/feature/*/screen`, `presentation/feature/*/ui` | MVVM is used, but ViewModels mix UI‑state, navigation, logging and validation logic. |
| **Domain** | `domain/model`, `domain/constants` | Contains only data models and constants; no use‑case classes. |
| **Data** | `data/data/use_case`, `data/data/repository`, `data/data/repository/impl` | All use‑cases and the **repository interface** live here, breaking Clean‑Architecture boundaries. |
| **DI / Koin** | Various modules | Bindings are scattered across the module rather than being feature‑scoped. |
| **Testing** | `src/test/java/presentation`, `src/androidTest/java/...` | Limited unit‑test coverage for core business logic; tests focus on UI components. |

---

## 2️⃣ Architectural Gaps & Risks
- **Use‑cases in Data layer** – violates dependency rule; makes reuse and testing harder.
- **Repository interface in Data layer** – tight coupling; prevents swapping data sources.
- **Fat ViewModels** – low cohesion, harder to maintain, validation logic duplicated.
- **Scattered DI bindings** – increased start‑up time, unclear ownership of dependencies.
- **Sparse unit‑test coverage** – business‑logic bugs may surface only at UI level.
- **Monolithic feature folder** – slows compile times as the module grows.

---

## 3️⃣ Recommended Improvements
### 3.1 Re‑organize Clean‑Architecture boundaries
- Move **all use‑case classes** to `domain/use_case`.
- Create `domain/repository` and place the `AccountRepository` interface there.
- Keep repository **implementations** (`AccountRepositoryImpl`) in `data/repository/impl`.
- Optionally add thin wrappers in `data/use_case` that delegate to domain use‑cases.

### 3.2 Refactor ViewModels
- Extract validation logic into a domain `AddAccountValidationUseCase`.
- Introduce a `NavigationHandler` interface (domain) and inject the concrete `NavigationKit`.
- Split responsibilities: ViewModel only exposes UI state & handles UI events.
- Add a shared `BaseViewModel` for logging/loading helpers.

### 3.3 Improve Dependency Injection
- Define **feature‑scoped Koin modules** (e.g., `accountsModule`).
- Bind domain interfaces to implementations in a dedicated `dataModule`.
- Provide a `ViewModelFactory` that injects only required use‑cases.

### 3.4 Strengthen Testing Strategy
- Unit‑test use‑cases with a fake `AccountRepository`.
- Instrumented tests for `AccountRepositoryImpl` using an in‑memory Room DB.
- ViewModel tests using `kotlinx.coroutines.test` and mocked use‑cases.
- Keep existing Compose UI tests; add screen‑level tests that mock the ViewModel.
- End‑to‑end test covering the full add‑account flow.

### 3.5 Modularization & Build Optimisation
- Split the module into separate Gradle modules: `domain`, `data`, `presentation` (or per‑feature).
- Use `api` vs `implementation` correctly to enforce boundaries.

### 3.6 Documentation & Conventions
- Add a `README-architecture.md` summarising layer diagram and rules.
- Enforce a Detekt rule (`ForbiddenImport`) to prevent illegal imports.
- Standardise naming: `*UseCase`, `*Repository`, `*RepositoryImpl`.

---

## 4️⃣ Quick‑Start Refactoring Checklist
1. Create `domain/use_case` and move all use‑case files.
2. Create `domain/repository` and move `AccountRepository` interface.
3. Update Koin modules to bind `AccountRepository` → `AccountRepositoryImpl`.
4. Adjust imports throughout the codebase.
5. Extract validation logic into a new domain use‑case.
6. Add unit tests for the moved use‑cases.
7. Introduce feature‑scoped Koin modules (`accountsModule`).
8. Document the new architecture in `README-architecture.md`.

---

## 5️⃣ Next Steps
- Prioritise moving the use‑cases and repository interface to enforce clean boundaries.
- Refactor the `AddAccountScreenViewModel` to use the new validation use‑case and navigation handler.
- Incrementally add the suggested unit tests to keep the build green.
- Once the core refactor is stable, consider splitting the module into separate Gradle sub‑modules.

---

*Prepared on 2025‑11‑22.*
