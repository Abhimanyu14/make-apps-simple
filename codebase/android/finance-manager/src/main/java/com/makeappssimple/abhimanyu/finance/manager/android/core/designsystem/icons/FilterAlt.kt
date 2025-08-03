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

package com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val Icons.Rounded.FilterAlt: ImageVector
    get() {
        if (_filterAlt != null) {
            return _filterAlt!!
        }
        _filterAlt = materialIcon(name = "Rounded.FilterAlt") {
            materialPath {
                moveTo(4.25f, 5.61f)
                curveTo(6.57f, 8.59f, 10.0f, 13.0f, 10.0f, 13.0f)
                verticalLineToRelative(5.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(0.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineToRelative(-5.0f)
                curveToRelative(0.0f, 0.0f, 3.43f, -4.41f, 5.75f, -7.39f)
                curveTo(20.26f, 4.95f, 19.79f, 4.0f, 18.95f, 4.0f)
                horizontalLineTo(5.04f)
                curveTo(4.21f, 4.0f, 3.74f, 4.95f, 4.25f, 5.61f)
                close()
            }
        }
        return _filterAlt!!
    }

private var _filterAlt: ImageVector? = null
