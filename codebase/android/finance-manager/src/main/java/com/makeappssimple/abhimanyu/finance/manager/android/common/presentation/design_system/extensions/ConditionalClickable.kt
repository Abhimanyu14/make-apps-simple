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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.extensions

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull

internal fun Modifier.conditionalClickable(
    role: Role? = null,
    onClickLabel: String? = null,
    onLongClickLabel: String? = null,
    onClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
): Modifier {
    return composed {
        this.conditionalClickable(
            indication = LocalIndication.current,
            interactionSource = remember {
                MutableInteractionSource()
            },
            role = role,
            onClickLabel = onClickLabel,
            onLongClickLabel = onLongClickLabel,
            onClick = onClick,
            onDoubleClick = onDoubleClick,
            onLongClick = onLongClick,
        )
    }
}

internal fun Modifier.conditionalClickable(
    indication: Indication?,
    interactionSource: MutableInteractionSource,
    role: Role? = null,
    onClickLabel: String? = null,
    onLongClickLabel: String? = null,
    onClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
): Modifier {
    return if (onClick.isNotNull() && onLongClick.isNotNull()) {
        this.then(
            other = Modifier
                .combinedClickable(
                    interactionSource = interactionSource,
                    indication = indication,
                    onClickLabel = onClickLabel,
                    role = role,
                    onLongClickLabel = onLongClickLabel,
                    onLongClick = onLongClick,
                    onDoubleClick = onDoubleClick,
                    onClick = onClick,
                )
        )
    } else if (onClick.isNotNull()) {
        this.then(
            other = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = indication,
                    onClickLabel = onClickLabel,
                    role = role,
                    onClick = onClick,
                )
        )
    } else {
        this
    }
}
