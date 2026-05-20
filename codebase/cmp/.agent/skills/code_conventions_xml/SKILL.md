---
name: code_conventions_xml
description: XML coding conventions for the project.
---

# XML Code Conventions

## Naming

1. **File Names:**
    - Use snake_case for file names (e.g., `barcodes_strings.xml`, `barcodes_ic_launcher.xml`).
    - Prefix resource files with the module name to avoid conflicts (e.g., `barcodes_`).
2. **Resource IDs and Names:**
    - Use snake_case for resource IDs and names (e.g., `barcodes_app_name`,
      `barcodes_launcher_background`).
    - Prefix string, color, and dimen resources with the module name.
    - Use PascalCase for style names (e.g., `BarcodesTheme`).

## Formatting

1. **Indentation:**
    - Use 4 spaces for indentation.
2. **Attributes:**
    - Place attributes on separate lines for complex elements (e.g., `<vector>`,
      `<path>`) or when there are multiple attributes.
    - Indent attributes by 4 spaces relative to the element tag.
    - Place the closing tag `/>` on a new line for multi-line elements.
    - Single-line elements are acceptable for simple resources (e.g., `<string>`, `<color>`).

## Organization

1. **Region Comments:**
    - Use `<!-- region section_name -->` and
      `<!-- endregion -->` to group related resources in large files (e.g., `strings.xml`).
2. **License Header:**
    - Include the project's copyright license header at the top of every XML file.
3. **Ordering:**
    - Group resources logically (e.g., by screen or feature).
    - Sort resources alphabetically within groups where applicable.
