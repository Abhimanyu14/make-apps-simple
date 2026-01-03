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

package com.makeappssimple.abhimanyu.common.core.extensions

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.Test

internal class BooleanExtensionTest {
    @Test
    fun isTrue_returnsTrueForTrue() {
        val value: Boolean? = true

        value.isTrue().shouldBeTrue()
    }

    @Test
    fun isTrue_returnsFalseForFalseOrNull() {
        val valueFalse: Boolean? = false
        val valueNull: Boolean? = null

        valueFalse.isTrue().shouldBeFalse()
        valueNull.isTrue().shouldBeFalse()
    }

    @Test
    fun isFalse_returnsTrueForFalse() {
        val value: Boolean? = false

        value.isFalse().shouldBeTrue()
    }

    @Test
    fun isFalse_returnsFalseForTrueOrNull() {
        val valueTrue: Boolean? = true
        val valueNull: Boolean? = null

        valueTrue.isFalse().shouldBeFalse()
        valueNull.isFalse().shouldBeFalse()
    }

    @Test
    fun orFalse_returnsFalseForNullOrFalse() {
        val valueNull: Boolean? = null
        val valueFalse: Boolean? = false

        valueNull.orFalse().shouldBeFalse()
        valueFalse.orFalse().shouldBeFalse()
    }

    @Test
    fun orFalse_returnsTrueForTrue() {
        val value: Boolean? = true

        value.orFalse().shouldBeTrue()
    }
}
