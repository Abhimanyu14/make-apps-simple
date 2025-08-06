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

package com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val Icons.Rounded.Category: ImageVector
    get() {
        if (_category != null) {
            return _category!!
        }
        _category = materialIcon(name = "Rounded.Category") {
            materialPath {
                moveTo(11.15f, 3.4f)
                lineTo(7.43f, 9.48f)
                curveToRelative(-0.41f, 0.66f, 0.07f, 1.52f, 0.85f, 1.52f)
                horizontalLineToRelative(7.43f)
                curveToRelative(0.78f, 0.0f, 1.26f, -0.86f, 0.85f, -1.52f)
                lineTo(12.85f, 3.4f)
                curveToRelative(-0.39f, -0.64f, -1.31f, -0.64f, -1.7f, 0.0f)
                close()
            }
            materialPath {
                moveTo(17.5f, 17.5f)
                moveToRelative(-4.5f, 0.0f)
                arcToRelative(4.5f, 4.5f, 0.0f, true, true, 9.0f, 0.0f)
                arcToRelative(4.5f, 4.5f, 0.0f, true, true, -9.0f, 0.0f)
            }
            materialPath {
                moveTo(4.0f, 21.5f)
                horizontalLineToRelative(6.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(-6.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineTo(4.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(6.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                close()
            }
        }
        return _category!!
    }

private var _category: ImageVector? = null
