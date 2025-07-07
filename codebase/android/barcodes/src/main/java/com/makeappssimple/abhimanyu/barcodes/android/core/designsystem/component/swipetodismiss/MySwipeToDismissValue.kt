package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.swipetodismiss

import androidx.compose.material3.SwipeToDismissBoxValue

public enum class MySwipeToDismissValue {
    Default,
    DismissedToEnd,
    DismissedToStart
}

internal fun SwipeToDismissBoxValue.toMySwipeToDismissValue(): MySwipeToDismissValue {
    return when (this) {
        SwipeToDismissBoxValue.StartToEnd -> MySwipeToDismissValue.DismissedToEnd
        SwipeToDismissBoxValue.EndToStart -> MySwipeToDismissValue.DismissedToStart
        SwipeToDismissBoxValue.Settled -> MySwipeToDismissValue.Default
    }
}
