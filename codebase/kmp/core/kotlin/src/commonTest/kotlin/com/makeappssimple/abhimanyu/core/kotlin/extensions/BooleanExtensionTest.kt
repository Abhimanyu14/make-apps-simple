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
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class BooleanExtensionTest {
    @Test
    fun isTrue_returnsTrueForTrue() {
        val value: Boolean? = true

        assertTrue(value.isTrue())
    }

    @Test
    fun isTrue_returnsFalseForFalseOrNull() {
        val valueFalse: Boolean? = false
        val valueNull: Boolean? = null

        assertFalse(valueFalse.isTrue())
        assertFalse(valueNull.isTrue())
    }

    @Test
    fun isFalse_returnsTrueForFalse() {
        val value: Boolean? = false

        assertTrue(value.isFalse())
    }

    @Test
    fun isFalse_returnsFalseForTrueOrNull() {
        val valueTrue: Boolean? = true
        val valueNull: Boolean? = null

        assertFalse(valueTrue.isFalse())
        assertFalse(valueNull.isFalse())
    }

    @Test
    fun orFalse_returnsFalseForNullOrFalse() {
        val valueNull: Boolean? = null
        val valueFalse: Boolean? = false

        assertFalse(valueNull.orFalse())
        assertFalse(valueFalse.orFalse())
    }

    @Test
    fun orFalse_returnsTrueForTrue() {
        val value: Boolean? = true

        assertTrue(value.orFalse())
    }
}
