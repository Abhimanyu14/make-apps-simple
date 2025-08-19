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
import com.makeappssimple.abhimanyu.common.core.extensions.combineAndCollectLatest
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
import kotlinx.coroutines.flow.collectLatest
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
    // endregion

    // region observables
    private val titleSuggestions: MutableStateFlow<ImmutableList<String>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    // endregion

    // region UI state
    private val screenBottomSheetType: MutableStateFlow<EditTransactionScreenBottomSheetType> =
        MutableStateFlow(
            value = EditTransactionScreenBottomSheetType.None,
        )
    private val screenSnackbarType: MutableStateFlow<EditTransactionScreenSnackbarType> =
        MutableStateFlow(
            value = EditTransactionScreenSnackbarType.None,
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
            updateTransaction = {
                updateTransaction(
                    uiState = uiState.value,
                )
            },
            updateTransactionDate = ::updateTransactionDate,
            updateTransactionTime = ::updateTransactionTime,
        )
    // endregion

    // region updateUiStateAndStateEvents
    override fun updateUiStateAndStateEvents() {
        val validationState =
            editTransactionScreenDataValidationUseCase(
                accountFrom = accountFrom.value,
                accountTo = accountTo.value,
                maxRefundAmount = maxRefundAmount,
                amount = amount.value.text,
                title = title.value.text,
                selectedTransactionType = selectedTransactionType,
            )
        _uiState.update {
            EditTransactionScreenUIState(
                accountFrom = accountFrom.value,
                accountFromText = if (selectedTransactionType == TransactionType.TRANSFER) {
                    AccountFromText.AccountFrom
                } else {
                    AccountFromText.Account
                },
                accountTo = accountTo.value,
                accountToText = if (selectedTransactionType == TransactionType.TRANSFER) {
                    AccountToText.AccountTo
                } else {
                    AccountToText.Account
                },
                screenBottomSheetType = screenBottomSheetType.value,
                screenSnackbarType = screenSnackbarType.value,
                uiVisibilityState = uiVisibilityState,
                isBottomSheetVisible = screenBottomSheetType != EditTransactionScreenBottomSheetType.None,
                isCtaButtonEnabled = validationState.isCtaButtonEnabled,
                isLoading = isLoading,
                isTransactionDatePickerDialogVisible = isTransactionDatePickerDialogVisible.value,
                isTransactionTimePickerDialogVisible = isTransactionTimePickerDialogVisible.value,
                category = category.value,
                selectedTransactionForIndex = selectedTransactionForIndex.value,
                selectedTransactionTypeIndex = selectedTransactionTypeIndex.value,
                accounts = accounts.orEmpty(),
                filteredCategories = filteredCategories,
                titleSuggestionsChipUIData = titleSuggestions.value
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
                titleSuggestions = titleSuggestions.value,
                currentLocalDate = dateTimeKit.getCurrentLocalDate()
                    .orMin(),
                transactionDate = transactionDate.value,
                transactionTime = transactionTime.value,
                amountErrorText = validationState.amountErrorText,
                amount = amount.value,
                title = title.value,
            )
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
            // completeLoading()
        }
    }
    // endregion

    // region observeData
    override fun observeData() {
        observeForTitleSuggestions()
        observeForSelectedTransactionType()
    }
    // endregion

    // region state events
    private fun clearAmount(): Job {
        amount.update {
            it.copy(
                text = "",
            )
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun clearTitle(): Job {
        title.update {
            it.copy(
                text = "",
            )
        }
        return refreshIfRequired(
            shouldRefresh = true,
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
        accountFrom.update {
            updatedAccountFrom
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateAccountTo(
        updatedAccountTo: Account?,
    ): Job {
        accountTo.update {
            updatedAccountTo
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateAmount(
        updatedAmount: TextFieldValue,
    ): Job {
        amount.update {
            updatedAmount.copy(
                text = updatedAmount.text.filterDigits(),
            )
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateAmount(
        updatedAmount: String,
    ): Job {
        amount.update {
            it.copy(
                text = updatedAmount.filterDigits(),
            )
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateCategory(
        updatedCategory: Category?,
    ): Job {
        category.update {
            updatedCategory
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateIsTransactionDatePickerDialogVisible(
        updatedIsTransactionDatePickerDialogVisible: Boolean,
    ): Job {
        isTransactionDatePickerDialogVisible.update {
            updatedIsTransactionDatePickerDialogVisible
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateIsTransactionTimePickerDialogVisible(
        updatedIsTransactionTimePickerDialogVisible: Boolean,
    ): Job {
        isTransactionTimePickerDialogVisible.update {
            updatedIsTransactionTimePickerDialogVisible
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateScreenBottomSheetType(
        updatedEditTransactionScreenBottomSheetType: EditTransactionScreenBottomSheetType,
    ): Job {
        screenBottomSheetType.update {
            updatedEditTransactionScreenBottomSheetType
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateScreenSnackbarType(
        updatedEditTransactionScreenSnackbarType: EditTransactionScreenSnackbarType,
    ): Job {
        screenSnackbarType.update {
            updatedEditTransactionScreenSnackbarType
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ): Job {
        selectedTransactionForIndex.update {
            updatedSelectedTransactionForIndex
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ): Job {
        selectedTransactionTypeIndex.update {
            updatedSelectedTransactionTypeIndex
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateTitle(
        updatedTitle: TextFieldValue,
    ): Job {
        title.update {
            updatedTitle
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateTransactionDate(
        updatedTransactionDate: LocalDate,
    ): Job {
        transactionDate.update {
            updatedTransactionDate
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateTransactionTime(
        updatedTransactionTime: LocalTime,
    ): Job {
        transactionTime.update {
            updatedTransactionTime
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateTransaction(
        uiState: EditTransactionScreenUIState,
    ): Job {
        // TODO(Abhi): Fix update transaction logic
        val enteredAmountValue = amount.value.text.toLongOrZero()
        Amount(
            value = enteredAmountValue,
        )
        when (uiState.selectedTransactionType) {
            TransactionType.INCOME -> {
                uiState.category?.id
            }

            TransactionType.EXPENSE -> {
                uiState.category?.id
            }

            TransactionType.TRANSFER -> {
                null
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                uiState.category?.id
            }

            TransactionType.REFUND -> {
                uiState.category?.id
            }
        }
        val accountFromId = when (uiState.selectedTransactionType) {
            TransactionType.INCOME -> {
                null
            }

            TransactionType.EXPENSE -> {
                uiState.accountFrom?.id
            }

            TransactionType.TRANSFER -> {
                uiState.accountFrom?.id
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                uiState.accountFrom?.id
            }

            TransactionType.REFUND -> {
                null
            }
        }
        val accountToId = when (uiState.selectedTransactionType) {
            TransactionType.INCOME -> {
                uiState.accountTo?.id
            }

            TransactionType.EXPENSE -> {
                null
            }

            TransactionType.TRANSFER -> {
                uiState.accountTo?.id
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                null
            }

            TransactionType.REFUND -> {
                uiState.accountTo?.id
            }
        }
        when (uiState.selectedTransactionType) {
            TransactionType.TRANSFER -> {
                TransactionType.TRANSFER.title
            }

            TransactionType.REFUND -> {
                TransactionType.REFUND.title
            }

            else -> {
                uiState.title.text.capitalizeWords()
            }
        }
        when (uiState.selectedTransactionType) {
            TransactionType.INCOME -> {
                1
            }

            TransactionType.EXPENSE -> {
                uiState.selectedTransactionForIndex
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
        LocalDateTime.of(
            uiState.transactionDate,
            uiState.transactionTime
        )
            .toEpochMilli()

        if (accountFromId.isNotNull()) {
            uiState.accountFrom
        } else {
            null
        }
        if (accountToId.isNotNull()) {
            uiState.accountTo
        } else {
            null
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
        /*
        val transaction = Transaction(
            amount = amount,
            categoryId = categoryId,
            originalTransactionId = currentTransactionData?.transaction?.id,
            accountFromId = accountFromId,
            accountToId = accountToId,
            title = title,
            creationTimestamp = dateTimeUtil.getCurrentTimeMillis(),
            transactionTimestamp = transactionTimestamp,
            transactionForId = transactionForId,
            transactionType = uiState.selectedTransactionType,
        )

        coroutineScope.launch {
            currentTransactionData?.transaction?.let { originalTransaction ->
                updateTransactionUseCase(
                    originalTransaction = originalTransaction,
                    updatedTransaction = transaction,
                )
            }
            navigator.navigateUp()
        }
        */
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

    // region observeForTitleSuggestions
    private fun observeForTitleSuggestions() {
        coroutineScope.launch {
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
        coroutineScope.launch {
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
                // completeLoading()
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
