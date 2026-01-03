/*
 * Copyright 2025-2026 Abhimanyu
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.component.MyText

@Composable
internal fun MyOutlinedTextFieldLabelText(
    modifier: Modifier = Modifier,
    @StringRes textStringResourceId: Int,
) {
    // Not providing style as the default style has font size change based on floating or not
    MyText(
        textStringResourceId = textStringResourceId,
        modifier = modifier,
        // style = FinanceManagerAppTheme.typography.labelSmall,
    )
}
