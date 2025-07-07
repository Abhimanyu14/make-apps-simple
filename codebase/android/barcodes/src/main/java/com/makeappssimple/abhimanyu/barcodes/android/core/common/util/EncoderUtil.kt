package com.makeappssimple.abhimanyu.barcodes.android.core.common.util

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

public fun encodeString(
    text: String,
): String {
    return URLEncoder.encode(
        text,
        StandardCharsets.UTF_8.toString(),
    )
}

public fun decodeString(
    text: String,
): String {
    return URLDecoder.decode(
        text,
        StandardCharsets.UTF_8.toString(),
    )
}
