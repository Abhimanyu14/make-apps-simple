# Cosmos Design System – Enhancement Backlog (Android)

**Package:** `com.makeappssimple.abhimanyu.cosmos.design.system.android`  
**Module:** `cosmos-design-system`  
**Focus areas:** **Consistency**, **Clear UI**, **Design quality**, **Clean code**

---

## 1. Foundations (Theme, Color, Typography)

### 1.1 Color System

- **F1–C1 · Document color tokens and usage**  
  - **Current:** `Color.kt` defines a rich palette (`Brown*`, `Blue*`, `Green*`, grays, error) plus theme colors via `CosmosColor`, but there is no doc mapping tokens → usage (e.g., primary vs accent vs semantic states).  
  - **Change:**  
    - Add a short color spec section in `docs/android.md` (or a new `docs/colors.md`) that:  
      - Groups tokens by **role** (primary, secondary, success, warning, error, surface, border, background, overlays).  
      - Lists **allowed usages** for each (e.g., “`CosmosColor.PRIMARY` → primary actions; `CosmosColor.SECONDARY` → secondary actions, chips; `Error*` → destructive states only”).  
      - Documents light vs dark color behavior (what changes, what stays).  
  - **Benefits:** Better **consistency** in new components and app usage.

- **F1–C2 · Enforce role-based colors in components**  
  - **Current:** Most components use `CosmosAppTheme.colorScheme.*` correctly, but some ad‑hoc choices (e.g. `CosmosText` with manual colors in a few places, `CosmosTopAppBar` TODOs around colors) can drift.  
  - **Change:**  
    - Review all Cosmos components and align their colors strictly with roles:  
      - **Primary actions:** `colorScheme.primary` + `onPrimary`.  
      - **Secondary/surface:** `surface`, `onSurface`, `surfaceVariant`, `onSurfaceVariant`.  
      - **Destructive:** `error`, `onError`.  
    - Add KDoc notes on each public component’s color behavior (e.g. “Uses `primary` as container color and `onPrimary` for label”).  
  - **Benefits:** Predictable **visual hierarchy** and easier design reviews.

- **F1–C3 · Introduce semantic color aliases (optional)**  
  - **Current:** Callers use raw Material roles (`primary`, `error`, `surface`, etc.).  
  - **Change (optional, phased):**  
    - Add a `CosmosSemanticColor` (or extend `CosmosColor`) to expose high‑level intents, e.g. `PrimaryAction`, `SecondaryAction`, `Destructive`, `Info`, `Success`, `Warning`.  
    - Use these semantics inside Cosmos components, mapping internally to Material roles.  
  - **Benefits:** Stronger **design language** and future flexibility if the underlying scheme changes.

### 1.2 Typography & Text System

- **F2–T1 · Align `CosmosTextStyle` with Material 3 roles**  
  - **Current:** `CosmosTextStyle` (Heading1–3, Body1–4, Footnote, Caption, Small1–2) has sizes/weights defined in `CosmosText.kt`, while `cosmosTypography` in `CosmosType.kt` defines Material3 styles independently. There is some overlap but no single canonical mapping.  
  - **Change:**  
    - Define a **mapping table** in docs (or KDoc) from `CosmosTextStyle` → Material typography roles (`display*`, `headline*`, `title*`, `body*`, `label*`).  
    - Ensure `CosmosTextStyle` values are consistent with the design system spec (spacing, line-height, weights).  
    - Where possible, derive `CosmosTextStyle` from `cosmosTypography` instead of re‑stating font sizes/weights in two places.  
  - **Benefits:** Less duplication, more **consistency** and easier maintenance.

- **F2–T2 · Clarify `CosmosText` overload responsibilities**  
  - **Current:**  
    - `CosmosText(stringResource: CosmosStringResource, style: TextStyle)` → thin wrapper over `Text` using `LocalTextStyle`.  
    - `CosmosText(text: String, style: CosmosTextStyle)` → uses `BasicText` with custom font and sizes.  
  - **Change:**  
    - Document clear guidance in docs:  
      - Use `CosmosText(stringResource = …)` for localized strings + Material3 style.  
      - Use `CosmosText(text = …, style = CosmosTextStyle.…)` for design‑system‑specific typography.  
    - Add KDoc to both overloads describing the difference and typical usage scenarios.  
  - **Benefits:** Clearer API; easier for consumers to pick the right overload.

- **F2–T3 · Normalize text alignment & truncation defaults**  
  - **Current:** Text alignment and `maxLines`/`overflow` vary per component (e.g. buttons center text manually, some list items rely on defaults).  
  - **Change:**  
    - Define standard rules (e.g. buttons + chips always center; list titles ellipsize after 1–2 lines; captions use `maxLines = 1`, `overflow = Ellipsis`).  
    - Audit Cosmos components and adjust `CosmosText` calls to match these rules.  
  - **Benefits:** **Clear UI** and predictable behavior across screens.

---

## 2. Components – Consistency & API Design

### 2.1 Buttons & Action Components

- **C1–B1 · Align button API shape**  
  - **Current:** `CosmosElevatedButton`, `CosmosTextButton`, `CosmosFloatingActionButton`, `CosmosCircularFloatingActionButton`, `CosmosActionButton` etc. use slightly different parameter sets (e.g. `stringResource` vs `text`, `onClickLabelStringResource` naming, optional icons, loading flags).  
  - **Change:**  
    - Define a **standard button contract**:  
      - Label: `CosmosStringResource` (primary) + optional icon.  
      - Common parameters: `isEnabled`, `isLoading`, `modifier`, `onClick`.  
      - Optional slots: leading/trailing icons where applicable.  
    - Audit all button‑like components and refactor their signatures to this standard where it doesn’t cause breaking changes, or introduce `v2` APIs with consistent naming and deprecate the older ones.  
  - **Benefits:** More consistent **API surface** and easier mental model for consumers.

- **C1–B2 · Consistent loading states**  
  - **Current:** Some components expose an `isLoading` flag with shimmer placeholders (e.g. `CosmosElevatedButton`, `CosmosOutlinedTextField`), while others simply disable interactions or do nothing.  
  - **Change:**  
    - Decide on a unified loading pattern per component **type**:  
      - Buttons: show shimmer or progress indicator, disable presses.  
      - Text fields: shimmer skeleton and disable edits.  
      - Lists/items: skeleton rows via `cosmosShimmer`.  
    - Ensure all action components that support loading either:  
      - Implement that pattern, or  
      - Explicitly **do not** support loading (and document that).  
  - **Benefits:** More **predictable UX** and visual consistency during loading.

### 2.2 Text Fields & Search

- **C2–TF1 · Consolidate `MyOutlinedTextField*` and `CosmosOutlinedTextField*`**  
  - **Current:** There are legacy `My*` text field components (`MyOutlinedTextField`, `MyOutlinedTextFieldV2`, related data/events/label) alongside newer Cosmos text fields (`CosmosOutlinedTextField`, `CosmosReadOnlyTextField`, `CosmosSearchBar`, etc.). Mixed naming and duplication can confuse consumers.  
  - **Change:**  
    - Decide on the canonical API: likely `CosmosOutlinedTextField`, `CosmosReadOnlyTextField`, and `CosmosSearchBar`.  
    - Mark `My*` components as **internal/experimental** or **@Deprecated** and prepare migration notes.  
    - If `My*` have capabilities that Cosmos variants lack, incorporate those features into the Cosmos APIs before deprecating.  
  - **Benefits:** Stronger brand consistency (**Cosmos‑only** exports) and a simpler surface area.

- **C2–TF2 · Standardize data + event models**  
  - **Current:** Some text components use `Data` + `Event` sealed classes (`MyOutlinedTextFieldDataV2`, `MyOutlinedTextFieldEventV2`), while Cosmos text fields use parameters.  
  - **Change:**  
    - Decide on a single approach per **pattern** (e.g. “simple fields are parameter‑based; complex widgets use `Data` + `Event` for unidirectional data flow”).  
    - Document this in the design‑system docs so features can mirror the pattern.  
  - **Benefits:** Clearer mental model and easier reuse in apps following UDF patterns.

- **C2–TF3 · Improve accessibility and input hints**  
  - **Current:** Trailing icon content descriptions and labels are supported but not always documented; error/success states are mostly visual.  
  - **Change:**  
    - Ensure every text field & search component:  
      - Has accessible content descriptions on actionable icons.  
      - Exposes error/supporting text patterns (and sample usage in docs).  
      - Uses consistent heights, paddings, and shapes.  
  - **Benefits:** Better **clarity** and accessibility.

### 2.3 Scaffold, Layouts & Bottom Sheets

- **C3–S1 · Document `CosmosScaffold` patterns**  
  - **Current:** `CosmosScaffold` provides a rich API (sheet, snackbar, FAB, spacers, click‑to‑dismiss wrappers, back‑handler), but it’s documented only in code.  
  - **Change:**  
    - Add a **Scaffold patterns** section in docs explaining:  
      - How to structure a typical screen with `CosmosScaffold`.  
      - When to use modal vs expanded bottom sheets (`CosmosBottomSheetShape` vs `CosmosBottomSheetExpandedShape`).  
      - How `CosmosScaffoldContentWrapper` and click handling work.  
    - Provide 1–2 “recipe” snippets (e.g. screen with top bar + FAB + bottom sheet).  
  - **Benefits:** Encourages consistent page layouts and reduces bespoke scaffolds in apps.

- **C3–S2 · Normalize insets & spacers**  
  - **Current:** There are multiple `Cosmos*Spacer` functions for system bars, IME, navigation bars, etc. `CosmosScaffold` also handles window insets.  
  - **Change:**  
    - Define a single recommended pattern for handling insets in screen layouts (prefer `CosmosScaffold` + dedicated spacers only when needed).  
    - Mark any redundant spacer helpers as **internal** or consolidate them.  
  - **Benefits:** Less confusion around **safe areas** and consistent layouts across apps.

### 2.4 Navigation & Top App Bar

- **C4–N1 · Finalize `CosmosTopAppBar` API**  
  - **Current:** `CosmosTopAppBar` contains `TODO(Abhi)` notes (`Update to StringResource`, `To update`) and might have partial API coverage (e.g. title as `String` instead of `CosmosStringResource`, icon semantics, overflow actions).  
  - **Change:**  
    - Replace raw `String` / ad‑hoc types with `CosmosStringResource` and `CosmosIconResource` where applicable.  
    - Define standard slots: navigation icon, title, actions; ensure semantics and test tags are consistent with other Cosmos components.  
    - Remove or resolve TODO comments and add KDoc with usage examples.  
  - **Benefits:** **Consistent** top app bars and clearer integration into host apps.

### 2.5 Lists, Chips, Charts & Misc

- **C5–L1 · Standardize list item structure**  
  - **Current:** `CosmosSimpleList`, `CosmosSwipeableList`, and `CosmosListItem` provide flexible list UIs but the recommended combinations (e.g. which paddings, dividers, swipe actions) are not documented.  
  - **Change:**  
    - Document a small set of **canonical list patterns** (single‑line list, two‑line list with subtitle, swipeable list with destructive/secondary actions).  
    - Ensure `CosmosHorizontalDivider` and padding choices are consistent across patterns.  
  - **Benefits:** Clearer **UI patterns** and reusable list designs.

- **C5–C1 · Clarify chart APIs**  
  - **Current:** `ComposePieChart` / `PieChartRenderer` and related legend components exist, with a TODO about data immutability (`PieChartData.kt`), but there’s no public guidance on when/why to use them.  
  - **Change:**  
    - Resolve the `TODO` by making chart data immutable (or documenting why it must be mutable).  
    - Add docs describing design constraints (max slices, label behavior, colors from theme) and example usage.  
  - **Benefits:** More **predictable visuals** and easier integration of charts in apps.

- **C5–CH1 · Remove or rebrand `ChipUI`**  
  - **Current:** `ChipUI.kt` uses a generic name instead of `CosmosChip` or similar.  
  - **Change:**  
    - Rename to `CosmosChip`/`CosmosFilterChip` (or mark as internal) to match other Cosmos‑prefixed components.  
  - **Benefits:** Better **naming consistency**.

---

## 3. Resources, Icons & Strings

- **R1–I1 · Icon naming & grouping**  
  - **Current:** `CosmosIcons` exposes many Material icons as individual properties. Names broadly match Material names but some grouping/aliasing (for common actions) could help.  
  - **Change:**  
    - Add doc section listing **recommended icons per use case** (e.g. primary action, destructive action, navigation, info).  
    - Optionally group icons in `CosmosIcons` via nested objects (e.g. `CosmosIcons.Navigation.Back`, `CosmosIcons.Action.Delete`).  
  - **Benefits:** Clearer guidance; reduces arbitrary icon choices.

- **R1–S1 · String resource guidelines**  
  - **Current:** `CosmosStringResource` supports IDs and raw strings, but there’s no design‑system guideline on when to use which.  
  - **Change:**  
    - In docs, state that design‑system components should prefer **resource‑based** strings for anything user‑facing (for localization), and only use raw strings for debug/test/prototype APIs.  
    - Ensure all public Cosmos components that expose labels/ARIA text take `CosmosStringResource`.  
  - **Benefits:** Better **localization readiness** and consistency with barcodes app usage.

---

## 4. Clean Code & Internal Quality

- **Q1–CC1 · Remove or resolve `TODO(Abhi)` markers**  
  - **Current:** TODOs exist in `CosmosIcon.kt`, `CosmosLinkText.kt`, `PieChartData.kt`, `CosmosTopAppBar.kt`, etc.  
  - **Change:**  
    - Turn each TODO into a specific backlog item (many already covered above, e.g. chart immutability, link text data removal, top app bar string resources).  
    - Remove inline TODO comments once there is a clear backlog entry here (to avoid stale comments).  
  - **Benefits:** Cleaner code and a single source of truth for future work.

- **Q1–CC2 · API surface audit (explicit API)**  
  - **Current:** `explicitApi()` is enabled, which is good, but some non‑Cosmos, `My*` types are still `public`.  
  - **Change:**  
    - Audit the package for all `public` symbols; make internal anything that is not intended as part of the public design system (especially `My*` types and certain helper functions).  
  - **Benefits:** Smaller, clearer **public API** and easier versioning.

- **Q1–CC3 · Package & naming consistency**  
  - **Current:** Most components use the `Cosmos*` prefix, but a few outliers remain (`MyTabRow`, `My*TextField*`, `ChipUI`).  
  - **Change:**  
    - Either rename these to `Cosmos*` or clearly mark them as internal/legacy.  
    - Document the naming rule: all public design system components shipped to consumers should start with `Cosmos` (or a clear, documented exception).  
  - **Benefits:** Stronger brand identity and easier discovery via autocomplete.

- **Q1–CC4 · Samples & screenshot tests**  
  - **Current:** The build is configured for screenshot tests, but there is no backlog entry outlining what to cover.  
  - **Change:**  
    - Add a sample/screenshot suite plan: cover **foundational components first** (buttons, text, text fields, lists, scaffold, top app bar, dialogs, toggles).  
    - Document this plan in the main `backlog.md` and link back to this enhancement backlog.  
  - **Benefits:** Guards visual regressions and improves design system stability.

---

## 5. Documentation & Examples

- **D1–DOC1 · Expand `docs/android.md` with component catalog**  
  - **Current:** `android.md` only covers platforms, package name, minSdk, and high‑level notes.  
  - **Change:**  
    - Add an overview table listing all major components (buttons, text, scaffolds, lists, dialogs, bottom sheets, charts, spacers, etc.) with:  
      - Short description.  
      - Typical use cases.  
      - Links to sample snippets if available.  
  - **Benefits:** Faster onboarding and a clearer **design system map**.

- **D1–DOC2 · Coding guidelines for Cosmos components**  
  - **Current:** `coding.md` focuses on TOML and dependency notation but not on component implementation style.  
  - **Change:**  
    - Add section describing:  
      - Preferred patterns for new Cosmos components (slots vs parameters, `CosmosStringResource` usage, naming, theming).  
      - How to use `CosmosAppTheme` and `CosmosShapes` / `cosmosTypography`.  
  - **Benefits:** New components will naturally align with **consistency** and **clean code** goals.

- **D1–DOC3 · Cross‑module guidance (barcodes + cosmos)**  
  - **Current:** The barcodes app uses Cosmos components but there’s no explicit “how to consume Cosmos design system” guide.  
  - **Change:**  
    - Add a short section in `android.md` or a new `usage.md` describing recommended integration patterns (e.g. wrap app in `CosmosAppTheme`, prefer Cosmos components over raw Material where available, how to map app‑specific resources into `CosmosStringResource` / `CosmosIconResource`).  
  - **Benefits:** More coherent **app UI** and less divergence between Cosmos and app‑local components.

---

## 6. Testing & CI (Link to Main Backlog)

The main `docs/backlog.md` already lists:

1. Unit Testing  
2. CI

These remain valid but should be expanded as follows:

- **T1–UNIT1 · Component‑level unit tests**  
  - Add unit tests (and/or `@Composable` previews with snapshot tests) for high‑priority components (buttons, text, text fields, scaffold, dialog, toggles, lists).

- **T1–UNIT2 · Theming tests**  
  - Add tests ensuring light/dark theme combinations and typography changes don’t break component contracts.

- **T2–CI1 · CI enforcement**  
  - Ensure CI runs: ktlint/detekt, unit tests, snapshot/screenshot tests, and Compose compiler metrics checks, failing the build on regressions.

