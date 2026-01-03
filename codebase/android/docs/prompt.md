# Prompt

1. "BarcodeAnalyser" is used to scan barcodes from ImageProxy. The ImageProxy is closed once a barcode is scanned. Post scanning, there are other app specific operations like saving the barcode details. In case of any app specific issues, we may need to scan the barcode again. Make changes so that it is possible to continue/restart the barcode scanning is barcode saving fails.

# Scope

- Contain all changes within these modules.
    - "barcodes"

# General rules

- Follow all instructions in [AGENT.md](../AGENT.md)
