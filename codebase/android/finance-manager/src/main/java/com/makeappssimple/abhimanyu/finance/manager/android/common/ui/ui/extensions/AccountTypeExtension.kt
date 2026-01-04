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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.extensions

import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosIconResource
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.AccountType

internal val AccountType.iconResource: CosmosIconResource
    get() = when (this) {
        AccountType.BANK -> {
            CosmosIcons.AccountBalance
        }

        AccountType.CASH -> {
            CosmosIcons.CurrencyRupee
        }

        AccountType.E_WALLET -> {
            CosmosIcons.AccountBalanceWallet
        }
    }
