package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.textfield

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.text.MyText

@Composable
public fun MyOutlinedTextFieldLabelText(
    modifier: Modifier = Modifier,
    @StringRes textStringResourceId: Int,
) {
    // Not providing style as the default style has font size change based on floating or not
    MyText(
        textStringResourceId = textStringResourceId,
        modifier = modifier,
        // style = MaterialTheme.typography.labelSmall,
    )
}
