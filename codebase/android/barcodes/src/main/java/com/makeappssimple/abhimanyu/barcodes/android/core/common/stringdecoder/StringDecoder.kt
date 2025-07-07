package com.makeappssimple.abhimanyu.barcodes.android.core.common.stringdecoder

public interface StringDecoder {
    public fun decodeString(
        encodedString: String,
    ): String
}
