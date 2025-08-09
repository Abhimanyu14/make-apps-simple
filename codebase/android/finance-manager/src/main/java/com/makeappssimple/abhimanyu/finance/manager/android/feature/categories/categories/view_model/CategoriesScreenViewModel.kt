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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.view_model

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.common.core.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.common.core.extensions.groupBy
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.DeleteCategoryByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.SetDefaultCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.tab_row.MyTabData
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.grid_item.CategoriesGridItemData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.bottom_sheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.snackbar.CategoriesScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.state.CategoriesScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.state.CategoriesScreenUIStateEvents
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class CategoriesScreenViewModel(
    private val checkIfCategoryIsUsedInTransactionsUseCase: CheckIfCategoryIsUsedInTransactionsUseCase,
    private val coroutineScope: CoroutineScope,
    private val deleteCategoryByIdUseCase: DeleteCategoryByIdUseCase,
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val getAllCategoriesFlowUseCase: GetAllCategoriesFlowUseCase,
    private val setDefaultCategoryUseCase: SetDefaultCategoryUseCase,
    private val navigationKit: NavigationKit,
    private val screenUIStateDelegate: ScreenUIStateDelegate,
    internal val logKit: LogKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    logKit = logKit,
    navigationKit = navigationKit,
), ScreenUIStateDelegate by screenUIStateDelegate {
    // region initial data
    private val categoriesGridItemDataMap: MutableStateFlow<ImmutableMap<TransactionType, ImmutableList<CategoriesGridItemData>>> =
        MutableStateFlow(
            value = persistentMapOf(),
        )
    // endregion

    // region initial data
    val validTransactionTypes: PersistentList<TransactionType> =
        persistentListOf(
            TransactionType.EXPENSE,
            TransactionType.INCOME,
            TransactionType.INVESTMENT,
        )
    // endregion

    // region UI state
    var screenBottomSheetType: CategoriesScreenBottomSheetType =
        CategoriesScreenBottomSheetType.None
    var screenSnackbarType: CategoriesScreenSnackbarType =
        CategoriesScreenSnackbarType.None
    var categoryIdToDelete: Int? = null
    var clickedItemId: Int? = null
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<CategoriesScreenUIState> =
        MutableStateFlow(
            value = CategoriesScreenUIState(),
        )
    internal val uiStateEvents: CategoriesScreenUIStateEvents =
        CategoriesScreenUIStateEvents(
            deleteCategory = ::deleteCategory,
            navigateToAddCategoryScreen = ::navigateToAddCategoryScreen,
            navigateToEditCategoryScreen = ::navigateToEditCategoryScreen,
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            resetScreenSnackbarType = ::resetScreenSnackbarType,
            updateCategoryIdToDelete = ::updateCategoryIdToDelete,
            updateClickedItemId = ::updateClickedItemId,
            updateDefaultCategoryIdInDataStore = ::updateDefaultCategoryIdInDataStore,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateScreenSnackbarType = ::updateScreenSnackbarType,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        observeData()
        fetchData()
    }

    private fun fetchData() {
        val defaultDataId: Flow<DefaultDataId?> =
            financeManagerPreferencesRepository.getDefaultDataIdFlow()
        val categoriesTransactionTypeMap: Flow<Map<TransactionType, ImmutableList<Category>>> =
            getAllCategoriesFlowUseCase()
                .map { categories ->
                    categories.groupBy { category ->
                        category.transactionType
                    }
                }

        viewModelScope.launch {
            combineAndCollectLatest(
                defaultDataId,
                categoriesTransactionTypeMap
            ) { (defaultDataId, categoriesTransactionTypeMap) ->
                val expenseCategoriesGridItemDataList =
                    categoriesTransactionTypeMap[TransactionType.EXPENSE]
                        ?.sortedBy {
                            it.title
                        }
                        .map { category ->
                            val isDefault =
                                if (defaultDataId.isNull() || defaultDataId.expenseCategory == 0) {
                                    isDefaultExpenseCategory(
                                        category = category.title,
                                    )
                                } else {
                                    defaultDataId.expenseCategory == category.id
                                }
                            val isUsedInTransactions =
                                checkIfCategoryIsUsedInTransactionsUseCase(
                                    categoryId = category.id,
                                )
                            val isDeleteEnabled =
                                !isDefault && !isUsedInTransactions

                            getCategoriesGridItemData(
                                isDefault = isDefault,
                                isDeleteEnabled = isDeleteEnabled,
                                category = category,
                            )
                        }
                val incomeCategoriesGridItemDataList =
                    categoriesTransactionTypeMap[TransactionType.INCOME]
                        ?.sortedBy {
                            it.title
                        }
                        .map { category ->
                            val isDefault =
                                if (defaultDataId.isNull() || defaultDataId.incomeCategory == 0) {
                                    isDefaultIncomeCategory(
                                        category = category.title,
                                    )
                                } else {
                                    defaultDataId.incomeCategory == category.id
                                }
                            val isUsedInTransactions =
                                checkIfCategoryIsUsedInTransactionsUseCase(
                                    categoryId = category.id,
                                )
                            val isDeleteEnabled =
                                !isDefault && !isUsedInTransactions

                            getCategoriesGridItemData(
                                isDefault = isDefault,
                                isDeleteEnabled = isDeleteEnabled,
                                category = category,
                            )
                        }
                val investmentCategoriesGridItemDataList =
                    categoriesTransactionTypeMap[TransactionType.INVESTMENT]
                        ?.sortedBy {
                            it.title
                        }
                        .map { category ->
                            val isDefault =
                                if (defaultDataId.isNull() || defaultDataId.investmentCategory == 0) {
                                    isDefaultInvestmentCategory(
                                        category = category.title,
                                    )
                                } else {
                                    defaultDataId.investmentCategory == category.id
                                }
                            val isUsedInTransactions =
                                checkIfCategoryIsUsedInTransactionsUseCase(
                                    categoryId = category.id,
                                )
                            val isDeleteEnabled =
                                !isDefault && !isUsedInTransactions

                            getCategoriesGridItemData(
                                isDefault = isDefault,
                                isDeleteEnabled = isDeleteEnabled,
                                category = category,
                            )
                        }
                categoriesGridItemDataMap.update {
                    persistentMapOf(
                        TransactionType.EXPENSE to expenseCategoriesGridItemDataList,
                        TransactionType.INCOME to incomeCategoriesGridItemDataList,
                        TransactionType.INVESTMENT to investmentCategoriesGridItemDataList,
                    )
                }
            }
        }
        viewModelScope.launch {
            startLoading()
            completeLoading()
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEvents()
    }

    private fun getCategoriesGridItemData(
        isDefault: Boolean,
        isDeleteEnabled: Boolean,
        category: Category,
    ): CategoriesGridItemData {
        val isEditVisible = !isDefaultExpenseCategory(
            category = category.title,
        ) && !isDefaultIncomeCategory(
            category = category.title,
        ) && !isDefaultInvestmentCategory(
            category = category.title,
        )
        val isSetAsDefaultVisible = !isDefault
        val isDeleteVisible = !isDefaultExpenseCategory(
            category = category.title,
        ) && !isDefaultIncomeCategory(
            category = category.title,
        ) && !isDefaultInvestmentCategory(
            category = category.title,
        ) && isDeleteEnabled

        return CategoriesGridItemData(
            isDeleteVisible = isDeleteVisible,
            isEditVisible = isEditVisible,
            isSetAsDefaultVisible = isSetAsDefaultVisible,
            isSelected = isDefault,
            category = category,
        )
    }
    // endregion

    // region state events
    fun deleteCategory() {
        coroutineScope.launch {
            categoryIdToDelete?.let { id ->
                val isCategoryDeleted = deleteCategoryByIdUseCase(
                    id = id,
                )
                if (isCategoryDeleted) {
                    categoryIdToDelete = null
                } else {
                    // TODO(Abhi): Handle this error scenario
                }
            } ?: run {
                // TODO(Abhi): Handle this error scenario
            }
        }
    }

    fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedCategoriesScreenBottomSheetType = CategoriesScreenBottomSheetType.None,
        )
    }

    fun resetScreenSnackbarType() {
        updateScreenSnackbarType(
            updatedCategoriesScreenSnackbarType = CategoriesScreenSnackbarType.None,
        )
    }

    fun updateDefaultCategoryIdInDataStore(
        selectedTabIndex: Int,
    ) {
        coroutineScope.launch {
            clickedItemId?.let {
                val isSetDefaultCategorySuccessful = setDefaultCategoryUseCase(
                    defaultCategoryId = it,
                    transactionType = validTransactionTypes[selectedTabIndex],
                )
                if (isSetDefaultCategorySuccessful) {
                    updateScreenSnackbarType(
                        updatedCategoriesScreenSnackbarType = CategoriesScreenSnackbarType.SetDefaultCategorySuccessful,
                    )
                } else {
                    updateScreenSnackbarType(
                        updatedCategoriesScreenSnackbarType = CategoriesScreenSnackbarType.SetDefaultCategoryFailed,
                    )
                    clickedItemId = null
                }
            } ?: run {
                // TODO(Abhi): Handle this error scenario
            }
        }
    }

    fun updateCategoryIdToDelete(
        updatedCategoryIdToDelete: Int?,
        refresh: Boolean = false,
    ) {
        categoryIdToDelete = updatedCategoryIdToDelete
        if (refresh) {
            refresh()
        }
    }

    fun updateClickedItemId(
        updatedClickedItemId: Int?,
        refresh: Boolean = false,
    ) {
        clickedItemId = updatedClickedItemId
        if (refresh) {
            refresh()
        }
    }

    fun updateScreenBottomSheetType(
        updatedCategoriesScreenBottomSheetType: CategoriesScreenBottomSheetType,
        refresh: Boolean = false,
    ) {
        screenBottomSheetType = updatedCategoriesScreenBottomSheetType
        if (refresh) {
            refresh()
        }
    }

    fun updateScreenSnackbarType(
        updatedCategoriesScreenSnackbarType: CategoriesScreenSnackbarType,
        refresh: Boolean = false,
    ) {
        screenSnackbarType = updatedCategoriesScreenSnackbarType
        if (refresh) {
            refresh()
        }
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        observeForRefreshSignal()
        observeForCategoriesGridItemDataMap()
    }

    private fun observeForRefreshSignal() {
        viewModelScope.launch {
            refreshSignal.collectLatest {
                updateUiStateAndStateEvents(
                    categoriesGridItemDataMap = categoriesGridItemDataMap.value,
                )
            }
        }
    }

    private fun observeForCategoriesGridItemDataMap() {
        viewModelScope.launch {
            categoriesGridItemDataMap.collectLatest { categoriesGridItemDataMap ->
                updateUiStateAndStateEvents(
                    categoriesGridItemDataMap = categoriesGridItemDataMap,
                )
            }
        }
    }

    private fun updateUiStateAndStateEvents(
        categoriesGridItemDataMap: ImmutableMap<TransactionType, ImmutableList<CategoriesGridItemData>>,
    ) {
        val tabData = validTransactionTypes.map {
            MyTabData(
                title = it.title,
            )
        }

        uiState.update {
            CategoriesScreenUIState(
                isBottomSheetVisible = screenBottomSheetType != CategoriesScreenBottomSheetType.None,
                screenBottomSheetType = screenBottomSheetType,
                screenSnackbarType = screenSnackbarType,
                isLoading = isLoading,
                categoryIdToDelete = categoryIdToDelete,
                clickedItemId = clickedItemId,
                tabData = tabData,
                validTransactionTypes = validTransactionTypes,
                categoriesGridItemDataMap = categoriesGridItemDataMap,
            )
        }
    }
    // endregion
}
