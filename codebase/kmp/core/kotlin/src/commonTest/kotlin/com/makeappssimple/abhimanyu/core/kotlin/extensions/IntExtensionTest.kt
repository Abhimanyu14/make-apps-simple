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

internal class IntExtensionTest {
    @Test
    fun isNotZero_returnsTrueForNonZero() {
        val value = 42

        assertTrue(value.isNotZero())
    }

    @Test
    fun isNotZero_returnsFalseForZero() {
        val value = 0

        assertFalse(value.isNotZero())
    }

    @Test
    fun orZero_returnsZeroForNull() {
        val value: Int? = null

        assertEquals(
            expected = 0,
            actual = value.orZero(),
        )
    }

    @Test
    fun orZero_returnsValueForNonNull() {
        val value: Int? = 7

        assertEquals(
            expected = 7,
            actual = value.orZero(),
        )
    }
}
