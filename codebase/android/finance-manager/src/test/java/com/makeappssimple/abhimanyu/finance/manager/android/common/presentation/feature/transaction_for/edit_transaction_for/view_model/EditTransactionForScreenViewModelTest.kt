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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.edit_transaction_for.view_model

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.edit_transaction_for.state.EditTransactionForScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation.FinanceManagerNavigationDirections
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
import org.junit.Ignore
import org.junit.Test
import kotlin.test.assertFailsWith

internal class EditTransactionForScreenViewModelTest {
    // region test setup
    private lateinit var editTransactionForScreenViewModel: EditTransactionForScreenViewModel
    private lateinit var testSavedStateHandle: SavedStateHandle
    private lateinit var testDependencies: TestDependencies

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        testSavedStateHandle = SavedStateHandle(
            initialState = mapOf(
                pair = "transactionForId" to testDependencies.testTransactionForId1,
            ),
        )
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region screen args
    @Test
    fun screenArgs_transactionForIdIsNull() =
        testDependencies.runTestWithTimeout {
            val emptySavedStateHandle = SavedStateHandle()

            val exception = assertFailsWith(
                exceptionClass = IllegalArgumentException::class,
            ) {
                setUpViewModel(
                    savedStateHandle = emptySavedStateHandle,
                )
            }

            exception.message.shouldBe(
                expected = "current transaction for id must not be null",
            )
        }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        setUpViewModel()
        editTransactionForScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeTrue()

            editTransactionForScreenViewModel.initViewModel()

            val result = awaitItem()
            result.isCtaButtonEnabled.shouldBeTrue()
            result.isLoading.shouldBeFalse()
            result.titleError.shouldBe(
                expected = EditTransactionForScreenTitleError.None,
            )
            result.titleTextFieldState.text.toString().shouldBe(
                expected = testDependencies.testTransactionForTitle1,
            )
        }
    }
    // endregion

    // region state events
    @Test
    @Ignore("To Fix")
    fun clearTitle_shouldClearText() =
        testDependencies.runTestWithTimeout {
            setUpViewModel()
            editTransactionForScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                editTransactionForScreenViewModel.initViewModel()
                val result = awaitItem()
                result.isLoading.shouldBeFalse()
                result.titleTextFieldState.text.toString().shouldBe(
                    expected = testDependencies.testTransactionForTitle1,
                )

                editTransactionForScreenViewModel.uiStateEvents.clearTitle()

                result.titleTextFieldState.text.toString().shouldBeEmpty()
            }
        }

    @Test
    @Ignore("To Fix")
    fun updateTitle_titleExists_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            setUpViewModel()
            val updatedTitle = testDependencies.testTransactionForTitle2
            editTransactionForScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                editTransactionForScreenViewModel.initViewModel()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isCtaButtonEnabled.shouldBeTrue()
                fetchDataCompletedState.isLoading.shouldBeFalse()
                fetchDataCompletedState.titleError.shouldBe(
                    expected = EditTransactionForScreenTitleError.None,
                )
                fetchDataCompletedState.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = testDependencies.testTransactionForTitle1,
                    )

                editTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedTitle
                )

                fetchDataCompletedState.isCtaButtonEnabled.shouldBeFalse()
                fetchDataCompletedState.isLoading.shouldBeFalse()
                fetchDataCompletedState.titleError.shouldBe(
                    expected = EditTransactionForScreenTitleError.TransactionForExists,
                )
                fetchDataCompletedState.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedTitle,
                    )
            }
        }

    @Test
    @Ignore("To Fix")
    fun updateTitle_titleIsBlank_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = "   "
            setUpViewModel()
            editTransactionForScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                editTransactionForScreenViewModel.initViewModel()
                val fetchDataCompleted = awaitItem()
                fetchDataCompleted.isCtaButtonEnabled.shouldBeTrue()
                fetchDataCompleted.isLoading.shouldBeFalse()
                fetchDataCompleted.titleError.shouldBe(
                    expected = EditTransactionForScreenTitleError.None,
                )
                fetchDataCompleted.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = testDependencies.testTransactionForTitle1,
                    )

                editTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedTitle
                )

                val result = awaitItem()
                result.isCtaButtonEnabled.shouldBeFalse()
                result.isLoading.shouldBeFalse()
                result.titleError.shouldBe(
                    expected = EditTransactionForScreenTitleError.None,
                )
                result.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedTitle,
                    )
            }
        }

    @Test
    @Ignore("To Fix")
    fun updateTitle_titleIsValid_ctaIsEnabled() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = "test-title"
            setUpViewModel()
            editTransactionForScreenViewModel.uiState.test {
                val fetchDataCompleted = awaitItem()
                fetchDataCompleted.isLoading.shouldBeFalse()

                editTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedTitle
                )

                val result = awaitItem()
                result.titleError.shouldBe(
                    expected = EditTransactionForScreenTitleError.None,
                )
                result.isCtaButtonEnabled.shouldBeTrue()
                result.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedTitle,
                    )
            }
        }

    @Test
    @Ignore("To Fix")
    fun updateTitle_shouldUpdateValue() = testDependencies.runTestWithTimeout {
        val updatedTitle = "updated-title"
        setUpViewModel()
        editTransactionForScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.titleTextFieldState.text.toString().shouldBeEmpty()
            editTransactionForScreenViewModel.initViewModel()
            val fetchDataCompletedState = awaitItem()
            fetchDataCompletedState.titleTextFieldState.text.toString()
                .shouldBe(
                    expected = testDependencies.testTransactionForTitle1,
                )

            editTransactionForScreenViewModel.uiStateEvents.updateTitle(
                updatedTitle
            )

            val result = awaitItem()
            result.titleTextFieldState.shouldBe(
                expected = updatedTitle,
            )
        }
    }

    @Test
    @Ignore("To Fix")
    fun updateTransactionFor_shouldUpdateAndNavigateUp() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = "updated-title"
            setUpViewModel()
            turbineScope {
                val navigationCommandTurbine =
                    testDependencies.navigationKit.command.testIn(
                        scope = backgroundScope,
                    )
                val uiStateTurbine =
                    editTransactionForScreenViewModel.uiState.testIn(
                        scope = backgroundScope,
                    )
                val lastChangeTimestamp =
                    testDependencies.financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                        ?: -1L

                val initialState = uiStateTurbine.awaitItem()
                initialState.titleTextFieldState.text.toString().shouldBeEmpty()
                editTransactionForScreenViewModel.initViewModel()
                val fetchDataCompletedState = uiStateTurbine.awaitItem()
                fetchDataCompletedState.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = testDependencies.testTransactionForTitle1,
                    )

                editTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedTitle
                )
                uiStateTurbine.awaitItem().titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedTitle,
                    )
                editTransactionForScreenViewModel.uiStateEvents.updateTransactionFor()

                val result = uiStateTurbine.awaitItem()
                result.isLoading.shouldBeTrue()
                testDependencies.fakeTransactionForDao.getTransactionForById(
                    testDependencies.testTransactionForId1
                )?.title.shouldBe(
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
    // endregion

    // region common
    private fun setUpViewModel(
        savedStateHandle: SavedStateHandle = testSavedStateHandle,
    ) {
        editTransactionForScreenViewModel = EditTransactionForScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            savedStateHandle = savedStateHandle,
            coroutineScope = testDependencies.testScope.backgroundScope,
            editTransactionForScreenDataValidationUseCase = testDependencies.editTransactionForScreenDataValidationUseCase,
            getTransactionForByIdUseCase = testDependencies.getTransactionForByIdUseCase,
            updateTransactionForUseCase = testDependencies.updateTransactionForUseCase,
            logKit = testDependencies.logKit,
        )
    }
    // endregion
}
