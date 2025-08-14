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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.view_model

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.common.core.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.common.core.extensions.combine
import com.makeappssimple.abhimanyu.common.core.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.common.core.extensions.filter
import com.makeappssimple.abhimanyu.common.core.extensions.filterDigits
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.common.core.extensions.isNotZero
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.common.core.extensions.sortedWith
import com.makeappssimple.abhimanyu.common.core.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.common.core.extensions.toImmutableList
import com.makeappssimple.abhimanyu.common.core.extensions.toIntOrZero
import com.makeappssimple.abhimanyu.common.core.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.uri_decoder.UriDecoder
import com.makeappssimple.abhimanyu.common.core.util.defaultImmutableListStateIn
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.UpdateAccountBalanceAmountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetTransactionDataByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.UpdateTransactionUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.minus
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.plus
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.sortOrder
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.navigation.EditTransactionScreenArgs
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.math.abs

public class EditTransactionScreenViewModelOld(
    navigationKit: NavigationKit,
    savedStateHandle: SavedStateHandle,
    screenUIStateDelegate: ScreenUIStateDelegate,
    uriDecoder: UriDecoder,
    private val coroutineScope: CoroutineScope,
    private val dateTimeKit: DateTimeKit,
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getTitleSuggestionsUseCase: GetTitleSuggestionsUseCase,
    private val getTransactionDataByIdUseCase: GetTransactionDataByIdUseCase,
    private val updateAccountBalanceAmountUseCase: UpdateAccountBalanceAmountUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    logKit = logKit,
    navigationKit = navigationKit,
    screenUIStateDelegate = screenUIStateDelegate,
) {
    // region screen args
    private val screenArgs = EditTransactionScreenArgs(
        savedStateHandle = savedStateHandle,
        uriDecoder = uriDecoder,
    )
    // endregion

    // region Transaction data
    private var editingTransactionData: TransactionData? = null
    private var maxRefundAmount: Amount? = null
    // endregion

    // region Default data
    private var defaultAccount: Account? = null
    private var defaultExpenseCategory: Category? = null
    private var defaultIncomeCategory: Category? = null
    private var defaultInvestmentCategory: Category? = null
    private var defaultDataIdFromDataStore: DefaultDataId? = null
    // endregion

    // region Data source
    private var categories: ImmutableList<Category> = persistentListOf()

    private val titleSuggestions: MutableStateFlow<ImmutableList<String>?> =
        MutableStateFlow(
            value = null,
        )
    // endregion

    // region valid transaction types for new transaction
    private val validTransactionTypesForNewTransaction: MutableStateFlow<ImmutableList<TransactionType>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    // endregion

    // region transaction for values
    private val transactionForValues: MutableStateFlow<ImmutableList<TransactionFor>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    // endregion

    // region accounts
    private val accounts: MutableStateFlow<ImmutableList<Account>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    // endregion

    internal val currentLocalDate: LocalDate = dateTimeKit.getCurrentLocalDate()


    private val _uiState: MutableStateFlow<EditTransactionScreenUiStateData> =
        MutableStateFlow(
            value = EditTransactionScreenUiStateData(
                selectedTransactionTypeIndex = null,
                amount = TextFieldValue(),
                title = TextFieldValue(),
                description = TextFieldValue(),
                category = null,
                selectedTransactionForIndex = 0,
                accountFrom = null,
                accountTo = null,
                transactionDate = dateTimeKit.getCurrentLocalDate(),
                transactionTime = dateTimeKit.getCurrentLocalTime(),
            ),
        )
    internal val uiState: StateFlow<EditTransactionScreenUiStateData> =
        _uiState.asStateFlow()
    private val uiVisibilityState: MutableStateFlow<EditTransactionScreenUiVisibilityState> =
        MutableStateFlow(
            value = EditTransactionScreenUiVisibilityState.Expense,
        )

    // Dependant data
    private val selectedTransactionType: MutableStateFlow<TransactionType?> =
        MutableStateFlow(
            value = null,
        )

    internal val filteredCategories: StateFlow<ImmutableList<Category>> =
        selectedTransactionType.map { selectedTransactionType ->
            categories.filter { category ->
                category.transactionType == selectedTransactionType
            }
        }.defaultImmutableListStateIn(
            scope = coroutineScope,
        )

    private val selectedCategoryId: Flow<Int?> = uiState.map {
        it.category?.id
    }

    internal val isCtaButtonEnabled: Flow<Boolean> = combine(
        flow = uiState,
        flow2 = selectedTransactionType,
    ) { uiState, selectedTransactionType ->
        when (selectedTransactionType) {
            TransactionType.INCOME -> {
                uiState.amount.text.isNotNullOrBlank() &&
                        uiState.title.text.isNotNullOrBlank() &&
                        uiState.amount.text.toIntOrZero().isNotZero() &&
                        uiState.amountErrorText.isNull()
            }

            TransactionType.EXPENSE -> {
                uiState.amount.text.isNotNullOrBlank() &&
                        uiState.title.text.isNotNullOrBlank() &&
                        uiState.amount.text.toIntOrZero().isNotZero() &&
                        uiState.amountErrorText.isNull()
            }

            TransactionType.TRANSFER -> {
                uiState.amount.text.isNotNullOrBlank() &&
                        uiState.accountFrom?.id != uiState.accountTo?.id &&
                        uiState.amount.text.toIntOrZero().isNotZero() &&
                        uiState.amountErrorText.isNull()
            }

            TransactionType.ADJUSTMENT -> {
                false
            }

            TransactionType.INVESTMENT -> {
                uiState.amount.text.isNotNullOrBlank() &&
                        uiState.title.text.isNotNullOrBlank() &&
                        uiState.amount.text.toIntOrZero().isNotZero() &&
                        uiState.amountErrorText.isNull()
            }

            TransactionType.REFUND -> {
                val maxRefundAmountValue = maxRefundAmount?.value.orZero()
                if (uiState.amountErrorText.isNull() &&
                    (uiState.amount.text.toLongOrZero() > maxRefundAmountValue)
                ) {
                    updateEditTransactionScreenUiState(
                        updatedEditTransactionScreenUiStateData = uiState
                            .copy(
                                amountErrorText = maxRefundAmount?.run {
                                    this.toString()
                                },
                            )
                    )
                    false
                } else if (uiState.amountErrorText.isNotNull() &&
                    (uiState.amount.text.toLongOrZero() <= maxRefundAmountValue)
                ) {
                    updateEditTransactionScreenUiState(
                        updatedEditTransactionScreenUiStateData = uiState
                            .copy(
                                amountErrorText = null,
                            )
                    )
                    false
                } else {
                    uiState.amount.text.isNotNullOrBlank() &&
                            uiState.title.text.isNotNullOrBlank() &&
                            uiState.amount.text.toIntOrZero().isNotZero() &&
                            uiState.amountErrorText.isNull()
                }
            }

            null -> {
                false
            }
        }
    }

    private val isDataFetchCompleted: MutableStateFlow<Boolean> =
        MutableStateFlow(
            value = false,
        )

    // region fetchData
    override fun fetchData(): Job {
        return coroutineScope.launch {
            awaitAll(
                async {
                    defaultDataIdFromDataStore =
                        financeManagerPreferencesRepository.getDefaultDataId()
                },
                async {
                    categories = getAllCategoriesUseCase()
                },
                async {
                    accounts.update {
                        getAllAccountsUseCase()
                            .sortedWith(
                                comparator = compareBy<Account> {
                                    it.type.sortOrder
                                }.thenByDescending {
                                    it.balanceAmount.value
                                }
                            )
                    }
                },
                async {
                    transactionForValues.update {
                        getAllTransactionForValuesUseCase()
                    }
                },
            )
            calculateValidTransactionTypesForNewTransaction()
            updateDefaultCategory()
            updateDefaultAccount()
            if (isAddingRefundTransactionOrEditingAnyTransaction()) {
                getTransactionDataForAddingRefundTransactionOrEditingAnyTransaction() // TODO(Abhi): Better naming
            } else {
                updateInitialSelectedTransactionType()
            }
            isDataFetchCompleted.update {
                true
            }
        }
    }
    // endregion

    // region observeData
    override fun observeData() {
        observeSelectedTransactionType()
        observeSelectedCategory()
    }

    override fun updateUiStateAndStateEvents() {
        // TODO(Abhi): Not Implemented
    }
    // endregion

    internal fun updateTransaction() {
        coroutineScope.launch {
            val selectedTransactionTypeValue = selectedTransactionType.value
            val uiStateValue = uiState.value
            selectedTransactionTypeValue?.let {
                val amount = Amount(
                    value = uiStateValue.amount.text.toLongOrZero(),
                )
                val categoryId = when (selectedTransactionTypeValue) {
                    TransactionType.INCOME -> {
                        uiStateValue.category?.id
                    }

                    TransactionType.EXPENSE -> {
                        uiStateValue.category?.id
                    }

                    TransactionType.TRANSFER -> {
                        null
                    }

                    TransactionType.ADJUSTMENT -> {
                        null
                    }

                    TransactionType.INVESTMENT -> {
                        uiStateValue.category?.id
                    }

                    TransactionType.REFUND -> {
                        editingTransactionData?.category?.id
                    }
                }
                val accountFromId = when (selectedTransactionTypeValue) {
                    TransactionType.INCOME -> {
                        null
                    }

                    TransactionType.EXPENSE -> {
                        uiStateValue.accountFrom?.id
                    }

                    TransactionType.TRANSFER -> {
                        uiStateValue.accountFrom?.id
                    }

                    TransactionType.ADJUSTMENT -> {
                        null
                    }

                    TransactionType.INVESTMENT -> {
                        uiStateValue.accountFrom?.id
                    }

                    TransactionType.REFUND -> {
                        null
                    }
                }
                val accountToId = when (selectedTransactionTypeValue) {
                    TransactionType.INCOME -> {
                        uiStateValue.accountTo?.id
                    }

                    TransactionType.EXPENSE -> {
                        null
                    }

                    TransactionType.TRANSFER -> {
                        uiStateValue.accountTo?.id
                    }

                    TransactionType.ADJUSTMENT -> {
                        null
                    }

                    TransactionType.INVESTMENT -> {
                        null
                    }

                    TransactionType.REFUND -> {
                        uiStateValue.accountTo?.id
                    }
                }
                val title = when (selectedTransactionTypeValue) {
                    TransactionType.TRANSFER -> {
                        TransactionType.TRANSFER.title
                    }

                    TransactionType.REFUND -> {
                        TransactionType.REFUND.title
                    }

                    else -> {
                        uiStateValue.title.text.capitalizeWords()
                    }
                }
                val transactionForId: Int =
                    when (selectedTransactionTypeValue) {
                        TransactionType.INCOME -> {
                            1
                        }

                        TransactionType.EXPENSE -> {
                            transactionForValues.value.getOrNull(uiStateValue.selectedTransactionForIndex)?.id
                                ?: 1
                        }

                        TransactionType.TRANSFER -> {
                            1
                        }

                        TransactionType.ADJUSTMENT -> {
                            1
                        }

                        TransactionType.INVESTMENT -> {
                            1
                        }

                        TransactionType.REFUND -> {
                            1
                        }
                    }

                editingTransactionData?.transaction?.let { transaction ->
                    val transactionTimestamp = LocalDateTime
                        .of(
                            uiStateValue.transactionDate,
                            uiStateValue.transactionTime
                        )
                        .toEpochMilli()
                    updateTransactionUseCase(
                        originalTransaction = transaction,
                        updatedTransaction = transaction
                            .copy(
                                amount = amount,
                                categoryId = categoryId,
                                accountFromId = accountFromId,
                                accountToId = accountToId,
                                description = uiStateValue.description.text,
                                title = title,
                                creationTimestamp = dateTimeKit.getCurrentTimeMillis(),
                                transactionTimestamp = transactionTimestamp,
                                transactionForId = transactionForId,
                                transactionType = selectedTransactionTypeValue,
                            ),
                    )

                    // region transaction account updates
                    val accountBalanceAmountChangeMap = hashMapOf<Int, Long>()
                    editingTransactionData?.accountFrom?.let { transactionAccountFrom ->
                        accountBalanceAmountChangeMap[transactionAccountFrom.id] =
                            accountBalanceAmountChangeMap[transactionAccountFrom.id].orZero() + transaction.amount.value
                    }
                    uiStateValue.accountFrom?.let { accountFrom ->
                        accountBalanceAmountChangeMap[accountFrom.id] =
                            accountBalanceAmountChangeMap[accountFrom.id].orZero() - uiState.value.amount.text.toLongOrZero()
                    }
                    editingTransactionData?.accountTo?.let { transactionAccountTo ->
                        accountBalanceAmountChangeMap[transactionAccountTo.id] =
                            accountBalanceAmountChangeMap[transactionAccountTo.id].orZero() - transaction.amount.value
                    }
                    uiStateValue.accountTo?.let { accountTo ->
                        accountBalanceAmountChangeMap[accountTo.id] =
                            accountBalanceAmountChangeMap[accountTo.id].orZero() + uiState.value.amount.text.toLongOrZero()
                    }
                    updateAccountBalanceAmountUseCase(
                        accountsBalanceAmountChange = accountBalanceAmountChangeMap.toImmutableList(),
                    )
                    // endregion
                }
            }
            navigateUp()
        }
    }

    // region UI changes
    private fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiStateData = uiState.value
                .copy(
                    selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex,
                ),
        )
        selectedTransactionType.value =
            validTransactionTypesForNewTransaction.value.getOrNull(
                index = updatedSelectedTransactionTypeIndex,
            )
    }

    private fun updateAmount(
        updatedAmount: TextFieldValue,
    ) {
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiStateData = uiState.value
                .copy(
                    amount = updatedAmount
                        .copy(
                            updatedAmount.text.filterDigits(),
                        ),
                ),
        )
    }

    internal fun clearAmount() {
        updateAmount(
            updatedAmount = uiState.value.amount
                .copy(
                    text = "",
                ),
        )
    }

    private fun updateTitle(
        updatedTitle: TextFieldValue,
    ) {
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiStateData = uiState.value
                .copy(
                    title = updatedTitle,
                ),
        )
    }

    internal fun clearTitle() {
        updateTitle(
            updatedTitle = uiState.value.title
                .copy(
                    text = "",
                ),
        )
    }

    private fun updateDescription(
        updatedDescription: TextFieldValue,
    ) {
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiStateData = uiState.value
                .copy(
                    description = updatedDescription,
                ),
        )
    }

    internal fun clearDescription() {
        updateDescription(
            updatedDescription = uiState.value.description
                .copy(
                    text = "",
                ),
        )
    }

    private fun updateCategory(
        updatedCategory: Category?,
    ) {
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiStateData = uiState.value
                .copy(
                    category = updatedCategory,
                ),
        )
    }

    internal fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ) {
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiStateData = uiState.value
                .copy(
                    selectedTransactionForIndex = updatedSelectedTransactionForIndex,
                ),
        )
    }

    private fun updateAccountFrom(
        updatedAccountFrom: Account?,
    ) {
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiStateData = uiState.value
                .copy(
                    accountFrom = updatedAccountFrom,
                ),
        )
    }

    private fun updateAccountTo(
        updatedAccountTo: Account?,
    ) {
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiStateData = uiState.value
                .copy(
                    accountTo = updatedAccountTo,
                ),
        )
    }

    internal fun updateTransactionDate(
        updatedTransactionDate: LocalDate,
    ) {
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiStateData = uiState.value
                .copy(
                    transactionDate = updatedTransactionDate,
                ),
        )
    }

    internal fun updateTransactionTime(
        updatedTransactionTime: LocalTime,
    ) {
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiStateData = uiState.value
                .copy(
                    transactionTime = updatedTransactionTime,
                ),
        )
    }

    private fun updateEditTransactionScreenUiState(
        updatedEditTransactionScreenUiStateData: EditTransactionScreenUiStateData,
    ) {
        _uiState.update {
            updatedEditTransactionScreenUiStateData
        }
    }

    private fun updateEditTransactionScreenUiVisibilityState(
        updatedEditTransactionScreenUiVisibilityState: EditTransactionScreenUiVisibilityState,
    ) {
        uiVisibilityState.value = updatedEditTransactionScreenUiVisibilityState
    }
    // endregion

    private fun observeSelectedTransactionType() {
        coroutineScope.launch {
            selectedTransactionType.collectLatest { transactionType ->
                transactionType?.let {
                    handleTransactionTypeChange(
                        transactionType = transactionType,
                    )
                }
            }
        }
    }

    private fun handleTransactionTypeChange(
        transactionType: TransactionType,
    ) {
        val uiVisibilityState: EditTransactionScreenUiVisibilityState? =
            when (transactionType) {
                TransactionType.INCOME -> {
                    EditTransactionScreenUiVisibilityState.Income
                }

                TransactionType.EXPENSE -> {
                    EditTransactionScreenUiVisibilityState.Expense
                }

                TransactionType.TRANSFER -> {
                    EditTransactionScreenUiVisibilityState.Transfer
                }

                TransactionType.ADJUSTMENT -> {
                    null
                }

                TransactionType.INVESTMENT -> {
                    EditTransactionScreenUiVisibilityState.Investment
                }

                TransactionType.REFUND -> {
                    EditTransactionScreenUiVisibilityState.Refund
                }
            }
        uiVisibilityState?.let {
            updateEditTransactionScreenUiVisibilityState(
                updatedEditTransactionScreenUiVisibilityState = uiVisibilityState,
            )
        }

        when (transactionType) {
            TransactionType.INCOME -> {
                val updatedCategory =
                    if (transactionType == editingTransactionData?.transaction?.transactionType) {
                        editingTransactionData?.category
                            ?: defaultIncomeCategory
                    } else {
                        defaultIncomeCategory
                    }
                updateCategory(
                    updatedCategory = updatedCategory,
                )

                updateAccountFrom(
                    updatedAccountFrom = null,
                )
                updateAccountTo(
                    updatedAccountTo = editingTransactionData?.accountTo
                        ?: defaultAccount,
                )
            }

            TransactionType.EXPENSE -> {
                val updatedCategory =
                    if (transactionType == editingTransactionData?.transaction?.transactionType) {
                        editingTransactionData?.category
                            ?: defaultExpenseCategory
                    } else {
                        defaultExpenseCategory
                    }
                updateCategory(
                    updatedCategory = updatedCategory,
                )

                updateAccountFrom(
                    updatedAccountFrom = editingTransactionData?.accountFrom
                        ?: defaultAccount,
                )
                updateAccountTo(
                    updatedAccountTo = null,
                )
            }

            TransactionType.TRANSFER -> {
                updateAccountFrom(
                    updatedAccountFrom = editingTransactionData?.accountFrom
                        ?: defaultAccount,
                )
                updateAccountTo(
                    updatedAccountTo = editingTransactionData?.accountTo
                        ?: defaultAccount,
                )
            }

            TransactionType.ADJUSTMENT -> {}

            TransactionType.INVESTMENT -> {
                val updatedCategory =
                    if (transactionType == editingTransactionData?.transaction?.transactionType) {
                        editingTransactionData?.category
                            ?: defaultInvestmentCategory
                    } else {
                        defaultInvestmentCategory
                    }
                updateCategory(
                    updatedCategory = updatedCategory,
                )

                updateAccountFrom(
                    updatedAccountFrom = editingTransactionData?.accountFrom
                        ?: defaultAccount,
                )
                updateAccountTo(
                    updatedAccountTo = null,
                )
            }

            TransactionType.REFUND -> {}
        }
    }

    private fun observeSelectedCategory() {
        coroutineScope.launch {
            combineAndCollectLatest(
                flow = selectedCategoryId,
                flow2 = uiState,
            ) { (selectedCategoryId, uiState) ->
                selectedCategoryId?.let {
                    titleSuggestions.update {
                        getTitleSuggestionsUseCase(
                            categoryId = selectedCategoryId,
                            enteredTitle = uiState.title.text,
                        )
                    }
                }
            }
        }
    }

    private fun calculateValidTransactionTypesForNewTransaction() {
        val excludedTransactionTypes = mutableSetOf(
            TransactionType.ADJUSTMENT,
            TransactionType.REFUND
        )
        // Cannot create transfer with single account
        if (accounts.value.size <= 1) {
            excludedTransactionTypes.add(TransactionType.TRANSFER)
        }

        val transactionTypesRemainingAfterExclusion =
            (TransactionType.entries.toSet() - excludedTransactionTypes).toList()
        validTransactionTypesForNewTransaction.update {
            transactionTypesRemainingAfterExclusion.toImmutableList()
        }

        updateSelectedTransactionTypeIndex(
            updatedSelectedTransactionTypeIndex = transactionTypesRemainingAfterExclusion.indexOf(
                element = TransactionType.EXPENSE,
            ),
        )
    }

    private suspend fun getTransactionDataForAddingRefundTransactionOrEditingAnyTransaction() {
        screenArgs.transactionId?.let { id ->
            editingTransactionData = getTransactionDataByIdUseCase(
                id = id,
            )
            editingTransactionData?.let { originalTransactionData ->
                if (originalTransactionData.transaction.transactionType == TransactionType.REFUND) {
                    calculateMaxRefundAmount()
                }
                updateEditTransactionScreenUiStateWithOriginalTransactionData(
                    originalTransaction = originalTransactionData.transaction,
                    transactionTypesForNewTransaction = validTransactionTypesForNewTransaction.value,
                    transactionForValues = transactionForValues.value,
                )
            }
        }
    }

    private suspend fun calculateMaxRefundAmount() {
        var transactionDataToRefund: TransactionData? = null
        editingTransactionData?.transaction?.originalTransactionId?.let {
            transactionDataToRefund = getTransactionDataByIdUseCase(
                id = it,
            )
        }
        if (transactionDataToRefund.isNull()) {
            return
        }

        var refundedAmountCalculated: Amount? = null
        transactionDataToRefund?.transaction?.refundTransactionIds?.forEach {
            if (it != screenArgs.transactionId) {
                getTransactionDataByIdUseCase(
                    id = it,
                )?.transaction?.amount?.let { prevRefundedTransactionAmount ->
                    refundedAmountCalculated = refundedAmountCalculated?.run {
                        this.plus(prevRefundedTransactionAmount)
                    } ?: prevRefundedTransactionAmount
                }
            }
        }
        transactionDataToRefund?.transaction?.amount?.let { originalTransactionAmount ->
            maxRefundAmount = if (refundedAmountCalculated.isNotNull()) {
                originalTransactionAmount.minus(
                    (refundedAmountCalculated ?: Amount())
                )
            } else {
                originalTransactionAmount
            }
        }
    }

    private fun updateEditTransactionScreenUiStateWithOriginalTransactionData(
        originalTransaction: Transaction,
        transactionTypesForNewTransaction: ImmutableList<TransactionType>,
        transactionForValues: ImmutableList<TransactionFor>,
    ) {
        val initialEditTransactionScreenUiStateData =
            EditTransactionScreenUiStateData(
                selectedTransactionTypeIndex = transactionTypesForNewTransaction.indexOf(
                    element = originalTransaction.transactionType,
                ),
                amount = uiState.value.amount
                    .copy(
                        text = abs(originalTransaction.amount.value).toString(),
                        selection = TextRange(abs(originalTransaction.amount.value).toString().length),
                    ),
                title = uiState.value.title
                    .copy(
                        text = originalTransaction.title,
                    ),
                description = uiState.value.description
                    .copy(
                        text = originalTransaction.description,
                    ),
                category = editingTransactionData?.category,
                selectedTransactionForIndex = transactionForValues.indexOf(
                    element = transactionForValues.firstOrNull {
                        it.id == originalTransaction.transactionForId
                    },
                ),
                accountFrom = editingTransactionData?.accountFrom,
                accountTo = editingTransactionData?.accountTo,
                transactionDate = dateTimeKit.getLocalDate(
                    timestamp = originalTransaction.transactionTimestamp,
                ),
                transactionTime = dateTimeKit.getLocalTime(
                    timestamp = originalTransaction.transactionTimestamp,
                ),
            )
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiStateData = initialEditTransactionScreenUiStateData,
        )

        updateInitialSelectedTransactionType()
    }

    private fun updateDefaultCategory() {
        defaultExpenseCategory = getCategory(
            categoryId = defaultDataIdFromDataStore?.expenseCategory,
        ) ?: categories.firstOrNull { category ->
            isDefaultExpenseCategory(
                category = category.title,
            )
        }
        defaultIncomeCategory = getCategory(
            categoryId = defaultDataIdFromDataStore?.incomeCategory,
        ) ?: categories.firstOrNull { category ->
            isDefaultIncomeCategory(
                category = category.title,
            )
        }
        defaultInvestmentCategory = getCategory(
            categoryId = defaultDataIdFromDataStore?.investmentCategory,
        ) ?: categories.firstOrNull { category ->
            isDefaultInvestmentCategory(
                category = category.title,
            )
        }
    }

    private fun updateDefaultAccount() {
        defaultAccount = getAccount(
            accountId = defaultDataIdFromDataStore?.account,
        ) ?: accounts.value.firstOrNull { account ->
            isDefaultAccount(
                account = account.name,
            )
        }
    }

    private fun updateInitialSelectedTransactionType() {
        selectedTransactionType.value =
            if (editingTransactionData?.transaction?.transactionType == TransactionType.REFUND) {
                TransactionType.REFUND
            } else {
                uiState.value.selectedTransactionTypeIndex?.let {
                    validTransactionTypesForNewTransaction.value.getOrNull(
                        index = uiState.value.selectedTransactionTypeIndex.orZero(),
                    )
                }
            }
    }

    private fun getCategory(
        categoryId: Int?,
    ): Category? {
        return categories.find { category ->
            category.id == categoryId
        }
    }

    private fun getAccount(
        accountId: Int?,
    ): Account? {
        return accounts.value.find { account ->
            account.id == accountId
        }
    }

    private fun isAddingRefundTransactionOrEditingAnyTransaction(): Boolean {
        return screenArgs.transactionId.isNotNull()
    }
}
