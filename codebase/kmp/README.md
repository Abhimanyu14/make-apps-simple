# Make Apps Simple

A collection of Android applications and shared modules.

## List of Apps

1. **Barcodes**: An app to scan and generate barcodes.
2. **Cosmos Design System Catalog**: A showcase app for the Cosmos Design System.
3. **Finance Manager**: A comprehensive app to track and manage personal finances.
4. **Make Apps Simple**: The main entry point or umbrella application.

## Project Structure

### Application Modules

- `:app-barcodes`: Android application module for the Barcodes app.
- `:app-cosmos-design-system-catalog`: Android application module for the Design System showcase.
- `:app-finance-manager`: Android application module for the Finance Manager app.
- `:app-make-apps-simple`: The primary application module for the project.

### Feature Modules

- `:barcodes`: Core logic and UI components specifically for barcode-related features.
- `:cosmos-design-system-catalog`: Logic and screen implementations for the design system catalog.
- `:finance-manager`: Core logic and UI components for finance tracking and management.

### Shared Modules

- `:barcode-generator`: Utility module for generating various barcode formats.
- `:common`: Project-wide shared utilities, extensions, and base classes.
- `:core:date-time`: Dedicated module for date and time manipulation using `kotlinx.datetime`.
- `:cosmos-design-system`: The custom Design System component library used across all apps.
