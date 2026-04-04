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

import com.makeappssimple.abhimanyu.core.date.time.formatInstant
import com.makeappssimple.abhimanyu.core.date.time.getCurrentSystemDefaultTimeZone
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
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
        val dummyDate = LocalDate(2023, 1, 1)
        val instant = dummyDate.atTime(localTime).toInstant(zoneId)
        return formatInstant(
            instant = instant,
            zoneId = zoneId,
            pattern = "hh:mm a",
        ).uppercase()
    }

    public companion object {
        private val DEFAULT_TIME_ZONE: TimeZone by lazy {
            getCurrentSystemDefaultTimeZone()
        }

        public val MAX: MyLocalTime = MyLocalTime(
            localTime = LocalTime(
                hour = 23,
                minute = 59,
                second = 59,
            ),
        )

        public val MIN: MyLocalTime = MyLocalTime(
            localTime = LocalTime(
                hour = 0,
                minute = 0,
                second = 0,
            ),
        )

        public fun now(): MyLocalTime {
            val timestamp = Clock.System.now().toEpochMilliseconds()
            return MyLocalTime(
                localTime = Instant
                    .fromEpochMilliseconds(
                        epochMilliseconds = timestamp,
                    )
                    .toLocalDateTime(
                        timeZone = getCurrentSystemDefaultTimeZone(),
                    )
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
