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

package com.makeappssimple.abhimanyu.barcodes.android.core.common.build_config

import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class BuildConfigKitTest {
    @Test
    fun isDebugBuild_returnsInjectedValue_true() {
        val buildConfigKit = BuildConfigKitImpl(
            isDebugBuild = true,
            buildVersion = 33,
        )

        val result = buildConfigKit.isDebugBuild()

        assertThat(result).isTrue()
    }

    @Test
    fun isDebugBuild_returnsInjectedValue_false() {
        val buildConfigKit = BuildConfigKitImpl(
            isDebugBuild = false,
            buildVersion = 33,
        )

        val result = buildConfigKit.isDebugBuild()

        assertThat(result).isFalse()
    }

    @Test
    fun getBuildVersion_returnsInjectedSdkInt() {
        val buildConfigKit = BuildConfigKitImpl(
            isDebugBuild = false,
            buildVersion = 29,
        )

        val result = buildConfigKit.getBuildVersion()

        assertThat(result).isEqualTo(29)
    }

    @Test
    fun isAndroidApiEqualToOrAbove_trueWhenEqualOrAbove() {
        val buildConfigKit = BuildConfigKitImpl(
            isDebugBuild = false,
            buildVersion = 30,
        )

        val resultEqual = buildConfigKit.isAndroidApiEqualToOrAbove(
            buildVersionNumber = 30,
        )
        val resultAbove = buildConfigKit.isAndroidApiEqualToOrAbove(
            buildVersionNumber = 29,
        )

        assertThat(resultEqual).isTrue()
        assertThat(resultAbove).isTrue()
    }

    @Test
    fun isAndroidApiEqualToOrAbove_falseWhenBelow() {
        val buildConfigKit = BuildConfigKitImpl(
            isDebugBuild = false,
            buildVersion = 28,
        )

        val result = buildConfigKit.isAndroidApiEqualToOrAbove(
            buildVersionNumber = 29,
        )

        assertThat(result).isFalse()
    }
}
