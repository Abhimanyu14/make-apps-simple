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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.typealiases.ColumnScopedComposableContent

@Composable
public fun MyExpandableItemUIWrapper(
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    isSelected: Boolean = false,
    content: ColumnScopedComposableContent,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
            )
            .clip(
                shape = FinanceManagerAppTheme.shapes.large,
            )
            .background(
                color = if (isExpanded || isSelected) {
                    FinanceManagerAppTheme.colorScheme.primaryContainer
                } else {
                    FinanceManagerAppTheme.colorScheme.background
                },
            )
            .animateContentSize(),
        content = content,
    )
}
