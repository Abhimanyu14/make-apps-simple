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

@file:OptIn(ExperimentalMaterial3Api::class)

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.scaffold

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.cosmos.design.system.android.typealiases.ColumnScopedComposableContent
import com.makeappssimple.abhimanyu.cosmos.design.system.android.typealiases.ComposableContent
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.theme.BottomSheetShape

import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.common.BottomSheetBackHandler
import kotlinx.coroutines.CoroutineScope

private object MyScaffoldConstants {
    val topAppBarHeight = 64.dp
}

@Composable
internal fun MyScaffold(
    modifier: Modifier = Modifier,

    // ModalBottomSheetLayout
    sheetContent: ColumnScopedComposableContent = {},
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    ),
    sheetShape: Shape = BottomSheetShape,
    sheetBackgroundColor: Color = CosmosAppTheme.colorScheme.surface,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,

    // Scaffold
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    topBar: ComposableContent = {},
    bottomBar: ComposableContent = {},
    snackbarHost: ComposableContent = {
        SnackbarHost(snackbarHostState)
    },
    floatingActionButton: ComposableContent = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    backgroundColor: Color = CosmosAppTheme.colorScheme.background,
    contentColor: Color = contentColorFor(backgroundColor),

    // MyScaffoldContentWrapper
    onClick: () -> Unit = {},

    contentTestTag: String = "",

    // BottomSheetBackHandler
    isModalBottomSheetVisible: Boolean = false,
    isBackHandlerEnabled: Boolean = false,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onBottomSheetDismiss: () -> Unit = {},

    content: ColumnScopedComposableContent,
) {
    BottomSheetBackHandler(
        isEnabled = isBackHandlerEnabled,
        coroutineScope = coroutineScope,
        modalBottomSheetState = sheetState,
        onBottomSheetDismiss = onBottomSheetDismiss,
    )

    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = backgroundColor,
        contentColor = contentColor,
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .testTag(
                    tag = contentTestTag,
                ),
        ) {
            MyScaffoldContentWrapper(
                innerPadding = innerPadding,
                onClick = onClick,
                content = content,
            )
        }
    }
    if (isModalBottomSheetVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            shape = sheetShape,
            containerColor = sheetBackgroundColor,
            tonalElevation = 0.dp,
            dragHandle = {},
            scrimColor = scrimColor,
            onDismissRequest = onBottomSheetDismiss,
            contentWindowInsets = {
                WindowInsets.systemBars.only(WindowInsetsSides.Bottom)
            },
        ) {
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            val navigationBarsHeight =
                WindowInsets.navigationBars.asPaddingValues()
                    .calculateBottomPadding()

            val bottomSheetModifier =
                if (sheetShape == BottomSheetExpandedShape) {
                    Modifier
                        .fillMaxSize()
                } else {
                    Modifier
                        .heightIn(
                            max = screenHeight + navigationBarsHeight - MyScaffoldConstants.topAppBarHeight,
                        )
                }

            Column(
                modifier = bottomSheetModifier,
            ) {
                Box(
                    modifier = bottomSheetModifier
                        .animateContentSize(),
                ) {
                    this@Column.sheetContent()
                }
            }
        }
    }
}
