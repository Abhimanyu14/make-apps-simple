/*
 * Copyright 2025-2026 Abhimanyu
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

package com.makeappssimple.abhimanyu.barcodes.android.feature.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.makeappssimple.abhimanyu.barcodes.android.common.di.BarcodesAppModule
import com.makeappssimple.abhimanyu.barcodes.android.platform.activity.BarcodesActivity
import com.makeappssimple.abhimanyu.barcodes.android.test.KoinTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.ksp.generated.module

@RunWith(AndroidJUnit4::class)
internal class HomeScreenTest {
    @get:Rule(order = 0)
    val koinTestRule = KoinTestRule(
        modules = listOf(
            BarcodesAppModule().module,
            module {
                // Add instrumented test-specific dependencies here
            },
        ),
    )

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<BarcodesActivity>()

    @Before
    fun setup() {
        // Setup will be added if needed
    }

    @Test
    fun homeScreen_isDisplayed() {
        composeTestRule.onNodeWithText("Barcodes").assertIsDisplayed()
    }
}
