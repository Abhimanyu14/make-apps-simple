package com.makeappssimple.abhimanyu.barcodes.android.core.testing

import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.model.BarcodeSource
import com.google.android.gms.vision.barcode.Barcode as VisionBarcode

internal val TEST_SOURCE_1 = BarcodeSource.CREATED
internal val TEST_SOURCE_2 = BarcodeSource.SCANNED
internal val TEST_SOURCE_3 = BarcodeSource.CREATED
internal const val TEST_FORMAT_1 = VisionBarcode.QR_CODE
internal const val TEST_FORMAT_2 = VisionBarcode.CODE_128
internal const val TEST_FORMAT_3 = VisionBarcode.EAN_13
internal const val TEST_ID_1 = 1
internal const val TEST_ID_2 = 2
internal const val TEST_ID_3 = 3
internal const val TEST_TIMESTAMP_1 = 1L
internal const val TEST_TIMESTAMP_2 = 2L
internal const val TEST_TIMESTAMP_3 = 3L
internal const val TEST_INVALID_ID = 100
internal const val TEST_NAME_1 = "Test 1"
internal const val TEST_NAME_2 = "Test 2"
internal const val TEST_NAME_3 = "Test 3"
internal const val TEST_VALUE_1 = "Test value 1"
internal const val TEST_VALUE_2 = "Test value 2"
internal const val TEST_VALUE_3 = "Test value 3"
internal val testBarcodes = arrayOf(
    Barcode(
        source = TEST_SOURCE_1,
        format = TEST_FORMAT_1,
        id = TEST_ID_1,
        timestamp = TEST_TIMESTAMP_1,
        name = TEST_NAME_1,
        value = TEST_VALUE_1,
    ),
    Barcode(
        source = TEST_SOURCE_2,
        format = TEST_FORMAT_2,
        id = TEST_ID_2,
        timestamp = TEST_TIMESTAMP_2,
        name = TEST_NAME_2,
        value = TEST_VALUE_2,
    ),
    Barcode(
        source = TEST_SOURCE_3,
        format = TEST_FORMAT_3,
        id = TEST_ID_3,
        timestamp = TEST_TIMESTAMP_3,
        name = TEST_NAME_3,
        value = TEST_VALUE_3,
    ),
)
