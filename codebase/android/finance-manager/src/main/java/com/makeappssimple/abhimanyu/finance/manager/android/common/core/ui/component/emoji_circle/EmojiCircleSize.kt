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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.emoji_circle

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
public sealed class EmojiCircleSize(
    public val padding: Dp,
    public val textSize: Float,
) {
    public data object Small : EmojiCircleSize(
        padding = 2.dp,
        textSize = 20F,
    )

    public data object Normal : EmojiCircleSize(
        padding = 4.dp,
        textSize = 28F,
    )

    public data object Large : EmojiCircleSize(
        padding = 8.dp,
        textSize = 32F,
    )
}
