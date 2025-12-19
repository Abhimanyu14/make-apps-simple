/*
 * Copyright 2025-2025 Abhimanyu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.text_field

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
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.extensions.shimmer
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.resource.StringResource

@Composable
internal fun MyReadOnlyTextField(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    text: String,
    labelStringResource: StringResource,
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
                        stringResource = labelStringResource,
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
