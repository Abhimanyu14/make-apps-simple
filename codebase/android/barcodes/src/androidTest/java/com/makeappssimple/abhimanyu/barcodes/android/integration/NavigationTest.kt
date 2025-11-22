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

package com.makeappssimple.abhimanyu.barcodes.android.integration

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.makeappssimple.abhimanyu.barcodes.android.activity.BarcodesActivity
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.SCREEN_HOME
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.SCREEN_SETTINGS
import com.makeappssimple.abhimanyu.barcodes.android.di.BarcodesAppModule
import com.makeappssimple.abhimanyu.barcodes.android.test.KoinTestRule
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.ksp.generated.module

@RunWith(AndroidJUnit4::class)
class NavigationTest {
    private val testCoroutineDispatcher = StandardTestDispatcher()
    private val settingsIconContentDescription = "Settings"

    @get:Rule(order = 0)
    val koinTestRule = KoinTestRule(
        modules = listOf(
            BarcodesAppModule().module,
            module {
                // Test dependencies here if needed
            },
        ),
    )

    @OptIn(ExperimentalTestApi::class)
    @get:Rule(order = 1)
    val composeTestRule = createComposeRule(
        effectContext = testCoroutineDispatcher,
    )

    @Before
    fun setUp() {
        ActivityScenario.launch(
            BarcodesActivity::class.java,
        )
    }

    @Test
    fun settingsNavigationTest() {
        assertHomeScreenIsDisplayed()
        assertSettingsIconIsDisplayed()
        clickSettingsIcon()
        assertSettingsScreenIsDisplayed()
    }

    private fun clickSettingsIcon() {
        composeTestRule
            .onNodeWithContentDescription(
                label = settingsIconContentDescription,
            )
            .performClick()
    }

    private fun assertSettingsIconIsDisplayed() {
        composeTestRule.waitUntil {
            composeTestRule
                .onNodeWithContentDescription(
                    label = settingsIconContentDescription,
                )
                .isDisplayed()
        }
    }

    private fun assertHomeScreenIsDisplayed() {
        composeTestRule
            .onNodeWithTag(
                testTag = SCREEN_HOME,
            )
            .assertIsDisplayed()
    }

    private fun assertSettingsScreenIsDisplayed() {
        composeTestRule
            .onNodeWithTag(
                testTag = SCREEN_SETTINGS,
            )
            .assertIsDisplayed()
    }
}
