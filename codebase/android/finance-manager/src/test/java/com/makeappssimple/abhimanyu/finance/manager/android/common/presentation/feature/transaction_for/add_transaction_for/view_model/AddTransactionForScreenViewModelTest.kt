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

@file:OptIn(ExperimentalCoroutinesApi::class)

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.add_transaction_for.view_model

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.navigation.FinanceManagerNavigationDirections
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.add_transaction_for.state.AddTransactionForScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class AddTransactionForScreenViewModelTest {
    // region test setup
    private lateinit var addTransactionForScreenViewModel: AddTransactionForScreenViewModel
    private lateinit var testDependencies: TestDependencies

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        addTransactionForScreenViewModel = AddTransactionForScreenViewModel(
            navigationKit = testDependencies.navigationKit,
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

            result.titleError.shouldBe(
                expected = AddTransactionForScreenTitleError.None,
            )
            result.isCtaButtonEnabled.shouldBeFalse()
            result.isLoading.shouldBeFalse()
            result.titleTextFieldState.text.toString().shouldBeEmpty()
        }
    }
    // endregion

    // region state events
    @Test
    fun clearTitle_shouldClearText() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = testDependencies.testTransactionForTitle1
            addTransactionForScreenViewModel.uiState.test {
                val fetchDataCompleted = awaitItem()
                fetchDataCompleted.isLoading.shouldBeFalse()
                addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedTitle
                )
                val result = awaitItem()
                result.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedTitle,
                    )

                addTransactionForScreenViewModel.uiStateEvents.clearTitle()
                result.titleTextFieldState.text.toString().shouldBeEmpty()
            }
        }

    @Test
    fun insertTransactionFor_shouldInsertAndNavigateUp() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = "test-title"
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
                val fetchDataCompleted = uiStateTurbine.awaitItem()
                fetchDataCompleted.isLoading.shouldBeFalse()
                addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedTitle
                )
                val textFieldStateUpdateCompleted = uiStateTurbine.awaitItem()
                textFieldStateUpdateCompleted.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedTitle,
                    )

                addTransactionForScreenViewModel.uiStateEvents.insertTransactionFor()

                uiStateTurbine.awaitItem().isLoading.shouldBeTrue()
                testDependencies.fakeTransactionForDao.getAllTransactionForValues()
                    .find {
                        it.id == 1
                    }
                    ?.title.shouldBe(
                        expected = updatedTitle,
                    )
                (testDependencies.financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                    ?: -1L).shouldBeGreaterThan(
                    other = lastChangeTimestamp,
                )
                navigationCommandTurbine.awaitItem().shouldBe(
                    expected = FinanceManagerNavigationDirections.NavigateUp,
                )
            }
        }

    @Test
    fun updateTitle_titleAlreadyExists_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = testDependencies.testTransactionForTitle1
            addTransactionForScreenViewModel.uiState.test {
                val fetchDataCompleted = awaitItem()
                fetchDataCompleted.isLoading.shouldBeFalse()

                addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedTitle
                )

                val result = awaitItem()
                result.titleError.shouldBe(
                    expected = AddTransactionForScreenTitleError.TransactionForExists,
                )
                result.isCtaButtonEnabled.shouldBeFalse()
                result.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedTitle,
                    )
            }
        }

    @Test
    fun updateTitle_titleIsBlank_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = "   "
            addTransactionForScreenViewModel.uiState.test {
                val previousResult = awaitItem()
                previousResult.isLoading.shouldBeFalse()

                addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedTitle
                )

                previousResult.titleError.shouldBe(
                    expected = AddTransactionForScreenTitleError.None,
                )
                previousResult.isCtaButtonEnabled.shouldBeFalse()
                previousResult.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedTitle,
                    )
            }
        }

    @Test
    fun updateTitle_titleIsValid_ctaIsEnabled() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = "test-title"
            addTransactionForScreenViewModel.uiState.test {
                val fetchDataCompleted = awaitItem()
                fetchDataCompleted.isLoading.shouldBeFalse()

                addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedTitle
                )

                val result = awaitItem()
                result.titleError.shouldBe(
                    expected = AddTransactionForScreenTitleError.None,
                )
                result.isCtaButtonEnabled.shouldBeTrue()
                result.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedTitle,
                    )
            }
        }
    // endregion
}
