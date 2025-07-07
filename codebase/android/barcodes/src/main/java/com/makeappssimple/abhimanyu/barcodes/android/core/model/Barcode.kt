package com.makeappssimple.abhimanyu.barcodes.android.core.model

import com.google.android.gms.vision.barcode.Barcode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Barcode(
    @SerialName(value = BarcodeConstants.SOURCE)
    public val source: BarcodeSource,

    @SerialName(value = BarcodeConstants.FORMAT)
    public val format: Int = Barcode.QR_CODE,

    @SerialName(value = BarcodeConstants.ID)
    public val id: Int = 0,

    @SerialName(value = BarcodeConstants.TIMESTAMP)
    public val timestamp: Long,

    @SerialName(value = BarcodeConstants.NAME)
    public val name: String? = null,

    @SerialName(value = BarcodeConstants.VALUE)
    public val value: String,
)

private object BarcodeConstants {
    const val SOURCE: String = "source"
    const val FORMAT: String = "format"
    const val ID: String = "id"
    const val TIMESTAMP: String = "timestamp"
    const val NAME: String = "name"
    const val VALUE: String = "value"
}
