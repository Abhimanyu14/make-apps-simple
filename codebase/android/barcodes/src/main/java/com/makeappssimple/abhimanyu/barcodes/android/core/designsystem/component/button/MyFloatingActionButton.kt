package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.button

import androidx.annotation.StringRes
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.icon.MyIcon

@Composable
public fun MyFloatingActionButton(
    modifier: Modifier = Modifier,
    iconImageVector: ImageVector,
    @StringRes contentDescriptionStringResourceId: Int,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primary,
        onClick = onClick,
        modifier = modifier,
    ) {
        MyIcon(
            imageVector = iconImageVector,
            contentDescriptionStringResourceId = contentDescriptionStringResourceId,
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
