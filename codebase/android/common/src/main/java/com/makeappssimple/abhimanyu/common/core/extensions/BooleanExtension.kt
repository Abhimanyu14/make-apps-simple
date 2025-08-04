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

@file:OptIn(ExperimentalContracts::class)

package com.makeappssimple.abhimanyu.common.core.extensions

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

public fun Boolean?.isTrue(): Boolean {
    contract {
        returns(true) implies (this@isTrue != null)
    }
    return this == true
}

public fun Boolean?.isFalse(): Boolean {
    contract {
        returns(true) implies (this@isFalse != null)
    }
    return this == false
}

public fun Boolean?.orFalse(): Boolean {
    return if (this.isNull()) {
        false
    } else {
        this
    }
}
