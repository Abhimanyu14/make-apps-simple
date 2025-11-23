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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

internal val Icons.Rounded.CheckCircle: ImageVector
    get() {
        if (_checkCircle != null) {
            return _checkCircle!!
        }
        _checkCircle = materialIcon(name = "Rounded.CheckCircle") {
            materialPath {
                moveTo(
                    12.0f,
                    2.0f
                )
                curveTo(
                    6.48f,
                    2.0f,
                    2.0f,
                    6.48f,
                    2.0f,
                    12.0f
                )
                reflectiveCurveToRelative(
                    4.48f,
                    10.0f,
                    10.0f,
                    10.0f
                )
                reflectiveCurveToRelative(
                    10.0f,
                    -4.48f,
                    10.0f,
                    -10.0f
                )
                reflectiveCurveTo(
                    17.52f,
                    2.0f,
                    12.0f,
                    2.0f
                )
                close()
                moveTo(
                    9.29f,
                    16.29f
                )
                lineTo(
                    5.7f,
                    12.7f
                )
                curveToRelative(
                    -0.39f,
                    -0.39f,
                    -0.39f,
                    -1.02f,
                    0.0f,
                    -1.41f
                )
                curveToRelative(
                    0.39f,
                    -0.39f,
                    1.02f,
                    -0.39f,
                    1.41f,
                    0.0f
                )
                lineTo(
                    10.0f,
                    14.17f
                )
                lineToRelative(
                    6.88f,
                    -6.88f
                )
                curveToRelative(
                    0.39f,
                    -0.39f,
                    1.02f,
                    -0.39f,
                    1.41f,
                    0.0f
                )
                curveToRelative(
                    0.39f,
                    0.39f,
                    0.39f,
                    1.02f,
                    0.0f,
                    1.41f
                )
                lineToRelative(
                    -7.59f,
                    7.59f
                )
                curveToRelative(
                    -0.38f,
                    0.39f,
                    -1.02f,
                    0.39f,
                    -1.41f,
                    0.0f
                )
                close()
            }
        }
        return _checkCircle!!
    }

private var _checkCircle: ImageVector? = null
