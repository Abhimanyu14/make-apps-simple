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

@file:OptIn(ExperimentalMaterial3Api::class)

package com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.common.core.extensions.orFalse
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.constants.TestTags.SCREEN_CONTENT_SETTINGS
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.constants.TestTags.SCREEN_SETTINGS
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.MyLinearProgressIndicator
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.settings.SettingsListItemAppVersion
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.settings.SettingsListItemAppVersionData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.settings.SettingsListItemContent
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.settings.SettingsListItemContentData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.settings.SettingsListItemContentEvent
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.settings.SettingsListItemDivider
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.settings.SettingsListItemDividerData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.settings.SettingsListItemHeader
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.settings.SettingsListItemHeaderData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.components.SettingsScreenListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.event.SettingsScreenUIEvent
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.snackbar.SettingsScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.state.SettingsScreenUIState
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@Composable
internal fun SettingsScreenUI(
    uiState: SettingsScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: SettingsScreenUIEvent) -> Unit = {},
) {
    val restoreDataFailedSnackbarText = stringResource(
        id = R.string.finance_manager_screen_settings_restore_data_failed,
    )
    val cancelReminderFailedSnackbarText = stringResource(
        id = R.string.finance_manager_screen_settings_cancel_reminder_failed,
    )
    val cancelReminderSuccessfulSnackbarText = stringResource(
        id = R.string.finance_manager_screen_settings_cancel_reminder_successful,
    )

    val settingsScreenListItemData: ImmutableList<SettingsScreenListItemData> =
        getSettingsListItemData(
            uiState = uiState,
            handleUIEvent = handleUIEvent,
        )

    LaunchedEffect(
        key1 = uiState.screenSnackbarType,
        key2 = handleUIEvent,
    ) {
        when (uiState.screenSnackbarType) {
            SettingsScreenSnackbarType.None -> {}

            SettingsScreenSnackbarType.RestoreDataFailed -> {
                launch {
                    val snackbarResult = state.snackbarHostState.showSnackbar(
                        message = restoreDataFailedSnackbarText,
                    )
                    when (snackbarResult) {
                        SnackbarResult.ActionPerformed -> {}
                        SnackbarResult.Dismissed -> {
                            handleUIEvent(SettingsScreenUIEvent.OnSnackbarDismissed)
                        }
                    }
                }
            }

            SettingsScreenSnackbarType.CancelReminderFailed -> {
                launch {
                    val snackbarResult = state.snackbarHostState.showSnackbar(
                        message = cancelReminderFailedSnackbarText,
                    )
                    when (snackbarResult) {
                        SnackbarResult.ActionPerformed -> {}
                        SnackbarResult.Dismissed -> {
                            handleUIEvent(SettingsScreenUIEvent.OnSnackbarDismissed)
                        }
                    }
                }
            }

            SettingsScreenSnackbarType.CancelReminderSuccessful -> {
                launch {
                    val snackbarResult = state.snackbarHostState.showSnackbar(
                        message = cancelReminderSuccessfulSnackbarText,
                    )
                    when (snackbarResult) {
                        SnackbarResult.ActionPerformed -> {}
                        SnackbarResult.Dismissed -> {
                            handleUIEvent(SettingsScreenUIEvent.OnSnackbarDismissed)
                        }
                    }
                }
            }
        }
    }

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_SETTINGS,
            )
            .fillMaxSize(),
        sheetState = state.modalBottomSheetState,
        snackbarHostState = state.snackbarHostState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.finance_manager_screen_settings_appbar_title,
                navigationAction = {
                    handleUIEvent(SettingsScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(SettingsScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_SETTINGS,
                )
                .fillMaxWidth()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            SettingsScreenLoader(
                isLoading = uiState.isLoading,
            )
            SettingsScreenContent(
                settingsScreenListItemData = settingsScreenListItemData,
            )
            NavigationBarsAndImeSpacer()
        }
    }
}

@Composable
private fun SettingsScreenLoader(
    isLoading: Boolean,
) {
    AnimatedVisibility(
        visible = isLoading,
    ) {
        MyLinearProgressIndicator(
            modifier = Modifier
                .testTag(
                    tag = stringResource(
                        id = R.string.finance_manager_screen_settings_linear_progress_indicator_test_tag,
                    ),
                ),
        )
    }
}

@Composable
private fun SettingsScreenContent(
    settingsScreenListItemData: ImmutableList<SettingsScreenListItemData>,
) {
    settingsScreenListItemData.mapIndexed { index, listItemData ->
        when (listItemData.data) {
            is SettingsListItemAppVersionData -> {
                SettingsListItemAppVersion(
                    data = SettingsListItemAppVersionData(
                        appVersionText = listItemData.data.appVersionText,
                    ),
                )
            }

            is SettingsListItemDividerData -> {
                SettingsListItemDivider(
                    modifier = Modifier
                        .testTag(
                            tag = "Item $index",
                        ),
                )
            }

            is SettingsListItemContentData -> {
                SettingsListItemContent(
                    modifier = Modifier
                        .testTag(
                            tag = "Item $index",
                        ),
                    data = listItemData.data,
                    handleEvent = listItemData.handleEvent,
                )
            }

            is SettingsListItemHeaderData -> {
                SettingsListItemHeader(
                    modifier = Modifier
                        .testTag(
                            tag = "Item $index",
                        ),
                    data = listItemData.data,
                )
            }
        }
    }
}

@Composable
private fun getSettingsListItemData(
    uiState: SettingsScreenUIState,
    handleUIEvent: (uiEvent: SettingsScreenUIEvent) -> Unit,
): ImmutableList<SettingsScreenListItemData> {
    val context = LocalContext.current
    return persistentListOf(
        SettingsScreenListItemData(
            data = SettingsListItemHeaderData(
                textStringResourceId = R.string.finance_manager_screen_settings_data,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isEnabled = !uiState.isLoading,
                imageVector = MyIcons.Category,
                textStringResourceId = R.string.finance_manager_screen_settings_categories,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnCategoriesListItemClick)
                    }

                    else -> {
                        // No-op
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isEnabled = !uiState.isLoading,
                imageVector = MyIcons.AccountBalance,
                textStringResourceId = R.string.finance_manager_screen_settings_accounts,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnAccountsListItemClick)
                    }

                    else -> {
                        // No-op
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isEnabled = !uiState.isLoading,
                imageVector = MyIcons.Groups,
                textStringResourceId = R.string.finance_manager_screen_settings_transaction_for,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnTransactionForListItemClick)
                    }

                    else -> {
                        // No-op
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemDividerData(),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemHeaderData(
                textStringResourceId = R.string.finance_manager_screen_settings_backup_and_restore,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isEnabled = !uiState.isLoading,
                imageVector = MyIcons.Backup,
                textStringResourceId = R.string.finance_manager_screen_settings_backup,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnBackupDataListItemClick)
                    }

                    else -> {
                        // No-op
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isEnabled = !uiState.isLoading,
                imageVector = MyIcons.Restore,
                textStringResourceId = R.string.finance_manager_screen_settings_restore,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnRestoreDataListItemClick)
                    }

                    else -> {
                        // No-op
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isEnabled = !uiState.isLoading,
                imageVector = MyIcons.Calculate,
                textStringResourceId = R.string.finance_manager_screen_settings_recalculate_total,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnRecalculateTotalListItemClick)
                    }

                    else -> {
                        // No-op
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemDividerData(),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemHeaderData(
                textStringResourceId = R.string.finance_manager_screen_settings_notifications,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isChecked = uiState.isReminderEnabled.orFalse(),
                isEnabled = !uiState.isLoading,
                hasToggle = true,
                imageVector = MyIcons.Notifications,
                textStringResourceId = R.string.finance_manager_screen_settings_reminder,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnCheckedChange -> {
                        if (uiState.isReminderEnabled.orFalse()) {
                            handleUIEvent(SettingsScreenUIEvent.OnReminderEnabled)
                        } else {
                            handleUIEvent(SettingsScreenUIEvent.OnReminderEnabled)
                        }
                    }

                    is SettingsListItemContentEvent.OnClick -> {
                        if (uiState.isReminderEnabled.orFalse()) {
                            handleUIEvent(SettingsScreenUIEvent.OnReminderEnabled)
                        } else {
                            handleUIEvent(SettingsScreenUIEvent.OnReminderEnabled)
                        }
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemDividerData(),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemHeaderData(
                textStringResourceId = R.string.finance_manager_screen_settings_about,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isEnabled = !uiState.isLoading,
                imageVector = MyIcons.TextSnippet,
                textStringResourceId = R.string.finance_manager_screen_settings_credits,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnClick -> {
                        Toast.makeText(
                            context,
                            "Not Yet Implemented",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        // No-op
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isEnabled = !uiState.isLoading,
                imageVector = MyIcons.TextSnippet,
                textStringResourceId = R.string.finance_manager_screen_settings_open_source_licenses,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnOpenSourceLicensesListItemClick)
                    }

                    else -> {
                        // No-op
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemDividerData(),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemAppVersionData(
                appVersionText = stringResource(
                    id = R.string.finance_manager_screen_settings_app_version,
                    uiState.appVersion.orEmpty(),
                ),
            ),
        ),
    )
}
