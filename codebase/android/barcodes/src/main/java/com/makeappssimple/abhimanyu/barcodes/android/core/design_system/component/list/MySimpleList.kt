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

package com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun MySimpleList(
    modifier: Modifier = Modifier,
    listItemsDataAndEventHandler: List<MyListItemDataEventDataAndEventHandler>,
    contentPadding: PaddingValues = PaddingValues(),
) {
    LazyColumn(
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        items(
            items = listItemsDataAndEventHandler,
            key = { listItem ->
                listItem.hashCode()
            },
        ) { listItemsDataAndEventHandler ->
            MyListItem(
                data = listItemsDataAndEventHandler.data,
                handleEvent = listItemsDataAndEventHandler.handleEvent,
            )
        }
    }
}
