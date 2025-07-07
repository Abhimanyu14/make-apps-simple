package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.image

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.stringResource

@Composable
public fun MyImage(
    bitmap: ImageBitmap,
    @StringRes contentDescriptionStringResourceId: Int?,
) {
    Image(
        bitmap = bitmap,
        contentDescription = contentDescriptionStringResourceId?.run {
            stringResource(
                id = this,
            )
        },
    )
}
