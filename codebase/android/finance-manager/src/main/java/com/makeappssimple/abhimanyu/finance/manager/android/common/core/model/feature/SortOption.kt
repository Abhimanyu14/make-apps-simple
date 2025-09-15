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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.feature

import com.makeappssimple.abhimanyu.common.core.extensions.isNull

public enum class SortOption(
    public val title: String,
) {
    AMOUNT_ASC(
        title = "Amount Asc",
    ),
    AMOUNT_DESC(
        title = "Amount Desc",
    ),
    LATEST_FIRST(
        title = "Latest First",
    ),
    OLDEST_FIRST(
        title = "Oldest First",
    ),
}

public fun SortOption?.orDefault(): SortOption {
    return if (this.isNull()) {
        SortOption.LATEST_FIRST
    } else {
        this
    }
}
