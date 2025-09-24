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

package com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.categories.view_model

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.coroutines.getCompletedJob
import com.makeappssimple.abhimanyu.common.core.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.common.core.extensions.groupBy
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.category.DeleteCategoryByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.category.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.category.SetDefaultCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.transaction.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.tab_row.MyTabData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.grid_item.CategoriesGridItemData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.categories.bottom_sheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.categories.snackbar.CategoriesScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.categories.state.CategoriesScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.categories.state.CategoriesScreenUIStateEvents
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val navigationKit: NavigationKit,
    private val setDefaultCategoryUseCase: SetDefaultCategoryUseCase,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit {
    // region data
    private var isLoading: Boolean = true
    private var categoriesGridItemDataMap: ImmutableMap<TransactionType, ImmutableList<CategoriesGridItemData>> =
        persistentMapOf()
    private val validTransactionTypes: PersistentList<TransactionType> =
        persistentListOf(
            TransactionType.EXPENSE,
            TransactionType.INCOME,
            TransactionType.INVESTMENT,
        )
    private val tabData = validTransactionTypes.map {
        MyTabData(
            title = it.title,
        )
    }
    private var screenBottomSheetType: CategoriesScreenBottomSheetType =
        CategoriesScreenBottomSheetType.None
    private var screenSnackbarType: CategoriesScreenSnackbarType =
        CategoriesScreenSnackbarType.None
    private var categoryIdToDelete: Int? = null
    private var clickedItemId: Int? = null
    // endregion

    // region uiState
    private val _uiState: MutableStateFlow<CategoriesScreenUIState> =
        MutableStateFlow(
            value = CategoriesScreenUIState(),
        )
    internal val uiState: StateFlow<CategoriesScreenUIState> =
        _uiState.asStateFlow()
    // endregion

    // region uiStateEvents
    internal val uiStateEvents: CategoriesScreenUIStateEvents =
        CategoriesScreenUIStateEvents(
            deleteCategory = ::deleteCategory,
            navigateToAddCategoryScreen = navigationKit::navigateToAddCategoryScreen,
            navigateToEditCategoryScreen = navigationKit::navigateToEditCategoryScreen,
            navigateUp = navigationKit::navigateUp,
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
        completeLoading()
        observeData()
    }
    // endregion

    // region refreshUiState
    private fun refreshUiState(): Job {
        return coroutineScope.launch {
            updateUiState()
        }
    }

    private fun updateUiState() {
        _uiState.update {
            CategoriesScreenUIState(
                isBottomSheetVisible = screenBottomSheetType != CategoriesScreenBottomSheetType.None,
                screenBottomSheetType = screenBottomSheetType,
                screenSnackbarType = screenSnackbarType,
                isLoading = isLoading,
                tabData = tabData,
                transactionTypeTabs = validTransactionTypes,
                categoriesGridItemDataMap = categoriesGridItemDataMap,
            )
        }
    }
    // endregion

    // region observeData
    private fun observeData() {
        coroutineScope.launch {
            val defaultDataId: Flow<DefaultDataId?> =
                financeManagerPreferencesRepository.getDefaultDataIdFlow()
            val categoriesTransactionTypeMap: Flow<Map<TransactionType, ImmutableList<Category>>> =
                getAllCategoriesFlowUseCase()
                    .map { categories ->
                        categories.groupBy { category ->
                            category.transactionType
                        }
                    }
            combineAndCollectLatest(
                flow = defaultDataId,
                flow2 = categoriesTransactionTypeMap,
            ) { (
                    defaultDataId,
                    categoriesTransactionTypeMap,
                ) ->
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
                categoriesGridItemDataMap = persistentMapOf(
                    TransactionType.EXPENSE to expenseCategoriesGridItemDataList,
                    TransactionType.INCOME to incomeCategoriesGridItemDataList,
                    TransactionType.INVESTMENT to investmentCategoriesGridItemDataList,
                )
                refreshUiState()
            }
        }
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
    private fun deleteCategory(): Job {
        return coroutineScope.launch {
            val id = requireNotNull(
                value = categoryIdToDelete,
                lazyMessage = {
                    "Category ID to delete cannot be null."
                },
            )
            deleteCategoryByIdUseCase(
                id = id,
            )
            categoryIdToDelete = null
        }
    }

    private fun resetScreenBottomSheetType(): Job {
        return updateScreenBottomSheetType(
            updatedCategoriesScreenBottomSheetType = CategoriesScreenBottomSheetType.None,
        )
    }

    private fun resetScreenSnackbarType(): Job {
        return updateScreenSnackbarType(
            updatedCategoriesScreenSnackbarType = CategoriesScreenSnackbarType.None,
        )
    }

    private fun updateDefaultCategoryIdInDataStore(
        selectedTabIndex: Int,
    ): Job {
        return coroutineScope.launch {
            val id = requireNotNull(
                value = clickedItemId,
                lazyMessage = {
                    "Clicked item ID cannot be null."
                },
            )
            val isSetDefaultCategorySuccessful = setDefaultCategoryUseCase(
                defaultCategoryId = id,
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
            }
            clickedItemId = null
        }
    }

    private fun updateCategoryIdToDelete(
        updatedCategoryIdToDelete: Int?,
        shouldRefresh: Boolean = false,
    ): Job {
        categoryIdToDelete = updatedCategoryIdToDelete
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateClickedItemId(
        updatedClickedItemId: Int?,
        shouldRefresh: Boolean = false,
    ): Job {
        clickedItemId = updatedClickedItemId
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateScreenBottomSheetType(
        updatedCategoriesScreenBottomSheetType: CategoriesScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenBottomSheetType = updatedCategoriesScreenBottomSheetType
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateScreenSnackbarType(
        updatedCategoriesScreenSnackbarType: CategoriesScreenSnackbarType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenSnackbarType = updatedCategoriesScreenSnackbarType
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
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
