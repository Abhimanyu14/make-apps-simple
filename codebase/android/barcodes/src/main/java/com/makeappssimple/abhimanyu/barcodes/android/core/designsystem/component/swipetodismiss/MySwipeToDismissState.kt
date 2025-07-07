package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.swipetodismiss

import androidx.compose.material3.SwipeToDismissBoxState

public class MySwipeToDismissState(
    dismissState: SwipeToDismissBoxState,
    public val targetValue: MySwipeToDismissValue = dismissState.targetValue.toMySwipeToDismissValue(),
)
