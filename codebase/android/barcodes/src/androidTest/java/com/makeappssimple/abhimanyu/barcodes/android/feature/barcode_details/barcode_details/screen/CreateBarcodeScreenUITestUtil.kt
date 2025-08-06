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

package com.makeappssimple.abhimanyu.barcodes.android.feature.barcode_details.barcode_details.screen

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags

internal class CreateBarcodeScreenUITestUtil(
    private val composeTestRule: ComposeContentTestRule,
) {
    private val barcodeNameTextFieldLabel = "Barcode Name"
    private val barcodeValueTextFieldLabel = "Barcode Value"
    private val clearBarcodeNameButtonContentDescription = "Clear barcode name"
    private val clearBarcodeValueButtonContentDescription =
        "Clear barcode value"
    private val copyBarcodeValueButtonContentDescription = "Copy barcode value"
    private val ctaButtonLabel = "SAVE"
    private val topAppbarTitleText = "Enter Barcode Details"

    // region Finders
    private fun findRootNode(): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithTag(
            testTag = TestTags.SCREEN_CONTENT_CREATE_BARCODE,
            useUnmergedTree = true,
        )
    }

    private fun findBarcodeNameTextField(): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithText(
            text = barcodeNameTextFieldLabel,
        )
    }

    private fun findBarcodeValueTextField(): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithText(
            text = barcodeValueTextFieldLabel,
        )
    }

    private fun findClearBarcodeNameButton(): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithContentDescription(
            label = clearBarcodeNameButtonContentDescription,
        )
    }

    private fun findClearBarcodeValueButton(): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithContentDescription(
            label = clearBarcodeValueButtonContentDescription,
        )
    }

    private fun findCopyBarcodeValueButton(): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithContentDescription(
            label = copyBarcodeValueButtonContentDescription,
        )
    }

    private fun findCtaButton(): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithText(
            text = ctaButtonLabel,
        )
    }

    private fun findTopAppBar(): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithTag(
            testTag = TestTags.COMPONENT_MY_TOP_APP_BAR,
        )
    }

    private fun findTopAppBarNavigationButton(): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithTag(
            testTag = TestTags.COMPONENT_MY_TOP_APP_BAR_NAVIGATION_BUTTON,
        )
    }

    private fun findTopAppBarTitleText(): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithTag(
            testTag = TestTags.COMPONENT_MY_TOP_APP_BAR_TITLE_TEXT,
        )
    }
    // endregion

    // region Actions
    internal fun performBarcodeNameTextFieldClick() {
        findBarcodeNameTextField().assertHasClickAction()
        findBarcodeNameTextField().performClick()
    }

    internal fun performBarcodeNameTextFieldImeAction() {
        findBarcodeNameTextField().performImeAction()
    }

    internal fun performBarcodeNameTextFieldTextInput(
        text: String,
    ) {
        findBarcodeNameTextField().performTextInput(text)
    }

    internal fun performBarcodeValueTextFieldClick() {
        findBarcodeValueTextField().assertHasClickAction()
        findBarcodeValueTextField().performClick()
    }

    internal fun performBarcodeValueTextFieldImeAction() {
        findBarcodeValueTextField().performImeAction()
    }

    internal fun performBarcodeValueTextFieldTextInput(
        text: String,
    ) {
        findBarcodeValueTextField().performTextInput(text)
    }
    // endregion

    // region Assertions
    internal fun assertBarcodeNameTextFieldHasText(
        text: String,
    ) {
        findBarcodeNameTextField().assert(hasText(text))
    }

    internal fun assertBarcodeNameTextFieldIsDisplayed() {
        findBarcodeNameTextField().assertIsDisplayed()
    }

    internal fun assertBarcodeNameTextFieldIsFocused() {
        findBarcodeNameTextField().assertIsFocused()
    }

    internal fun assertBarcodeNameTextFieldIsNotFocused() {
        findBarcodeNameTextField().assertIsNotFocused()
    }

    internal fun assertBarcodeValueTextFieldHasText(
        text: String,
    ) {
        findBarcodeValueTextField().assert(hasText(text))
    }

    internal fun assertBarcodeValueTextFieldIsDisplayed() {
        findBarcodeValueTextField().assertIsDisplayed()
    }

    internal fun assertBarcodeValueTextFieldIsNotEnabled() {
        findBarcodeValueTextField().assertIsNotEnabled()
    }

    internal fun assertBarcodeValueTextFieldIsNotEditable() {
        // TODO-Abhi: Compose testing does not support this yet.
    }

    internal fun assertBarcodeValueTextFieldIsFocused() {
        findBarcodeValueTextField().assertIsFocused()
    }

    internal fun assertBarcodeValueTextFieldIsNotFocused() {
        findBarcodeValueTextField().assertIsNotFocused()
    }

    internal fun assertClearBarcodeNameButtonIsDisplayed() {
        findClearBarcodeNameButton().assertIsDisplayed()
    }

    internal fun assertClearBarcodeValueButtonDoesNotExist() {
        findClearBarcodeValueButton().assertDoesNotExist()
    }

    internal fun assertClearBarcodeValueButtonIsDisplayed() {
        findClearBarcodeValueButton().assertIsDisplayed()
    }

    internal fun assertCopyBarcodeValueButtonDoesNotExist() {
        findCopyBarcodeValueButton().assertDoesNotExist()
    }

    internal fun assertCopyBarcodeValueButtonIsDisplayed() {
        findCopyBarcodeValueButton().assertIsDisplayed()
    }

    internal fun assertCtaButtonIsDisplayed() {
        findCtaButton().assertIsDisplayed()
    }

    internal fun assertCtaButtonIsEnabled() {
        findCtaButton().assertIsEnabled()
    }

    internal fun assertCtaButtonIsNotEnabled() {
        findCtaButton().assertIsNotEnabled()
    }

    internal fun assertRootNodeIsScrollable() {
        findRootNode().assert(hasScrollAction())
    }

    internal fun assertTopAppBarIsDisplayed() {
        findTopAppBar().assertIsDisplayed()
    }

    internal fun assertTopAppBarNavigationButtonIsDisplayed() {
        findTopAppBarNavigationButton().assertIsDisplayed()
    }

    internal fun assertTopAppBarTitleTextIsMatching() {
        findTopAppBarTitleText().assertTextEquals(topAppbarTitleText)
    }
    // endregion
}
