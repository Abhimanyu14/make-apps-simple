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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val Icons.Rounded.Checklist: ImageVector
    get() {
        if (_checklist != null) {
            return _checklist!!
        }
        _checklist = materialIcon(name = "Rounded.Checklist") {
            materialPath {
                moveTo(22.0f, 8.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(-7.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(7.0f)
                curveTo(21.55f, 9.0f, 22.0f, 8.55f, 22.0f, 8.0f)
                close()
                moveTo(13.0f, 16.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(7.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(-7.0f)
                curveTo(13.45f, 15.0f, 13.0f, 15.45f, 13.0f, 16.0f)
                close()
                moveTo(10.47f, 4.63f)
                curveToRelative(0.39f, 0.39f, 0.39f, 1.02f, 0.0f, 1.41f)
                lineToRelative(-4.23f, 4.25f)
                curveToRelative(-0.39f, 0.39f, -1.02f, 0.39f, -1.42f, 0.0f)
                lineTo(2.7f, 8.16f)
                curveToRelative(-0.39f, -0.39f, -0.39f, -1.02f, 0.0f, -1.41f)
                curveToRelative(0.39f, -0.39f, 1.02f, -0.39f, 1.41f, 0.0f)
                lineToRelative(1.42f, 1.42f)
                lineToRelative(3.54f, -3.54f)
                curveTo(9.45f, 4.25f, 10.09f, 4.25f, 10.47f, 4.63f)
                close()
                moveTo(10.48f, 12.64f)
                curveToRelative(0.39f, 0.39f, 0.39f, 1.02f, 0.0f, 1.41f)
                lineToRelative(-4.23f, 4.25f)
                curveToRelative(-0.39f, 0.39f, -1.02f, 0.39f, -1.42f, 0.0f)
                lineTo(2.7f, 16.16f)
                curveToRelative(-0.39f, -0.39f, -0.39f, -1.02f, 0.0f, -1.41f)
                reflectiveCurveToRelative(1.02f, -0.39f, 1.41f, 0.0f)
                lineToRelative(1.42f, 1.42f)
                lineToRelative(3.54f, -3.54f)
                curveTo(9.45f, 12.25f, 10.09f, 12.25f, 10.48f, 12.64f)
                lineTo(10.48f, 12.64f)
                close()
            }
        }
        return _checklist!!
    }

private var _checklist: ImageVector? = null
