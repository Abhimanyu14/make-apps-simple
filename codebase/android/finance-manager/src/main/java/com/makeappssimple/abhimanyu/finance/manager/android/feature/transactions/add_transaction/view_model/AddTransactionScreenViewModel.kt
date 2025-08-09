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
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.common.core.extensions.combineAndCollectLatest
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.time.LocalDate
import java.time.LocalTime

@KoinViewModel
internal class AddTransactionScreenViewModel(
    navigationKit: NavigationKit,
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
    // endregion

    // region initial data
    private var originalTransactionData: TransactionData? = null
    private var transactionForValues: ImmutableList<TransactionFor> =
        persistentListOf()
    private var selectedTransactionType: TransactionType? = null
    // endregion

    // region observables
    private val titleSuggestions: MutableStateFlow<ImmutableList<String>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    // endregion

    // region UI state
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val screenBottomSheetType: MutableStateFlow<AddTransactionScreenBottomSheetType> =
        MutableStateFlow(
            value = AddTransactionScreenBottomSheetType.None,
        )
    private val screenSnackbarType: MutableStateFlow<AddTransactionScreenSnackbarType> =
        MutableStateFlow(
            value = AddTransactionScreenSnackbarType.None,
        )
    private val selectedTransactionTypeIndex: MutableStateFlow<Int> =
        MutableStateFlow(
            value = 0,
        )
    private val amount: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(
            value = TextFieldValue(),
        )
    private val category: MutableStateFlow<Category?> =
        MutableStateFlow(
            value = null,
        )
    private val title: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(
            value = TextFieldValue(),
        )
    private val selectedTransactionForIndex: MutableStateFlow<Int> =
        MutableStateFlow(
            value = 0,
        )
    private val accountFrom: MutableStateFlow<Account?> =
        MutableStateFlow(
            value = null,
        )
    private val accountTo: MutableStateFlow<Account?> =
        MutableStateFlow(
            value = null,
        )
    private val transactionDate: MutableStateFlow<LocalDate> =
        MutableStateFlow(
            value = dateTimeKit.getCurrentLocalDate(),
        )
    private val transactionTime: MutableStateFlow<LocalTime> =
        MutableStateFlow(
            value = dateTimeKit.getCurrentLocalTime(),
        )
    private val isTransactionDatePickerDialogVisible: MutableStateFlow<Boolean> =
        MutableStateFlow(
            value = false,
        )
    private val isTransactionTimePickerDialogVisible: MutableStateFlow<Boolean> =
        MutableStateFlow(
            value = false,
        )
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<AddTransactionScreenUIState> =
        MutableStateFlow(
            value = AddTransactionScreenUIState(),
        )
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

    // region initViewModel
    internal fun initViewModel() {
        observeData()
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
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

    private fun observeData() {
        observeForUiStateAndStateEvents()
        observeForTitleSuggestions()
        observeForSelectedTransactionType()
    }
    // endregion

    // region loading
    fun startLoading() {
        isLoading.update {
            true
        }
    }

    fun completeLoading() {
        isLoading.update {
            false
        }
    }

    fun <T> withLoading(
        block: () -> T,
    ): T {
        startLoading()
        val result = block()
        completeLoading()
        return result
    }

    suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T {
        startLoading()
        try {
            return block()
        } finally {
            completeLoading()
        }
    }
    // endregion

    // region state events
    private fun clearAmount() {
        amount.update {
            it.copy(
                text = "",
            )
        }
    }

    private fun clearTitle() {
        title.update {
            it.copy(
                text = "",
            )
        }
    }

    private fun insertTransaction() {
        val selectedAccountFrom = accountFrom.value
        val selectedAccountTo = accountTo.value
        val selectedCategoryId = category.value?.id
        val selectedTransactionForId =
            if (selectedTransactionForIndex.value != -1) {
                transactionForValues[selectedTransactionForIndex.value].id
            } else {
                -1
            }
        val selectedTransactionDate = transactionDate.value
        val selectedTransactionTime = transactionTime.value
        val enteredAmountValue = amount.value.text.toLongOrZero()
        val enteredTitle = title.value.text
        val selectedTransactionType = this.selectedTransactionType ?: return
        val originalTransaction = originalTransactionData?.transaction

        coroutineScope.launch {
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

    private fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedAddTransactionScreenBottomSheetType = AddTransactionScreenBottomSheetType.None,
        )
    }

    private fun resetScreenSnackbarType() {
        updateScreenSnackbarType(
            updatedAddTransactionScreenSnackbarType = AddTransactionScreenSnackbarType.None,
        )
    }

    private fun updateAccountFrom(
        updatedAccountFrom: Account?,
    ) {
        accountFrom.update {
            updatedAccountFrom
        }
    }

    private fun updateAccountTo(
        updatedAccountTo: Account?,
    ) {
        accountTo.update {
            updatedAccountTo
        }
    }

    private fun updateAmount(
        updatedAmount: TextFieldValue,
    ) {
        amount.update {
            updatedAmount.copy(
                text = updatedAmount.text.filterDigits(),
            )
        }
    }

    private fun updateAmount(
        updatedAmount: String,
    ) {
        amount.update {
            it.copy(
                text = updatedAmount.filterDigits(),
            )
        }
    }

    private fun updateCategory(
        updatedCategory: Category?,
    ) {
        category.update {
            updatedCategory
        }
    }

    private fun updateIsTransactionDatePickerDialogVisible(
        updatedIsTransactionDatePickerDialogVisible: Boolean,
    ) {
        isTransactionDatePickerDialogVisible.update {
            updatedIsTransactionDatePickerDialogVisible
        }
    }

    private fun updateIsTransactionTimePickerDialogVisible(
        updatedIsTransactionTimePickerDialogVisible: Boolean,
    ) {
        isTransactionTimePickerDialogVisible.update {
            updatedIsTransactionTimePickerDialogVisible
        }
    }

    private fun updateScreenBottomSheetType(
        updatedAddTransactionScreenBottomSheetType: AddTransactionScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedAddTransactionScreenBottomSheetType
        }
    }

    private fun updateScreenSnackbarType(
        updatedAddTransactionScreenSnackbarType: AddTransactionScreenSnackbarType,
    ) {
        screenSnackbarType.update {
            updatedAddTransactionScreenSnackbarType
        }
    }

    private fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ) {
        selectedTransactionForIndex.update {
            updatedSelectedTransactionForIndex
        }
    }

    private fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        selectedTransactionTypeIndex.update {
            updatedSelectedTransactionTypeIndex
        }
    }

    private fun updateTitle(
        updatedTitle: TextFieldValue,
    ) {
        title.update {
            updatedTitle
        }
    }

    private fun updateTransactionDate(
        updatedTransactionDate: LocalDate,
    ) {
        transactionDate.update {
            updatedTransactionDate
        }
    }

    private fun updateTransactionTime(
        updatedTransactionTime: LocalTime,
    ) {
        transactionTime.update {
            updatedTransactionTime
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

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                screenSnackbarType,
                selectedTransactionTypeIndex,
                amount,
                title,
                accountFrom,
                accountTo,
                isTransactionDatePickerDialogVisible,
                isTransactionTimePickerDialogVisible,
                category,
                selectedTransactionForIndex,
                transactionDate,
                transactionTime,
                titleSuggestions,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        screenSnackbarType,
                        selectedTransactionTypeIndex,
                        amount,
                        title,
                        accountFrom,
                        accountTo,
                        isTransactionDatePickerDialogVisible,
                        isTransactionTimePickerDialogVisible,
                        category,
                        selectedTransactionForIndex,
                        transactionDate,
                        transactionTime,
                        titleSuggestions,
                    ),
                ->
                val validationState = addTransactionScreenDataValidationUseCase(
                    accountFrom = accountFrom,
                    accountTo = accountTo,
                    maxRefundAmount = maxRefundAmount,
                    amount = amount.text,
                    title = title.text,
                    selectedTransactionType = selectedTransactionType,
                )
                uiState.update {
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
    }
    // endregion

    // region observeForTitleSuggestions
    private fun observeForTitleSuggestions() {
        viewModelScope.launch {
            combineAndCollectLatest(
                flow = title,
                flow2 = category
            ) { (title, category) ->
                titleSuggestions.update {
                    category?.id?.let { categoryId ->
                        getTitleSuggestionsUseCase(
                            categoryId = categoryId,
                            enteredTitle = title.text,
                        )
                    } ?: persistentListOf()
                }
            }
        }
    }
    // endregion

    // region observeForSelectedTransactionType
    private fun observeForSelectedTransactionType() {
        viewModelScope.launch {
            selectedTransactionTypeIndex.collectLatest { selectedTransactionTypeIndex ->
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
                completeLoading()
            }
        }
    }

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
        return screenArgs.transactionId
    }
    // endregion
}
