/*
 * Copyright 2025-2026 Abhimanyu
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

@file:OptIn(ExperimentalLayoutApi::class)

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.component

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.areStatusBarsVisible
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.extensions.ifTrue

private val defaultSpacerSize = 100.dp

@Composable
internal fun HorizontalSpacer(
    modifier: Modifier = Modifier,
    width: Dp = defaultSpacerSize,
) {
    Spacer(
        modifier = modifier
            .width(
                width = width,
            ),
    )
}

@Composable
internal fun VerticalSpacer(
    modifier: Modifier = Modifier,
    height: Dp = defaultSpacerSize,
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(
                height = height,
            ),
    )
}

@Composable
internal fun NonFillingVerticalSpacer(
    modifier: Modifier = Modifier,
    height: Dp = defaultSpacerSize,
) {
    Spacer(
        modifier = modifier
            .height(
                height = height,
            ),
    )
}

@Composable
internal fun StatusBarSpacer(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .statusBarSpacer(),
    )
}

@Composable
internal fun NavigationBarsAndImeSpacer(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .navigationBarsSpacer()
            .imeSpacer(),
    )
}

@Composable
internal fun NavigationBarsSpacer(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .navigationBarsSpacer(),
    )
}

@Composable
internal fun ImeSpacer(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .imeSpacer(),
    )
}

@SuppressLint("UnnecessaryComposedModifier")
internal fun Modifier.navigationBarLandscapeSpacer(): Modifier {
    return composed {
        Modifier
            .fillMaxSize()
            .ifTrue(
                condition = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ) {
                this.navigationBarsSpacer()
            }
    }
}

internal fun Modifier.statusBarSpacer(): Modifier {
    return composed {
        if (WindowInsets.areStatusBarsVisible) {
            this
                .padding(
                    top = WindowInsets.statusBars.asPaddingValues()
                        .calculateTopPadding(),
                )
        } else {
            this
        }
    }
}

internal fun Modifier.navigationBarsSpacer(): Modifier {
    return this.navigationBarsPadding()
}

internal fun Modifier.imeSpacer(): Modifier {
    return this.imePadding()
}

@Composable
internal fun navigationBarHeight(): Dp {
    return 0.dp // WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
}
