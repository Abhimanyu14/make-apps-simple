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

@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.add_transaction_for.view_model

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.FinanceManagerNavigationDirections
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.add_transaction_for.state.AddTransactionForScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class AddTransactionForScreenViewModelTest {
    // region test setup
    private lateinit var testDependencies: TestDependencies
    private lateinit var addTransactionForScreenViewModel: AddTransactionForScreenViewModel

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        addTransactionForScreenViewModel = AddTransactionForScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            screenUIStateDelegate = testDependencies.screenUIStateDelegate,
            addTransactionForScreenDataValidationUseCase = testDependencies.addTransactionForScreenDataValidationUseCase,
            coroutineScope = testDependencies.testScope.backgroundScope,
            insertTransactionForUseCase = testDependencies.insertTransactionForUseCase,
            logKit = testDependencies.logKit,
        )
        addTransactionForScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        addTransactionForScreenViewModel.uiState.test {
            val result = awaitItem()

            assertThat(result.title.text).isEmpty()
            assertThat(result.titleError).isEqualTo(
                AddTransactionForScreenTitleError.None
            )
            assertThat(result.isCtaButtonEnabled).isFalse()
            assertThat(result.isLoading).isTrue()
        }
    }
    // endregion

    // region state events
    @Test
    fun updateTitle_shouldUpdateValue() = testDependencies.runTestWithTimeout {
        val updatedTitle = "Test Title"
        val updatedValue = TextFieldValue(
            text = updatedTitle,
        )
        addTransactionForScreenViewModel.uiState.test {
            assertThat(awaitItem().title.text).isEmpty()

            addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                updatedValue
            )

            assertThat(awaitItem().title.text).isEqualTo(updatedTitle)
        }
    }

    @Test
    fun clearTitle_shouldClearText() = testDependencies.runTestWithTimeout {
        val updatedTitle = "Test Title"
        val updatedValue = TextFieldValue(
            text = updatedTitle,
        )
        addTransactionForScreenViewModel.uiState.test {
            assertThat(awaitItem().title.text).isEmpty()
            addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                updatedValue
            )
            assertThat(awaitItem().title.text).isEqualTo(updatedTitle)

            addTransactionForScreenViewModel.uiStateEvents.clearTitle()

            assertThat(awaitItem().title.text).isEmpty()
        }
    }
    // endregion

    // region updateUiStateAndStateEvents
    @Test
    fun updateUiStateAndStateEvents_titleIsBlank_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = "   "
            val updatedValue = TextFieldValue(
                text = updatedTitle,
            )
            addTransactionForScreenViewModel.uiState.test {
                assertThat(awaitItem().isCtaButtonEnabled).isFalse()

                addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedValue
                )

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isFalse()
                assertThat(result.titleError).isEqualTo(
                    AddTransactionForScreenTitleError.None,
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_titleAlreadyExists_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = "TestTitle"
            val updatedValue1 = TextFieldValue(
                text = updatedTitle,
            )
            val updatedValue2 = TextFieldValue(
                text = updatedTitle,
            )
            addTransactionForScreenViewModel.uiState.test {
                assertThat(awaitItem().isCtaButtonEnabled).isFalse()
                addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedValue1
                )
                assertThat(awaitItem().title.text).isEqualTo(updatedTitle)
                addTransactionForScreenViewModel.uiStateEvents.insertTransactionFor()
                    .join()

                addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedValue2
                )

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isFalse()
                assertThat(result.titleError).isEqualTo(
                    AddTransactionForScreenTitleError.TransactionForExists,
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_titleIsValid_ctaIsEnabled() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = "test-title"
            val updatedValue = TextFieldValue(
                text = updatedTitle,
            )
            addTransactionForScreenViewModel.uiState.test {
                assertThat(awaitItem().isCtaButtonEnabled).isFalse()

                addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedValue
                )

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isTrue()
                assertThat(result.titleError).isEqualTo(
                    AddTransactionForScreenTitleError.None,
                )
            }
        }
    // endregion

    // region insertTransactionFor
    @Test
    fun insertTransactionFor_shouldInsertAndNavigateUp() =
        testDependencies.runTestWithTimeout {
            turbineScope {
                val navigationCommandTurbine =
                    testDependencies.navigationKit.command.testIn(
                        scope = backgroundScope,
                    )
                val uiStateTurbine =
                    addTransactionForScreenViewModel.uiState.testIn(
                        scope = backgroundScope,
                    )
                val lastChangeTimestamp =
                    testDependencies.financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                        ?: -1L

                val testTitle = TextFieldValue(
                    text = "test-transaction-for",
                )
                val initialState = uiStateTurbine.awaitItem()
                assertThat(initialState.isLoading).isTrue()
                val postDataFetchCompletion = uiStateTurbine.awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    testTitle
                )
                assertThat(uiStateTurbine.awaitItem().isLoading).isFalse()

                addTransactionForScreenViewModel.uiStateEvents.insertTransactionFor()

                assertThat(uiStateTurbine.awaitItem().isLoading).isTrue()
                assertThat(
                    testDependencies.fakeTransactionForDao.getAllTransactionForValues()
                        .find {
                            it.id == 1
                        }
                        ?.title
                ).isEqualTo(testTitle.text)
                assertThat(
                    testDependencies.financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                        ?: -1L
                ).isGreaterThan(lastChangeTimestamp)
                assertThat(navigationCommandTurbine.awaitItem()).isEqualTo(
                    FinanceManagerNavigationDirections.NavigateUp
                )
            }
        }
    // endregion
}
