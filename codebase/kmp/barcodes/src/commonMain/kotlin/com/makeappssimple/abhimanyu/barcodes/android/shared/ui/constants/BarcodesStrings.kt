/*
 * Copyright 2025-2026 Abhimanyu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.makeappssimple.abhimanyu.barcodes.android.shared.ui.constants

internal object BarcodesStrings {
    const val appName = "Barcodes"

    const val barcodeDetails = "Barcode Details"
    const val barcodeDetailsBarcodeName = "Barcode Name"
    const val barcodeDetailsBarcodeTimestampScanned = "Scanned at "
    const val barcodeDetailsBarcodeTimestampCreated = "Created at "
    const val barcodeDetailsBarcodeValue = "Barcode Value"
    const val barcodeDetailsBarcodeImage = "Barcode image"
    const val barcodeDetailsCopyBarcodeValue = "Copy barcode value"
    const val barcodeDetailsDeleteBarcode = "Delete barcode"
    const val barcodeDetailsEditBarcode = "Edit barcode"
    const val barcodeDetailsOptionsMenu = "Options Menu"
    const val barcodeDetailsDeleteButtonLabel = "Delete"
    const val barcodeDetailsCancelButtonLabel = "Cancel"
    const val barcodeDetailsDeleteBarcodeDialogTitle = "Delete Barcode"
    const val barcodeDetailsDeleteBarcodeDialogMessage = "Are you sure you want to delete the barcode?"
    const val barcodeDetailsErrorMessage = "Error fetching barcode details"
    const val barcodeDetailsDeleteFailedSnackbarMessage = "Failed to delete barcode!"

    const val createBarcode = "Enter Barcode Details"
    const val createBarcodeBarcodeName = "Barcode Name"
    const val createBarcodeBarcodeValue = "Barcode Value"
    const val createBarcodeClearBarcodeName = "Clear barcode name"
    const val createBarcodeClearBarcodeValue = "Clear barcode value"
    const val createBarcodeCopyBarcodeValue = "Copy barcode value"
    const val createBarcodeCtaButtonLabel = "SAVE"
    const val createBarcodeErrorMessage = "Error fetching barcode details"
    const val createBarcodeSaveFailedSnackbarMessage = "Failed to save barcode!"

    const val credits = "Credits"
    const val creditsIconsTitle = "Icons"
    const val creditsPrivacyPolicyTitle = "Privacy Policy"
    const val creditsTermsAndConditionsTitle = "Terms and Conditions"

    const val home = "Barcodes"
    const val homeBarcodeDeletedFailedSnackbarMessage = "Failed to delete barcode(s)!"
    const val homeBarcodeDeletedSnackbarActionLabel = "Undo"
    const val homeBarcodeRestoreFailedSnackbarMessage = "Failed to restore barcode!"
    const val homeBottomSheetCreateBarcode = "Create barcode"
    const val homeBottomSheetScanBarcode = "Scan barcode"
    const val homeAdd = "Add"
    const val homeDeleteBarcodeDialogConfirmButtonLabel = "Delete"
    const val homeDeleteBarcodeDialogDismissButtonLabel = "Cancel"
    const val homeDeleteBarcodeDialogTitleOne = "Delete 1 Barcode"
    const val homeDeleteBarcodeDialogTitleOtherPrefix = "Delete "
    const val homeDeleteBarcodeDialogTitleOtherSuffix = " Barcodes"
    const val homeDeleteBarcodeDialogMessageOne = "Are you sure you want to delete 1 barcode?"
    const val homeDeleteBarcodeDialogMessageOtherPrefix = "Are you sure you want to delete "
    const val homeDeleteBarcodeDialogMessageOtherSuffix = " barcodes?"
    const val homeDeleteBarcodeFailedSnackbarMessage = "Failed to delete barcode(s)!"
    const val homeDeleteBarcode = "Delete barcode"
    const val homeDelete = "Delete"
    const val homeOpenSettings = "Open settings"
    const val homeCloseSelectionMode = "Close Selection Mode"
    const val homeSettings = "Settings"
    const val homeSelectionModeTopAppBarTitleSuffix = " selected"

    const val scanBarcode = "Scan Barcode"
    const val scanBarcodeCameraPermissionRationale = "Camera permission is needed to scan barcodes."
    const val scanBarcodeCameraPermissionActionLabel = "Grant Permission"
    const val scanBarcodeCameraPermissionDeniedDialogTitle = "Permission Required"
    const val scanBarcodeCameraPermissionDeniedDialogMessage = "Camera permission is required to scan barcodes. Please enable it in settings."
    const val scanBarcodeCameraPermissionDeniedDialogPositiveButtonText = "Open Settings"
    const val scanBarcodeCameraPermissionDeniedDialogNegativeButtonText = "Cancel"
    const val scanBarcodeSaveFailedSnackbarMessage = "Failed to save barcode!"

    const val settings = "Settings"
    const val settingsCredits = "Credits"
    const val settingsOpenSourceLicenses = "Open Source Licenses"
    const val settingsPrivacyPolicy = "Privacy Policy"

    const val commonCopyBarcodeValueToastMessageSuffix = " copied to clipboard"

    fun barcodeDetailsBarcodeValueCopiedToastMessage(barcodeValue: String): String {
        return "$barcodeValue$commonCopyBarcodeValueToastMessageSuffix"
    }

    fun createBarcodeBarcodeValueCopiedToastMessage(barcodeValue: String): String {
        return "$barcodeValue$commonCopyBarcodeValueToastMessageSuffix"
    }

    fun homeBarcodeDeletedSnackbarMessage(barcodeName: String): String {
        return "$barcodeName deleted!"
    }

    fun homeSelectionModeTopAppBarTitle(selectedCount: Int): String {
        return "$selectedCount$homeSelectionModeTopAppBarTitleSuffix"
    }

    fun homeDeleteBarcodeDialogTitle(selectedCount: Int): String {
        return if (selectedCount == 1) {
            homeDeleteBarcodeDialogTitleOne
        } else {
            "${homeDeleteBarcodeDialogTitleOtherPrefix}${selectedCount}${homeDeleteBarcodeDialogTitleOtherSuffix}"
        }
    }

    fun homeDeleteBarcodeDialogMessage(selectedCount: Int): String {
        return if (selectedCount == 1) {
            homeDeleteBarcodeDialogMessageOne
        } else {
            "${homeDeleteBarcodeDialogMessageOtherPrefix}${selectedCount}${homeDeleteBarcodeDialogMessageOtherSuffix}"
        }
    }
}
