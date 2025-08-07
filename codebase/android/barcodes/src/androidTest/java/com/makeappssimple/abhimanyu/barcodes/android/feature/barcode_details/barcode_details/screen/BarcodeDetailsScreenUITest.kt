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

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.theme.BarcodesAppTheme
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcode_details.barcode_details.state.BarcodeDetailsScreenUIState
import com.makeappssimple.abhimanyu.library.barcodes.android.R
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

internal class BarcodeDetailsScreenUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val testBarcodeName = "test-name"
    private val testBarcodeValue = "test-value"
    private val formattedTimestamp = "2023-Mar-30, 02:44 PM"
    private val uiState = BarcodeDetailsScreenUIState(
        barcodeName = testBarcodeName,
        barcodeValue = testBarcodeValue,
        formattedTimestamp = formattedTimestamp,
        formattedTimestampLabelId = R.string.barcodes_screen_barcode_details_barcode_timestamp_created,
        barcodeImageBitmap = createRandomBitmap(),
    )

    @Test
    fun barcodeDetailsScreenElementsAreDisplayed() {
        composeTestRule.setContent {
            BarcodesAppTheme {
                BarcodeDetailsScreenUI(
                    uiState = uiState,
                )
            }
        }

        // Barcode name
        assertBarcodeNameLabelIsDisplayed()
        assertBarcodeNameTextIsDisplayed()

        // Formatted timestamp
        assertBarcodeCreatedAtLabelIsDisplayed()
        assertBarcodeFormattedTimestampTextIsDisplayed()

        // Barcode value
        assertBarcodeValueLabelIsDisplayed()
        assertBarcodeValueTextIsDisplayed()

        // Copy barcode button
        assertCopyBarcodeValueButtonIsDisplayed()
        assertCopyBarcodeValueButtonIsClickable()

        // Barcode image
        assertBarcodeImageIsDisplayed()
    }

    // region Finders
    private fun findCopyBarcodeValueButton(): SemanticsNodeInteraction {
        return composeTestRule
            .onNodeWithContentDescription(
                label = "Copy barcode value",
            )
    }
    // endregion

    // region Actions
    // endregion

    // region Assertions
    private fun assertBarcodeNameLabelIsDisplayed() {
        composeTestRule
            .onNodeWithText(
                text = "Barcode Name",
            )
            .assertIsDisplayed()
    }

    private fun assertBarcodeNameTextIsDisplayed() {
        composeTestRule
            .onNodeWithText(
                text = testBarcodeName,
            )
            .assertIsDisplayed()
    }

    private fun assertBarcodeCreatedAtLabelIsDisplayed() {
        composeTestRule
            .onNodeWithText(
                text = "Created at",
            )
            .assertIsDisplayed()
    }

    private fun assertBarcodeFormattedTimestampTextIsDisplayed() {
        composeTestRule
            .onNodeWithText(
                text = formattedTimestamp,
            )
            .assertIsDisplayed()
    }

    private fun assertBarcodeValueLabelIsDisplayed() {
        composeTestRule
            .onNodeWithText(
                text = "Barcode Value",
            )
            .assertIsDisplayed()
    }

    private fun assertBarcodeValueTextIsDisplayed() {
        composeTestRule
            .onNodeWithText(
                text = testBarcodeValue,
            )
            .assertIsDisplayed()
    }

    private fun assertCopyBarcodeValueButtonIsDisplayed() {
        findCopyBarcodeValueButton().assertIsDisplayed()
    }

    private fun assertCopyBarcodeValueButtonIsClickable() {
        findCopyBarcodeValueButton().assertHasClickAction()
    }

    private fun assertBarcodeImageIsDisplayed() {
        composeTestRule
            .onNodeWithContentDescription(
                label = "Barcode image",
            )
            .assertIsDisplayed()
    }
    // endregion

    private fun createRandomBitmap(): ImageBitmap {
        val size = 512
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        fun getRandomColor(): Int {
            return if (Random.nextInt(2) == 0) {
                Color.BLACK
            } else {
                Color.WHITE
            }
        }
        for (x in 0..<size) {
            for (y in 0..<size) {
                bitmap.setPixel(x, y, getRandomColor())
            }
        }
        return bitmap
            .asImageBitmap()
    }
}
