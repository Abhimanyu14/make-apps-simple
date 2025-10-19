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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.common.core.uri_decoder.UriDecoder
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.base.ScreenArgs

internal class ViewTransactionScreenArgs(
    val currentTransactionId: Int,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
        uriDecoder: UriDecoder,
    ) : this(
        currentTransactionId = uriDecoder.decode(
            string = requireNotNull(
                value = savedStateHandle.get<String>(NavigationArguments.TRANSACTION_ID),
                lazyMessage = {
                    "current transaction id must not be null"
                },
            ),
        ).toInt(),
    )
}
