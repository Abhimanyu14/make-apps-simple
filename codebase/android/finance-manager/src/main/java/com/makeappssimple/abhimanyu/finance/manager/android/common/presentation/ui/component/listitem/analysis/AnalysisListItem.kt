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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.analysis

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.emoji_circle.MyEmojiCircle
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.emoji_circle.MyEmojiCircleData

@Composable
internal fun AnalysisListItem(
    modifier: Modifier = Modifier,
    data: AnalysisListItemData,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(
                shape = FinanceManagerAppTheme.shapes.large,
            )
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 4.dp,
                bottom = 8.dp,
            ),
    ) {
        MyEmojiCircle(
            data = MyEmojiCircleData(
                backgroundColor = FinanceManagerAppTheme.colorScheme.outline,
                emoji = data.emoji,
            ),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            val density = LocalDensity.current
            val endTextWidth = with(
                receiver = density,
            ) {
                data.maxEndTextWidth.toDp() + 24.dp
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                        )
                        .weight(
                            weight = 1F,
                        ),
                ) {
                    MyText(
                        modifier = Modifier,
                        text = data.title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = FinanceManagerAppTheme.typography.headlineMedium
                            .copy(
                                color = FinanceManagerAppTheme.colorScheme.onBackground,
                            ),
                    )
                    Spacer(
                        modifier = Modifier
                            .height(
                                height = 4.dp,
                            ),
                    )
                    LinearProgressIndicator(
                        progress = {
                            data.percentage
                        },
                        color = FinanceManagerAppTheme.colorScheme.primary,
                        trackColor = FinanceManagerAppTheme.colorScheme.surfaceVariant,
                        strokeCap = StrokeCap.Round,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(
                                height = 8.dp,
                            ),
                    )
                    Spacer(
                        modifier = Modifier
                            .height(
                                height = 4.dp,
                            ),
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                        )
                        .width(
                            width = endTextWidth,
                        ),
                ) {
                    MyText(
                        modifier = Modifier,
                        text = data.amountText,
                        style = FinanceManagerAppTheme.typography.headlineMedium
                            .copy(
                                textAlign = TextAlign.End,
                            ),
                    )
                    Spacer(
                        modifier = Modifier
                            .height(
                                height = 4.dp,
                            ),
                    )
                    MyText(
                        modifier = Modifier,
                        text = data.percentageText,
                        style = FinanceManagerAppTheme.typography.bodySmall
                            .copy(
                                color = FinanceManagerAppTheme.colorScheme.onBackground,
                                textAlign = TextAlign.End,
                            ),
                    )
                }
            }
        }
    }
}
