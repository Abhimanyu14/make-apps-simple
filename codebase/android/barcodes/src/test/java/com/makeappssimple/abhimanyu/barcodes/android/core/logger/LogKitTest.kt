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

import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.barcodes.android.core.common.build_config.BuildConfigKitImpl
import org.junit.Test

internal class LogKitTest {
    @Test
    fun logError_logsWhenDebug() {
        val buildConfigKit = BuildConfigKitImpl(
            isDebugBuild = true,
            buildVersion = 35,
        )
        val logs = mutableListOf<Pair<String, String>>()
        val logKit = LogKitImpl(
            buildConfigKit = buildConfigKit,
            logErrorMessage = { tag, message ->
                logs.add(tag to message)
            },
        )

        logKit.logError(
            message = "Test message",
            tag = "TestTag",
        )

        assertThat(logs).containsExactly("TestTag" to "Test message")
    }

    @Test
    fun logError_doesNotLogWhenNotDebug() {
        val buildConfigKit = BuildConfigKitImpl(
            isDebugBuild = false,
            buildVersion = 35,
        )
        val logs = mutableListOf<Pair<String, String>>()
        val logKit = LogKitImpl(
            buildConfigKit = buildConfigKit,
            logErrorMessage = { tag, message ->
                logs.add(tag to message)
            },
        )

        logKit.logError(
            message = "Test message",
            tag = "TestTag",
        )

        assertThat(logs).isEmpty()
    }
}
