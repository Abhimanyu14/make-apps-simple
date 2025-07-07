package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.extensions.shimmer

@Composable
public fun MyReadOnlyTextField(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    text: String,
    @StringRes labelTextStringResourceId: Int,
    onClick: () -> Unit = {},
) {
    if (isLoading) {
        MyReadOnlyTextFieldLoadingUI(
            modifier = modifier,
        )
    } else {
        Box(
            modifier = modifier,
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    MyOutlinedTextFieldLabelText(
                        textStringResourceId = labelTextStringResourceId,
                    )
                },
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .alpha(
                        alpha = 0F,
                    )
                    .conditionalClickable(
                        onClick = onClick,
                    ),
            )
        }
    }
}

@Composable
private fun MyReadOnlyTextFieldLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(
                height = 56.dp,
            )
            .clip(
                shape = RoundedCornerShape(
                    size = 8.dp,
                ),
            )
            .shimmer(),
    )
}
