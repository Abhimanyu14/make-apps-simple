package com.makeappssimple.abhimanyu.cosmos.design.system.catalog

import androidx.compose.ui.window.ComposeUIViewController
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.app.CosmosDesignSystemCatalogApp
import platform.UIKit.UIViewController

public fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        CosmosDesignSystemCatalogApp()
    }
}
