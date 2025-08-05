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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.navigation_back_button

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
public fun MyNavigationBackButton(
    modifier: Modifier = Modifier,
    handleEvent: (event: MyNavigationBackButtonEvents) -> Unit = {},
) {
    MyIconButton(
        modifier = modifier,
        imageVector = MyIcons.ArrowBack,
        contentDescription = stringResource(
            id = R.string.finance_manager_navigation_back_button_navigation_icon_content_description,
        ),
        tint = MaterialTheme.colorScheme.primary,
        onClick = {
            handleEvent(MyNavigationBackButtonEvents.OnClick)
        },
    )
}
