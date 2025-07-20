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

package com.makeappssimple.abhimanyu.barcodes.android.core.common.extensions

import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class FloatExtensionTest {
    @Test
    fun isNotZero_returnsTrueForNonZero() {
        val value = 1.23F

        assertThat(value.isNotZero()).isTrue()
    }

    @Test
    fun isNotZero_returnsFalseForZero() {
        val value = 0F

        assertThat(value.isNotZero()).isFalse()
    }

    @Test
    fun orZero_returnsZeroForNull() {
        val value: Float? = null

        assertThat(value.orZero()).isEqualTo(0F)
    }

    @Test
    fun orZero_returnsValueForNonNull() {
        val value: Float? = 2.5F

        assertThat(value.orZero()).isEqualTo(2.5F)
    }
}
