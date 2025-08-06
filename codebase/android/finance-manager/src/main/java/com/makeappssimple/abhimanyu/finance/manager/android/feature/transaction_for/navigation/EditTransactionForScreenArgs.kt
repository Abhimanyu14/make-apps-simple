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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.common.core.uri_decoder.UriDecoder
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenArgs

internal class EditTransactionForScreenArgs(
    val transactionForId: Int?,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
        @Suppress("UNUSED_PARAMETER") uriDecoder: UriDecoder,
    ) : this(
        transactionForId = savedStateHandle.get<Int>(NavigationArguments.TRANSACTION_FOR_ID),
    )
}
