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

package com.makeappssimple.abhimanyu.barcodes.android.core.common.extensions

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

internal fun Any?.isNull(): Boolean {
    contract {
        returns(true) implies (this@isNull == null)
        returns(false) implies (this@isNull != null)
    }
    return this == null
}

internal fun Any?.isNotNull(): Boolean {
    contract {
        returns(false) implies (this@isNotNull == null)
        returns(true) implies (this@isNotNull != null)
    }
    return this != null
}
