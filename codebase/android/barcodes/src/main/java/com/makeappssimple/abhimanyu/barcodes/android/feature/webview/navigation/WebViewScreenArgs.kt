package com.makeappssimple.abhimanyu.barcodes.android.feature.webview.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.barcodes.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenArgs

internal class WebViewScreenArgs(
    val url: String?,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
        stringDecoder: StringDecoder,
    ) : this(
        url = stringDecoder.decodeString(
            encodedString = checkNotNull(
                value = savedStateHandle.get<String>(NavigationArguments.URL),
            ),
        ),
    )
}
