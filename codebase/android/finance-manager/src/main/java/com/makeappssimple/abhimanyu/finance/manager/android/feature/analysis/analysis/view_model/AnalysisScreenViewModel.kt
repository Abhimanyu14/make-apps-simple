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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.view_model

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.common.core.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.common.core.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.extensions.orMin
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.common.core.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionDataMappedByCategory
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.analysis.orEmpty
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.analysis.AnalysisListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.bottom_sheet.AnalysisScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.state.AnalysisScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.state.AnalysisScreenUIStateEvents
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.time.LocalDate

private object AnalysisScreenViewModelConstants {
    const val FULL_PERCENTAGE = 100
}

@KoinViewModel
internal class AnalysisScreenViewModel(
    coroutineScope: CoroutineScope,
    private val dateTimeKit: DateTimeKit,
    private val getAllTransactionDataUseCase: GetAllTransactionDataUseCase,
    private val navigationKit: NavigationKit,
    internal val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
) {
    // region initial data
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
    private val analysisListItemData: MutableStateFlow<ImmutableList<AnalysisListItemData>> =
        MutableStateFlow(
            value = persistentListOf(),
        )

    private var allTransactionData: ImmutableList<TransactionData> =
        persistentListOf()
    private var oldestTransactionLocalDate: LocalDate? = null
    // endregion

    // region UI state
    val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    val selectedFilter: MutableStateFlow<Filter> = MutableStateFlow(
        value = Filter(),
    )
    val screenBottomSheetType: MutableStateFlow<AnalysisScreenBottomSheetType> =
        MutableStateFlow(
            value = AnalysisScreenBottomSheetType.None,
        )
    val selectedTransactionTypeIndex: MutableStateFlow<Int> =
        MutableStateFlow(
            value = 0,
        )
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<AnalysisScreenUIState> =
        MutableStateFlow(
            value = AnalysisScreenUIState(),
        )
    internal val uiStateEvents: AnalysisScreenUIStateEvents =
        AnalysisScreenUIStateEvents(
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateSelectedFilter = ::updateSelectedFilter,
            updateSelectedTransactionTypeIndex = ::updateSelectedTransactionTypeIndex,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        observeData()
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            withLoadingSuspend {
                allTransactionData = getAllTransactionDataUseCase()
                oldestTransactionLocalDate = getOldestTransactionLocalDate()
            }
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEvents()
        observeForTransactionDataMappedByCategory()
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
    fun navigateUp() {
        navigationKit.navigateUp()
    }

    fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedAnalysisScreenBottomSheetType = AnalysisScreenBottomSheetType.None,
        )
    }

    fun updateScreenBottomSheetType(
        updatedAnalysisScreenBottomSheetType: AnalysisScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedAnalysisScreenBottomSheetType
        }
    }

    fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
    ) {
        selectedFilter.update {
            updatedSelectedFilter
        }
    }

    fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        selectedTransactionTypeIndex.update {
            updatedSelectedTransactionTypeIndex
        }
    }
    // endregion

    // region getOldestTransactionLocalDate
    private fun getOldestTransactionLocalDate(): LocalDate {
        return dateTimeKit.getLocalDate(
            timestamp = allTransactionData.minOfOrNull { transactionData ->
                transactionData.transaction.transactionTimestamp
            }.orZero(),
        )
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                selectedFilter,
                selectedTransactionTypeIndex,
                analysisListItemData,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        selectedFilter,
                        selectedTransactionTypeIndex,
                        analysisListItemData,
                    ),
                ->
                uiState.update {
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
        }
    }
    // endregion

    // region observeForTransactionDataMappedByCategory
    private fun observeForTransactionDataMappedByCategory() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                selectedTransactionTypeIndex,
                selectedFilter,
            ) {
                    (
                        isLoading,
                        selectedTransactionTypeIndex,
                        selectedFilter,
                    ),
                ->
                analysisListItemData.update {
                    getAnalysisListItemData(
                        selectedFilterValue = selectedFilter,
                        selectedTransactionTypeIndexValue = selectedTransactionTypeIndex,
                        allTransactionDataValue = allTransactionData,
                    )
                }
            }
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
                ).toString(),
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
        startLocalDate: LocalDate?,
        endLocalDate: LocalDate?,
        transactionData: TransactionData,
    ): Boolean {
        if (startLocalDate.isNull() || endLocalDate.isNull()) {
            return true
        }
        val fromDateStartOfDayTimestamp = startLocalDate
            .atStartOfDay()
            .toEpochMilli()
        val toDateStartOfDayTimestamp = endLocalDate
            .atEndOfDay()
            .toEpochMilli()
        return transactionData.transaction.transactionTimestamp in fromDateStartOfDayTimestamp until toDateStartOfDayTimestamp
    }
    // endregion
}
