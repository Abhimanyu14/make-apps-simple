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

package com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.fake

import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationCommand
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

public class FakeNavigationKitImpl : NavigationKit {
    private val _command: MutableSharedFlow<NavigationCommand> =
        MutableSharedFlow()
    override val command: SharedFlow<NavigationCommand> = _command

    override fun navigateToAccountsScreen(): Job {
        return Job()
    }

    override fun navigateToAddAccountScreen(): Job {
        return Job()
    }

    override fun navigateToAddCategoryScreen(
        transactionType: String,
    ): Job {
        return Job()
    }

    override fun navigateToAddTransactionScreen(
        transactionId: Int?,
    ): Job {
        return Job()
    }

    override fun navigateToAddTransactionForScreen(): Job {
        return Job()
    }

    override fun navigateToAnalysisScreen(): Job {
        return Job()
    }

    override fun navigateToCategoriesScreen(): Job {
        return Job()
    }

    override fun navigateToEditAccountScreen(
        accountId: Int,
    ): Job {
        return Job()
    }

    override fun navigateToEditCategoryScreen(
        categoryId: Int,
    ): Job {
        return Job()
    }

    override fun navigateToEditTransactionScreen(
        transactionId: Int,
    ): Job {
        return Job()
    }

    override fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    ): Job {
        return Job()
    }

    override fun navigateToHomeScreen(): Job {
        return Job()
    }

    override fun navigateToOpenSourceLicensesScreen(): Job {
        return Job()
    }

    override fun navigateToSettingsScreen(): Job {
        return Job()
    }

    override fun navigateToTransactionForValuesScreen(): Job {
        return Job()
    }

    override fun navigateToTransactionsScreen(): Job {
        return Job()
    }

    override fun navigateToViewTransactionScreen(
        transactionId: Int,
    ): Job {
        return Job()
    }

    override fun navigateUp(): Job {
        return Job()
    }
}
