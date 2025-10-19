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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

internal val Icons.Rounded.AccountBalanceWallet: ImageVector
    get() {
        if (_accountBalanceWallet != null) {
            return _accountBalanceWallet!!
        }
        _accountBalanceWallet =
            materialIcon(name = "Rounded.AccountBalanceWallet") {
                materialPath {
                    moveTo(10.0f, 16.0f)
                    lineTo(10.0f, 8.0f)
                    curveToRelative(0.0f, -1.1f, 0.89f, -2.0f, 2.0f, -2.0f)
                    horizontalLineToRelative(9.0f)
                    lineTo(21.0f, 5.0f)
                    curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                    lineTo(5.0f, 3.0f)
                    curveToRelative(-1.11f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                    verticalLineToRelative(14.0f)
                    curveToRelative(0.0f, 1.1f, 0.89f, 2.0f, 2.0f, 2.0f)
                    horizontalLineToRelative(14.0f)
                    curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                    verticalLineToRelative(-1.0f)
                    horizontalLineToRelative(-9.0f)
                    curveToRelative(-1.11f, 0.0f, -2.0f, -0.9f, -2.0f, -2.0f)
                    close()
                    moveTo(13.0f, 8.0f)
                    curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                    verticalLineToRelative(6.0f)
                    curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                    horizontalLineToRelative(9.0f)
                    lineTo(22.0f, 8.0f)
                    horizontalLineToRelative(-9.0f)
                    close()
                    moveTo(16.0f, 13.5f)
                    curveToRelative(-0.83f, 0.0f, -1.5f, -0.67f, -1.5f, -1.5f)
                    reflectiveCurveToRelative(0.67f, -1.5f, 1.5f, -1.5f)
                    reflectiveCurveToRelative(1.5f, 0.67f, 1.5f, 1.5f)
                    reflectiveCurveToRelative(-0.67f, 1.5f, -1.5f, 1.5f)
                    close()
                }
            }
        return _accountBalanceWallet!!
    }

private var _accountBalanceWallet: ImageVector? = null
