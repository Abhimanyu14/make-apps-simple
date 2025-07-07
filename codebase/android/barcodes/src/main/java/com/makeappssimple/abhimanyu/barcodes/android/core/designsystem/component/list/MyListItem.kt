package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.list

import androidx.annotation.StringRes
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.icon.MyIcon
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.text.MyText
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.theme.cosmosFontFamily

@Immutable
public data class MyListItemDataEventDataAndEventHandler(
    val data: MyListItemData,
    val handleEvent: (event: MyListItemDataEvent) -> Unit = {},
)

@Immutable
public data class MyListItemData(
    @StringRes val stringResourceId: Int? = null,
    val text: String? = null,
    val iconVector: ImageVector? = null,
    val painter: Painter? = null,
    val isSelectionMode: Boolean = false,
    val isSelected: Boolean = false,
)

@Immutable
public sealed class MyListItemDataEvent {
    public data object OnClick : MyListItemDataEvent()
    public data object OnLongClick : MyListItemDataEvent()
    public data object OnToggleSelection : MyListItemDataEvent()
}

@Composable
public fun MyListItem(
    modifier: Modifier = Modifier,
    data: MyListItemData,
    handleEvent: (event: MyListItemDataEvent) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                role = Role.Button,
                onClick = {
                    if (data.isSelectionMode) {
                        handleEvent(MyListItemDataEvent.OnToggleSelection)
                    } else {
                        handleEvent(MyListItemDataEvent.OnClick)
                    }
                },
                onLongClick = {
                    handleEvent(MyListItemDataEvent.OnLongClick)
                },
            ),
    ) {
        ListItem(
            headlineContent = {
                if (data.stringResourceId != null) {
                    MyText(
                        textStringResourceId = data.stringResourceId,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = cosmosFontFamily,
                        ),
                    )
                } else {
                    MyText(
                        text = data.text.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = cosmosFontFamily,
                        ),
                    )
                }
            },
            leadingContent = if (data.isSelectionMode) {
                {
                    Checkbox(
                        checked = data.isSelected,
                        onCheckedChange = {
                            handleEvent(MyListItemDataEvent.OnToggleSelection)
                        },
                        modifier = Modifier
                            .size(
                                size = 24.dp,
                            ),
                    )
                }
            } else {
                if (data.iconVector != null) {
                    {
                        MyIcon(
                            imageVector = data.iconVector,
                            modifier = Modifier
                                .size(
                                    size = 24.dp,
                                ),
                        )
                    }
                } else if (data.painter != null) {
                    {
                        MyIcon(
                            painter = data.painter,
                            modifier = Modifier
                                .size(
                                    size = 24.dp,
                                ),
                        )
                    }
                } else {
                    null
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline,
        )
    }
}
