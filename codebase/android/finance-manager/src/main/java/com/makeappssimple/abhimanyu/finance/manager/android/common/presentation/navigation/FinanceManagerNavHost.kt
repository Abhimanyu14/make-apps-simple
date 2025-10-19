/*
 * Copyright 2025-2025 Abhimanyu
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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.navigation.Screen
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.navigation.accountsNavGraph
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.analysis.navigation.analysisNavGraph
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.navigation.categoriesNavGraph
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.home.navigation.homeNavGraph
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.settings.navigation.settingsNavGraph
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.navigation.transactionForNavGraph
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.navigation.transactionsNavGraph

@Composable
internal fun FinanceManagerNavHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
    ) {
        accountsNavGraph()
        analysisNavGraph()
        categoriesNavGraph()
        homeNavGraph()
        settingsNavGraph()
        transactionForNavGraph()
        transactionsNavGraph()
    }
}
