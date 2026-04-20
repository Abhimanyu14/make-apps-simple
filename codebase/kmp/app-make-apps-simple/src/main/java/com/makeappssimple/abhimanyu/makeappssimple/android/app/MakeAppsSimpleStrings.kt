package com.makeappssimple.abhimanyu.makeappssimple.android.app

internal object MakeAppsSimpleStrings {
    const val barcodes_app_name: String = "Barcodes"
    const val cosmos_design_system_catalog_app_name: String = "Cosmos Catalog"
    const val finance_manager_app_name: String = "Finance Manager"
    const val make_apps_simple_app_name: String = "Make Apps Simple"
    const val make_apps_simple_screen_launcher: String = "Make Apps Simple!"

    fun get(
        template: String,
        args: List<Any> = emptyList(),
    ): String {
        return if (args.isEmpty()) {
            template
        } else {
            template.format(*args.toTypedArray())
        }
    }
}
