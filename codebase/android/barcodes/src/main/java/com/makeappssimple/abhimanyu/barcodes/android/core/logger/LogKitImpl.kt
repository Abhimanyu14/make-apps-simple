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

import android.util.Log
import com.makeappssimple.abhimanyu.barcodes.android.core.common.buildconfig.BuildConfigKit

internal class LogKitImpl(
    private val buildConfigKit: BuildConfigKit,
    private val logErrorMessage: (String, String) -> Unit = { tag, message ->
        Log.e(tag, message)
    },
) : LogKit {
    override fun logError(
        message: String,
        tag: String,
    ) {
        if (buildConfigKit.isDebugBuild()) {
            logErrorMessage(tag, message)
        }
    }
}
