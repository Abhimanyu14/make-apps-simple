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

package com.makeappssimple.abhimanyu.common.app_version

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.pm.PackageInfoCompat
import com.makeappssimple.abhimanyu.common.build_config.BuildConfigKit
import org.koin.core.annotation.Single

@Single(
    binds = [
        AppVersionKit::class,
    ],
)
internal class AppVersionKitImpl(
    private val context: Context,
    private val buildConfigKit: BuildConfigKit,
) : AppVersionKit {
    override fun getAppVersion(): AppVersion? {
        return try {
            val packageManager = context.packageManager
            val packageName = context.packageName
            val packageInfo =
                if (buildConfigKit.isAndroidApiEqualToOrAboveApi33()) {
                    packageManager.getPackageInfo(
                        packageName,
                        PackageManager.PackageInfoFlags.of(0)
                    )
                } else {
                    packageManager.getPackageInfo(
                        packageName,
                        0
                    )
                }
            AppVersion(
                versionName = packageInfo.versionName.orEmpty(),
                versionNumber = PackageInfoCompat.getLongVersionCode(packageInfo),
            )
        } catch (
            exception: Exception,
        ) {
            exception.printStackTrace()
            null
        }
    }
}
