package com.makeappssimple.abhimanyu.cosmos.design.system.catalog

import androidx.compose.ui.window.ComposeUIViewController
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.common.CosmosDesignSystemCatalogCommonApp
import platform.UIKit.UIViewController

public fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        CosmosDesignSystemCatalogCommonApp()
    }
}
