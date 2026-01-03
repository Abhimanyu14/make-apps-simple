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

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

internal val Icons.Rounded.SwapVert: ImageVector
    get() {
        if (_SwapVert != null) {
            return _SwapVert!!
        }

        _SwapVert = materialIcon(
            name = "Rounded.SwapVert",
        ) {
            materialPath {
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
        }

        return _SwapVert!!
    }

private var _SwapVert: ImageVector? = null
