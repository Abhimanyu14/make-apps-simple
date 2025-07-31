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

package com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.barcodes.android.core.common.constants.TestTags.SCREEN_CONTENT_HOME
import com.makeappssimple.abhimanyu.barcodes.android.core.common.constants.TestTags.SCREEN_HOME
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.button.MyFloatingActionButton
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.divider.MyHorizontalDivider
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.icon.MyIcon
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.list.MyListItemData
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.list.MyListItemDataEvent
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.list.MyListItemDataEventDataAndEventHandler
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.list.MySwipeableList
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.spacer.VerticalSpacer
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.spacer.navigationBarsSpacer
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.swipetodismiss.MySwipeToDismissState
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.swipetodismiss.MySwipeToDismissValue
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.topappbar.MyTopAppBar
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.topappbar.MyTopAppBarActionButton
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.theme.MyAppTheme
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.bottomsheet.HomeMenuBottomSheet
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.bottomsheet.HomeScreenBottomSheetType
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.dialog.HomeDeleteBarcodeDialog
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.event.HomeScreenUIEvent
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.state.HomeScreenUIState
import com.makeappssimple.abhimanyu.library.barcodes.android.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
internal fun HomeScreenUI(
    uiState: HomeScreenUIState = HomeScreenUIState(),
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: HomeScreenUIEvent) -> Unit = {},
) {
    val barcodeDeletedSnackbarActionLabel = stringResource(
        id = R.string.screen_home_barcode_deleted_snackbar_action_label,
    )
    // TODO(Abhi): Move to view model
    val selectedBarcodes = rememberSaveable(
        saver = listSaver(
            save = {
                it.toList()
            },
            restore = {
                it.toMutableStateList()
            }
        ),
    ) {
        mutableStateListOf<Int>()
    }
    val listItemsDataAndEventHandler =
        // TODO(Abhi): Remove this mapIndexed
        uiState.allBarcodes.mapIndexed { index, barcode ->
            val toggleIsSelected = {
                if (!selectedBarcodes.remove(index)) {
                    selectedBarcodes.add(index)
                }
            }

            MyListItemDataEventDataAndEventHandler(
                data = MyListItemData(
                    text = if (barcode.name.isNullOrBlank()) {
                        uiState.barcodeFormattedTimestamps[index]
                    } else {
                        barcode.name
                    },
                    painter = painterResource(
                        id = R.drawable.icon_barcode2_black_24dp,
                    ),
                    isSelectionMode = selectedBarcodes.isNotEmpty(),
                    isSelected = selectedBarcodes.contains(index),
                ),
                handleEvent = { event ->
                    when (event) {
                        MyListItemDataEvent.OnClick -> {
                            handleUIEvent(
                                HomeScreenUIEvent.OnListItem.Click(
                                    barcode = barcode,
                                ),
                            )
                        }

                        MyListItemDataEvent.OnLongClick -> {
                            toggleIsSelected()
                        }

                        MyListItemDataEvent.OnToggleSelection -> {
                            toggleIsSelected()
                        }
                    }
                },
            )
        }
    val showBarcodeDeletedSnackbar: (Barcode) -> Unit = { barcode ->
        state.coroutineScope.launch {
            val snackbarResult = state.snackbarHostState
                .showSnackbar(
                    message = state.context.getString(
                        R.string.screen_home_barcode_deleted_snackbar_message,
                        barcode.name,
                    ),
                    actionLabel = barcodeDeletedSnackbarActionLabel,
                )
            when (snackbarResult) {
                SnackbarResult.ActionPerformed -> {
                    handleUIEvent(
                        HomeScreenUIEvent.OnBarcodeDeletedSnackbar.ActionButtonClick(
                            barcode = barcode,
                        )
                    )
                }

                SnackbarResult.Dismissed -> {
                }
            }
        }
    }
    val isInSelectionMode = selectedBarcodes.isNotEmpty()

    BottomSheetHandler(
        isBottomSheetVisible = uiState.isModalBottomSheetVisible,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
        keyboardController = state.keyboardController,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_HOME,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                HomeScreenBottomSheetType.Menu -> {
                    HomeMenuBottomSheet(
                        handleEvent = { event ->
                            handleUIEvent(
                                HomeScreenUIEvent.OnHomeMenuBottomSheetEvent(
                                    event = event,
                                )
                            )
                        },
                    )
                }

                HomeScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        snackbarHostState = state.snackbarHostState,
        topBar = {
            if (isInSelectionMode) {
                MyTopAppBar(
                    titleText = state.context.getString(
                        R.string.screen_home_selection_mode_top_app_bar_title,
                        selectedBarcodes.size
                    ),
                    navigationIcon = MyIcons.Close,
                    navigationLabel = "Close Selection Mode",
                    navigationAction = {
                        selectedBarcodes.clear()
                    },
                    appBarActions = {
                        MyTopAppBarActionButton(
                            iconImageVector = MyIcons.Delete,
                            onClickLabelStringResourceId = R.string.screen_home_content_description_delete_barcode,
                            iconContentDescriptionStringResourceId = R.string.screen_home_content_description_delete_barcode,
                            onClick = {
                                handleUIEvent(HomeScreenUIEvent.OnTopAppBar.DeleteBarcodeButtonClick)
                            },
                        )
                    },
                )
            } else {
                MyTopAppBar(
                    titleStringResourceId = R.string.screen_home,
                    appBarActions = {
                        MyTopAppBarActionButton(
                            iconImageVector = MyIcons.Settings,
                            onClickLabelStringResourceId = R.string.screen_home_on_click_label_settings,
                            iconContentDescriptionStringResourceId = R.string.screen_home_content_description_settings,
                            onClick = {
                                handleUIEvent(HomeScreenUIEvent.OnTopAppBar.SettingsButtonClick)
                            },
                        )
                    },
                )
            }
        },
        floatingActionButton = {
            MyFloatingActionButton(
                modifier = Modifier
                    .navigationBarsSpacer(),
                iconImageVector = MyIcons.Add,
                contentDescriptionStringResourceId = R.string.screen_home_content_description_add,
                onClick = {
                    handleUIEvent(HomeScreenUIEvent.OnAddFloatingActionButtonClick)
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        isModalBottomSheetVisible = uiState.isModalBottomSheetVisible,
        isBackHandlerEnabled = uiState.isBackHandlerEnabled,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(HomeScreenUIEvent.OnBottomSheetDismiss)
        },
    ) {
        AnimatedVisibility(
            visible = uiState.isDeleteBarcodeDialogVisible,
        ) {
            HomeDeleteBarcodeDialog(
                selectedBarcodesSize = selectedBarcodes.size,
                onConfirmButtonClick = {
                    handleUIEvent(
                        HomeScreenUIEvent.OnHomeDeleteBarcodeDialog.ConfirmButtonClick(
                            barcodes = uiState.allBarcodes.filterIndexed { index, _ ->
                                selectedBarcodes.contains(index)
                            },
                        ),
                    )
                    selectedBarcodes.clear()
                },
                onDismiss = {
                    handleUIEvent(HomeScreenUIEvent.OnHomeDeleteBarcodeDialog.Dismiss)
                },
                onDismissButtonClick = {
                    handleUIEvent(HomeScreenUIEvent.OnHomeDeleteBarcodeDialog.DismissButtonClick)
                },
            )
        }

        // TODO(Abhi): Empty screen UI
        HomeScreenList(
            listItemsDataAndEventHandler = listItemsDataAndEventHandler,
            onSwipeToEnd = {
                handleUIEvent(
                    HomeScreenUIEvent.OnListItem.SwipeToEnd(
                        barcodes = listOf(uiState.allBarcodes[it]),
                    ),
                )
                showBarcodeDeletedSnackbar(uiState.allBarcodes[it])
            },
        )
    }
}

@Composable
private fun HomeScreenList(
    listItemsDataAndEventHandler: List<MyListItemDataEventDataAndEventHandler>,
    onSwipeToEnd: (Int) -> Unit,
) {
    MySwipeableList(
        listItemsDataAndEventHandler = listItemsDataAndEventHandler,
        contentPadding = PaddingValues(
            bottom = 80.dp,
        ),
        actionOnSwipeToEnd = onSwipeToEnd,
        backgroundContent = { dismissState: MySwipeToDismissState ->
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    MySwipeToDismissValue.Default -> MyAppTheme.colorScheme.surfaceVariant
                    MySwipeToDismissValue.DismissedToEnd -> MyAppTheme.colorScheme.error
                    MySwipeToDismissValue.DismissedToStart -> MyAppTheme.colorScheme.background
                },
                label = "swipe_to_dismiss_background_color",
            )
            val scale by animateFloatAsState(
                if (dismissState.targetValue == MySwipeToDismissValue.Default) {
                    1F
                } else {
                    1.25F
                },
                label = "swipe_to_dismiss_background_icon",
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = color,
                    ),
            ) {
                MyIcon(
                    imageVector = MyIcons.DeleteForever,
                    contentDescriptionStringResourceId = R.string.screen_home_content_description_delete,
                    tint = MyAppTheme.colorScheme.onError,
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        )
                        .scale(
                            scale = scale,
                        )
                        .padding(
                            start = 16.dp,
                        ),
                )
                MyHorizontalDivider()
            }
        },
        modifier = Modifier
            .testTag(
                tag = SCREEN_CONTENT_HOME,
            )
            .fillMaxSize(),
    )
}
