/*
 * Copyright 2025-2026 Abhimanyu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.makeappssimple.abhimanyu.cosmos.design.system.catalog.screens.icons

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosListItem
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosListItemData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.navigation.CosmosDesignSystemCatalogNavigationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun CosmosDesignSystemCatalogIconsScreen(
    navigationState: CosmosDesignSystemCatalogNavigationState,
    screenViewModel: CosmosDesignSystemCatalogIconsScreenViewModel = viewModel {
        CosmosDesignSystemCatalogIconsScreenViewModel(navigationState)
    },
) {
    val icons = listOf(
        CosmosIcons.AccountBalance to "AccountBalance",
        CosmosIcons.AccountBalanceWallet to "AccountBalanceWallet",
        CosmosIcons.Add to "Add",
        CosmosIcons.ArrowBack to "ArrowBack",
        CosmosIcons.AttachMoney to "AttachMoney",
        CosmosIcons.Backup to "Backup",
        CosmosIcons.Calculate to "Calculate",
        CosmosIcons.Category to "Category",
        CosmosIcons.Check to "Check",
        CosmosIcons.CheckCircle to "CheckCircle",
        CosmosIcons.Checklist to "Checklist",
        CosmosIcons.ChevronLeft to "ChevronLeft",
        CosmosIcons.ChevronRight to "ChevronRight",
        CosmosIcons.Close to "Close",
        CosmosIcons.ContentCopy to "ContentCopy",
        CosmosIcons.CurrencyExchange to "CurrencyExchange",
        CosmosIcons.CurrencyRupee to "CurrencyRupee",
        CosmosIcons.Delete to "Delete",
        CosmosIcons.DeleteForever to "DeleteForever",
        CosmosIcons.Edit to "Edit",
        CosmosIcons.FilterAlt to "FilterAlt",
        CosmosIcons.Groups to "Groups",
        CosmosIcons.History to "History",
        CosmosIcons.Keyboard to "Keyboard",
        CosmosIcons.Lock to "Lock",
        CosmosIcons.MoreVert to "MoreVert",
        CosmosIcons.Notifications to "Notifications",
        CosmosIcons.RadioButtonUnchecked to "RadioButtonUnchecked",
        CosmosIcons.Schedule to "Schedule",
        CosmosIcons.Search to "Search",
        CosmosIcons.Settings to "Settings",
        CosmosIcons.SwapVert to "SwapVert",
        CosmosIcons.TextSnippet to "TextSnippet",
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Icons")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { screenViewModel.navigateUp() },
                    ) {
                        CosmosIcon(
                            iconResource = CosmosIcons.ArrowBack,
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            items(
                items = icons,
                key = { it.second },
            ) { icon ->
                CosmosListItem(
                    data = CosmosListItemData(
                        stringResource = CosmosStringResource.Text(
                            text = icon.second,
                        ),
                        iconResource = icon.first,
                    ),
                )
            }
        }
    }
}
