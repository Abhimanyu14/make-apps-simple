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

package com.makeappssimple.abhimanyu.common.core.build_config

import android.os.Build
import com.makeappssimple.abhimanyu.common.BuildConfig
import org.koin.core.annotation.Single

@Single(
    binds = [
        BuildConfigKit::class,
    ],
)
internal class BuildConfigKitImpl(
    private val isDebugBuild: Boolean = BuildConfig.DEBUG,
    private val buildVersion: Int = Build.VERSION.SDK_INT,
) : BuildConfigKit {
    override fun isDebugBuild(): Boolean {
        return isDebugBuild
    }

    override fun getBuildVersion(): Int {
        return buildVersion
    }

    override fun isAndroidApiEqualToOrAbove(
        buildVersionNumber: Int,
    ): Boolean {
        return getBuildVersion() >= buildVersionNumber
    }
}
