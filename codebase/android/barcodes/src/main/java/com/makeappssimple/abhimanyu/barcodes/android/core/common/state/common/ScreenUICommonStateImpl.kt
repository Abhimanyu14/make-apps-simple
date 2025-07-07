package com.makeappssimple.abhimanyu.barcodes.android.core.common.state.common

import com.makeappssimple.abhimanyu.barcodes.android.core.common.state.loading.ScreenUIStateLoading

public class ScreenUICommonStateImpl(
    private val screenUIStateLoading: ScreenUIStateLoading,
) : ScreenUICommonState, ScreenUIStateLoading by screenUIStateLoading
