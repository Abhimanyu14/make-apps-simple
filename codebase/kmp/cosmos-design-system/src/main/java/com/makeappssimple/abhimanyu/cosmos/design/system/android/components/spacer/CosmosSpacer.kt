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

package com.makeappssimple.abhimanyu.cosmos.design.system.android.components.spacer

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
import com.makeappssimple.abhimanyu.cosmos.design.system.android.extensions.ifTrue

private val defaultSpacerSize = 100.dp

@Composable
public fun CosmosHorizontalSpacer(
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
public fun CosmosVerticalSpacer(
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
public fun CosmosNonFillingVerticalSpacer(
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
public fun CosmosStatusBarSpacer(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .cosmosStatusBarSpacer(),
    )
}

@Composable
public fun CosmosNavigationBarsAndImeSpacer(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .cosmosNavigationBarsSpacer()
            .cosmosImeSpacer(),
    )
}

@Composable
public fun CosmosNavigationBarsSpacer(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .cosmosNavigationBarsSpacer(),
    )
}

@Composable
public fun CosmosImeSpacer(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .cosmosImeSpacer(),
    )
}

public fun Modifier.cosmosImeSpacer(): Modifier {
    return this.imePadding()
}

@SuppressLint("UnnecessaryComposedModifier")
public fun Modifier.cosmosNavigationBarLandscapeSpacer(): Modifier {
    return composed {
        Modifier
            .fillMaxSize()
            .ifTrue(
                condition = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ) {
                this.cosmosNavigationBarsSpacer()
            }
    }
}

public fun Modifier.cosmosNavigationBarsSpacer(): Modifier {
    return this.navigationBarsPadding()
}

@OptIn(ExperimentalLayoutApi::class)
public fun Modifier.cosmosStatusBarSpacer(): Modifier {
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

@Composable
public fun cosmosNavigationBarHeight(): Dp {
    return 0.dp // WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
}
