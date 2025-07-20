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
import com.makeappssimple.abhimanyu.barcodes.android.core.logger.fake.FakeLogKitImpl
import org.junit.Before
import org.junit.Test

internal class LogKitTest {
    private lateinit var fakeLogKitImpl: FakeLogKitImpl

    @Before
    fun setUp() {
        fakeLogKitImpl = FakeLogKitImpl()
    }

    @Test
    fun logError_withDefaultTag_logsCorrectly() {
        val message = "Test error message"

        fakeLogKitImpl.logError(
            message = message,
        )

        assertThat(fakeLogKitImpl.loggedMessages.size).isEqualTo(1)
        assertThat(fakeLogKitImpl.loggedMessages[0].first).isEqualTo(message)
        assertThat(fakeLogKitImpl.loggedMessages[0].second).isEqualTo("Abhi")
    }

    @Test
    fun logError_withCustomTag_logsCorrectly() {
        val message = "Another error"
        val tag = "CustomTag"

        fakeLogKitImpl.logError(
            message = message,
            tag = tag,
        )

        assertThat(fakeLogKitImpl.loggedMessages.size).isEqualTo(1)
        assertThat(fakeLogKitImpl.loggedMessages[0].first).isEqualTo(message)
        assertThat(fakeLogKitImpl.loggedMessages[0].second).isEqualTo(tag)
    }
} 
