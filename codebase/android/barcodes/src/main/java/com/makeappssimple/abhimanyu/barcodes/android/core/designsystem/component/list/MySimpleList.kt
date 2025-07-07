package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
public fun MySimpleList(
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
