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

@file:OptIn(ExperimentalSerializationApi::class)

package com.makeappssimple.abhimanyu.finance.manager.android.core.model

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
public data class Category(
    @EncodeDefault
    val id: Int = 0,

    @SerialName(value = "parent_category")
    val parentCategoryId: Int? = null,

    @SerialName(value = "sub_categories")
    val subCategoryIds: List<Int>? = null,

    @EncodeDefault
    val description: String = "",

    val emoji: String,

    val title: String,

    @SerialName(value = "transaction_type")
    @JsonNames("transactionType")
    val transactionType: TransactionType,
)
