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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Change Notes
 * 1. Any changes in this data class should also have corresponding changes in [initial_data.json]
 * 2. There changes do NOT need migrations as
 * they will be bundled with the new APK when any changes are done
 */
@Serializable
internal data class InitialDatabaseData(
    @SerialName("default_accounts")
    val defaultAccounts: Accounts,

    @SerialName("default_categories")
    val defaultCategories: Categories,

    @SerialName("default_transaction_for_values")
    val defaultTransactionForValues: TransactionForValues,
)

@Serializable
internal data class Accounts(
    @SerialName("version_number")
    val versionNumber: Int,

    @SerialName("versioned_accounts")
    val versionedAccounts: List<VersionedAccounts>,
)

@Serializable
internal data class VersionedAccounts(
    @SerialName("version_number")
    val versionNumber: Int,

    @SerialName("accounts_data")
    val accountsData: List<AccountEntity>,
)

@Serializable
internal data class Categories(
    @SerialName("version_number")
    val versionNumber: Int,

    @SerialName("versioned_categories")
    val versionedCategories: List<VersionedCategories>,
)

@Serializable
internal data class VersionedCategories(
    @SerialName("version_number")
    val versionNumber: Int,

    @SerialName("categories_data")
    val categoriesData: List<CategoryEntity>,
)

@Serializable
internal data class TransactionForValues(
    @SerialName("version_number")
    val versionNumber: Int,

    @SerialName("versioned_transaction_for_values")
    val versionedTransactionForValues: List<VersionedTransactionForValues>,
)

@Serializable
internal data class VersionedTransactionForValues(
    @SerialName("version_number")
    val versionNumber: Int,

    @SerialName("transaction_for_values_data")
    val transactionForValuesData: List<TransactionForEntity>,
)
