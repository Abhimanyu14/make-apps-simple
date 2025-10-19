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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.converters

import androidx.room.TypeConverter
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

public class IntListConverter {
    @TypeConverter
    public fun stringToIntList(
        value: String?,
    ): List<Int>? {
        if (value.isNullOrBlank()) {
            return null
        }
        return try {
            Json.decodeFromString<List<Int>>(
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
    public fun intListToString(
        intList: List<Int>?,
    ): String {
        if (intList.isNull()) {
            return ""
        }
        return try {
            Json.encodeToString(
                value = intList,
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
