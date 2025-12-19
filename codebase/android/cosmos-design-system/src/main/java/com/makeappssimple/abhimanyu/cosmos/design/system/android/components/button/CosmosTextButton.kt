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

package com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.text

@Composable
public fun CosmosTextButton(
    modifier: Modifier = Modifier,
    onClickLabel: CosmosStringResource,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val onClickLabelText = onClickLabel.text
    TextButton(
        onClick = onClick,
        content = content,
        modifier = modifier
            .semantics {
                onClick(
                    label = onClickLabelText,
                    action = null,
                )
            },
    )
}
