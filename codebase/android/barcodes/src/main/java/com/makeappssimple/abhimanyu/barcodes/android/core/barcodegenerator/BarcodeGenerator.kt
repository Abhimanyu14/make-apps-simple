/*
 * Copyright 2025-2025 Abhimanyu
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

package com.makeappssimple.abhimanyu.barcodes.android.core.barcodegenerator

import android.graphics.Bitmap
import android.graphics.Color

private object BarcodeDefault {
    const val WIDTH: Int = 500
    const val HEIGHT: Int = 500
    const val BARCODE_COLOR: Int = Color.BLACK
    const val BACKGROUND_COLOR: Int = Color.WHITE
}

public interface BarcodeGenerator {
    public suspend fun generateBarcode(
        data: String,
        visionBarcodeFormat: Int,
        width: Int = BarcodeDefault.WIDTH,
        height: Int = BarcodeDefault.HEIGHT,
        barcodeColor: Int = BarcodeDefault.BARCODE_COLOR,
        backgroundColor: Int = BarcodeDefault.BACKGROUND_COLOR,
    ): Bitmap?
}
