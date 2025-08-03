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

@file:OptIn(ExperimentalLayoutApi::class)

package com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.component

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
import com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.extensions.ifTrue

private val defaultSpacerSize = 100.dp

@Composable
public fun HorizontalSpacer(
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
public fun VerticalSpacer(
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
public fun NonFillingVerticalSpacer(
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
public fun StatusBarSpacer(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .statusBarSpacer(),
    )
}

@Composable
public fun NavigationBarsAndImeSpacer(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .navigationBarsSpacer()
            .imeSpacer(),
    )
}

@Composable
public fun NavigationBarsSpacer(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .navigationBarsSpacer(),
    )
}

@Composable
public fun ImeSpacer(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .imeSpacer(),
    )
}

@SuppressLint("UnnecessaryComposedModifier")
public fun Modifier.navigationBarLandscapeSpacer(): Modifier {
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

public fun Modifier.statusBarSpacer(): Modifier {
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

public fun Modifier.navigationBarsSpacer(): Modifier {
    return this.navigationBarsPadding()
}

public fun Modifier.imeSpacer(): Modifier {
    return this.imePadding()
}

@Composable
public fun navigationBarHeight(): Dp {
    return 0.dp // WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
}
