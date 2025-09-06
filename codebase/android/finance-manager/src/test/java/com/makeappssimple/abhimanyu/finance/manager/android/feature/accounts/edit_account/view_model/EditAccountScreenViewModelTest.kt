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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.view_model

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.state.EditAccountScreenNameError
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import kotlin.test.Test

internal class EditAccountScreenViewModelTest {
    // region test setup
    private lateinit var viewModel: EditAccountScreenViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var testDependencies: TestDependencies

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        savedStateHandle = SavedStateHandle(
            initialState = mapOf(
                "accountId" to testDependencies.testAccountId1,
            ),
        )
        viewModel = EditAccountScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            savedStateHandle = savedStateHandle,
            coroutineScope = testDependencies.testScope.backgroundScope,
            editAccountScreenDataValidationUseCase = testDependencies.editAccountScreenDataValidationUseCase,
            getAccountByIdUseCase = testDependencies.getAccountByIdUseCase,
            updateAccountUseCase = testDependencies.updateAccountUseCase,
            logKit = testDependencies.logKit,
        )
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        viewModel.uiState.test {
            val result = awaitItem()
            result.selectedAccountTypeIndex.shouldBe(
                expected = -1,
            )
            result.nameError.shouldBe(
                expected = EditAccountScreenNameError.None,
            )
            result.isCtaButtonEnabled.shouldBeFalse()
            result.isLoading.shouldBeTrue()
            result.accountTypesChipUIDataList.shouldNotBeNull()
            result.nameTextFieldState.text.toString().shouldBeEmpty()
        }
    }
}
