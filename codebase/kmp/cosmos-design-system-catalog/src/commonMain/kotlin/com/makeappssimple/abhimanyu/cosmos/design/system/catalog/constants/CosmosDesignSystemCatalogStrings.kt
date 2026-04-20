package com.makeappssimple.abhimanyu.cosmos.design.system.catalog.constants

internal object CosmosDesignSystemCatalogStrings {
    const val cosmos_design_system_catalog_app_name: String = "Cosmos Catalog"

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
