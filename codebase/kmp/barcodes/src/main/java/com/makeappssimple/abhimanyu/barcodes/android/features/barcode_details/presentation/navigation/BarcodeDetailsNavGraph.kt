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

package com.makeappssimple.abhimanyu.barcodes.android.features.barcode_details.presentation.navigation

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.BarcodesScreen
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.barcodes.android.features.barcode_details.presentation.barcode_details.view_model.BarcodeDetailsScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.features.barcode_details.ui.barcode_details.screen.BarcodeDetailsScreen
import com.makeappssimple.abhimanyu.barcodes.android.shared.ui.constants.BarcodesStrings
import org.koin.compose.viewmodel.koinViewModel

internal fun NavGraphBuilder.barcodeDetailsNavGraph() {
    composable(
        route = "${BarcodesScreen.BarcodeDetails.route}/{${NavigationArguments.BARCODE_ID}}",
        arguments = listOf(
            navArgument(NavigationArguments.BARCODE_ID) {
                type = NavType.IntType
            },
        ),
    ) {
        val context = LocalContext.current

        BarcodeDetailsScreen(
            screenViewModel = koinViewModel<BarcodeDetailsScreenViewModel>(),
            showBarcodeValueCopiedToastMessage = { barcodeValue ->
                Toast.makeText(
                    context,
                    BarcodesStrings.barcodeDetailsBarcodeValueCopiedToastMessage(
                        barcodeValue = barcodeValue,
                    ),
                    Toast.LENGTH_SHORT,
                ).show()
            },
        )
    }
}
