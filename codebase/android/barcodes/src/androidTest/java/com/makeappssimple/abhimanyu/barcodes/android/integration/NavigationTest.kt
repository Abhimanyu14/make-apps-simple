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
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.makeappssimple.abhimanyu.barcodes.android.common.di.BarcodesAppModule
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.SCREEN_CREATE_BARCODE
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.SCREEN_CREDITS
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.SCREEN_HOME
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.SCREEN_SETTINGS
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.SCREEN_WEB_VIEW
import com.makeappssimple.abhimanyu.barcodes.android.platform.activity.BarcodesActivity
import com.makeappssimple.abhimanyu.barcodes.android.test.KoinTestRule
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Ignore
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

    private var activityScenario: ActivityScenario<BarcodesActivity>? = null

    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(
            BarcodesActivity::class.java,
        )
    }

    @After
    fun tearDown() {
        activityScenario?.close()
    }

    @Test
    fun settingsScreenNavigationTest() {
        assertHomeScreenIsDisplayed()
        assertSettingsIconIsDisplayed()
        clickSettingsIcon()
        assertSettingsScreenIsDisplayed()
    }

    @Test
    @Ignore("To Fix")
    fun creditsScreenNavigationTest() {
        assertHomeScreenIsDisplayed()
        assertSettingsIconIsDisplayed()
        clickSettingsIcon()
        assertCreditsListItemIsDisplayed()
        clickCreditsListItem()
        assertCreditsScreenIsDisplayed()
    }

    @Test
    @Ignore("To Fix")
    fun openSourceLicensesScreenNavigationTest() {
        assertHomeScreenIsDisplayed()
        assertSettingsIconIsDisplayed()
        clickSettingsIcon()
        assertOpenSourceLicensesListItemIsDisplayed()
        clickOpenSourceLicensesListItem()
        assertOpenSourceLicensesScreenIsDisplayed()
    }

    @Test
    @Ignore("To Fix")
    fun privacyPolicyScreenNavigationTest() {
        assertHomeScreenIsDisplayed()
        assertSettingsIconIsDisplayed()
        clickSettingsIcon()
        assertPrivacyPolicyListItemIsDisplayed()
        clickPrivacyPolicyListItem()
        assertPrivacyPolicyScreenIsDisplayed()
    }

    @Test
    @Ignore("To Fix")
    fun createBarcodeScreenNavigationTest() {
        assertHomeScreenIsDisplayed()
        assertAddFloatingActionButtonIsDisplayed()
        clickAddFloatingActionButton()
        assertCreateBarcodeBottomSheetListItemIsDisplayed()
        assertCreateBarcodeBottomSheetListItemHasClickAction()
        clickCreateBarcodeBottomSheetListItem()
        assertCreateBarcodeScreenIsDisplayed()
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

    private fun assertAddFloatingActionButtonIsDisplayed() {
        composeTestRule.waitUntil {
            composeTestRule
                .onNodeWithContentDescription(
                    label = "Add",
                )
                .isDisplayed()
        }
    }

    private fun assertCreateBarcodeBottomSheetListItemIsDisplayed() {
        composeTestRule.waitUntil {
            composeTestRule
                .onNodeWithText(
                    "Create barcode"
                )
                .isDisplayed()
        }
    }

    private fun assertCreateBarcodeBottomSheetListItemHasClickAction() {
        composeTestRule
            .onNodeWithText(
                "Create barcode"
            )
            .assertHasClickAction()
    }

    private fun assertCreditsListItemIsDisplayed() {
        composeTestRule.waitUntil {
            composeTestRule
                .onNodeWithText(
                    "Credits"
                )
                .isDisplayed()
        }
    }

    private fun assertPrivacyPolicyListItemIsDisplayed() {
        composeTestRule.waitUntil {
            composeTestRule
                .onNodeWithText(
                    "Privacy Policy"
                )
                .isDisplayed()
        }
    }

    private fun assertOpenSourceLicensesListItemIsDisplayed() {
        composeTestRule.waitUntil {
            composeTestRule
                .onNodeWithText(
                    "Open Source Licenses"
                )
                .isDisplayed()
        }
    }

    private fun clickCreditsListItem() {
        composeTestRule
            .onNodeWithText(
                "Credits"
            )
            .performClick()
    }

    private fun clickPrivacyPolicyListItem() {
        composeTestRule
            .onNodeWithText(
                "Privacy Policy"
            )
            .performClick()
    }

    private fun clickOpenSourceLicensesListItem() {
        composeTestRule
            .onNodeWithText(
                "Open Source Licenses"
            )
            .performClick()
    }

    private fun clickCreateBarcodeBottomSheetListItem() {
        composeTestRule
            .onNodeWithText(
                "Create barcode"
            )
            .isDisplayed()
    }

    private fun clickAddFloatingActionButton() {
        composeTestRule
            .onNodeWithContentDescription(
                label = "Add",
            )
            .performClick()
    }

    private fun assertHomeScreenIsDisplayed() {
        composeTestRule.waitUntil {
            composeTestRule
                .onNodeWithTag(
                    testTag = SCREEN_HOME,
                )
                .isDisplayed()
        }
    }

    private fun assertSettingsScreenIsDisplayed() {
        composeTestRule.waitUntil {
            composeTestRule
                .onNodeWithTag(
                    testTag = SCREEN_SETTINGS,
                )
                .isDisplayed()
        }
    }

    private fun assertCreateBarcodeScreenIsDisplayed() {
        composeTestRule.waitUntil(
            timeoutMillis = 5_000,
        ) {
            composeTestRule
                .onNodeWithTag(
                    testTag = SCREEN_CREATE_BARCODE,
                )
                .isDisplayed()
        }
    }

    private fun assertCreditsScreenIsDisplayed() {
        composeTestRule.waitUntil {
            composeTestRule
                .onNodeWithTag(
                    testTag = SCREEN_CREDITS,
                )
                .isDisplayed()
        }
    }

    private fun assertOpenSourceLicensesScreenIsDisplayed() {
        composeTestRule.waitUntil {
            composeTestRule
                .onNodeWithText(
                    text = "Open Source Licenses"
                )
                .isDisplayed()
        }
    }

    private fun assertPrivacyPolicyScreenIsDisplayed() {
        composeTestRule.waitUntil {
            composeTestRule
                .onNodeWithTag(
                    testTag = SCREEN_WEB_VIEW,
                )
                .isDisplayed()
        }
        composeTestRule.waitUntil(
            timeoutMillis = 5_000,
        ) {
            composeTestRule
                .onNodeWithText(
                    text = "Privacy Policy"
                )
                .isDisplayed()
        }
    }
}
