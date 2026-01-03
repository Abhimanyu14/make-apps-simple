/*
 * Copyright 2025-2026 Abhimanyu
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

package com.makeappssimple.abhimanyu.barcodes.android.common.ui.feature.home.home.screen

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.home.home.event.HomeScreenUIEvent
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.home.home.snackbar.HomeScreenSnackbarType
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.home.home.state.HomeScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.model.BarcodeUiModel
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.constants.TestTags.SCREEN_CONTENT_HOME
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.constants.TestTags.SCREEN_HOME
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.feature.home.home.bottom_sheet.HomeCosmosBottomSheetType
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.feature.home.home.bottom_sheet.HomeMenuBottomSheet
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.feature.home.home.dialog.HomeDeleteBarcodeDialog
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.icons.BarcodesIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.bottom_sheet.CosmosBottomSheetHandler
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosFloatingActionButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.divider.CosmosHorizontalDivider
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosListItemData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosListItemDataEvent
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosListItemDataEventDataAndEventHandler
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosSwipeableList
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.scaffold.CosmosScaffold
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.spacer.CosmosVerticalSpacer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.spacer.cosmosNavigationBarsSpacer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.swipe_to_dismiss.CosmosSwipeToDismissState
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.swipe_to_dismiss.CosmosSwipeToDismissValue
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.top_app_bar.CosmosTopAppBar
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.top_app_bar.CosmosTopAppBarActionButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.text
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.library.barcodes.android.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreenUI(
    uiState: HomeScreenUIState = HomeScreenUIState(),
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: HomeScreenUIEvent) -> Unit = {},
) {
    val barcodeDeleteFailedSnackbarMessage = CosmosStringResource.Id(
        id = R.string.barcodes_screen_home_barcode_deleted_failed_snackbar_message,
    ).text
    val barcodeDeletedSnackbarActionLabel = CosmosStringResource.Id(
        id = R.string.barcodes_screen_home_barcode_deleted_snackbar_action_label,
    ).text
    val barcodeRestoreFailedSnackbarMessage = CosmosStringResource.Id(
        id = R.string.barcodes_screen_home_barcode_restore_failed_snackbar_message,
    ).text
    // TODO(Abhi): Move to view model
    val selectedBarcodes = rememberSaveable(
        saver = listSaver(
            save = {
                it.toList()
            },
            restore = {
                it.toMutableStateList()
            },
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

            CosmosListItemDataEventDataAndEventHandler(
                data = CosmosListItemData(
                    stringResource = CosmosStringResource.Text(
                        text = if (barcode.name.isNullOrBlank()) {
                            uiState.barcodeFormattedTimestamps[index]
                        } else {
                            barcode.name
                        },
                    ),
                    iconResource = BarcodesIcons.Barcode2,
                    isSelectionMode = selectedBarcodes.isNotEmpty(),
                    isSelected = selectedBarcodes.contains(index),
                ),
                handleEvent = { event ->
                    when (event) {
                        CosmosListItemDataEvent.OnClick -> {
                            handleUIEvent(
                                HomeScreenUIEvent.OnListItem.Click(
                                    barcode = barcode,
                                ),
                            )
                        }

                        CosmosListItemDataEvent.OnLongClick -> {
                            toggleIsSelected()
                        }

                        CosmosListItemDataEvent.OnToggleSelection -> {
                            toggleIsSelected()
                        }
                    }
                },
            )
        }.toPersistentList()
    val showBarcodeDeletedSnackbar: (BarcodeUiModel) -> Unit =
        { barcodeUiModel ->
            state.coroutineScope.launch {
                val snackbarResult = state.snackbarHostState
                    .showSnackbar(
                        message = state.context.getString(
                            R.string.barcodes_screen_home_barcode_deleted_snackbar_message,
                            barcodeUiModel.name,
                        ),
                        actionLabel = barcodeDeletedSnackbarActionLabel,
                    )
                when (snackbarResult) {
                    SnackbarResult.ActionPerformed -> {
                        handleUIEvent(
                            HomeScreenUIEvent.OnBarcodeDeletedSnackbar.ActionButtonClick(
                                barcode = barcodeUiModel,
                            )
                        )
                    }

                    SnackbarResult.Dismissed -> {
                    }
                }
            }
        }
    val isInSelectionMode = selectedBarcodes.isNotEmpty()

    LaunchedEffect(
        key1 = uiState.screenSnackbarType,
    ) {
        when (uiState.screenSnackbarType) {
            HomeScreenSnackbarType.None -> {}

            HomeScreenSnackbarType.DeleteBarcodeFailed -> {
                state.snackbarHostState.showSnackbar(
                    message = barcodeDeleteFailedSnackbarMessage,
                )
                handleUIEvent(HomeScreenUIEvent.OnSnackbarDismissed)
            }

            HomeScreenSnackbarType.RestoreBarcodeFailed -> {
                state.snackbarHostState.showSnackbar(
                    message = barcodeRestoreFailedSnackbarMessage,
                )
                handleUIEvent(HomeScreenUIEvent.OnSnackbarDismissed)
            }
        }
    }

    CosmosBottomSheetHandler(
        isBottomSheetVisible = uiState.isModalBottomSheetVisible,
        cosmosBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
        keyboardController = state.keyboardController,
    )

    CosmosScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_HOME,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                HomeCosmosBottomSheetType.Menu -> {
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

                HomeCosmosBottomSheetType.None -> {
                    CosmosVerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        snackbarHostState = state.snackbarHostState,
        topBar = {
            if (isInSelectionMode) {
                CosmosTopAppBar(
                    titleStringResource = CosmosStringResource.Text(
                        text = state.context.getString(
                            R.string.barcodes_screen_home_selection_mode_top_app_bar_title,
                            selectedBarcodes.size,
                        ),
                    ),
                    navigationIconResource = CosmosIcons.Close,
                    navigationLabelStringResource = CosmosStringResource.Text(
                        text = "Close Selection Mode",
                    ),
                    navigationAction = {
                        selectedBarcodes.clear()
                    },
                    appBarActions = {
                        CosmosTopAppBarActionButton(
                            iconResource = CosmosIcons.Delete,
                            onClickLabelStringResource = CosmosStringResource.Id(
                                id = R.string.barcodes_screen_home_content_description_delete_barcode,
                            ),
                            iconContentDescriptionStringResource = CosmosStringResource.Id(
                                id = R.string.barcodes_screen_home_content_description_delete_barcode,
                            ),
                            onClick = {
                                handleUIEvent(HomeScreenUIEvent.OnTopAppBar.DeleteBarcodeButtonClick)
                            },
                        )
                    },
                )
            } else {
                CosmosTopAppBar(
                    titleStringResource = CosmosStringResource.Id(
                        id = R.string.barcodes_screen_home,
                    ),
                    appBarActions = {
                        CosmosTopAppBarActionButton(
                            iconResource = CosmosIcons.Settings,
                            onClickLabelStringResource = CosmosStringResource.Id(
                                id = R.string.barcodes_screen_home_on_click_label_settings,
                            ),
                            iconContentDescriptionStringResource = CosmosStringResource.Id(
                                id = R.string.barcodes_screen_home_content_description_settings,
                            ),
                            onClick = {
                                handleUIEvent(HomeScreenUIEvent.OnTopAppBar.SettingsButtonClick)
                            },
                        )
                    },
                )
            }
        },
        floatingActionButton = {
            CosmosFloatingActionButton(
                modifier = Modifier
                    .cosmosNavigationBarsSpacer(),
                iconResource = CosmosIcons.Add,
                contentDescriptionStringResource = CosmosStringResource.Id(
                    id = R.string.barcodes_screen_home_content_description_add,
                ),
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
            actionOnSwipeToEnd = {
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
    listItemsDataAndEventHandler: ImmutableList<CosmosListItemDataEventDataAndEventHandler>,
    actionOnSwipeToEnd: (Int) -> Unit,
) {
    CosmosSwipeableList(
        listItemsDataAndEventHandler = listItemsDataAndEventHandler,
        contentPadding = PaddingValues(
            bottom = 80.dp,
        ),
        actionOnSwipeToEnd = actionOnSwipeToEnd,
        backgroundContent = { dismissState: CosmosSwipeToDismissState ->
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    CosmosSwipeToDismissValue.Default -> CosmosAppTheme.colorScheme.surfaceVariant
                    CosmosSwipeToDismissValue.DismissedToEnd -> CosmosAppTheme.colorScheme.error
                    CosmosSwipeToDismissValue.DismissedToStart -> CosmosAppTheme.colorScheme.background
                },
                label = "swipe_to_dismiss_background_color",
            )
            val scale by animateFloatAsState(
                targetValue = if (dismissState.targetValue == CosmosSwipeToDismissValue.Default) {
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
                CosmosIcon(
                    iconResource = CosmosIcons.DeleteForever,
                    contentDescriptionStringResource = CosmosStringResource.Id(
                        id = R.string.barcodes_screen_home_content_description_delete,
                    ),
                    tint = CosmosAppTheme.colorScheme.onError,
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
                CosmosHorizontalDivider()
            }
        },
        modifier = Modifier
            .testTag(
                tag = SCREEN_CONTENT_HOME,
            )
            .fillMaxSize(),
    )
}
