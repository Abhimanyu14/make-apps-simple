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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.view_model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.coroutines.getCompletedJob
import com.makeappssimple.abhimanyu.common.core.extensions.filter
import com.makeappssimple.abhimanyu.common.core.extensions.filterDigits
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.extensions.orEmpty
import com.makeappssimple.abhimanyu.common.core.extensions.orMin
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.common.core.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.uri_decoder.UriDecoder
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.transaction.GetMaxRefundAmountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.transaction.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.transaction.GetTransactionDataByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.transaction.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.transaction_for.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.orEmpty
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.bottom_sheet.AddTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.snackbar.AddTransactionScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.state.AccountFromText
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.state.AccountToText
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.state.AddTransactionScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.state.AddTransactionScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.use_case.AddTransactionScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.navigation.AddTransactionScreenArgs
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.util.isDefaultInvestmentCategory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.time.LocalDate
import java.time.LocalTime

@KoinViewModel
internal class AddTransactionScreenViewModel(
    savedStateHandle: SavedStateHandle,
    uriDecoder: UriDecoder,
    private val addTransactionScreenDataValidationUseCase: AddTransactionScreenDataValidationUseCase,
    private val coroutineScope: CoroutineScope,
    private val dateTimeKit: DateTimeKit,
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getTitleSuggestionsUseCase: GetTitleSuggestionsUseCase,
    private val getTransactionDataByIdUseCase: GetTransactionDataByIdUseCase,
    private val getMaxRefundAmountUseCase: GetMaxRefundAmountUseCase,
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val navigationKit: NavigationKit,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit {
    // region screen args
    private val screenArgs = AddTransactionScreenArgs(
        savedStateHandle = savedStateHandle,
        uriDecoder = uriDecoder,
    )
    // endregion

    // region data
    private var maxRefundAmount: Amount? = null

    private var defaultAccount: Account? = null
    private var defaultExpenseCategory: Category? = null
    private var defaultIncomeCategory: Category? = null
    private var defaultInvestmentCategory: Category? = null

    private var accounts: ImmutableList<Account> = persistentListOf()
    private var categories: ImmutableList<Category> = persistentListOf()
    private var validTransactionTypesForNewTransaction: ImmutableList<TransactionType> =
        persistentListOf()

    private var uiVisibilityState: AddTransactionScreenUiVisibilityState =
        AddTransactionScreenUiVisibilityState.Expense()
    private var filteredCategories: ImmutableList<Category> = persistentListOf()
    private var originalTransactionData: TransactionData? = null
    private var transactionForValues: ImmutableList<TransactionFor> =
        persistentListOf()
    private var selectedTransactionType: TransactionType? = null
    private var titleSuggestions: ImmutableList<String> = persistentListOf()

    private var addTransactionScreenDataValidationState: AddTransactionScreenDataValidationState =
        AddTransactionScreenDataValidationState()
    private var screenBottomSheetType: AddTransactionScreenBottomSheetType =
        AddTransactionScreenBottomSheetType.None
    private var screenSnackbarType: AddTransactionScreenSnackbarType =
        AddTransactionScreenSnackbarType.None
    private var selectedTransactionTypeIndex: Int = 0
    private var amountTextFieldState: TextFieldState = TextFieldState()
    private var category: Category? = null
    private var titleTextFieldState: TextFieldState = TextFieldState()
    private var selectedTransactionForIndex: Int = 0
    private var accountFrom: Account? = null
    private var accountTo: Account? = null
    private var transactionDate: LocalDate = dateTimeKit.getCurrentLocalDate()
    private var transactionTime: LocalTime = dateTimeKit.getCurrentLocalTime()
    private var isTransactionDatePickerDialogVisible: Boolean = false
    private var isTransactionTimePickerDialogVisible: Boolean = false
    private var isLoading: Boolean = true
    // endregion

    // region uiState
    private val _uiState: MutableStateFlow<AddTransactionScreenUIState> =
        MutableStateFlow(
            value = AddTransactionScreenUIState(),
        )
    internal val uiState: StateFlow<AddTransactionScreenUIState> =
        _uiState.asStateFlow()
    // endregion

    // region uiStateEvents
    internal val uiStateEvents: AddTransactionScreenUIStateEvents =
        AddTransactionScreenUIStateEvents(
            clearAmount = ::clearAmount,
            clearTitle = ::clearTitle,
            insertTransaction = ::insertTransaction,
            navigateUp = navigationKit::navigateUp,
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
            updateTransactionDate = ::updateTransactionDate,
            updateTransactionTime = ::updateTransactionTime,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        coroutineScope.launch {
            fetchData()
            completeLoading()
        }
        observeData()
    }
    // endregion

    // region refreshUiState
    private fun refreshUiState(): Job {
        return coroutineScope.launch {
            addTransactionScreenDataValidationState =
                addTransactionScreenDataValidationUseCase(
                    accountFrom = accountFrom,
                    accountTo = accountTo,
                    maxRefundAmount = maxRefundAmount,
                    amount = amountTextFieldState.text.toString(),
                    title = titleTextFieldState.text.toString(),
                    selectedTransactionType = selectedTransactionType,
                )
            updateTitleSuggestions()
            updateUiVisibilityState()
            updateUiState()
        }
    }

    private fun updateUiState() {
        _uiState.update {
            AddTransactionScreenUIState(
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
                isBottomSheetVisible = screenBottomSheetType != AddTransactionScreenBottomSheetType.None,
                isCtaButtonEnabled = addTransactionScreenDataValidationState.isCtaButtonEnabled,
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
                            text = transactionFor.title,
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
                amountErrorText = addTransactionScreenDataValidationState.amountErrorText,
                amountTextFieldState = amountTextFieldState,
                titleTextFieldState = titleTextFieldState,
            )
        }
    }
    // endregion

    // region observeData
    private fun observeData() {
        observeTextFieldState(
            textFieldState = amountTextFieldState,
        )
        observeTextFieldState(
            textFieldState = titleTextFieldState,
        )
    }

    private fun observeTextFieldState(
        textFieldState: TextFieldState,
    ) {
        coroutineScope.launch {
            snapshotFlow {
                textFieldState.text.toString()
            }.collectLatest {
                refreshUiState()
            }
        }
    }
    // endregion

    // region fetchData
    private suspend fun fetchData() {
        joinAll(
            coroutineScope.launch {
                accounts = getAllAccountsUseCase()
            },
            coroutineScope.launch {
                categories = getAllCategoriesUseCase()
            },
            coroutineScope.launch {
                transactionForValues = getAllTransactionForValuesUseCase()
            },
        )
        updateDefaultData()
        updateValidTransactionTypesForNewTransaction()
        updateDataForRefundTransaction()
        processInitialData()
    }
    // endregion

    // region state events
    private fun clearAmount(): Job {
        return updateAmount(
            updatedAmount = "",
        )
    }

    private fun clearTitle(): Job {
        return updateTitle(
            updatedTitle = "",
        )
    }

    private fun insertTransaction(): Job {
        val selectedAccountFrom = accountFrom
        val selectedAccountTo = accountTo
        val selectedCategoryId = category?.id
        val selectedTransactionForId =
            if (selectedTransactionForIndex != -1) {
                transactionForValues[selectedTransactionForIndex].id
            } else {
                -1
            }
        val selectedTransactionDate = transactionDate
        val selectedTransactionTime = transactionTime
        val enteredAmountValue =
            amountTextFieldState.text.toString().toLongOrZero()
        val enteredTitle = titleTextFieldState
        val selectedTransactionType = this.selectedTransactionType
            ?: throw IllegalStateException("selectedTransactionType should not be null")
        val originalTransaction = originalTransactionData?.transaction

        return coroutineScope.launch {
            val isTransactionInserted = insertTransactionUseCase(
                selectedAccountFrom = selectedAccountFrom,
                selectedAccountTo = selectedAccountTo,
                selectedCategoryId = selectedCategoryId,
                selectedTransactionForId = selectedTransactionForId,
                selectedTransactionDate = selectedTransactionDate,
                selectedTransactionTime = selectedTransactionTime,
                enteredAmountValue = enteredAmountValue,
                enteredTitle = enteredTitle.text.toString(),
                selectedTransactionType = selectedTransactionType,
                originalTransaction = originalTransaction,
            )
            if (isTransactionInserted) {
                updateScreenSnackbarType(AddTransactionScreenSnackbarType.AddTransactionSuccessful)
                navigationKit.navigateUp()
            } else {
                updateScreenSnackbarType(AddTransactionScreenSnackbarType.AddTransactionFailed)
            }
        }
    }

    private fun resetScreenBottomSheetType(): Job {
        return updateScreenBottomSheetType(
            updatedAddTransactionScreenBottomSheetType = AddTransactionScreenBottomSheetType.None,
        )
    }

    private fun resetScreenSnackbarType(): Job {
        return updateScreenSnackbarType(
            updatedAddTransactionScreenSnackbarType = AddTransactionScreenSnackbarType.None,
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
        updatedAmount: String,
        shouldRefresh: Boolean = true,
    ): Job {
        amountTextFieldState.setTextAndPlaceCursorAtEnd(
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
        updatedAddTransactionScreenBottomSheetType: AddTransactionScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenBottomSheetType = updatedAddTransactionScreenBottomSheetType
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateScreenSnackbarType(
        updatedAddTransactionScreenSnackbarType: AddTransactionScreenSnackbarType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenSnackbarType = updatedAddTransactionScreenSnackbarType
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
        val updatedSelectedTransactionType =
            validTransactionTypesForNewTransaction
                .getOrNull(
                    index = selectedTransactionTypeIndex,
                )
        if (updatedSelectedTransactionType != null) {
            handleSelectedTransactionTypeChange(
                updatedSelectedTransactionType = updatedSelectedTransactionType,
            )
        }
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateTitle(
        updatedTitle: String,
        shouldRefresh: Boolean = true,
    ): Job {
        titleTextFieldState.setTextAndPlaceCursorAtEnd(
            text = updatedTitle,
        )
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
        getOriginalTransactionId() ?: return
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
            updatedSelectedTransactionTypeIndex = validTransactionTypesForNewTransaction.indexOf(
                element = TransactionType.REFUND,
            ),
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
        updateAccountFrom(
            updatedAccountFrom = null,
            shouldRefresh = false,
        )
        updateAccountTo(
            updatedAccountTo = originalTransactionData.accountFrom,
            shouldRefresh = false,
        )
        updateSelectedTransactionForIndex(
            updatedSelectedTransactionForIndex = transactionForValues.indexOf(
                element = transactionForValues.firstOrNull {
                    it.id == originalTransactionData.transaction.id
                },
            ),
            shouldRefresh = false,
        )
    }

    private fun processInitialDataForOtherTransactions() {
        updateSelectedTransactionTypeIndex(
            updatedSelectedTransactionTypeIndex = validTransactionTypesForNewTransaction.indexOf(
                element = TransactionType.EXPENSE,
            ),
            shouldRefresh = false,
        )
        updateCategory(
            updatedCategory = defaultExpenseCategory,
            shouldRefresh = false,
        )
        updateAccountFrom(
            updatedAccountFrom = defaultAccount,
            shouldRefresh = false,
        )
        updateAccountTo(
            updatedAccountTo = defaultAccount,
            shouldRefresh = false,
        )
    }
    // endregion

    // region updateTitleSuggestions
    private suspend fun updateTitleSuggestions() {
        titleSuggestions = category?.id?.let { categoryId ->
            getTitleSuggestionsUseCase(
                categoryId = categoryId,
                enteredTitle = titleTextFieldState.text.toString(),
            )
        } ?: persistentListOf()
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
        updateCategory(
            updatedCategory = originalTransactionData?.category
                ?: defaultIncomeCategory,
        )
        clearTitle()
        updateAccountFrom(
            updatedAccountFrom = null,
        )
        updateAccountTo(
            updatedAccountTo = originalTransactionData?.accountTo
                ?: defaultAccount,
        )
    }

    private fun handleSelectedTransactionTypeChangeToExpense() {
        updateCategory(
            updatedCategory = originalTransactionData?.category
                ?: defaultExpenseCategory,
        )
        clearTitle()
        updateAccountFrom(
            updatedAccountFrom = originalTransactionData?.accountFrom
                ?: defaultAccount,
        )
        updateAccountTo(
            updatedAccountTo = null,
        )
    }

    private fun handleSelectedTransactionTypeChangeToTransfer() {
        clearTitle()
        updateAccountFrom(
            updatedAccountFrom = originalTransactionData?.accountFrom
                ?: defaultAccount,
        )
        updateAccountTo(
            updatedAccountTo = originalTransactionData?.accountTo
                ?: defaultAccount,
        )
    }

    private fun handleSelectedTransactionTypeChangeToInvestment() {
        updateCategory(
            updatedCategory = originalTransactionData?.category
                ?: defaultInvestmentCategory,
        )
        clearTitle()
        updateAccountFrom(
            updatedAccountFrom = originalTransactionData?.accountFrom
                ?: defaultAccount,
        )
        updateAccountTo(
            updatedAccountTo = null,
        )
    }

    private fun handleSelectedTransactionTypeChangeToRefund() {
        updateAmount(
            updatedAmount = maxRefundAmount.orEmpty().value.toString(),
        )
        updateAccountTo(
            updatedAccountTo = originalTransactionData?.accountFrom,
        )
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

    private fun updateUiVisibilityState() {
        when (selectedTransactionType) {
            TransactionType.INCOME -> {
                uiVisibilityState =
                    AddTransactionScreenUiVisibilityState.Income(
                        isTitleSuggestionsVisible = titleSuggestions.isNotEmpty(),
                    )
            }

            TransactionType.EXPENSE -> {
                uiVisibilityState =
                    AddTransactionScreenUiVisibilityState.Expense(
                        isTitleSuggestionsVisible = titleSuggestions.isNotEmpty(),
                    )
            }

            TransactionType.TRANSFER -> {
                uiVisibilityState =
                    AddTransactionScreenUiVisibilityState.Transfer()
            }

            TransactionType.ADJUSTMENT -> {}

            TransactionType.INVESTMENT -> {
                uiVisibilityState =
                    AddTransactionScreenUiVisibilityState.Investment(
                        isTitleSuggestionsVisible = titleSuggestions.isNotEmpty(),
                    )
            }

            TransactionType.REFUND -> {
                uiVisibilityState =
                    AddTransactionScreenUiVisibilityState.Refund()
            }

            null -> {}
        }
    }
    // endregion

    // region screen args
    private fun getOriginalTransactionId(): Int? {
        return screenArgs.originalTransactionId
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
