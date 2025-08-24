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
import com.makeappssimple.abhimanyu.common.core.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.common.core.extensions.filter
import com.makeappssimple.abhimanyu.common.core.extensions.filterDigits
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
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
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.UpdateAccountBalanceAmountUseCase
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
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.orEmpty
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
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
import kotlinx.collections.immutable.toImmutableList
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
    screenUIStateDelegate: ScreenUIStateDelegate,
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

    // region initial data
    private var originalTransactionData: TransactionData? = null
    private var maxRefundAmount: Amount? = null

    private var defaultAccount: Account? = null
    private var defaultExpenseCategory: Category? = null
    private var defaultIncomeCategory: Category? = null
    private var defaultInvestmentCategory: Category? = null

    private var accounts: ImmutableList<Account> = persistentListOf()
    private var categories: ImmutableList<Category> = persistentListOf()
    private var transactionForValues: ImmutableList<TransactionFor> =
        persistentListOf()
    private var validTransactionTypesForNewTransaction: ImmutableList<TransactionType> =
        persistentListOf()

    private var uiVisibilityState: EditTransactionScreenUiVisibilityState =
        EditTransactionScreenUiVisibilityState.Expense
    private var filteredCategories: ImmutableList<Category> = persistentListOf()
    private var selectedTransactionType: TransactionType? = null
    private var titleSuggestions: ImmutableList<String> = persistentListOf()
    // endregion

    // region UI state
    private var screenBottomSheetType: EditTransactionScreenBottomSheetType =
        EditTransactionScreenBottomSheetType.None
    private var screenSnackbarType: EditTransactionScreenSnackbarType =
        EditTransactionScreenSnackbarType.None
    private var selectedTransactionTypeIndex: Int = 0
    private var amount: TextFieldValue = TextFieldValue()
    private var category: Category? = null
    private var title: TextFieldValue = TextFieldValue()
    private var selectedTransactionForIndex: Int = 0
    private var accountFrom: Account? = null
    private var accountTo: Account? = null
    private var transactionDate: LocalDate = dateTimeKit.getCurrentLocalDate()
    private var transactionTime: LocalTime = dateTimeKit.getCurrentLocalTime()
    private var isTransactionDatePickerDialogVisible: Boolean = false
    private var isTransactionTimePickerDialogVisible: Boolean = false
    // endregion

    // region uiStateAndStateEvents
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

    // region updateUiStateAndStateEvents
    override fun updateUiStateAndStateEvents() {
        val validationState =
            editTransactionScreenDataValidationUseCase(
                accountFrom = accountFrom,
                accountTo = accountTo,
                maxRefundAmount = maxRefundAmount,
                amount = amount.text,
                title = title.text,
                selectedTransactionType = selectedTransactionType,
            )
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
                isCtaButtonEnabled = validationState.isCtaButtonEnabled,
                isLoading = isLoading,
                isTransactionDatePickerDialogVisible = isTransactionDatePickerDialogVisible,
                isTransactionTimePickerDialogVisible = isTransactionTimePickerDialogVisible,
                category = category,
                selectedTransactionForIndex = selectedTransactionForIndex,
                selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                accounts = accounts.orEmpty(),
                filteredCategories = filteredCategories,
                titleSuggestionsChipUIData = titleSuggestions
                    .map { title ->
                        ChipUIData(
                            text = title,
                        )
                    },
                transactionForValuesChipUIData = transactionForValues
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
                amountErrorText = validationState.amountErrorText,
                amount = amount,
                title = title,
            )
        }
    }
    // endregion

    // region fetchData
    override fun fetchData(): Job {
        return coroutineScope.launch {
            joinAll(
                launch {
                    accounts = getAllAccountsUseCase()
                },
                launch {
                    categories = getAllCategoriesUseCase()
                },
                launch {
                    transactionForValues = getAllTransactionForValuesUseCase()
                },
            )
            updateDefaultData()
            updateValidTransactionTypesForNewTransaction()
            updateDataForRefundTransaction()
            processInitialData()
        }
    }
    // endregion

    // region state events
    private fun clearAmount(): Job {
        return updateAmount(
            updatedAmount = uiState.value.amount.copy(
                text = "",
            ),
        )
    }

    private fun clearTitle(): Job {
        return updateTitle(
            updatedTitle = uiState.value.title.copy(
                text = "",
            ),
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
    ): Job {
        accountFrom = updatedAccountFrom
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateAccountTo(
        updatedAccountTo: Account?,
    ): Job {
        accountTo = updatedAccountTo
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateAmount(
        updatedAmount: TextFieldValue,
    ): Job {
        amount = updatedAmount.copy(
            text = updatedAmount.text.filterDigits(),
        )
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateAmount(
        updatedAmount: String,
    ): Job {
        amount = uiState.value.amount.copy(
            text = updatedAmount.filterDigits(),
        )
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateCategory(
        updatedCategory: Category?,
    ): Job {
        category = updatedCategory
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateIsTransactionDatePickerDialogVisible(
        updatedIsTransactionDatePickerDialogVisible: Boolean,
    ): Job {
        isTransactionDatePickerDialogVisible =
            updatedIsTransactionDatePickerDialogVisible
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateIsTransactionTimePickerDialogVisible(
        updatedIsTransactionTimePickerDialogVisible: Boolean,
    ): Job {
        isTransactionTimePickerDialogVisible =
            updatedIsTransactionTimePickerDialogVisible
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateScreenBottomSheetType(
        updatedEditTransactionScreenBottomSheetType: EditTransactionScreenBottomSheetType,
    ): Job {
        screenBottomSheetType = updatedEditTransactionScreenBottomSheetType
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateScreenSnackbarType(
        updatedEditTransactionScreenSnackbarType: EditTransactionScreenSnackbarType,
    ): Job {
        screenSnackbarType = updatedEditTransactionScreenSnackbarType
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ): Job {
        selectedTransactionForIndex = updatedSelectedTransactionForIndex
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ): Job {
        selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex
        handleUpdatedSelectedTransactionTypeIndex()
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun handleUpdatedSelectedTransactionTypeIndex() {
        startLoading()
        val updatedSelectedTransactionType =
            validTransactionTypesForNewTransaction.getOrNull(
                index = selectedTransactionTypeIndex,
            )
        if (updatedSelectedTransactionType != null) {
            handleSelectedTransactionTypeChange(
                updatedSelectedTransactionType = updatedSelectedTransactionType,
            )
        }
    }

    private fun updateTitle(
        updatedTitle: TextFieldValue,
    ): Job {
        title = updatedTitle
        updateTitleSuggestions()
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateTransactionDate(
        updatedTransactionDate: LocalDate,
    ): Job {
        transactionDate = updatedTransactionDate
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateTransactionTime(
        updatedTransactionTime: LocalTime,
    ): Job {
        transactionTime = updatedTransactionTime
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateTransaction(): Job {
        val enteredAmountValue = amount.text.toLongOrZero()
        val amount = Amount(
            value = enteredAmountValue,
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
        when (uiState.value.selectedTransactionType) {
            TransactionType.INCOME -> {
                1
            }

            TransactionType.EXPENSE -> {
                uiState.value.selectedTransactionForIndex
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
        val transactionTimestamp = LocalDateTime.of(
            uiState.value.transactionDate,
            uiState.value.transactionTime
        ).toEpochMilli()

        if (accountFromId.isNotNull()) {
            uiState.value.accountFrom
        } else {
            null
        }
        if (accountToId.isNotNull()) {
            uiState.value.accountTo
        } else {
            null
        }
//        val transaction = Transaction(
//            amount = amount,
//            categoryId = categoryId,
//            originalTransactionId = currentTransactionData?.transaction?.id,
//            accountFromId = accountFromId,
//            accountToId = accountToId,
//            title = title,
//            creationTimestamp = dateTimeUtil.getCurrentTimeMillis(),
//            transactionTimestamp = transactionTimestamp,
//            transactionForId = transactionForId,
//            transactionType = uiState.selectedTransactionType,
//        )
        return coroutineScope.launch {
//            currentTransactionData?.transaction?.let { originalTransaction ->
//                updateTransactionUseCase(
//                    originalTransaction = originalTransaction,
//                    updatedTransaction = transaction,
//                )
//            }
            navigateUp()
        }
    }
    // endregion

    // region updateDefaultData
    private suspend fun updateDefaultData() {
        val defaultDataIdFromDataStore =
            financeManagerPreferencesRepository.getDefaultDataId()
        defaultDataIdFromDataStore?.let {
            updateDefaultCategories(
                defaultDataIdFromDataStore = defaultDataIdFromDataStore,
            )
            updateDefaultAccount(
                defaultDataIdFromDataStore = defaultDataIdFromDataStore,
            )
        }
    }

    private fun updateDefaultCategories(
        defaultDataIdFromDataStore: DefaultDataId,
    ) {
        defaultExpenseCategory = getCategory(
            categoryId = defaultDataIdFromDataStore.expenseCategory,
        ) ?: categories.firstOrNull { category ->
            isDefaultExpenseCategory(
                category = category.title,
            )
        }
        defaultIncomeCategory = getCategory(
            categoryId = defaultDataIdFromDataStore.incomeCategory,
        ) ?: categories.firstOrNull { category ->
            isDefaultIncomeCategory(
                category = category.title,
            )
        }
        defaultInvestmentCategory = getCategory(
            categoryId = defaultDataIdFromDataStore.investmentCategory,
        ) ?: categories.firstOrNull { category ->
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
        ) ?: accounts.firstOrNull { account ->
            isDefaultAccount(
                account = account.name,
            )
        }
    }

    private fun getCategory(
        categoryId: Int,
    ): Category? {
        return categories.find { category ->
            category.id == categoryId
        }
    }

    private fun getAccount(
        accountId: Int,
    ): Account? {
        return accounts.find { account ->
            account.id == accountId
        }
    }
    // endregion

    // region updateValidTransactionTypesForNewTransaction
    private fun updateValidTransactionTypesForNewTransaction() {
        val originalTransactionId = getOriginalTransactionId()
        val validTransactionTypes = when {
            originalTransactionId != null -> {
                listOf(
                    TransactionType.REFUND,
                )
            }

            accounts.size > 1 -> {
                listOf(
                    TransactionType.INCOME,
                    TransactionType.EXPENSE,
                    TransactionType.TRANSFER,
                    TransactionType.INVESTMENT,
                )
            }

            else -> {
                listOf(
                    TransactionType.INCOME,
                    TransactionType.EXPENSE,
                    TransactionType.INVESTMENT,
                )
            }
        }
        validTransactionTypesForNewTransaction =
            validTransactionTypes.toImmutableList()
    }
    // endregion

    // region updateDataForRefundTransaction
    private suspend fun updateDataForRefundTransaction() {
        getOriginalTransactionId()
        updateOriginalTransactionData()
        updateMaxRefundAmount()
    }

    private suspend fun updateOriginalTransactionData() {
        val originalTransactionId = getOriginalTransactionId() ?: return
        originalTransactionData = getTransactionDataByIdUseCase(
            id = originalTransactionId,
        )
    }

    private suspend fun updateMaxRefundAmount() {
        val originalTransactionId = getOriginalTransactionId() ?: return
        maxRefundAmount = getMaxRefundAmountUseCase(
            id = originalTransactionId,
        )
    }
    // endregion

    // region processInitialData
    private fun processInitialData() {
        val originalTransactionData = originalTransactionData
        if (originalTransactionData != null) {
            processInitialDataForRefundTransaction(
                originalTransactionData = originalTransactionData,
            )
        } else {
            processInitialDataForOtherTransactions()
        }
    }

    private fun processInitialDataForRefundTransaction(
        originalTransactionData: TransactionData,
    ) {
        updateSelectedTransactionTypeIndex(
            validTransactionTypesForNewTransaction.indexOf(
                element = TransactionType.REFUND,
            )
        )
        updateAmount(maxRefundAmount.orEmpty().value.toString())
        updateCategory(originalTransactionData.category)
        updateAccountFrom(null)
        updateAccountTo(originalTransactionData.accountFrom)
        updateSelectedTransactionForIndex(
            transactionForValues.indexOf(
                element = transactionForValues.firstOrNull {
                    it.id == originalTransactionData.transaction.id
                },
            )
        )
    }

    private fun processInitialDataForOtherTransactions() {
        updateCategory(defaultExpenseCategory)
        updateAccountFrom(defaultAccount)
        updateAccountTo(defaultAccount)
        updateSelectedTransactionTypeIndex(
            validTransactionTypesForNewTransaction.indexOf(
                element = TransactionType.EXPENSE,
            )
        )
    }
    // endregion

    // region updateTitleSuggestions
    private fun updateTitleSuggestions() {
        coroutineScope.launch {
            titleSuggestions = category?.id?.let { categoryId ->
                getTitleSuggestionsUseCase(
                    categoryId = categoryId,
                    enteredTitle = title.text,
                )
            } ?: persistentListOf()
        }
    }
    // endregion

    // region observeForSelectedTransactionType
    private fun handleSelectedTransactionTypeChange(
        updatedSelectedTransactionType: TransactionType,
    ) {
        updateFilteredCategories(
            updatedSelectedTransactionType = updatedSelectedTransactionType,
        )
        when (updatedSelectedTransactionType) {
            TransactionType.INCOME -> {
                handleSelectedTransactionTypeChangeToIncome()
            }

            TransactionType.EXPENSE -> {
                handleSelectedTransactionTypeChangeToExpense()
            }

            TransactionType.TRANSFER -> {
                handleSelectedTransactionTypeChangeToTransfer()
            }

            TransactionType.ADJUSTMENT -> {}

            TransactionType.INVESTMENT -> {
                handleSelectedTransactionTypeChangeToInvestment()
            }

            TransactionType.REFUND -> {
                handleSelectedTransactionTypeChangeToRefund()
            }
        }

        selectedTransactionType = updatedSelectedTransactionType
    }

    private fun updateFilteredCategories(
        updatedSelectedTransactionType: TransactionType,
    ) {
        filteredCategories = categories.filter { category ->
            category.transactionType == updatedSelectedTransactionType
        }
    }

    private fun handleSelectedTransactionTypeChangeToIncome() {
        updateUiVisibilityState(EditTransactionScreenUiVisibilityState.Income)

        updateCategory(
            originalTransactionData?.category ?: defaultIncomeCategory
        )
        clearTitle()
        updateAccountFrom(null)
        updateAccountTo(originalTransactionData?.accountTo ?: defaultAccount)
    }

    private fun handleSelectedTransactionTypeChangeToExpense() {
        updateUiVisibilityState(EditTransactionScreenUiVisibilityState.Expense)

        updateCategory(
            originalTransactionData?.category ?: defaultExpenseCategory
        )
        clearTitle()
        updateAccountFrom(
            originalTransactionData?.accountFrom ?: defaultAccount
        )
        updateAccountTo(null)
    }

    private fun handleSelectedTransactionTypeChangeToTransfer() {
        updateUiVisibilityState(EditTransactionScreenUiVisibilityState.Transfer)

        clearTitle()
        updateAccountFrom(
            originalTransactionData?.accountFrom ?: defaultAccount
        )
        updateAccountTo(originalTransactionData?.accountTo ?: defaultAccount)
    }

    private fun handleSelectedTransactionTypeChangeToInvestment() {
        updateUiVisibilityState(EditTransactionScreenUiVisibilityState.Investment)

        updateCategory(
            originalTransactionData?.category ?: defaultInvestmentCategory
        )
        clearTitle()
        updateAccountFrom(
            originalTransactionData?.accountFrom ?: defaultAccount
        )
        updateAccountTo(null)
    }

    private fun handleSelectedTransactionTypeChangeToRefund() {
        updateUiVisibilityState(EditTransactionScreenUiVisibilityState.Refund)

        updateAmount(maxRefundAmount.orEmpty().value.toString())
        updateAccountTo(originalTransactionData?.accountFrom)
        updateTransactionDate(
            updatedTransactionDate = dateTimeKit.getLocalDate(
                timestamp = originalTransactionData?.transaction?.transactionTimestamp.orZero(),
            ),
        )
        updateTransactionTime(
            updatedTransactionTime = dateTimeKit.getLocalTime(
                timestamp = originalTransactionData?.transaction?.transactionTimestamp.orZero(),
            ),
        )
    }

    private fun updateUiVisibilityState(
        updatedUiVisibilityState: EditTransactionScreenUiVisibilityState,
    ) {
        uiVisibilityState = updatedUiVisibilityState
    }
    // endregion

    // region screen args
    private fun getOriginalTransactionId(): Int? {
        return screenArgs.currentTransactionId
    }
    // endregion
}
