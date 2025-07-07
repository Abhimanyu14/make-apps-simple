package com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.scanbarcode.event

internal class ScanBarcodeScreenUIEventHandler internal constructor(
    private val onBarcodeScanned: (ScanBarcodeScreenUIEvent.OnBarcodeScanned) -> Unit?,
    private val onTopAppBarNavigationButtonClick: () -> Unit?,
) {
    fun handleUIEvent(
        uiEvent: ScanBarcodeScreenUIEvent,
    ) {
        when (uiEvent) {
            is ScanBarcodeScreenUIEvent.OnBarcodeScanned -> {
                onBarcodeScanned(uiEvent)
            }

            is ScanBarcodeScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                onTopAppBarNavigationButtonClick()
            }
        }
    }
}
