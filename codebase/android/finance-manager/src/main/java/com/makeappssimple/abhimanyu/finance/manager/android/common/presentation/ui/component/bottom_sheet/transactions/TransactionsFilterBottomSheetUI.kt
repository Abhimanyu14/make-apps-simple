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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.bottom_sheet.transactions

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.common.core.extensions.addIfDoesNotContainItemElseRemove
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.MyLocalDate
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.getLocalDate
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.getTimestamp
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFilter
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.button.MyIconButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.button.MyTextButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.statusBarSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.date_picker.MyDatePicker
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.date_picker.MyDatePickerData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.date_picker.MyDatePickerEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.selection_group.MySelectionGroup
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.selection_group.MySelectionGroupData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.selection_group.MySelectionGroupEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.text_field.MyReadOnlyTextField
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.text_field.MyReadOnlyTextFieldData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.text_field.MyReadOnlyTextFieldEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.util.minimumBottomSheetHeight
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun TransactionsFiltersBottomSheetUI(
    modifier: Modifier = Modifier,
    expenseCategories: ImmutableList<Category>,
    incomeCategories: ImmutableList<Category>,
    investmentCategories: ImmutableList<Category>,
    accounts: ImmutableList<Account>,
    transactionForValues: ImmutableList<TransactionFor>,
    transactionTypes: ImmutableList<TransactionType>,
    defaultMinDate: MyLocalDate,
    defaultMaxDate: MyLocalDate,
    selectedTransactionFilter: TransactionFilter,
    onPositiveButtonClick: (updatedTransactionFilter: TransactionFilter) -> Unit,
    onNegativeButtonClick: () -> Unit,
) {
    val expandedItemsIndices = remember {
        mutableStateListOf(
            selectedTransactionFilter.selectedExpenseCategoryIds.isNotEmpty(),
            selectedTransactionFilter.selectedIncomeCategoryIds.isNotEmpty(),
            selectedTransactionFilter.selectedInvestmentCategoryIds.isNotEmpty(),
            selectedTransactionFilter.selectedAccountIds.isNotEmpty(),
            selectedTransactionFilter.selectedTransactionForIds.isNotEmpty(),
            selectedTransactionFilter.selectedTransactionTypes.isNotEmpty(),
            selectedTransactionFilter.toTimestamp.isNotNull(),
        )
    }

    val selectedExpenseCategoryIndices = remember {
        mutableStateListOf(
            elements = selectedTransactionFilter.selectedExpenseCategoryIds.map { selectedExpenseCategoryId ->
                expenseCategories.indexOfFirst { expenseCategory ->
                    expenseCategory.id == selectedExpenseCategoryId
                }
            }.toTypedArray(),
        )
    }
    val selectedIncomeCategoryIndices = remember {
        mutableStateListOf(
            elements = selectedTransactionFilter.selectedIncomeCategoryIds.map { selectedIncomeCategoryId ->
                incomeCategories.indexOfFirst { incomeCategory ->
                    incomeCategory.id == selectedIncomeCategoryId
                }
            }.toTypedArray(),
        )
    }
    val selectedInvestmentCategoryIndices = remember {
        mutableStateListOf(
            elements = selectedTransactionFilter.selectedInvestmentCategoryIds.map { selectedInvestmentCategoryId ->
                investmentCategories.indexOfFirst { investmentCategory ->
                    investmentCategory.id == selectedInvestmentCategoryId
                }
            }.toTypedArray(),
        )
    }
    val selectedAccountIndicesValue = remember {
        mutableStateListOf(
            elements = selectedTransactionFilter.selectedAccountIds.map { selectedAccountsId ->
                accounts.indexOfFirst { account ->
                    account.id == selectedAccountsId
                }
            }.toTypedArray(),
        )
    }
    val selectedTransactionForValuesIndicesValue = remember {
        mutableStateListOf(
            elements = selectedTransactionFilter.selectedTransactionForIds.map { selectedTransactionForValuesId ->
                transactionForValues.indexOfFirst { transactionFor ->
                    transactionFor.id == selectedTransactionForValuesId
                }
            }.toTypedArray(),
        )
    }
    val selectedTransactionTypeIndicesValue = remember {
        mutableStateListOf(
            elements = selectedTransactionFilter.selectedTransactionTypes.map { selectedTransactionType ->
                transactionTypes.indexOfFirst { transactionType ->
                    transactionType == selectedTransactionType
                }
            }.toTypedArray(),
        )
    }
    var fromDate by remember {
        mutableStateOf(
            value = selectedTransactionFilter.fromTimestamp?.let { fromTimestamp ->
                getLocalDate(
                    timestamp = fromTimestamp,
                )
            } ?: defaultMinDate,
        )
    }
    var toDate by remember {
        mutableStateOf(
            value = selectedTransactionFilter.toTimestamp?.let { toTimestamp ->
                getLocalDate(
                    timestamp = toTimestamp,
                )
            } ?: defaultMaxDate,
        )
    }
    val filters = remember {
        buildList {
            if (expenseCategories.isNotEmpty() && expenseCategories.size > 1) {
                add(
                    TransactionsFiltersBottomSheetData(
                        data = TransactionFilterBottomSheetFilterGroupData(
                            headingTextStringResourceId = R.string.finance_manager_bottom_sheet_transactions_filter_expense_categories,
                            items = expenseCategories.map { category ->
                                ChipUIData(
                                    text = category.title,
                                )
                            },
                            selectedItemsIndices = selectedExpenseCategoryIndices,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is TransactionFilterBottomSheetFilterGroupEvent.OnClearButtonClick -> {
                                    selectedExpenseCategoryIndices.clear()
                                }
                            }
                        },
                    )
                )
            }
            if (incomeCategories.isNotEmpty() && incomeCategories.size > 1) {
                add(
                    TransactionsFiltersBottomSheetData(
                        data = TransactionFilterBottomSheetFilterGroupData(
                            headingTextStringResourceId = R.string.finance_manager_bottom_sheet_transactions_filter_income_categories,
                            items = incomeCategories.map { category ->
                                ChipUIData(
                                    text = category.title,
                                )
                            },
                            selectedItemsIndices = selectedIncomeCategoryIndices,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is TransactionFilterBottomSheetFilterGroupEvent.OnClearButtonClick -> {
                                    selectedIncomeCategoryIndices.clear()
                                }
                            }
                        },
                    )
                )
            }
            if (investmentCategories.isNotEmpty() && investmentCategories.size > 1) {
                add(
                    TransactionsFiltersBottomSheetData(
                        data = TransactionFilterBottomSheetFilterGroupData(
                            headingTextStringResourceId = R.string.finance_manager_bottom_sheet_transactions_filter_investment_categories,
                            items = investmentCategories.map { category ->
                                ChipUIData(
                                    text = category.title,
                                )
                            },
                            selectedItemsIndices = selectedInvestmentCategoryIndices,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is TransactionFilterBottomSheetFilterGroupEvent.OnClearButtonClick -> {
                                    selectedInvestmentCategoryIndices.clear()
                                }
                            }
                        },
                    )
                )
            }
            if (accounts.isNotEmpty() && accounts.size > 1) {
                add(
                    TransactionsFiltersBottomSheetData(
                        data = TransactionFilterBottomSheetFilterGroupData(
                            headingTextStringResourceId = R.string.finance_manager_bottom_sheet_transactions_filter_accounts,
                            items = accounts.map { account ->
                                ChipUIData(
                                    text = account.name,
                                )
                            },
                            selectedItemsIndices = selectedAccountIndicesValue,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is TransactionFilterBottomSheetFilterGroupEvent.OnClearButtonClick -> {
                                    selectedAccountIndicesValue.clear()
                                }
                            }
                        },
                    )
                )
            }
            if (transactionForValues.isNotEmpty() && transactionForValues.size > 1) {
                add(
                    TransactionsFiltersBottomSheetData(
                        data = TransactionFilterBottomSheetFilterGroupData(
                            headingTextStringResourceId = R.string.finance_manager_bottom_sheet_transactions_filter_transaction_for_values,
                            items = transactionForValues.map { transactionFor ->
                                ChipUIData(
                                    text = transactionFor.title,
                                )
                            },
                            selectedItemsIndices = selectedTransactionForValuesIndicesValue,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is TransactionFilterBottomSheetFilterGroupEvent.OnClearButtonClick -> {
                                    selectedTransactionForValuesIndicesValue.clear()
                                }
                            }
                        },
                    )
                )
            }
            if (transactionTypes.isNotEmpty() && transactionTypes.size > 1) {
                add(
                    TransactionsFiltersBottomSheetData(
                        data = TransactionFilterBottomSheetFilterGroupData(
                            headingTextStringResourceId = R.string.finance_manager_bottom_sheet_transactions_filter_transaction_types,
                            items = transactionTypes.map { transactionType ->
                                ChipUIData(
                                    text = transactionType.title,
                                )
                            },
                            selectedItemsIndices = selectedTransactionTypeIndicesValue,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is TransactionFilterBottomSheetFilterGroupEvent.OnClearButtonClick -> {
                                    selectedTransactionTypeIndicesValue.clear()
                                }
                            }
                        },
                    )
                )
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarSpacer()
            .defaultMinSize(
                minHeight = minimumBottomSheetHeight,
            ),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(
                    weight = 1F,
                ),
            contentPadding = PaddingValues(
                top = 16.dp,
            ),
        ) {
            itemsIndexed(
                items = filters,
                key = { _, listItem ->
                    listItem.data.headingTextStringResourceId.hashCode()
                },
            ) { index, listItem ->
                TransactionFilterBottomSheetFilterGroup(
                    isExpanded = expandedItemsIndices[index],
                    headingTextStringResourceId = listItem.data.headingTextStringResourceId,
                    items = listItem.data.items,
                    selectedItemsIndices = listItem.data.selectedItemsIndices.toImmutableList(),
                    onClearButtonClick = {
                        listItem.handleEvent(
                            TransactionFilterBottomSheetFilterGroupEvent.OnClearButtonClick
                        )
                    },
                    onExpandButtonClick = {
                        expandedItemsIndices[index] =
                            !expandedItemsIndices[index]
                    },
                    onItemClick = { clickedItemIndex ->
                        listItem.data.selectedItemsIndices.addIfDoesNotContainItemElseRemove(
                            item = clickedItemIndex,
                        )
                    },
                )
            }
            item {
                TransactionFilterBottomSheetDateFilter(
                    isExpanded = expandedItemsIndices[filters.lastIndex + 1],
                    headingTextStringResourceId = R.string.finance_manager_bottom_sheet_transactions_filter_transaction_date,
                    onClearButtonClick = {
                        fromDate = defaultMinDate
                        toDate = defaultMaxDate
                    },
                    onExpandButtonClick = {
                        expandedItemsIndices[filters.lastIndex + 1] =
                            !expandedItemsIndices[filters.lastIndex + 1]
                    },
                    minDate = defaultMinDate,
                    maxDate = defaultMaxDate,
                    fromDate = fromDate,
                    toDate = toDate,
                    updateFromDate = {
                        fromDate = it
                    },
                ) {
                    toDate = it
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(
                modifier = Modifier
                    .padding(
                        all = 16.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
                onClick = {
                    selectedExpenseCategoryIndices.clear()
                    selectedIncomeCategoryIndices.clear()
                    selectedInvestmentCategoryIndices.clear()
                    selectedAccountIndicesValue.clear()
                    selectedTransactionForValuesIndicesValue.clear()
                    selectedTransactionTypeIndicesValue.clear()
                    fromDate = defaultMinDate
                    toDate = defaultMaxDate
                    onNegativeButtonClick()
                },
            ) {
                MyText(
                    textStringResourceId = R.string.finance_manager_bottom_sheet_transactions_filter_reset,
                    style = FinanceManagerAppTheme.typography.labelLarge,
                )
            }
            Button(
                modifier = Modifier
                    .padding(
                        all = 16.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
                onClick = {
                    val isFromDateSameAsOldestTransactionDate =
                        fromDate == defaultMinDate
                    val isToDateSameAsCurrentDayDate = toDate == defaultMaxDate
                    val isDateFilterCleared =
                        isFromDateSameAsOldestTransactionDate &&
                                isToDateSameAsCurrentDayDate
                    val selectedExpenseCategoryIds =
                        selectedExpenseCategoryIndices.map { selectedExpenseCategoryIndex ->
                            expenseCategories[selectedExpenseCategoryIndex].id
                        }.toImmutableList()
                    val selectedIncomeCategoryIds =
                        selectedIncomeCategoryIndices.map { selectedIncomeCategoryIndex ->
                            incomeCategories[selectedIncomeCategoryIndex].id
                        }.toImmutableList()
                    val selectedInvestmentCategoryIds =
                        selectedInvestmentCategoryIndices.map { selectedInvestmentCategoryIndex ->
                            investmentCategories[selectedInvestmentCategoryIndex].id
                        }.toImmutableList()
                    val selectedAccountsIds =
                        selectedAccountIndicesValue.map { selectedAccountIndex ->
                            accounts[selectedAccountIndex].id
                        }.toImmutableList()
                    val selectedTransactionForValuesIds =
                        selectedTransactionForValuesIndicesValue.map { selectedTransactionForValuesIndex ->
                            transactionForValues[selectedTransactionForValuesIndex].id
                        }.toImmutableList()
                    val selectedTransactionTypes =
                        selectedTransactionTypeIndicesValue.map { selectedTransactionTypeIndex ->
                            transactionTypes[selectedTransactionTypeIndex]
                        }.toImmutableList()
                    onPositiveButtonClick(
                        TransactionFilter(
                            selectedExpenseCategoryIds = selectedExpenseCategoryIds,
                            selectedIncomeCategoryIds = selectedIncomeCategoryIds,
                            selectedInvestmentCategoryIds = selectedInvestmentCategoryIds,
                            selectedAccountIds = selectedAccountsIds,
                            selectedTransactionForIds = selectedTransactionForValuesIds,
                            selectedTransactionTypes = selectedTransactionTypes,
                            fromTimestamp = getTimestamp(
                                localDate = fromDate,
                            ),
                            toTimestamp = if (isDateFilterCleared) {
                                null
                            } else {
                                getTimestamp(
                                    localDate = toDate,
                                )
                            },
                        )
                    )
                },
            ) {
                MyText(
                    textStringResourceId = R.string.finance_manager_bottom_sheet_transactions_filter_apply,
                    style = FinanceManagerAppTheme.typography.labelLarge,
                )
            }
        }
        NavigationBarsAndImeSpacer()
    }
}

@Composable
internal fun TransactionFilterBottomSheetDateFilter(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    @StringRes headingTextStringResourceId: Int,
    onClearButtonClick: () -> Unit,
    onExpandButtonClick: () -> Unit,
    minDate: MyLocalDate,
    maxDate: MyLocalDate,
    fromDate: MyLocalDate,
    toDate: MyLocalDate,
    updateFromDate: (updatedFromDate: MyLocalDate) -> Unit,
    updateToDate: (updatedToDate: MyLocalDate) -> Unit,
) {
    val chevronDegrees: Float by animateFloatAsState(
        targetValue = if (isExpanded) {
            90F
        } else {
            0F
        },
        label = "chevron_degrees",
    )
    var isFromDatePickerDialogVisible by remember {
        mutableStateOf(false)
    }
    var isToDatePickerDialogVisible by remember {
        mutableStateOf(false)
    }

    MyDatePicker(
        data = MyDatePickerData(
            isVisible = isFromDatePickerDialogVisible,
            endLocalDate = toDate,
            selectedLocalDate = fromDate,
            startLocalDate = minDate,
        ),
        handleEvent = { event ->
            when (event) {
                is MyDatePickerEvent.OnNegativeButtonClick -> {
                    isFromDatePickerDialogVisible = false
                }

                is MyDatePickerEvent.OnPositiveButtonClick -> {
                    updateFromDate(event.selectedDate)
                    isFromDatePickerDialogVisible = false
                }
            }
        },
    )
    MyDatePicker(
        data = MyDatePickerData(
            isVisible = isToDatePickerDialogVisible,
            endLocalDate = maxDate,
            selectedLocalDate = toDate,
            startLocalDate = fromDate,
        ),
        handleEvent = { event ->
            when (event) {
                is MyDatePickerEvent.OnNegativeButtonClick -> {
                    isToDatePickerDialogVisible = false
                }

                is MyDatePickerEvent.OnPositiveButtonClick -> {
                    updateToDate(event.selectedDate)
                    isToDatePickerDialogVisible = false
                }
            }
        },
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(
                        shape = CircleShape,
                    )
                    .clickable {
                        onExpandButtonClick()
                    }
                    .weight(
                        weight = 1F,
                    ),
            ) {
                MyIconButton(
                    modifier = Modifier
                        .graphicsLayer {
                            rotationZ = chevronDegrees
                        },
                    tint = FinanceManagerAppTheme.colorScheme.onBackground,
                    imageVector = MyIcons.ChevronRight,
                    contentDescriptionStringResourceId = if (isExpanded) {
                        R.string.finance_manager_bottom_sheet_transactions_filter_collapse_group
                    } else {
                        R.string.finance_manager_bottom_sheet_transactions_filter_expand_group
                    },
                    onClick = onExpandButtonClick,
                )
                MyText(
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        ),
                    textStringResourceId = headingTextStringResourceId,
                    style = FinanceManagerAppTheme.typography.headlineLarge
                        .copy(
                            color = FinanceManagerAppTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Start,
                        ),
                )
            }
            MyTextButton(
                onClick = onClearButtonClick,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 16.dp,
                    ),
            ) {
                MyText(
                    textStringResourceId = R.string.finance_manager_bottom_sheet_transactions_filter_clear,
                    style = FinanceManagerAppTheme.typography.labelLarge,
                )
            }
        }
        if (isExpanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
            ) {
                MyReadOnlyTextField(
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        )
                        .padding(
                            horizontal = 8.dp,
                        ),
                    data = MyReadOnlyTextFieldData(
                        value = fromDate.formattedDate(),
                        labelTextStringResourceId = R.string.finance_manager_bottom_sheet_transactions_filter_from_date,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyReadOnlyTextFieldEvent.OnClick -> {
                                isFromDatePickerDialogVisible = true
                            }
                        }
                    },
                )
                MyReadOnlyTextField(
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        )
                        .padding(
                            horizontal = 8.dp,
                        ),
                    data = MyReadOnlyTextFieldData(
                        value = toDate.formattedDate(),
                        labelTextStringResourceId = R.string.finance_manager_bottom_sheet_transactions_filter_to_date,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyReadOnlyTextFieldEvent.OnClick -> {
                                isToDatePickerDialogVisible = true
                            }
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun TransactionFilterBottomSheetFilterGroup(
    isExpanded: Boolean,
    @StringRes headingTextStringResourceId: Int,
    items: ImmutableList<ChipUIData>,
    selectedItemsIndices: ImmutableList<Int>,
    onClearButtonClick: () -> Unit,
    onExpandButtonClick: () -> Unit,
    onItemClick: (index: Int) -> Unit,
) {
    val chevronDegrees: Float by animateFloatAsState(
        targetValue = if (isExpanded) {
            90F
        } else {
            0F
        },
        label = "chevron_degrees",
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(
                        shape = CircleShape,
                    )
                    .clickable {
                        onExpandButtonClick()
                    }
                    .weight(
                        weight = 1F,
                    ),
            ) {
                MyIconButton(
                    imageVector = MyIcons.ChevronRight,
                    tint = FinanceManagerAppTheme.colorScheme.onBackground,
                    contentDescriptionStringResourceId = if (isExpanded) {
                        R.string.finance_manager_bottom_sheet_transactions_filter_collapse_group
                    } else {
                        R.string.finance_manager_bottom_sheet_transactions_filter_expand_group
                    },
                    modifier = Modifier
                        .graphicsLayer {
                            rotationZ = chevronDegrees
                        },
                    onClick = onExpandButtonClick,
                )
                MyText(
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        ),
                    textStringResourceId = headingTextStringResourceId,
                    style = FinanceManagerAppTheme.typography.headlineLarge
                        .copy(
                            color = FinanceManagerAppTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Start,
                        ),
                )
            }
            MyTextButton(
                onClick = onClearButtonClick,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 16.dp,
                    ),
            ) {
                MyText(
                    textStringResourceId = R.string.finance_manager_bottom_sheet_transactions_filter_clear,
                    style = FinanceManagerAppTheme.typography.labelLarge,
                )
            }
        }
        if (isExpanded) {
            MySelectionGroup(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
                data = MySelectionGroupData(
                    items = items,
                    selectedItemsIndices = selectedItemsIndices,
                ),
                handleEvent = { event ->
                    when (event) {
                        is MySelectionGroupEvent.OnSelectionChange -> {
                            onItemClick(event.index)
                        }
                    }
                },
            )
        }
    }
}
