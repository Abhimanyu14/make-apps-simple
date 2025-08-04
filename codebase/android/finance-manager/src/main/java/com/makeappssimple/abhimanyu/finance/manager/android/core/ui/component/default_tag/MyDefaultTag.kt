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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.default_tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

private object MyDefaultTagConstants {
    val paddingHorizontal = 8.dp
    val paddingVertical = 1.dp
}

@Composable
public fun MyDefaultTag(
    modifier: Modifier = Modifier,
) {
    MyText(
        modifier = modifier
            .clip(
                shape = CircleShape,
            )
            .background(
                color = MaterialTheme.colorScheme.primary,
            )
            .padding(
                horizontal = MyDefaultTagConstants.paddingHorizontal,
                vertical = MyDefaultTagConstants.paddingVertical,
            ),
        text = stringResource(
            id = R.string.default_tag,
        ),
        style = MaterialTheme.typography.labelSmall
            .copy(
                color = MaterialTheme.colorScheme.onPrimary,
            ),
    )
}
