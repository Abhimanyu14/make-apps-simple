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

package com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.screen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isFocused
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.theme.BarcodesAppTheme
import com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.screen.CreateBarcodeScreenUI
import com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.state.CreateBarcodeScreenUIState
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
internal class CreateBarcodeScreenUITest {
    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    private val testBarcodeName = "test-barcode-name"
    private val testBarcodeValue = "test-barcode-value"

    private lateinit var testUtil: CreateBarcodeScreenUITestUtil

    @Before
    fun setUp() {
        testUtil = CreateBarcodeScreenUITestUtil(
            composeTestRule = composeTestRule,
        )
    }

    // region default state
    @Test
    fun createBarcodeScreenUIElementsAreDisplayed_byDefault() = runUITest {
        setScreenContent()

        assertTopAppBarIsDisplayed()
        assertTopAppBarNavigationButtonIsDisplayed()
        assertTopAppBarTitleTextIsMatching()
        assertBarcodeNameTextFieldIsDisplayed()
        assertBarcodeValueTextFieldIsDisplayed()
        assertCopyBarcodeValueButtonDoesNotExist()
        assertCtaButtonIsDisplayed()
    }

    @Test
    fun createBarcodeScreenUIContentIsScrollable_byDefault() = runUITest {
        setScreenContent()

        assertRootNodeIsScrollable()
    }
    // endregion

    // region clear text buttons
    @Test
    fun clearBarcodeNameButtonIsDisplayed_whenBarcodeNameTextFieldIsNotBlank() =
        runUITest {
            setScreenContent(
                barcodeName = testBarcodeName,
            )

            assertClearBarcodeNameButtonIsDisplayed()
        }

    @Test
    fun clearBarcodeValueButtonIsDisplayed_whenBarcodeValueTextFieldIsNotBlank() =
        runUITest {
            setScreenContent(
                barcodeValue = testBarcodeValue,
            )

            assertClearBarcodeValueButtonIsDisplayed()
        }
    // endregion

    // region cta button state
    @Test
    fun ctaButtonDisabled_byDefault() = runUITest {
        setScreenContent()

        assertCtaButtonIsNotEnabled()
    }

    @Test
    fun ctaButtonEnabled_whenBarcodeIsValid() =
        runUITest {
            setScreenContent(
                isSaveButtonEnabled = true,
            )

            assertCtaButtonIsEnabled()
        }
    // endregion

    // region isBarcodeValueEditable = false
    @Test
    fun clearBarcodeValueButtonDoesNotExist_whenIsBarcodeValueEditableIsFalse() =
        runUITest {
            setScreenContent(
                barcodeValue = testBarcodeValue,
                isBarcodeValueEditable = false,
            )

            performBarcodeValueTextFieldClick()
            assertClearBarcodeValueButtonDoesNotExist()
        }

    @Test
    fun copyBarcodeValueButtonDoesNotExist_whenIsBarcodeValueEditableIsFalse() =
        runUITest {
            setScreenContent(
                barcodeValue = testBarcodeValue,
                isBarcodeValueEditable = false,
            )

            assertCopyBarcodeValueButtonIsDisplayed()
        }

    @Test
    @Ignore("https://issuetracker.google.com/issues/317345954")
    fun barcodeValueTextFieldIsNotEditableIfIsBarcodeValueEditableIsFalse() =
        runUITest {
            setScreenContent(
                barcodeValue = testBarcodeValue,
                isBarcodeValueEditable = false,
            )

            assertBarcodeValueTextFieldIsNotEditable()
        }
    // endregion

    // region autofocus
    @Test
    fun barcodeNameTextFieldIsFocused_byDefault() = runUITest {
        setScreenContent()

        composeTestRule.waitUntilNodeCount(isFocused(), 1)
        assertBarcodeNameTextFieldIsFocused()
    }
    // endregion

    // region text input
    @Test
    @Ignore("https://issuetracker.google.com/issues/317432140")
    fun barcodeNameTextFieldHasGivenText_whenUserEntersText() = runUITest {
        setScreenContent()

        composeTestRule.waitUntilNodeCount(isFocused(), 1)
        performBarcodeNameTextFieldTextInput(
            text = testBarcodeName,
        )
        composeTestRule.waitForIdle()
        composeTestRule.waitUntilNodeCount(
            hasText(testBarcodeName),
            1
        )
        assertBarcodeNameTextFieldHasText(
            text = testBarcodeName,
        )
    }

    @Test
    @Ignore("https://issuetracker.google.com/issues/317432140")
    fun barcodeValueTextFieldHasGivenText_whenUserEntersText() = runUITest {
        setScreenContent()

        composeTestRule.waitUntilNodeCount(isFocused(), 1)
        performBarcodeValueTextFieldTextInput(
            text = testBarcodeValue,
        )
        composeTestRule.waitForIdle()
        assertBarcodeValueTextFieldHasText(
            text = testBarcodeValue,
        )
    }
    // endregion

    // region ime
    @Test
    fun barcodeValueTextFieldIsFocusedOnBarcodeNameTextFieldImeAction_whenIsBarcodeValueEditableIsTrue() =
        runUITest {
            setScreenContent(
                barcodeValue = testBarcodeValue,
                isBarcodeValueEditable = true,
            )

            composeTestRule.waitUntilNodeCount(isFocused(), 1)
            assertBarcodeNameTextFieldIsFocused()
            performBarcodeNameTextFieldImeAction()
            composeTestRule.waitForIdle()
            assertBarcodeNameTextFieldIsNotFocused()
            assertBarcodeValueTextFieldIsFocused()
        }

    @Test
    fun barcodeValueTextFieldIsNotFocusedOnBarcodeNameTextFieldImeAction_whenIsBarcodeValueEditableIsFalse() =
        runUITest {
            setScreenContent(
                barcodeName = testBarcodeName,
                barcodeValue = testBarcodeValue,
                isBarcodeValueEditable = false,
            )

            composeTestRule.waitUntilNodeCount(isFocused(), 1)
            assertBarcodeNameTextFieldIsFocused()
            performBarcodeNameTextFieldImeAction()
            assertBarcodeNameTextFieldIsFocused()
            assertBarcodeValueTextFieldIsNotFocused()
        }
    // endregion

    // region end to end flow
    @Test
    @Ignore("https://issuetracker.google.com/issues/317432140")
    fun barcodeCreationCompleteFlow() = runUITest {
        val (barcodeName, setBarcodeName) = mutableStateOf("")
        val (barcodeValue, setBarcodeValue) = mutableStateOf("")

        setScreenContent(
            barcodeValue = barcodeValue,
            barcodeName = barcodeName,
            isBarcodeValueEditable = true,
        )

        performBarcodeNameTextFieldTextInput(
            text = testBarcodeName,
        )
        composeTestRule
            .onNodeWithText(testBarcodeName)
            .assertIsDisplayed()

        assertClearBarcodeNameButtonIsDisplayed()

        performBarcodeNameTextFieldImeAction()
        assertBarcodeNameTextFieldIsNotFocused()
        assertBarcodeValueTextFieldIsFocused()
        assertCtaButtonIsNotEnabled()

        performBarcodeValueTextFieldTextInput(
            text = testBarcodeValue,
        )

        composeTestRule.onRoot().printToLog("currentLabelExists")

        composeTestRule
            .onNodeWithText(testBarcodeValue)
            .assertIsDisplayed()
        assertClearBarcodeValueButtonIsDisplayed()

        performBarcodeValueTextFieldImeAction()
        assertBarcodeValueTextFieldIsNotFocused()
        assertCtaButtonIsEnabled()
    }
    // endregion

    private fun setScreenContent(
        isSaveButtonEnabled: Boolean = false,
        isBarcodeValueEditable: Boolean = true,
        barcodeName: String = "",
        barcodeValue: String = "",
    ) {
        composeTestRule.setContent {
            BarcodesAppTheme {
                CreateBarcodeScreenUI(
                    uiState = CreateBarcodeScreenUIState(
                        isBarcodeValueEditable = isBarcodeValueEditable,
                        isSaveButtonEnabled = isSaveButtonEnabled,
                        barcodeName = barcodeName,
                        barcodeValue = barcodeValue,
                    ),
                )
            }
        }
    }

    private fun runUITest(
        block: CreateBarcodeScreenUITestUtil.() -> Unit,
    ) {
        with(
            receiver = testUtil,
        ) {
            block()
        }
    }
}
