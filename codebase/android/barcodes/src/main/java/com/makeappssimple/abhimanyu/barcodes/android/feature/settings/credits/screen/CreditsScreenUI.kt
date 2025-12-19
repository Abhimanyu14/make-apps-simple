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

package com.makeappssimple.abhimanyu.barcodes.android.feature.settings.credits.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.SCREEN_CONTENT_CREDITS
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.SCREEN_CREDITS
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.text.LinkText
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.text.LinkTextData
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.text.MyText
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.resource.StringResource
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.theme.BarcodesAppTheme
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.barcodes.android.feature.settings.credits.event.CreditsScreenUIEvent
import com.makeappssimple.abhimanyu.library.barcodes.android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreditsScreenUI(
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: CreditsScreenUIEvent) -> Unit = {},
) {
    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_CREDITS,
            )
            .fillMaxSize(),
        topBar = {
            MyTopAppBar(
                titleStringResource = StringResource.Id(
                    id = R.string.barcodes_screen_credits,
                ),
                navigationAction = {
                    handleUIEvent(CreditsScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        coroutineScope = state.coroutineScope,
    ) {
        Column(
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_CREDITS,
                )
                .background(
                    color = BarcodesAppTheme.colorScheme.background,
                )
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            TitleText(
                titleStringResource = StringResource.Id(
                    id = R.string.barcodes_screen_credits_icons_title,
                ),
            )
            LinkText(
                linkTextData = listOf(
                    LinkTextData(
                        text = "Icons made by ",
                    ),
                    LinkTextData(
                        text = "monkik",
                        tag = "icon_1_author",
                        annotation = "https://www.flaticon.com/free-icon/barcode_664456",
                        onClick = {
                            handleUIEvent(
                                CreditsScreenUIEvent.OnLinkClick(
                                    url = "https://www.flaticon.com/free-icon/barcode_664456",
                                )
                            )
                        },
                    ),
                    LinkTextData(
                        text = " from ",
                    ),
                    LinkTextData(
                        text = "Flaticon",
                        tag = "icon_1_source",
                        annotation = "https://www.flaticon.com/",
                        onClick = {
                            handleUIEvent(
                                CreditsScreenUIEvent.OnLinkClick(
                                    url = "https://www.flaticon.com/",
                                )
                            )
                        },
                    )
                ),
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 0.dp,
                    ),
            )
            LinkText(
                linkTextData = listOf(
                    LinkTextData(
                        text = "Icons made by ",
                    ),
                    LinkTextData(
                        text = "smalllikeart",
                        tag = "icon_1_author",
                        annotation = "https://www.flaticon.com/authors/smalllikeart",
                        onClick = {
                            handleUIEvent(
                                CreditsScreenUIEvent.OnLinkClick(
                                    url = "https://www.flaticon.com/authors/smalllikeart",
                                )
                            )
                        },
                    ),
                    LinkTextData(
                        text = " from ",
                    ),
                    LinkTextData(
                        text = "Flaticon",
                        tag = "icon_1_source",
                        annotation = "https://www.flaticon.com/",
                        onClick = {
                            handleUIEvent(
                                CreditsScreenUIEvent.OnLinkClick(
                                    url = "https://www.flaticon.com/",
                                )
                            )
                        },
                    )
                ),
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                    ),
            )
            SectionSpacer()

            TitleText(
                titleStringResource = StringResource.Id(
                    id = R.string.barcodes_screen_credits_privacy_policy_title,
                ),
            )
            LinkText(
                linkTextData = listOf(
                    LinkTextData(
                        text = "Privacy Policy Generator",
                        tag = "privacy_policy_generator",
                        annotation = "https://app-privacy-policy-generator.firebaseapp.com",
                        onClick = {
                            handleUIEvent(
                                CreditsScreenUIEvent.OnLinkClick(
                                    url = "https://app-privacy-policy-generator.firebaseapp.com",
                                )
                            )
                        },
                    ),
                ),
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                    ),
            )
            SectionSpacer()

            TitleText(
                titleStringResource = StringResource.Id(
                    id = R.string.barcodes_screen_credits_terms_and_conditions_title,
                ),
            )
            LinkText(
                linkTextData = listOf(
                    LinkTextData(
                        text = "Privacy Policy Generator",
                        tag = "privacy_policy_generator",
                        annotation = "https://app-privacy-policy-generator.firebaseapp.com",
                        onClick = {
                            handleUIEvent(
                                CreditsScreenUIEvent.OnLinkClick(
                                    url = "https://app-privacy-policy-generator.firebaseapp.com",
                                )
                            )
                        },
                    ),
                ),
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                    ),
            )
            SectionSpacer()
        }
    }
}

@Composable
private fun TitleText(
    titleStringResource: StringResource,
) {
    MyText(
        stringResource = titleStringResource,
        style = BarcodesAppTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Bold,
        ),
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp,
            ),
    )
}

@Composable
private fun SectionSpacer() {
    Spacer(
        modifier = Modifier
            .padding(
                all = 8.dp,
            ),
    )
}
