# Finance‑Manager Enhancements – Action Items (ordered by impact)

1. **Refactor ViewModels**
   - Extract validation logic into a domain `AddAccountValidationUseCase`.
   - Introduce a `NavigationHandler` interface (domain) and inject the concrete `NavigationKit`.
   - Split responsibilities: ViewModel only exposes UI state & handles UI events.
   - Add a shared `BaseViewModel` for logging/loading helpers.

2. **Improve Dependency Injection**
   - Define feature‑scoped Koin modules (e.g., `accountsModule`).
   - Bind domain interfaces to implementations in a dedicated `dataModule`.
   - Provide a `ViewModelFactory` that injects only required use‑cases.

3. **Strengthen Testing Strategy**
   - Unit‑test use‑cases with a fake `AccountRepository`.
   - Instrumented tests for `AccountRepositoryImpl` using an in‑memory Room DB.
   - ViewModel tests using `kotlinx.coroutines.test` and mocked use‑cases.
   - Keep existing Compose UI tests; add screen‑level tests that mock the ViewModel.
   - Add an end‑to‑end test covering the full add‑account flow.

4. **Documentation & Conventions**
   - Add a `README-architecture.md` summarising layer diagram and rules.
   - Enforce a Detekt rule (`ForbiddenImport`) to prevent illegal imports.
   - Standardise naming: `*UseCase`, `*Repository`, `*RepositoryImpl`.
