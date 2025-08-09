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

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.makeappssimple.abhimanyu.barcodes.android.activity.BarcodesActivity
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.SCREEN_HOME
import com.makeappssimple.abhimanyu.barcodes.android.di.BarcodesAppModule
import com.makeappssimple.abhimanyu.barcodes.android.test.KoinTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.ksp.generated.module

@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @get:Rule(order = 0)
    val koinTestRule = KoinTestRule(
        modules = listOf(
            BarcodesAppModule().module,
            module {
                // Test dependencies here if needed
            },
        ),
    )

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<BarcodesActivity>()

    @Test
    fun navigationTest() {
        // Verify we start at home screen
        composeTestRule
            .onNodeWithTag(
                testTag = SCREEN_HOME,
            )
            .assertIsDisplayed()

        // Verify the top app bar is displayed
        composeTestRule.waitUntil {
            composeTestRule
                .onNodeWithContentDescription(
                    label = "Settings",
                )
                .isDisplayed()
        }

        // Click settings button and wait for navigation
        // TODO(Abhi): Not working.
        // References:
        // https://youtrack.jetbrains.com/issue/CMP-8731/Compose-Navigation-is-throwing-an-Exception-in-the-Compose-UI-Test
        // https://kotlinlang.slack.com/archives/CJLTWPH7S/p1754704904614679
        /*
        composeTestRule
            .onNodeWithContentDescription(
                label = "Settings",
            )
            .performClick()
        */

        // Use waitForIdle to ensure navigation completes
        composeTestRule.waitForIdle()
    }
}
