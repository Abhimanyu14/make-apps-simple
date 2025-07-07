package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.button

import androidx.annotation.StringRes
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics

@Composable
public fun MyIconButton(
    modifier: Modifier = Modifier,
    @StringRes onClickLabelStringResourceId: Int,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val onClickLabel = stringResource(
        id = onClickLabelStringResourceId,
    )
    MyIconButton(
        modifier = modifier,
        onClickLabel = onClickLabel,
        onClick = onClick,
        content = content,
    )
}

@Composable
public fun MyIconButton(
    modifier: Modifier = Modifier,
    onClickLabel: String,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    IconButton(
        onClick = onClick,
        content = content,
        modifier = modifier
            .semantics {
                onClick(
                    label = onClickLabel,
                    action = null,
                )
            },
    )
}
