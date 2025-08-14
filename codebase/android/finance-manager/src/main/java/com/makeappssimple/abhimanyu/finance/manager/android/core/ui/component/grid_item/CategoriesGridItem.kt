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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.grid_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.emoji_circle.EmojiCircleSize
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.emoji_circle.MyEmojiCircle
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.emoji_circle.MyEmojiCircleData

@Composable
public fun CategoriesGridItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    category: Category,
    onClick: (() -> Unit)? = null,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp,
            )
            .clip(
                shape = RoundedCornerShape(
                    size = 16.dp,
                ),
            )
            .background(
                color = if (isSelected) {
                    FinanceManagerAppTheme.colorScheme.primaryContainer
                } else {
                    Color.Transparent
                },
            )
            .conditionalClickable(
                onClick = onClick,
            ),
    ) {
        MyEmojiCircle(
            data = MyEmojiCircleData(
                emojiCircleSize = EmojiCircleSize.Large,
                emoji = category.emoji,
            ),
        )
        MyText(
            modifier = Modifier
                .padding(
                    start = 6.dp,
                    end = 6.dp,
                    bottom = 4.dp,
                ),
            text = category.title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = FinanceManagerAppTheme.typography.headlineMedium
                .copy(
                    color = if (isSelected) {
                        FinanceManagerAppTheme.colorScheme.primary
                    } else {
                        FinanceManagerAppTheme.colorScheme.onBackground
                    },
                    textAlign = TextAlign.Center,
                ),
        )
    }
}
