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

@file:OptIn(ExperimentalSerializationApi::class)

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.converters.IntListConverter
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@Entity(tableName = "category_table")
internal data class CategoryEntity(
    @ColumnInfo(name = "id")
    @EncodeDefault
    @PrimaryKey(autoGenerate = true)
    @SerialName(value = "id")
    val id: Int = 0,

    // TODO(Abhi): Fix parent category id column name
    @ColumnInfo(name = "parent_category")
    @EncodeDefault
    @SerialName(value = "parent_category")
    val parentCategoryId: Int? = null,

    // TODO(Abhi): Fix sub category ids column name
    @ColumnInfo(name = "sub_categories")
    @EncodeDefault
    @SerialName(value = "sub_categories")
    @TypeConverters(IntListConverter::class)
    val subCategoryIds: List<Int>? = null,

    @ColumnInfo(name = "description")
    @EncodeDefault
    @SerialName(value = "description")
    val description: String = "",

    @ColumnInfo(name = "emoji")
    @EncodeDefault
    @SerialName(value = "emoji")
    val emoji: String,

    @ColumnInfo(name = "title")
    @EncodeDefault
    @SerialName(value = "title")
    val title: String,

    @ColumnInfo(name = "transaction_type")
    @EncodeDefault
    @JsonNames("transactionType")
    @SerialName(value = "transaction_type")
    val transactionType: TransactionType,
)

internal fun CategoryEntity.asExternalModel(): Category {
    return Category(
        id = id,
        parentCategoryId = parentCategoryId,
        subCategoryIds = subCategoryIds,
        description = description,
        emoji = emoji,
        title = title,
        transactionType = transactionType,
    )
}
