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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.util.minimumBottomSheetHeight

@Composable
public fun MyConfirmationBottomSheet(
    modifier: Modifier = Modifier,
    data: MyConfirmationBottomSheetData,
    handleEvent: (event: MyConfirmationBottomSheetEvent) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
            )
            .defaultMinSize(
                minHeight = minimumBottomSheetHeight,
            ),
    ) {
        MyText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = 16.dp,
                ),
            text = data.title,
            style = FinanceManagerAppTheme.typography.headlineLarge
                .copy(
                    color = FinanceManagerAppTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                ),
        )
        MyText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = 16.dp,
                ),
            text = data.message,
            style = FinanceManagerAppTheme.typography.bodyMedium
                .copy(
                    color = FinanceManagerAppTheme.colorScheme.onBackground,
                ),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(
                onClick = {
                    handleEvent(MyConfirmationBottomSheetEvent.OnNegativeButtonClick)
                },
                modifier = Modifier
                    .padding(
                        all = 16.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
            ) {
                MyText(
                    text = data.negativeButtonText,
                    style = FinanceManagerAppTheme.typography.labelLarge,
                )
            }
            Button(
                onClick = {
                    handleEvent(MyConfirmationBottomSheetEvent.OnPositiveButtonClick)
                },
                modifier = Modifier
                    .padding(
                        all = 16.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
            ) {
                MyText(
                    text = data.positiveButtonText,
                    style = FinanceManagerAppTheme.typography.labelLarge,
                )
            }
        }
        NavigationBarsAndImeSpacer()
    }
}
