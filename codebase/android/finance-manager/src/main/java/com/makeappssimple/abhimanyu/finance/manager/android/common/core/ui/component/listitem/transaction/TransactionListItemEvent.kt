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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.listitem.transaction

import androidx.compose.runtime.Immutable

@Immutable
public sealed class TransactionListItemEvent {
    public data object OnClick : TransactionListItemEvent()
    public data object OnDeleteButtonClick : TransactionListItemEvent()
    public data object OnEditButtonClick : TransactionListItemEvent()
    public data object OnLongClick : TransactionListItemEvent()
    public data object OnRefundButtonClick : TransactionListItemEvent()
}
