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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util

import com.makeappssimple.abhimanyu.finance.manager.android.core.common.extensions.equalsIgnoringCase

private object DefaultConstants {
    const val EXPENSE_CATEGORY = "Default"
    const val INCOME_CATEGORY = "Salary"
    const val INVESTMENT_CATEGORY = "Investment"
    const val ACCOUNT = "Cash"
    const val TRANSACTION_FOR = "Self"
}

public fun isDefaultExpenseCategory(
    category: String,
): Boolean {
    return category.equalsIgnoringCase(
        other = DefaultConstants.EXPENSE_CATEGORY,
    )
}

public fun isDefaultIncomeCategory(
    category: String,
): Boolean {
    return category.equalsIgnoringCase(
        other = DefaultConstants.INCOME_CATEGORY,
    )
}

public fun isDefaultInvestmentCategory(
    category: String,
): Boolean {
    return category.equalsIgnoringCase(
        other = DefaultConstants.INVESTMENT_CATEGORY,
    )
}

public fun isDefaultAccount(
    account: String,
): Boolean {
    return account.equalsIgnoringCase(
        other = DefaultConstants.ACCOUNT,
    )
}

public fun isDefaultTransactionFor(
    transactionFor: String,
): Boolean {
    return transactionFor.equalsIgnoringCase(
        other = DefaultConstants.TRANSACTION_FOR,
    )
}
