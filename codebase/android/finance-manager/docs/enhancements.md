# Finance‑Manager Enhancements – Action Items (ordered by impact)

1. **Re‑organize Clean‑Architecture boundaries**
   - Move all use‑case classes to `domain/use_case/`.
   - Create `domain/repository/` and move the `AccountRepository` interface there.
   - Keep repository implementations in `data/repository/impl/`.
   - Update imports and Koin bindings accordingly.

2. **Refactor ViewModels**
   - Extract validation logic into a domain `AddAccountValidationUseCase`.
   - Introduce a `NavigationHandler` interface (domain) and inject the concrete `NavigationKit`.
   - Split responsibilities: ViewModel only exposes UI state & handles UI events.
   - Add a shared `BaseViewModel` for logging/loading helpers.

3. **Improve Dependency Injection**
   - Define feature‑scoped Koin modules (e.g., `accountsModule`).
   - Bind domain interfaces to implementations in a dedicated `dataModule`.
   - Provide a `ViewModelFactory` that injects only required use‑cases.

4. **Strengthen Testing Strategy**
   - Unit‑test use‑cases with a fake `AccountRepository`.
   - Instrumented tests for `AccountRepositoryImpl` using an in‑memory Room DB.
   - ViewModel tests using `kotlinx.coroutines.test` and mocked use‑cases.
   - Keep existing Compose UI tests; add screen‑level tests that mock the ViewModel.
   - Add an end‑to‑end test covering the full add‑account flow.

5. **Modularize & Optimise Build**
   - Split the module into separate Gradle modules: `domain`, `data`, `presentation` (or per‑feature).
   - Use `api` vs `implementation` correctly to enforce boundaries.

6. **Documentation & Conventions**
   - Add a `README-architecture.md` summarising layer diagram and rules.
   - Enforce a Detekt rule (`ForbiddenImport`) to prevent illegal imports.
   - Standardise naming: `*UseCase`, `*Repository`, `*RepositoryImpl`.
