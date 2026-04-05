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

package com.makeappssimple.abhimanyu.core.kotlin.extensions

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class FloatExtensionTest {
    @Test
    fun isNotZero_returnsTrueForNonZero() {
        val value = 1.23F

        assertTrue(value.isNotZero())
    }

    @Test
    fun isNotZero_returnsFalseForZero() {
        val value = 0F

        assertFalse(value.isNotZero())
    }

    @Test
    fun orZero_returnsZeroForNull() {
        val value: Float? = null

        assertEquals(
            expected = 0F,
            actual = value.orZero(),
        )
    }

    @Test
    fun orZero_returnsValueForNonNull() {
        val value: Float? = 2.5F

        assertEquals(
            expected = 2.5F,
            actual = value.orZero(),
        )
    }
}
