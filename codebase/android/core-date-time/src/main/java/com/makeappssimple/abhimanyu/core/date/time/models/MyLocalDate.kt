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

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Instant

public class MyLocalDate(
    public val localDate: LocalDate,
) : Comparable<MyLocalDate> {
    public constructor(
        timestamp: Long,
        zoneId: TimeZone = DEFAULT_TIME_ZONE,
    ) : this(
        localDate = Instant
            .fromEpochMilliseconds(timestamp)
            .toLocalDateTime(zoneId)
            .date,
    )

    public val year: Int
        get() = localDate.year

    public val month: Int
        get() = localDate.monthNumber

    public val day: Int
        get() = localDate.day

    override fun compareTo(
        other: MyLocalDate,
    ): Int {
        return localDate.compareTo(other.localDate)
    }

    internal fun atEndOfDay(): MyLocalDateTime {
        return MyLocalDateTime(
            localDateTime = localDate.atTime(
                LocalTime(
                    23,
                    59,
                    59,
                    999_999_999
                )
            ),
        )
    }

    public fun atStartOfDay(): MyLocalDateTime {
        return MyLocalDateTime(
            localDateTime = localDate.atTime(
                LocalTime(
                    0,
                    0,
                    0,
                    0
                )
            ),
        )
    }

    public fun toStartOfLocalDayEpochMilli(
        zoneId: TimeZone = DEFAULT_TIME_ZONE,
    ): Long {
        return localDate
            .atStartOfDayIn(zoneId)
            .toEpochMilliseconds()
    }

    public fun toStartOfDayEpochMilli(): Long {
        return localDate
            .atStartOfDayIn(TimeZone.UTC)
            .toEpochMilliseconds()
    }

    public fun atStartOfDay(
        zone: TimeZone,
    ): Instant {
        return localDate.atStartOfDayIn(zone)
    }

    public fun atTime(
        time: MyLocalTime,
    ): MyLocalDateTime {
        return MyLocalDateTime(
            localDateTime = localDate.atTime(time.localTime),
        )
    }

    /**
     * Sample format - 30 Mar, 2023.
     */
    public fun formattedDate(
        zoneId: TimeZone = DEFAULT_TIME_ZONE,
    ): String {
        return DateTimeFormatter
            .ofPattern("dd MMM, yyyy")
            .format(localDate.toJavaLocalDate())
    }

    public fun withDayOfMonth(
        dayOfMonth: Int,
    ): MyLocalDate {
        return MyLocalDate(
            localDate = LocalDate(
                localDate.year,
                localDate.monthNumber,
                dayOfMonth,
            ),
        )
    }

    public fun withMonth(
        month: Int,
    ): MyLocalDate {
        return MyLocalDate(
            localDate = LocalDate(
                localDate.year,
                month,
                localDate.day,
            ),
        )
    }

    public companion object {
        private val DEFAULT_TIME_ZONE: TimeZone by lazy {
            TimeZone.currentSystemDefault()
        }

        public val MIN: MyLocalDate = MyLocalDate(
            localDate = LocalDate(
                1,
                1,
                1
            ),
        )

        public fun now(): MyLocalDate {
            return MyLocalDate(
                timestamp = System.currentTimeMillis(),
                zoneId = TimeZone.currentSystemDefault(),
            )
        }

        public fun of(
            year: Int,
            month: Int,
            dayOfMonth: Int,
        ): MyLocalDate {
            return MyLocalDate(
                localDate = LocalDate(
                    year,
                    month,
                    dayOfMonth,
                ),
            )
        }
    }
}

public fun MyLocalDate?.orMin(): MyLocalDate {
    return this ?: MyLocalDate.MIN
}
