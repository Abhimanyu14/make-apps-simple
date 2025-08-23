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

package com.makeappssimple.abhimanyu.common.core.extensions

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.Test

internal class AnyExtensionTest {
    @Test
    fun isNull_returnsTrueForNull() {
        val value: Any? = null

        value.isNull().shouldBeTrue()
    }

    @Test
    fun isNull_returnsFalseForNonNull() {
        val value: Any? = "not null"

        value.isNull().shouldBeFalse()
    }

    @Test
    fun isNotNull_returnsTrueForNonNull() {
        val value: Any? = 123

        value.isNotNull().shouldBeTrue()
    }

    @Test
    fun isNotNull_returnsFalseForNull() {
        val value: Any? = null

        value.isNotNull().shouldBeFalse()
    }
}
