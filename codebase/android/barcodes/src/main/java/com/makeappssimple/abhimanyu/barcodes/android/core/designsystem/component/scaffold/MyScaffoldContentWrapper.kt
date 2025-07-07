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

package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.typealiases.ColumnScopedComposableContent

@Composable
public fun MyScaffoldContentWrapper(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    onClick: () -> Unit,
    content: ColumnScopedComposableContent,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background,
            )
            .fillMaxSize()
            .conditionalClickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                onClick = onClick,
            )
            .padding(
                paddingValues = innerPadding,
            ),
        content = content,
    )
}
