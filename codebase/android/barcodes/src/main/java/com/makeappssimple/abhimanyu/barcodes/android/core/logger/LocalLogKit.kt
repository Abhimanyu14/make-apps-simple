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

package com.makeappssimple.abhimanyu.barcodes.android.core.logger

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.makeappssimple.abhimanyu.barcodes.android.core.logger.fake.NoOpLogKitImpl

public val LocalLogKit: ProvidableCompositionLocal<LogKit> =
    staticCompositionLocalOf {
        // Provide a default MyLogger which does nothing.
        // This is so that tests and previews do not have to provide one.
        // For real app builds provide a different implementation.
        NoOpLogKitImpl()
    }
