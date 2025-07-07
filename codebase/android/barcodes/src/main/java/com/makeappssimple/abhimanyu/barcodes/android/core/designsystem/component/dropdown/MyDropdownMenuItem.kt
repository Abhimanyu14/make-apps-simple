package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.dropdown

import androidx.annotation.StringRes
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.icon.MyIcon
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.text.MyText

@Composable
public fun MyDropdownMenuItem(
    modifier: Modifier = Modifier,
    @StringRes leadingIconContentDescriptionStringResourceId: Int,
    @StringRes textStringResourceId: Int,
    onClick: () -> Unit,
    leadingIconImageVector: ImageVector,
) {
    DropdownMenuItem(
        text = {
            MyText(
                textStringResourceId = textStringResourceId,
            )
        },
        onClick = onClick,
        leadingIcon = {
            MyIcon(
                imageVector = leadingIconImageVector,
                contentDescriptionStringResourceId = leadingIconContentDescriptionStringResourceId,
            )
        },
        modifier = modifier,
    )
}
