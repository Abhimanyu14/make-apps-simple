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

@file:Suppress("UnusedPrivateMember")

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.view_model

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.coroutines.getCompletedJob
import com.makeappssimple.abhimanyu.common.core.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.common.core.extensions.filter
import com.makeappssimple.abhimanyu.common.core.extensions.filterDigits
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.extensions.orEmpty
import com.makeappssimple.abhimanyu.common.core.extensions.orMin
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.common.core.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.common.core.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.uri_decoder.UriDecoder
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetMaxRefundAmountUseCase
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
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.orEmpty
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.bottom_sheet.EditTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.snackbar.EditTransactionScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.state.AccountFromText
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.state.AccountToText
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.state.EditTransactionScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.state.EditTransactionScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.use_case.EditTransactionScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.navigation.EditTransactionScreenArgs
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@KoinViewModel
internal class EditTransactionScreenViewModel(
    navigationKit: NavigationKit,
    savedStateHandle: SavedStateHandle,
    uriDecoder: UriDecoder,
    private val coroutineScope: CoroutineScope,
    private val dateTimeKit: DateTimeKit,
    private val editTransactionScreenDataValidationUseCase: EditTransactionScreenDataValidationUseCase,
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getTitleSuggestionsUseCase: GetTitleSuggestionsUseCase,
    private val getTransactionDataByIdUseCase: GetTransactionDataByIdUseCase,
    private val getMaxRefundAmountUseCase: GetMaxRefundAmountUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit,
    NavigationKit by navigationKit {
    // region screen args
    private val screenArgs = EditTransactionScreenArgs(
        savedStateHandle = savedStateHandle,
        uriDecoder = uriDecoder,
    )
    // endregion

    // region data
    private var isLoading: Boolean = false
    private var accountFrom: Account? = null
    private var accountTo: Account? = null
    private var defaultAccount: Account? = null
    private var maxRefundAmount: Amount? = null
    private var isTransactionDatePickerDialogVisible: Boolean = false
    private var isTransactionTimePickerDialogVisible: Boolean = false
    private var category: Category? = null
    private var defaultExpenseCategory: Category? = null
    private var defaultIncomeCategory: Category? = null
    private var defaultInvestmentCategory: Category? = null
    private var screenBottomSheetType: EditTransactionScreenBottomSheetType =
        EditTransactionScreenBottomSheetType.None
    private var screenSnackbarType: EditTransactionScreenSnackbarType =
        EditTransactionScreenSnackbarType.None
    private var uiVisibilityState: EditTransactionScreenUiVisibilityState =
        EditTransactionScreenUiVisibilityState.Expense
    private var allAccounts: ImmutableList<Account> = persistentListOf()
    private var allCategories: ImmutableList<Category> = persistentListOf()
    private var filteredCategories: ImmutableList<Category> = persistentListOf()
    private var titleSuggestions: ImmutableList<String> = persistentListOf()
    private var allTransactionForValues: ImmutableList<TransactionFor> =
        persistentListOf()
    private var validTransactionTypesForNewTransaction: ImmutableList<TransactionType> =
        persistentListOf()
    private var selectedTransactionForIndex: Int = 0
    private var selectedTransactionTypeIndex: Int = 0
    private var amount: TextFieldValue = TextFieldValue()
    private var title: TextFieldValue = TextFieldValue()
    private var currentTransactionData: TransactionData? = null
    private var originalTransactionData: TransactionData? = null
    private var selectedTransactionType: TransactionType? = null
    private var transactionDate: LocalDate = dateTimeKit.getCurrentLocalDate()
    private var transactionTime: LocalTime = dateTimeKit.getCurrentLocalTime()
    // endregion

    // region uiState
    private val _uiState: MutableStateFlow<EditTransactionScreenUIState> =
        MutableStateFlow(
            value = EditTransactionScreenUIState(),
        )
    internal val uiState: StateFlow<EditTransactionScreenUIState> =
        _uiState.asStateFlow()
    internal val uiStateEvents: EditTransactionScreenUIStateEvents =
        EditTransactionScreenUIStateEvents(
            clearAmount = ::clearAmount,
            clearTitle = ::clearTitle,
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            resetScreenSnackbarType = ::resetScreenSnackbarType,
            updateAccountFrom = ::updateAccountFrom,
            updateAccountTo = ::updateAccountTo,
            updateAmount = ::updateAmount,
            updateCategory = ::updateCategory,
            updateIsTransactionDatePickerDialogVisible = ::updateIsTransactionDatePickerDialogVisible,
            updateIsTransactionTimePickerDialogVisible = ::updateIsTransactionTimePickerDialogVisible,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateSelectedTransactionForIndex = ::updateSelectedTransactionForIndex,
            updateSelectedTransactionTypeIndex = ::updateSelectedTransactionTypeIndex,
            updateTitle = ::updateTitle,
            updateTransaction = ::updateTransaction,
            updateTransactionDate = ::updateTransactionDate,
            updateTransactionTime = ::updateTransactionTime,
        )
    // endregion

    // region refreshUiState
    private fun refreshUiState(): Job {
        val editTransactionScreenDataValidationState =
            editTransactionScreenDataValidationUseCase(
                accountFrom = accountFrom,
                accountTo = accountTo,
                maxRefundAmount = maxRefundAmount,
                amount = amount.text,
                title = title.text,
                selectedTransactionType = selectedTransactionType,
            )
        return coroutineScope.launch {
            _uiState.update {
                EditTransactionScreenUIState(
                    accountFrom = accountFrom,
                    accountFromText = if (selectedTransactionType == TransactionType.TRANSFER) {
                        AccountFromText.AccountFrom
                    } else {
                        AccountFromText.Account
                    },
                    accountTo = accountTo,
                    accountToText = if (selectedTransactionType == TransactionType.TRANSFER) {
                        AccountToText.AccountTo
                    } else {
                        AccountToText.Account
                    },
                    screenBottomSheetType = screenBottomSheetType,
                    screenSnackbarType = screenSnackbarType,
                    uiVisibilityState = uiVisibilityState,
                    isBottomSheetVisible = screenBottomSheetType != EditTransactionScreenBottomSheetType.None,
                    isCtaButtonEnabled = editTransactionScreenDataValidationState.isCtaButtonEnabled,
                    isLoading = isLoading,
                    isTransactionDatePickerDialogVisible = isTransactionDatePickerDialogVisible,
                    isTransactionTimePickerDialogVisible = isTransactionTimePickerDialogVisible,
                    category = category,
                    selectedTransactionForIndex = selectedTransactionForIndex,
                    selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                    accounts = allAccounts.orEmpty(),
                    filteredCategories = filteredCategories,
                    titleSuggestionsChipUIData = titleSuggestions
                        .map { title ->
                            ChipUIData(
                                text = title,
                            )
                        },
                    transactionForValuesChipUIData = allTransactionForValues
                        .map { transactionFor ->
                            ChipUIData(
                                text = transactionFor.titleToDisplay,
                            )
                        },
                    transactionTypesForNewTransactionChipUIData = validTransactionTypesForNewTransaction
                        .map { transactionType ->
                            ChipUIData(
                                text = transactionType.title,
                            )
                        },
                    titleSuggestions = titleSuggestions,
                    currentLocalDate = dateTimeKit.getCurrentLocalDate()
                        .orMin(),
                    transactionDate = transactionDate,
                    transactionTime = transactionTime,
                    amountErrorText = editTransactionScreenDataValidationState.amountErrorText,
                    amount = amount,
                    title = title,
                )
            }
        }
    }
    // endregion

    // region fetchData
    private fun fetchData(): Job {
        return coroutineScope.launch {
            joinAll(
                launch {
                    allAccounts = getAllAccountsUseCase()
                },
                launch {
                    allCategories = getAllCategoriesUseCase()
                },
                launch {
                    allTransactionForValues =
                        getAllTransactionForValuesUseCase()
                },
            )
            updateDefaultData()
            updateCurrentTransactionData()
        }
    }
    // endregion

    // region state events
    private fun clearAmount(
        shouldRefresh: Boolean = true,
    ): Job {
        return updateAmount(
            updatedAmount = uiState.value.amount.copy(
                text = "",
            ),
            shouldRefresh = shouldRefresh,
        )
    }

    private fun clearTitle(
        shouldRefresh: Boolean = true,
    ): Job {
        return updateTitle(
            updatedTitle = uiState.value.title.copy(
                text = "",
            ),
            shouldRefresh = shouldRefresh,
        )
    }

    private fun resetScreenBottomSheetType(): Job {
        return updateScreenBottomSheetType(
            updatedEditTransactionScreenBottomSheetType = EditTransactionScreenBottomSheetType.None,
        )
    }

    private fun resetScreenSnackbarType(): Job {
        return updateScreenSnackbarType(
            updatedEditTransactionScreenSnackbarType = EditTransactionScreenSnackbarType.None,
        )
    }

    private fun updateAccountFrom(
        updatedAccountFrom: Account?,
        shouldRefresh: Boolean = true,
    ): Job {
        accountFrom = updatedAccountFrom
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateAccountTo(
        updatedAccountTo: Account?,
        shouldRefresh: Boolean = true,
    ): Job {
        accountTo = updatedAccountTo
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateAmount(
        updatedAmount: TextFieldValue,
        shouldRefresh: Boolean = true,
    ): Job {
        amount = updatedAmount.copy(
            text = updatedAmount.text.filterDigits(),
        )
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateAmount(
        updatedAmount: String,
        shouldRefresh: Boolean = true,
    ): Job {
        amount = uiState.value.amount.copy(
            text = updatedAmount.filterDigits(),
        )
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateCategory(
        updatedCategory: Category?,
        shouldRefresh: Boolean = true,
    ): Job {
        category = updatedCategory
        updateTitleSuggestions()
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateIsTransactionDatePickerDialogVisible(
        updatedIsTransactionDatePickerDialogVisible: Boolean,
        shouldRefresh: Boolean = true,
    ): Job {
        isTransactionDatePickerDialogVisible =
            updatedIsTransactionDatePickerDialogVisible
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateIsTransactionTimePickerDialogVisible(
        updatedIsTransactionTimePickerDialogVisible: Boolean,
        shouldRefresh: Boolean = true,
    ): Job {
        isTransactionTimePickerDialogVisible =
            updatedIsTransactionTimePickerDialogVisible
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateScreenBottomSheetType(
        updatedEditTransactionScreenBottomSheetType: EditTransactionScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenBottomSheetType = updatedEditTransactionScreenBottomSheetType
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateScreenSnackbarType(
        updatedEditTransactionScreenSnackbarType: EditTransactionScreenSnackbarType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenSnackbarType = updatedEditTransactionScreenSnackbarType
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedTransactionForIndex = updatedSelectedTransactionForIndex
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex
        handleUpdatedSelectedTransactionTypeIndex(
            shouldRefresh = shouldRefresh,
        )
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun handleUpdatedSelectedTransactionTypeIndex(
        shouldRefresh: Boolean = true,
    ) {
        if (shouldRefresh) {
            startLoading()
        }
        val updatedSelectedTransactionType =
            validTransactionTypesForNewTransaction.getOrNull(
                index = selectedTransactionTypeIndex,
            )
        if (updatedSelectedTransactionType != null) {
            handleSelectedTransactionTypeChange(
                updatedSelectedTransactionType = updatedSelectedTransactionType,
                shouldRefresh = shouldRefresh,
            )
        }
    }

    private fun updateTitle(
        updatedTitle: TextFieldValue,
        shouldRefresh: Boolean = true,
    ): Job {
        title = updatedTitle
        updateTitleSuggestions()
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateTransactionDate(
        updatedTransactionDate: LocalDate,
        shouldRefresh: Boolean = true,
    ): Job {
        transactionDate = updatedTransactionDate
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateTransactionTime(
        updatedTransactionTime: LocalTime,
        shouldRefresh: Boolean = true,
    ): Job {
        transactionTime = updatedTransactionTime
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateTransaction(): Job {
        val amount = Amount(
            value = amount.text.toLongOrZero(),
        )
        val categoryId = when (uiState.value.selectedTransactionType) {
            TransactionType.INCOME -> {
                uiState.value.category?.id
            }

            TransactionType.EXPENSE -> {
                uiState.value.category?.id
            }

            TransactionType.TRANSFER -> {
                null
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                uiState.value.category?.id
            }

            TransactionType.REFUND -> {
                uiState.value.category?.id
            }
        }
        val originalTransactionId = currentTransactionData?.transaction?.id
        val accountFromId = when (uiState.value.selectedTransactionType) {
            TransactionType.INCOME -> {
                null
            }

            TransactionType.EXPENSE -> {
                uiState.value.accountFrom?.id
            }

            TransactionType.TRANSFER -> {
                uiState.value.accountFrom?.id
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                uiState.value.accountFrom?.id
            }

            TransactionType.REFUND -> {
                null
            }
        }
        val accountToId = when (uiState.value.selectedTransactionType) {
            TransactionType.INCOME -> {
                uiState.value.accountTo?.id
            }

            TransactionType.EXPENSE -> {
                null
            }

            TransactionType.TRANSFER -> {
                uiState.value.accountTo?.id
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                null
            }

            TransactionType.REFUND -> {
                uiState.value.accountTo?.id
            }
        }
        val title = when (uiState.value.selectedTransactionType) {
            TransactionType.TRANSFER -> {
                TransactionType.TRANSFER.title
            }

            TransactionType.REFUND -> {
                TransactionType.REFUND.title
            }

            else -> {
                uiState.value.title.text.capitalizeWords()
            }
        }
        val creationTimestamp = dateTimeKit.getCurrentTimeMillis()
        val transactionTimestamp = LocalDateTime.of(
            uiState.value.transactionDate,
            uiState.value.transactionTime,
        ).toEpochMilli()
        val transactionForId =
            allTransactionForValues[uiState.value.selectedTransactionForIndex].id
        val transactionType = uiState.value.selectedTransactionType
        val transaction = Transaction(
            amount = amount,
            categoryId = categoryId,
            originalTransactionId = originalTransactionId,
            accountFromId = accountFromId,
            accountToId = accountToId,
            title = title,
            creationTimestamp = creationTimestamp,
            transactionTimestamp = transactionTimestamp,
            transactionForId = transactionForId,
            transactionType = transactionType,
        )
        return coroutineScope.launch {
            val isTransactionUpdated = false
//                updateTransactionUseCase(
//                updatedTransaction = transaction,
//            )
            if (isTransactionUpdated) {
                navigateUp()
            } else {
                // TODO(Abhi): Show error
            }
        }
    }
    // endregion

    // region updateDefaultData
    private suspend fun updateDefaultData() {
        val defaultDataIdFromDataStore =
            financeManagerPreferencesRepository.getDefaultDataId() ?: return
        updateDefaultCategories(
            defaultDataIdFromDataStore = defaultDataIdFromDataStore,
        )
        updateDefaultAccount(
            defaultDataIdFromDataStore = defaultDataIdFromDataStore,
        )
    }

    private fun updateDefaultCategories(
        defaultDataIdFromDataStore: DefaultDataId,
    ) {
        defaultExpenseCategory = getCategory(
            categoryId = defaultDataIdFromDataStore.expenseCategory,
        ) ?: allCategories.firstOrNull { category ->
            isDefaultExpenseCategory(
                category = category.title,
            )
        }
        defaultIncomeCategory = getCategory(
            categoryId = defaultDataIdFromDataStore.incomeCategory,
        ) ?: allCategories.firstOrNull { category ->
            isDefaultIncomeCategory(
                category = category.title,
            )
        }
        defaultInvestmentCategory = getCategory(
            categoryId = defaultDataIdFromDataStore.investmentCategory,
        ) ?: allCategories.firstOrNull { category ->
            isDefaultInvestmentCategory(
                category = category.title,
            )
        }
    }

    private fun updateDefaultAccount(
        defaultDataIdFromDataStore: DefaultDataId,
    ) {
        defaultAccount = getAccount(
            accountId = defaultDataIdFromDataStore.account,
        ) ?: allAccounts.firstOrNull { account ->
            isDefaultAccount(
                account = account.name,
            )
        }
    }

    private fun getCategory(
        categoryId: Int,
    ): Category? {
        return allCategories.find { category ->
            category.id == categoryId
        }
    }

    private fun getAccount(
        accountId: Int,
    ): Account? {
        return allAccounts.find { account ->
            account.id == accountId
        }
    }
    // endregion

    // region updateCurrentTransactionData
    private suspend fun updateCurrentTransactionData() {
        val currentTransactionId = getCurrentTransactionId()
        val fetchedCurrentTransactionData: TransactionData? =
            getTransactionDataByIdUseCase(
                id = currentTransactionId,
            )
        checkNotNull(
            value = fetchedCurrentTransactionData,
            lazyMessage = {
                "Transaction data not found for id: $currentTransactionId"
            },
        )
        currentTransactionData = fetchedCurrentTransactionData
        val originalTransactionId =
            currentTransactionData?.transaction?.originalTransactionId
        if (originalTransactionId != null) {
            val fetchedOriginalTransactionData = getTransactionDataByIdUseCase(
                id = currentTransactionId,
            )
            checkNotNull(
                value = fetchedOriginalTransactionData,
                lazyMessage = {
                    "Original transaction data not found for id: $originalTransactionId"
                },
            )
            originalTransactionData = fetchedOriginalTransactionData
            maxRefundAmount = getMaxRefundAmountUseCase(
                id = originalTransactionId,
            )
            validTransactionTypesForNewTransaction = persistentListOf(
                TransactionType.REFUND,
            )
            processInitialDataForRefundTransaction(
                currentTransactionData = fetchedCurrentTransactionData,
                originalTransactionData = fetchedOriginalTransactionData,
            )
        } else {
            updateValidTransactionTypesForNonRefundTransaction()
            processInitialDataForNonRefundTransaction(
                currentTransactionData = fetchedCurrentTransactionData,
            )
        }
    }

    private fun processInitialDataForRefundTransaction(
        currentTransactionData: TransactionData,
        originalTransactionData: TransactionData,
    ) {
        updateSelectedTransactionTypeIndex(
            updatedSelectedTransactionTypeIndex = validTransactionTypesForNewTransaction.indexOf(
                element = TransactionType.REFUND,
            ),
            shouldRefresh = false,
        )
        updateAccountFrom(
            updatedAccountFrom = null,
            shouldRefresh = false,
        )
        updateAccountTo(
            updatedAccountTo = originalTransactionData.accountFrom,
            shouldRefresh = false,
        )
        updateAmount(
            updatedAmount = maxRefundAmount.orEmpty().value.toString(),
            shouldRefresh = false,
        )
        updateCategory(
            updatedCategory = originalTransactionData.category,
            shouldRefresh = false,
        )
        updateSelectedTransactionForIndex(
            updatedSelectedTransactionForIndex = allTransactionForValues.indexOf(
                element = allTransactionForValues.firstOrNull {
                    it.id == originalTransactionData.transaction.id
                },
            ),
            shouldRefresh = false,
        )
    }

    private fun updateValidTransactionTypesForNonRefundTransaction() {
        validTransactionTypesForNewTransaction = when {
            allAccounts.size > 1 -> {
                persistentListOf(
                    TransactionType.INCOME,
                    TransactionType.EXPENSE,
                    TransactionType.TRANSFER,
                    TransactionType.INVESTMENT,
                )
            }

            else -> {
                persistentListOf(
                    TransactionType.INCOME,
                    TransactionType.EXPENSE,
                    TransactionType.INVESTMENT,
                )
            }
        }
    }

    private fun processInitialDataForNonRefundTransaction(
        currentTransactionData: TransactionData,
    ) {
        updateSelectedTransactionTypeIndex(
            updatedSelectedTransactionTypeIndex = validTransactionTypesForNewTransaction.indexOf(
                element = currentTransactionData.transaction.transactionType,
            ),
            shouldRefresh = false,
        )
        updateAmount(
            updatedAmount = currentTransactionData.transaction.amount.value.toString(),
            shouldRefresh = false,
        )
        updateCategory(
            updatedCategory = allCategories.firstOrNull { category ->
                category.id == currentTransactionData.transaction.categoryId
            },
            shouldRefresh = false,
        )
        updateTitle(
            updatedTitle = uiState.value.title.copy(
                text = currentTransactionData.transaction.title,
            ),
            shouldRefresh = false,
        )
        updateSelectedTransactionForIndex(
            updatedSelectedTransactionForIndex = allTransactionForValues.indexOf(
                element = allTransactionForValues.firstOrNull { transactionFor ->
                    transactionFor.id == currentTransactionData.transaction.transactionForId
                },
            ),
            shouldRefresh = false,
        )
        updateAccountFrom(
            updatedAccountFrom = allAccounts.firstOrNull { account ->
                account.id == currentTransactionData.transaction.accountFromId
            },
            shouldRefresh = false,
        )
        updateAccountTo(
            updatedAccountTo = allAccounts.firstOrNull { account ->
                account.id == currentTransactionData.transaction.accountToId
            },
            shouldRefresh = false,
        )
        updateTransactionDate(
            updatedTransactionDate = dateTimeKit.getLocalDate(
                timestamp = currentTransactionData.transaction.transactionTimestamp,
            ),
            shouldRefresh = false,
        )
        updateTransactionTime(
            updatedTransactionTime = dateTimeKit.getLocalTime(
                timestamp = currentTransactionData.transaction.transactionTimestamp,
            ),
            shouldRefresh = false,
        )
    }
    // endregion

    // region updateTitleSuggestions
    private fun updateTitleSuggestions() {
        val categoryId = category?.id ?: run {
            titleSuggestions = persistentListOf()
            return
        }
        coroutineScope.launch {
            titleSuggestions = getTitleSuggestionsUseCase(
                categoryId = categoryId,
                enteredTitle = title.text,
            )
        }
    }
    // endregion

    // region handleSelectedTransactionTypeChange
    private fun handleSelectedTransactionTypeChange(
        updatedSelectedTransactionType: TransactionType,
        shouldRefresh: Boolean = true,
    ) {
        updateFilteredCategories(
            updatedSelectedTransactionType = updatedSelectedTransactionType,
        )
        when (updatedSelectedTransactionType) {
            TransactionType.INCOME -> {
                handleSelectedTransactionTypeChangeToIncome(
                    shouldRefresh = shouldRefresh,
                )
            }

            TransactionType.EXPENSE -> {
                handleSelectedTransactionTypeChangeToExpense(
                    shouldRefresh = shouldRefresh,
                )
            }

            TransactionType.TRANSFER -> {
                handleSelectedTransactionTypeChangeToTransfer(
                    shouldRefresh = shouldRefresh,
                )
            }

            TransactionType.ADJUSTMENT -> {}

            TransactionType.INVESTMENT -> {
                handleSelectedTransactionTypeChangeToInvestment(
                    shouldRefresh = shouldRefresh,
                )
            }

            TransactionType.REFUND -> {
                handleSelectedTransactionTypeChangeToRefund(
                    shouldRefresh = shouldRefresh,
                )
            }
        }

        selectedTransactionType = updatedSelectedTransactionType
    }

    private fun updateFilteredCategories(
        updatedSelectedTransactionType: TransactionType,
    ) {
        filteredCategories = allCategories.filter { category ->
            category.transactionType == updatedSelectedTransactionType
        }
    }

    private fun handleSelectedTransactionTypeChangeToIncome(
        shouldRefresh: Boolean = true,
    ) {
        updateUiVisibilityState(
            updatedUiVisibilityState = EditTransactionScreenUiVisibilityState.Income,
        )

        updateCategory(
            updatedCategory = currentTransactionData?.category
                ?: defaultIncomeCategory,
            shouldRefresh = shouldRefresh,
        )
        clearTitle(
            shouldRefresh = shouldRefresh,
        )
        updateAccountFrom(
            updatedAccountFrom = null,
            shouldRefresh = shouldRefresh,
        )
        updateAccountTo(
            updatedAccountTo = currentTransactionData?.accountTo
                ?: defaultAccount,
            shouldRefresh = shouldRefresh,
        )
    }

    private fun handleSelectedTransactionTypeChangeToExpense(
        shouldRefresh: Boolean = true,
    ) {
        updateUiVisibilityState(
            updatedUiVisibilityState = EditTransactionScreenUiVisibilityState.Expense,
        )

        updateCategory(
            updatedCategory = currentTransactionData?.category
                ?: defaultExpenseCategory,
            shouldRefresh = shouldRefresh,
        )
        clearTitle(
            shouldRefresh = shouldRefresh,
        )
        updateAccountFrom(
            updatedAccountFrom = currentTransactionData?.accountFrom
                ?: defaultAccount,
            shouldRefresh = shouldRefresh,
        )
        updateAccountTo(
            updatedAccountTo = null,
            shouldRefresh = shouldRefresh,
        )
    }

    private fun handleSelectedTransactionTypeChangeToTransfer(
        shouldRefresh: Boolean = true,
    ) {
        updateUiVisibilityState(
            updatedUiVisibilityState = EditTransactionScreenUiVisibilityState.Transfer,
        )

        clearTitle(
            shouldRefresh = shouldRefresh,
        )
        updateAccountFrom(
            updatedAccountFrom = currentTransactionData?.accountFrom
                ?: defaultAccount,
            shouldRefresh = shouldRefresh,
        )
        updateAccountTo(
            updatedAccountTo = currentTransactionData?.accountTo
                ?: defaultAccount,
            shouldRefresh = shouldRefresh,
        )
    }

    private fun handleSelectedTransactionTypeChangeToInvestment(
        shouldRefresh: Boolean = true,
    ) {
        updateUiVisibilityState(
            updatedUiVisibilityState = EditTransactionScreenUiVisibilityState.Investment,
        )

        updateCategory(
            updatedCategory = currentTransactionData?.category
                ?: defaultInvestmentCategory,
            shouldRefresh = shouldRefresh,
        )
        clearTitle(
            shouldRefresh = shouldRefresh,
        )
        updateAccountFrom(
            updatedAccountFrom = currentTransactionData?.accountFrom
                ?: defaultAccount,
            shouldRefresh = shouldRefresh,
        )
        updateAccountTo(
            updatedAccountTo = null,
            shouldRefresh = shouldRefresh,
        )
    }

    private fun handleSelectedTransactionTypeChangeToRefund(
        shouldRefresh: Boolean = true,
    ) {
        updateUiVisibilityState(
            updatedUiVisibilityState = EditTransactionScreenUiVisibilityState.Refund,
        )

        updateAmount(
            updatedAmount = maxRefundAmount.orEmpty().value.toString(),
            shouldRefresh = shouldRefresh,
        )
        updateAccountTo(
            updatedAccountTo = currentTransactionData?.accountFrom,
            shouldRefresh = shouldRefresh,
        )
        updateTransactionDate(
            updatedTransactionDate = dateTimeKit.getLocalDate(
                timestamp = currentTransactionData?.transaction?.transactionTimestamp.orZero(),
            ),
            shouldRefresh = shouldRefresh,
        )
        updateTransactionTime(
            updatedTransactionTime = dateTimeKit.getLocalTime(
                timestamp = currentTransactionData?.transaction?.transactionTimestamp.orZero(),
            ),
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateUiVisibilityState(
        updatedUiVisibilityState: EditTransactionScreenUiVisibilityState,
    ) {
        uiVisibilityState = updatedUiVisibilityState
    }
    // endregion

    // region screen args
    private fun getCurrentTransactionId(): Int {
        return screenArgs.currentTransactionId
    }
    // endregion

    // region loading
    private fun completeLoading() {
        isLoading = false
        refreshUiState()
    }

    private fun startLoading() {
        isLoading = true
        refreshUiState()
    }
    // endregion
}
