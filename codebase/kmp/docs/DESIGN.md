# Cosmos Design System

Detailed design specifications for the Cosmos Design System, intended for high-fidelity UI implementation and agentic workflow consumption.

## Typography

Cosmos uses the **Lexend** font family as its primary typeface.

| Style Name          | Font Size | Font Weight | Line Height | Letter Spacing |
|:--------------------|:----------|:------------|:------------|:---------------|
| **Display Large**   | 32.sp     | Bold        | 40.sp       | 0.6.sp         |
| **Display Medium**  | 24.sp     | Bold        | 32.sp       | 0.sp           |
| **Display Small**   | 16.sp     | Bold        | 24.sp       | 0.sp           |
| **Headline Large**  | 16.sp     | Bold        | 24.sp       | 0.sp           |
| **Headline Medium** | 14.sp     | Bold        | 22.sp       | 0.sp           |
| **Headline Small**  | 12.sp     | Bold        | 20.sp       | 0.sp           |
| **Title Large**     | 20.sp     | Normal      | 28.sp       | 0.sp           |
| **Title Medium**    | 14.sp     | Normal      | 22.sp       | 0.2.sp         |
| **Title Small**     | 12.sp     | Normal      | 20.sp       | 0.1.sp         |
| **Body Large**      | 16.sp     | Normal      | 24.sp       | 0.5.sp         |
| **Body Medium**     | 14.sp     | Normal      | 22.sp       | 0.2.sp         |
| **Body Small**      | 12.sp     | Normal      | 20.sp       | 0.4.sp         |
| **Label Large**     | 14.sp     | Bold        | 22.sp       | 0.5.sp         |
| **Label Medium**    | 14.sp     | Normal      | 22.sp       | 0.5.sp         |
| **Label Small**     | 10.sp     | Bold        | 18.sp       | 1.2.sp         |

## Colors

The system uses a semantic color palette based on primary, secondary, and tertiary themes.

### Semantic Palette

| Name                   | Hex Code  | Usage                                        |
|:-----------------------|:----------|:---------------------------------------------|
| **Primary**            | `#1858BA` | Primary actions and highlights.              |
| **OnPrimary**          | `#FFFFFF` | Text/icons on primary color.                 |
| **PrimaryContainer**   | `#D5F4FF` | Container background for primary elements.   |
| **Secondary**          | `#7B3F25` | Secondary highlights.                        |
| **SecondaryContainer** | `#FFD7B5` | Container background for secondary elements. |
| **Tertiary**           | `#529E54` | Tertiary highlights (often success-related). |
| **TertiaryContainer**  | `#DAF5D7` | Container background for tertiary elements.  |
| **Background**         | `#FFFFFF` | General app background.                      |
| **Surface**            | `#FFFFFF` | Card and surface backgrounds.                |
| **OnSurface**          | `#444444` | Primary text on surfaces.                    |
| **Error**              | `#FF0000` | Error states.                                |
| **Outline**            | `#CCCCCC` | Borders and dividers.                        |

### Color Tones (Internal Reference)

- **Blue**: `1000 (#0346B2)` to `10 (#EAF9FF)`
- **Brown**: `1000 (#6C2E14)` to `10 (#FFF8F2)`
- **Green**: `1000 (#419243)` to `10 (#F4FFF2)`

## Shapes

| Role             | Shape                                       | Usage                    |
|:-----------------|:--------------------------------------------|:-------------------------|
| **Extra Small**  | RoundedCorner(4.dp)                         | Small buttons, chips.    |
| **Small**        | RoundedCorner(8.dp)                         | Text fields, cards.      |
| **Medium**       | RoundedCorner(12.dp)                        | Dialogue boxes, menus.   |
| **Large**        | RoundedCorner(16.dp)                        | Featured cards, banners. |
| **Extra Large**  | RoundedCorner(32.dp)                        | Floating Action Buttons. |
| **Bottom Sheet** | RoundedCorner(topStart=16.dp, topEnd=16.dp) | Bottom sheets.           |

## Icons

Cosmos uses a custom set of rounded filled icons (24dp). Access them via `CosmosIcons`.

- **Action**: `Add`, `Delete`, `Edit`, `Search`, `Settings`, `Backup`, `Calculate`, `History`.
- **Navigation**: `ArrowBack`, `ChevronLeft`, `ChevronRight`, `Close`, `MoreVert`.
- **Finance**: `AccountBalance`, `AccountBalanceWallet`, `AttachMoney`, `CurrencyExchange`,
  `CurrencyRupee`.
- **Status**: `Check`, `CheckCircle`, `Schedule`, `Notifications`, `Lock`.
- **Utility**: `Category`, `Checklist`, `ContentCopy`, `Groups`, `Keyboard`, `TextSnippet`.

## Components

All components should be accessed from
`com.makeappssimple.abhimanyu.cosmos.design.system.android.components`.

### Layout & Structural

- **CosmosScaffold**: Base layout container with support for top bars and snackbars.
- **TopAppBar**: `CenterAlignedTopAppBar` and variants.
- **BottomSheet**: Modal and persistent bottom sheets.
- **Divider**: `CosmosHorizontalDivider`.

### Inputs & Actions

- **Buttons**: `CosmosElevatedButton`, `CosmosTextButton`, `CosmosIconButton`, `CosmosSaveButton`.
- **TextFields**: `CosmosOutlinedTextField`, `CosmosReadOnlyTextField`.
- **Selection**: `CosmosToggle`, `ChipUI`.
- **Pickers**: `CosmosDatePicker`, `CosmosTimePicker`.

### Navigation & Feedback

- **Feedback**: `CosmosLinearProgressIndicator`, `CosmosCircularProgressIndicator`.
- **Navigation**: `CosmosNavigationBackButton`.
- **Selection**: `CosmosTabRow`.

### UI Elements

- **Lists**: `CosmosListItem`, `CosmosExpandedListItem`.
- **Visuals**: `CosmosDot`, `CosmosImage`, `CosmosIcon`.
- **Dialogs**: `CosmosDialog`.
