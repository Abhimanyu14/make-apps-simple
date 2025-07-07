package com.makeappssimple.abhimanyu.barcodes.android.feature.settings.credits.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIEvent

@Immutable
internal sealed class CreditsScreenUIEvent : ScreenUIEvent {
    data object OnTopAppBarNavigationButtonClick : CreditsScreenUIEvent()

    data class OnLinkClick(
        val url: String,
    ) : CreditsScreenUIEvent()
}
