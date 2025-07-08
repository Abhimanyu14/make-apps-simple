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

package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.swipetodismiss.MySwipeToDismissState

@Composable
internal fun MySwipeableList(
    modifier: Modifier = Modifier,
    listItemsDataAndEventHandler: List<MyListItemDataEventDataAndEventHandler>,
    contentPadding: PaddingValues = PaddingValues(),
    actionOnSwipeToEnd: ((position: Int) -> Unit)? = null,
    actionOnSwipeToStart: ((position: Int) -> Unit)? = null,
    backgroundContent: @Composable (RowScope.(mySwipeToDismissState: MySwipeToDismissState) -> Unit)? = null,
) {
    LazyColumn(
        contentPadding = contentPadding,
        modifier = modifier,
    ) {
        itemsIndexed(
            items = listItemsDataAndEventHandler,
            key = { _, listItem ->
                listItem.hashCode()
            },
        ) { index, listItemsDataAndEventHandler ->
            if (actionOnSwipeToEnd != null || actionOnSwipeToStart != null) {
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { dismissValue ->
                        when (dismissValue) {
                            SwipeToDismissBoxValue.StartToEnd -> {
                                if (actionOnSwipeToEnd != null) {
                                    actionOnSwipeToEnd(index)
                                    true
                                } else {
                                    false
                                }
                            }

                            SwipeToDismissBoxValue.EndToStart -> {
                                if (actionOnSwipeToStart != null) {
                                    actionOnSwipeToStart(index)
                                    true
                                } else {
                                    false
                                }
                            }

                            SwipeToDismissBoxValue.Settled -> {
                                false
                            }
                        }
                    },
                )

                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {
                        backgroundContent?.invoke(
                            this,
                            MySwipeToDismissState(dismissState)
                        )
                    },
                    enableDismissFromStartToEnd = actionOnSwipeToEnd != null,
                    enableDismissFromEndToStart = actionOnSwipeToStart != null,
                ) {
                    MyListItem(
                        data = listItemsDataAndEventHandler.data,
                        handleEvent = listItemsDataAndEventHandler.handleEvent,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.background,
                            ),
                    )
                }
            } else {
                MyListItem(
                    data = listItemsDataAndEventHandler.data,
                    handleEvent = listItemsDataAndEventHandler.handleEvent,
                )
            }
        }
    }
}
