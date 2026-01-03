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

@file:OptIn(ExperimentalContracts::class)

package com.makeappssimple.abhimanyu.common.core.extensions

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

public fun String?.isNotNullOrBlank(): Boolean {
    contract {
        returns(true) implies (this@isNotNullOrBlank != null)
    }
    return !this.isNullOrBlank()
}

public fun Any.padStartWithZero(
    length: Int,
): String {
    if (length > 0) {
        return this.toString().padStart(
            length = length,
            padChar = '0',
        )
    }
    return this.toString()
}

public fun String.capitalizeWords(): String {
    return this
        .split(' ')
        .joinToString(" ") { word ->
            word.lowercase().replaceFirstChar {
                it.uppercaseChar()
            }
        }
}

public fun String.equalsIgnoringCase(
    other: String,
): Boolean {
    return this.equals(
        other = other,
        ignoreCase = true,
    )
}

public fun String.filterDigits(): String {
    return this.filter {
        it.isDigit()
    }
}

public fun String.toIntOrZero(): Int {
    return try {
        this.toInt()
    } catch (
        _: NumberFormatException,
    ) {
        0
    }
}

public fun String.toLongOrZero(): Long {
    return try {
        this.toLong()
    } catch (
        _: NumberFormatException,
    ) {
        0L
    }
}
