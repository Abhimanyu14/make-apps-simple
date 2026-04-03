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

package com.makeappssimple.abhimanyu.common.build_config.fake

import com.makeappssimple.abhimanyu.common.build_config.BuildConfigKit

public class FakeBuildConfigKitImpl(
    private val buildVersion: Int,
) : BuildConfigKit {
    override fun isDebugBuild(): Boolean {
        return true
    }

    override fun getBuildVersion(): Int {
        return buildVersion
    }

    override fun isAndroidApiEqualToOrAboveApi33(): Boolean {
        return buildVersion >= 33
    }

    override fun isAndroidApiEqualToOrAboveApi34(): Boolean {
        return buildVersion >= 34
    }

    override fun isAndroidApiEqualToOrAboveApi35(): Boolean {
        return buildVersion >= 35
    }
}
