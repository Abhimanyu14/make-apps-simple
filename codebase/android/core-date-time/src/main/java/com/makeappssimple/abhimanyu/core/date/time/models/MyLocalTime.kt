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

@file:OptIn(kotlin.time.ExperimentalTime::class)

package com.makeappssimple.abhimanyu.core.date.time.models

import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Instant

public class MyLocalTime(
    public val localTime: LocalTime,
) {
    /**
     * Sample format - 08:24 AM.
     */
    public fun formattedTime(
        zoneId: TimeZone = DEFAULT_TIME_ZONE,
    ): String {
        return DateTimeFormatter
            .ofPattern("hh:mm a")
            .format(localTime.toJavaLocalTime())
            .uppercase()
    }

    public companion object {
        private val DEFAULT_TIME_ZONE: TimeZone by lazy {
            TimeZone.currentSystemDefault()
        }

        public val MAX: MyLocalTime = MyLocalTime(
            localTime = LocalTime(
                23,
                59,
                59,
                999_999_999
            ),
        )

        public val MIN: MyLocalTime = MyLocalTime(
            localTime = LocalTime(
                0,
                0,
                0,
                0
            ),
        )

        public fun now(): MyLocalTime {
            val timestamp = System.currentTimeMillis()
            return MyLocalTime(
                localTime = Instant.fromEpochMilliseconds(timestamp)
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .time,
            )
        }

        public fun of(
            hour: Int,
            minute: Int,
        ): MyLocalTime {
            return MyLocalTime(
                localTime = LocalTime(
                    hour = hour,
                    minute = minute,
                ),
            )
        }
    }
}
