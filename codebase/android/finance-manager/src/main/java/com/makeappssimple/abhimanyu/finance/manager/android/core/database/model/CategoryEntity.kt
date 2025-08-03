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

package com.makeappssimple.abhimanyu.finance.manager.android.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.converters.IntListConverter
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@Entity(tableName = "category_table")
public data class CategoryEntity(
    @EncodeDefault
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // TODO(Abhi): Fix parent category id column name
    @ColumnInfo(name = "parent_category")
    @SerialName(value = "parent_category")
    val parentCategoryId: Int? = null,

    // TODO(Abhi): Fix sub category ids column name
    @ColumnInfo(name = "sub_categories")
    @SerialName(value = "sub_categories")
    @TypeConverters(IntListConverter::class)
    val subCategoryIds: List<Int>? = null,

    @EncodeDefault
    val description: String = "",

    val emoji: String,

    val title: String,

    @ColumnInfo(name = "transaction_type")
    @SerialName(value = "transaction_type")
    @JsonNames("transactionType")
    val transactionType: TransactionType,
)

public fun CategoryEntity.asExternalModel(): Category {
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
