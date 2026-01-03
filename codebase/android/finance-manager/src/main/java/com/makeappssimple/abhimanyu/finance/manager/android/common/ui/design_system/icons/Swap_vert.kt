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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

internal val Swap_vert: ImageVector
    get() {
        if (_Swap_vert != null) return _Swap_vert!!

        _Swap_vert = ImageVector.Builder(
            name = "Swap_vert",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000))
            ) {
                moveTo(
                    320f,
                    520f
                )
                verticalLineToRelative(-287f)
                lineTo(
                    217f,
                    336f
                )
                lineToRelative(
                    -57f,
                    -56f
                )
                lineToRelative(
                    200f,
                    -200f
                )
                lineToRelative(
                    200f,
                    200f
                )
                lineToRelative(
                    -57f,
                    56f
                )
                lineToRelative(
                    -103f,
                    -103f
                )
                verticalLineToRelative(287f)
                close()
                moveTo(
                    600f,
                    880f
                )
                lineTo(
                    400f,
                    680f
                )
                lineToRelative(
                    57f,
                    -56f
                )
                lineToRelative(
                    103f,
                    103f
                )
                verticalLineToRelative(-287f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(287f)
                lineToRelative(
                    103f,
                    -103f
                )
                lineToRelative(
                    57f,
                    56f
                )
                close()
            }
        }.build()

        return _Swap_vert!!
    }

private var _Swap_vert: ImageVector? = null
