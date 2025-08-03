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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.extensions.shimmer.shimmer

@Composable
public fun MyOutlinedTextField(
    modifier: Modifier = Modifier,
    data: MyOutlinedTextFieldData,
    handleEvent: (event: MyOutlinedTextFieldEvent) -> Unit = {},
) {
    if (data.isLoading) {
        MyOutlinedTextFieldLoadingUI(
            modifier = modifier,
        )
    } else {
        OutlinedTextField(
            value = data.textFieldValue,
            label = {
                MyOutlinedTextFieldLabelText(
                    textStringResourceId = data.labelTextStringResourceId,
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = data.textFieldValue.text.isNotNullOrBlank(),
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    MyIconButton(
                        tint = MaterialTheme.colorScheme.onBackground,
                        imageVector = MyIcons.Clear,
                        contentDescriptionStringResourceId = data.trailingIconContentDescriptionTextStringResourceId,
                        onClick = {
                            handleEvent(MyOutlinedTextFieldEvent.OnClickTrailingIcon)
                        },
                        modifier = Modifier
                            .padding(
                                end = 4.dp,
                            ),
                    )
                }
            },
            onValueChange = {
                handleEvent(
                    MyOutlinedTextFieldEvent.OnValueChange(
                        updatedValue = it,
                    )
                )
            },
            supportingText = data.supportingText,
            isError = data.isError,
            visualTransformation = data.visualTransformation,
            keyboardActions = data.keyboardActions,
            keyboardOptions = data.keyboardOptions,
            singleLine = true,
            modifier = modifier,
        )
    }
}

@Composable
private fun MyOutlinedTextFieldLoadingUI(
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
