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

package com.makeappssimple.abhimanyu.makeappssimple.android.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.makeappssimple.abhimanyu.cosmos.design.system.android.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.text.CosmosTextStyle
import com.makeappssimple.abhimanyu.makeappssimple.android.R

@Composable
internal fun LauncherAppUI(
    launcherItems: List<LauncherItem>,
) {
    Box(
        modifier = Modifier.Companion
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        Column(
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
            ),
        ) {
            Spacer(
                modifier = Modifier
                    .height(
                        height = 8.dp,
                    ),
            )
            CosmosText(
                text = stringResource(
                    id = R.string.make_apps_simple_screen_launcher,
                ),
                style = CosmosTextStyle.Heading1,
            )
            Spacer(
                modifier = Modifier
                    .height(
                        height = 8.dp,
                    ),
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                ),
                horizontalArrangement = Arrangement.spacedBy(
                    8.dp,
                ),
                modifier = Modifier
                    .padding(
                        horizontal = 8.dp,
                    )
            ) {
                items(
                    items = launcherItems,
                ) { launcherItem ->
                    LauncherItemUI(
                        launcherItem = launcherItem,
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .height(
                        height = 16.dp,
                    ),
            )
        }
    }
}

@Composable
private fun LauncherItemUI(
    launcherItem: LauncherItem,
) {
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
                onClick = launcherItem.onClick,
            ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LauncherItemIcon(
                launcherItem = launcherItem,
            )
            CosmosText(
                text = launcherItem.text,
                style = CosmosTextStyle.Footnote,
                maxLines = 2,
                minLines = 2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun LauncherItemIcon(
    launcherItem: LauncherItem,
) {
    AsyncImage(
        model = launcherItem.iconResourceId,
        contentDescription = launcherItem.text,
        modifier = Modifier
            .size(
                size = 60.dp,
            )
            .padding(
                top = 4.dp,
                start = 4.dp,
                end = 4.dp,
                bottom = 2.dp,
            )
            .clip(
                shape = RoundedCornerShape(
                    size = 12.dp,
                ),
            )
            .background(
                color = launcherItem.backgroundColor,
            ),
    )
}
