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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.text_field

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.typealiases.NullableComposableContent

@Immutable
internal data class MyOutlinedTextFieldData(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    @StringRes val labelTextStringResourceId: Int,
    @StringRes val trailingIconContentDescriptionTextStringResourceId: Int,
    val keyboardActions: KeyboardActions = KeyboardActions(),
    val keyboardOptions: KeyboardOptions = KeyboardOptions(),
    val textFieldValue: TextFieldValue = TextFieldValue(),
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val supportingText: NullableComposableContent = null,
)

@Immutable
internal data class MyOutlinedTextFieldDataV2(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    @StringRes val labelTextStringResourceId: Int,
    @StringRes val trailingIconContentDescriptionTextStringResourceId: Int,
    val keyboardActions: KeyboardActionHandler? = null,
    val keyboardOptions: KeyboardOptions = KeyboardOptions(),
    val textFieldState: TextFieldState = TextFieldState(),
    val inputTransformation: InputTransformation? = null,
    val outputTransformation: OutputTransformation? = null,
    val supportingText: NullableComposableContent = null,
)
