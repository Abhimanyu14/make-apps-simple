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

package com.makeappssimple.abhimanyu.cosmos.design.system.android.extensions.match_row_size

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints

/**
 * Source - https://stackoverflow.com/a/72428903/9636037
 */
public fun Modifier.matchRowSize(): Modifier {
    return layout { measurable, constraints ->
        if (constraints.maxHeight == Constraints.Infinity) {
            layout(
                width = 0,
                height = 0,
            ) {}
        } else {
            val placeable = measurable.measure(
                constraints = constraints,
            )
            layout(
                width = placeable.width,
                height = placeable.height,
            ) {
                placeable.place(
                    x = 0,
                    y = 0,
                )
            }
        }
    }
}
