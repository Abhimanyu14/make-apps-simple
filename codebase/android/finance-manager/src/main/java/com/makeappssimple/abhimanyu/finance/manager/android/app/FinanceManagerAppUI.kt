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

package com.makeappssimple.abhimanyu.finance.manager.android.app

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.navigation.FinanceManagerNavGraph

@Composable
internal fun FinanceManagerAppUI(
    financeManagerActivityViewModel: FinanceManagerActivityViewModel,
) {
    FinanceManagerAppTheme {
        // To remove overscroll effect globally
        CompositionLocalProvider(
            LocalOverscrollFactory provides null,
        ) {
            FinanceManagerNavGraph(
                financeManagerActivityViewModel = financeManagerActivityViewModel,
            )
        }
    }
}
