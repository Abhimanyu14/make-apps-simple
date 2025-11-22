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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.analysis.analysis.view_model

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.coroutines.getCompletedJob
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.MyLocalDate
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.orMin
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionDataMappedByCategory
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.feature.analysis.orEmpty
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.transaction.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.analysis.analysis.bottom_sheet.AnalysisScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.analysis.analysis.state.AnalysisScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.analysis.analysis.state.AnalysisScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.model.toDefaultString
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.analysis.AnalysisListItemData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private object AnalysisScreenViewModelConstants {
    const val FULL_PERCENTAGE = 100
}

@KoinViewModel
internal class AnalysisScreenViewModel(
    private val coroutineScope: CoroutineScope,
    private val dateTimeKit: DateTimeKit,
    private val getAllTransactionDataUseCase: GetAllTransactionDataUseCase,
    private val navigationKit: NavigationKit,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit {
    // region data
    private var isLoading: Boolean = true
    private val validTransactionTypes: ImmutableList<TransactionType> =
        persistentListOf(
            TransactionType.EXPENSE,
            TransactionType.INCOME,
            TransactionType.INVESTMENT,
        )
    private val validTransactionTypesChipUIData: ImmutableList<ChipUIData> =
        validTransactionTypes.map {
            ChipUIData(
                text = it.title,
            )
        }
    private var analysisListItemData: ImmutableList<AnalysisListItemData> =
        persistentListOf()
    private var allTransactionData: ImmutableList<TransactionData> =
        persistentListOf()
    private var oldestTransactionLocalDate: MyLocalDate? = null
    private var selectedFilter: Filter = Filter()
    private var screenBottomSheetType: AnalysisScreenBottomSheetType =
        AnalysisScreenBottomSheetType.None
    private var selectedTransactionTypeIndex: Int = 0
    // endregion

    // region uiState
    private val _uiState: MutableStateFlow<AnalysisScreenUIState> =
        MutableStateFlow(
            value = AnalysisScreenUIState(),
        )
    internal val uiState: StateFlow<AnalysisScreenUIState> =
        _uiState.asStateFlow()
    // endregion

    // region uiStateEvents
    internal val uiStateEvents: AnalysisScreenUIStateEvents =
        AnalysisScreenUIStateEvents(
            navigateUp = navigationKit::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateSelectedFilter = ::updateSelectedFilter,
            updateSelectedTransactionTypeIndex = ::updateSelectedTransactionTypeIndex,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        coroutineScope.launch {
            fetchData()
            completeLoading()
        }
    }
    // endregion

    // region refreshUiState
    private fun refreshUiState(): Job {
        return coroutineScope.launch {
            analysisListItemData = getAnalysisListItemData(
                selectedFilterValue = selectedFilter,
                selectedTransactionTypeIndexValue = selectedTransactionTypeIndex,
                allTransactionDataValue = allTransactionData,
            )
            updateUiState()
        }
    }

    private fun updateUiState() {
        logError(
            tag = "Abhi",
            message = "AnalysisScreenViewModel: updateUiState",
        )
        _uiState.update {
            AnalysisScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AnalysisScreenBottomSheetType.None,
                isLoading = isLoading,
                selectedFilter = selectedFilter.orEmpty(),
                selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                analysisListItemData = analysisListItemData,
                transactionTypesChipUIData = validTransactionTypesChipUIData,
                defaultStartLocalDate = oldestTransactionLocalDate.orMin(),
                defaultEndLocalDate = dateTimeKit.getCurrentLocalDate(),
                startOfCurrentMonthLocalDate = dateTimeKit.getStartOfMonthLocalDate(),
                startOfCurrentYearLocalDate = dateTimeKit.getStartOfYearLocalDate(),
            )
        }
    }

    private fun getAnalysisListItemData(
        selectedFilterValue: Filter,
        selectedTransactionTypeIndexValue: Int,
        allTransactionDataValue: ImmutableList<TransactionData>,
    ): ImmutableList<AnalysisListItemData> {
        val selectedTransactionType =
            validTransactionTypes[selectedTransactionTypeIndexValue]
        val result = allTransactionDataValue
            .filter { transactionData ->
                transactionData.transaction.transactionType == selectedTransactionType && isAvailableAfterDateFilter(
                    startLocalDate = selectedFilterValue.fromLocalDate,
                    endLocalDate = selectedFilterValue.toLocalDate,
                    transactionData = transactionData,
                )
            }
            .groupBy {
                it.category
            }
            .mapNotNull { (category, transactionDataList) ->
                category?.let {
                    TransactionDataMappedByCategory(
                        category = it,
                        amountValue = transactionDataList.sumOf { transactionData ->
                            transactionData.transaction.amount.value
                        },
                        percentage = 0.0,
                    )
                }
            }
            .sortedByDescending {
                it.amountValue
            }
        val sum = result.sumOf {
            it.amountValue
        }
        return result.map {
            val percentage = (it.amountValue.toDouble() / sum).toFloat()
            AnalysisListItemData(
                amountText = Amount(
                    value = it.amountValue,
                ).toDefaultString(),
                emoji = it.category.emoji,
                percentage = percentage,
                percentageText = "%.2f".format((percentage * AnalysisScreenViewModelConstants.FULL_PERCENTAGE))
                    .let { formattedPercentage ->
                        "$formattedPercentage%"
                    },
                title = it.category.title,
            )
        }
    }

    private fun isAvailableAfterDateFilter(
        startLocalDate: MyLocalDate?,
        endLocalDate: MyLocalDate?,
        transactionData: TransactionData,
    ): Boolean {
        if (startLocalDate.isNull() || endLocalDate.isNull()) {
            return true
        }
        val fromDateStartOfDayTimestamp =
            startLocalDate.toStartOfDayEpochMilli()
        val toDateStartOfDayTimestamp = endLocalDate.toStartOfDayEpochMilli()
        return transactionData.transaction.transactionTimestamp in fromDateStartOfDayTimestamp until toDateStartOfDayTimestamp
    }
    // endregion

    // region fetchData
    private suspend fun fetchData() {
        allTransactionData = getAllTransactionDataUseCase()
        oldestTransactionLocalDate = getOldestTransactionLocalDate()
    }
    // endregion

    // region state events
    private fun resetScreenBottomSheetType(): Job {
        return updateScreenBottomSheetType(
            updatedAnalysisScreenBottomSheetType = AnalysisScreenBottomSheetType.None,
        )
    }

    private fun updateScreenBottomSheetType(
        updatedAnalysisScreenBottomSheetType: AnalysisScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenBottomSheetType = updatedAnalysisScreenBottomSheetType
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedFilter = updatedSelectedFilter
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
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }
    // endregion

    // region getOldestTransactionLocalDate
    private fun getOldestTransactionLocalDate(): MyLocalDate {
        return dateTimeKit.getLocalDate(
            timestamp = allTransactionData.minOfOrNull { transactionData ->
                transactionData.transaction.transactionTimestamp
            }.orZero(),
        )
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
