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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.common.core.uri_decoder.UriDecoder
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenArgs

internal class AddCategoryScreenArgs(
    val transactionType: String,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
        uriDecoder: UriDecoder,
    ) : this(
        transactionType = requireNotNull(
            value = uriDecoder.decode(
                string = requireNotNull(
                    value = savedStateHandle.get<String>(NavigationArguments.TRANSACTION_TYPE),
                    lazyMessage = {
                        "Navigation argument '${NavigationArguments.TRANSACTION_TYPE}' is required and cannot be null."
                    },
                ),
            ),
            lazyMessage = {
                "Decoded transaction type from argument '${NavigationArguments.TRANSACTION_TYPE}' cannot be null."
            },
        )
    )
}
