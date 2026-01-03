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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.converters

import androidx.room.TypeConverter
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.CategoryEntity
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

internal class CategoryConverter {
    @TypeConverter
    internal fun stringToCategory(
        value: String?,
    ): CategoryEntity? {
        if (value.isNullOrBlank()) {
            return null
        }
        return try {
            Json.decodeFromString<CategoryEntity>(
                string = value,
            )
        } catch (
            serializationException: SerializationException,
        ) {
            serializationException.printStackTrace()
            null
        } catch (
            illegalArgumentException: IllegalArgumentException,
        ) {
            illegalArgumentException.printStackTrace()
            null
        }
    }

    @TypeConverter
    internal fun categoryToString(
        categoryEntity: CategoryEntity?,
    ): String {
        if (categoryEntity.isNull()) {
            return ""
        }
        return try {
            Json.encodeToString(
                value = categoryEntity,
            )
        } catch (
            serializationException: SerializationException,
        ) {
            serializationException.printStackTrace()
            ""
        } catch (
            illegalArgumentException: IllegalArgumentException,
        ) {
            illegalArgumentException.printStackTrace()
            ""
        }
    }
}
