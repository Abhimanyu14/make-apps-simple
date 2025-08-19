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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.view_model

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.common.core.extensions.filter
import com.makeappssimple.abhimanyu.common.core.extensions.filterDigits
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.extensions.orEmpty
import com.makeappssimple.abhimanyu.common.core.extensions.orMin
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
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
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.InsertTransactionUseCase
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
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.bottom_sheet.AddTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.snackbar.AddTransactionScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.state.AccountFromText
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.state.AccountToText
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.state.AddTransactionScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.state.AddTransactionScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.use_case.AddTransactionScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.navigation.AddTransactionScreenArgs
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
import java.time.LocalTime

@KoinViewModel
internal class AddTransactionScreenViewModel(
    navigationKit: NavigationKit,
    screenUIStateDelegate: ScreenUIStateDelegate,
    savedStateHandle: SavedStateHandle,
    uriDecoder: UriDecoder,
    private val addTransactionScreenDataValidationUseCase: AddTransactionScreenDataValidationUseCase,
    private val coroutineScope: CoroutineScope,
    private val dateTimeKit: DateTimeKit,
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getTitleSuggestionsUseCase: GetTitleSuggestionsUseCase,
    private val getTransactionDataByIdUseCase: GetTransactionDataByIdUseCase,
    private val getMaxRefundAmountUseCase: GetMaxRefundAmountUseCase,
    private val insertTransactionUseCase: InsertTransactionUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    logKit = logKit,
    navigationKit = navigationKit,
    screenUIStateDelegate = screenUIStateDelegate,
) {
    // region screen args
    private val screenArgs = AddTransactionScreenArgs(
        savedStateHandle = savedStateHandle,
        uriDecoder = uriDecoder,
    )
    // endregion

    // region initial data
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
        AddTransactionScreenUiVisibilityState.Expense
    private var filteredCategories: ImmutableList<Category> = persistentListOf()
    private var originalTransactionData: TransactionData? = null
    private var transactionForValues: ImmutableList<TransactionFor> =
        persistentListOf()
    private var selectedTransactionType: TransactionType? = null
    // endregion

    // region observables
    private var titleSuggestions: ImmutableList<String> = persistentListOf()
    // endregion

    // region UI state
    private var screenBottomSheetType: AddTransactionScreenBottomSheetType =
        AddTransactionScreenBottomSheetType.None
    private var screenSnackbarType: AddTransactionScreenSnackbarType =
        AddTransactionScreenSnackbarType.None
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
    private val _uiState: MutableStateFlow<AddTransactionScreenUIState> =
        MutableStateFlow(
            value = AddTransactionScreenUIState(),
        )
    internal val uiState: StateFlow<AddTransactionScreenUIState> =
        _uiState.asStateFlow()
    internal val uiStateEvents: AddTransactionScreenUIStateEvents =
        AddTransactionScreenUIStateEvents(
            clearAmount = ::clearAmount,
            clearTitle = ::clearTitle,
            insertTransaction = ::insertTransaction,
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            resetScreenSnackbarType = ::resetScreenSnackbarType,
            updateAccountFrom = ::updateAccountFrom,
            updateAccountTo = ::updateAccountTo,
            updateAmount = this::updateAmount,
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

    // region updateUiStateAndStateEvents
    override fun updateUiStateAndStateEvents() {
        coroutineScope.launch {
            val validationState = addTransactionScreenDataValidationUseCase(
                accountFrom = accountFrom,
                accountTo = accountTo,
                maxRefundAmount = maxRefundAmount,
                amount = amount.text,
                title = title.text,
                selectedTransactionType = selectedTransactionType,
            )
            updateTitleSuggestions()
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
    }
    // endregion

    // region fetchData
    override fun fetchData(): Job {
        return coroutineScope.launch {
            startLoading()
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
            completeLoading()
        }
    }
    // endregion

    // region state events
    private fun clearAmount(): Job {
        return updateAmount(
            updatedAmount = amount.copy(
                text = "",
            ),
        )
    }

    private fun clearTitle(): Job {
        return updateTitle(
            updatedTitle = title.copy(
                text = "",
            ),
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
        val enteredAmountValue = amount.text.toLongOrZero()
        val enteredTitle = title.text
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
                enteredTitle = enteredTitle,
                selectedTransactionType = selectedTransactionType,
                originalTransaction = originalTransaction,
            )
            if (isTransactionInserted) {
                updateScreenSnackbarType(AddTransactionScreenSnackbarType.AddTransactionSuccessful)
                navigateUp()
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
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateAccountTo(
        updatedAccountTo: Account?,
        shouldRefresh: Boolean = true,
    ): Job {
        accountTo = updatedAccountTo
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateAmount(
        updatedAmount: TextFieldValue,
        shouldRefresh: Boolean = true,
    ): Job {
        amount = updatedAmount.copy(
            text = updatedAmount.text.filterDigits(),
        )
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateAmount(
        updatedAmount: String,
        shouldRefresh: Boolean = true,
    ): Job {
        amount = amount.copy(
            text = updatedAmount.filterDigits(),
        )
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateCategory(
        updatedCategory: Category?,
        shouldRefresh: Boolean = true,
    ): Job {
        category = updatedCategory
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateIsTransactionDatePickerDialogVisible(
        updatedIsTransactionDatePickerDialogVisible: Boolean,
        shouldRefresh: Boolean = true,
    ): Job {
        isTransactionDatePickerDialogVisible =
            updatedIsTransactionDatePickerDialogVisible
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateIsTransactionTimePickerDialogVisible(
        updatedIsTransactionTimePickerDialogVisible: Boolean,
        shouldRefresh: Boolean = true,
    ): Job {
        isTransactionTimePickerDialogVisible =
            updatedIsTransactionTimePickerDialogVisible
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateScreenBottomSheetType(
        updatedAddTransactionScreenBottomSheetType: AddTransactionScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenBottomSheetType = updatedAddTransactionScreenBottomSheetType
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateScreenSnackbarType(
        updatedAddTransactionScreenSnackbarType: AddTransactionScreenSnackbarType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenSnackbarType = updatedAddTransactionScreenSnackbarType
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedTransactionForIndex = updatedSelectedTransactionForIndex
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
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
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateTitle(
        updatedTitle: TextFieldValue,
        shouldRefresh: Boolean = true,
    ): Job {
        title = updatedTitle
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateTransactionDate(
        updatedTransactionDate: LocalDate,
        shouldRefresh: Boolean = true,
    ): Job {
        transactionDate = updatedTransactionDate
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateTransactionTime(
        updatedTransactionTime: LocalTime,
        shouldRefresh: Boolean = true,
    ): Job {
        transactionTime = updatedTransactionTime
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
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
    private suspend fun updateTitleSuggestions() {
        titleSuggestions = category?.id?.let { categoryId ->
            getTitleSuggestionsUseCase(
                categoryId = categoryId,
                enteredTitle = title.text,
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
        updateUiVisibilityState(AddTransactionScreenUiVisibilityState.Income)

        updateCategory(
            originalTransactionData?.category ?: defaultIncomeCategory
        )
        clearTitle()
        updateAccountFrom(null)
        updateAccountTo(originalTransactionData?.accountTo ?: defaultAccount)
    }

    private fun handleSelectedTransactionTypeChangeToExpense() {
        updateUiVisibilityState(AddTransactionScreenUiVisibilityState.Expense)

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
        updateUiVisibilityState(AddTransactionScreenUiVisibilityState.Transfer)

        clearTitle()
        updateAccountFrom(
            originalTransactionData?.accountFrom ?: defaultAccount
        )
        updateAccountTo(originalTransactionData?.accountTo ?: defaultAccount)
    }

    private fun handleSelectedTransactionTypeChangeToInvestment() {
        updateUiVisibilityState(AddTransactionScreenUiVisibilityState.Investment)

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
        updateUiVisibilityState(AddTransactionScreenUiVisibilityState.Refund)

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
        updatedUiVisibilityState: AddTransactionScreenUiVisibilityState,
    ) {
        uiVisibilityState = updatedUiVisibilityState
    }
    // endregion

    // region common
    private fun getOriginalTransactionId(): Int? {
        return screenArgs.originalTransactionId
    }
    // endregion
}
