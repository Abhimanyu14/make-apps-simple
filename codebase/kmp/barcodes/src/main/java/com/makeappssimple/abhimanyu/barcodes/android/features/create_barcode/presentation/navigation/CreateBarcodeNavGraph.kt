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

package com.makeappssimple.abhimanyu.barcodes.android.features.create_barcode.presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.BarcodesScreen
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.barcodes.android.features.create_barcode.presentation.create_barcode.view_model.CreateBarcodeScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.features.create_barcode.ui.create_barcode.screen.CreateBarcodeScreen
import com.makeappssimple.abhimanyu.barcodes.android.resources.Res
import com.makeappssimple.abhimanyu.barcodes.android.resources.barcodes_screen_create_barcode_barcode_value_copied_toast_message
import com.makeappssimple.abhimanyu.barcodes.android.shared.ui.play_store_review.PlayStoreReviewHandler
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.jetbrains.compose.resources.getString

internal fun NavGraphBuilder.createBarcodeNavGraph() {
    composable(
        route = "${BarcodesScreen.CreateBarcode.route}?${NavigationArguments.BARCODE_ID}={${NavigationArguments.BARCODE_ID}}",
        arguments = listOf(
            navArgument(NavigationArguments.BARCODE_ID) {
                // Note: Int cannot be nullable, so nullable string
                type = NavType.StringType
                nullable = true
            },
        ),
    ) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        val playStoreReviewHandler = remember(context) {
            PlayStoreReviewHandler(context)
        }

        CreateBarcodeScreen(
            screenViewModel = koinViewModel<CreateBarcodeScreenViewModel>(),
            showBarcodeValueCopiedToastMessage = { barcodeValue ->
                coroutineScope.launch {
                    Toast.makeText(
                        context,
                        getString(
                            Res.string.barcodes_screen_create_barcode_barcode_value_copied_toast_message,
                            barcodeValue,
                        ),
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            },
            triggerInAppReview = playStoreReviewHandler::triggerInAppReview,
        )
    }
}
