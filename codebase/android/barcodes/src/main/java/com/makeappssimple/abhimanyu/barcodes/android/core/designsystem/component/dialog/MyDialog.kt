package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.button.MyTextButton
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.text.MyText

@Immutable
public data class DialogData(
    val isVisible: Boolean = false,
    val confirmButtonText: String? = null,
    val dismissButtonText: String? = null,
    val message: String = "",
    val title: String = "",
)

@Immutable
public sealed class MyDialogEvent {
    public data object OnConfirmButtonClick : MyDialogEvent()
    public data object OnDismiss : MyDialogEvent()
    public data object OnDismissButtonClick : MyDialogEvent()
}

@Composable
public fun MyDialog(
    modifier: Modifier = Modifier,
    dialogData: DialogData,
    handleEvent: (event: MyDialogEvent) -> Unit = {},
) {
    if (dialogData.isVisible) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {
                handleEvent(MyDialogEvent.OnDismiss)
            },
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true,
            ),
            title = {
                MyText(
                    text = dialogData.title,
                )
            },
            text = {
                MyText(
                    text = dialogData.message,
                )
            },
            confirmButton = {
                dialogData.confirmButtonText?.let {
                    MyTextButton(
                        onClickLabel = dialogData.confirmButtonText,
                        onClick = {
                            handleEvent(MyDialogEvent.OnConfirmButtonClick)
                        },
                    ) {
                        MyText(
                            text = dialogData.confirmButtonText,
                        )
                    }
                }
            },
            dismissButton = {
                dialogData.dismissButtonText?.let {
                    MyTextButton(
                        onClickLabel = dialogData.dismissButtonText,
                        onClick = {
                            handleEvent(MyDialogEvent.OnDismissButtonClick)
                        },
                    ) {
                        MyText(
                            text = dialogData.dismissButtonText,
                        )
                    }
                }
            }
        )
    }
}
