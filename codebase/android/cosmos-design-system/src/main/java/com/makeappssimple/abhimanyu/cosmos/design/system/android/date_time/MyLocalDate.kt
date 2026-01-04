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

package com.makeappssimple.abhimanyu.cosmos.design.system.android.date_time

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

public class MyLocalDate(
    public val localDate: LocalDate,
) : Comparable<MyLocalDate> {
    public constructor(
        timestamp: Long,
        zoneId: ZoneId = DEFAULT_ZONE_ID,
    ) : this(
        localDate = Instant
            .ofEpochMilli(timestamp)
            .atZone(zoneId)
            .toLocalDate(),
    )

    public val year: Int
        get() = localDate.year

    public val month: Int
        get() = localDate.monthValue

    public val day: Int
        get() = localDate.dayOfMonth

    override fun compareTo(
        other: MyLocalDate,
    ): Int {
        return localDate.compareTo(other.localDate)
    }

    public fun atEndOfDay(): MyLocalDateTime {
        return MyLocalDateTime(
            localDateTime = localDate.atTime(LocalTime.MAX),
        )
    }

    public fun atStartOfDay(): MyLocalDateTime {
        return MyLocalDateTime(
            localDateTime = localDate.atStartOfDay(),
        )
    }

    public fun toStartOfDayEpochMilli(
        zoneId: ZoneId = DEFAULT_ZONE_ID,
    ): Long {
        return this.localDate
            .atStartOfDay(zoneId)
            .toInstant()
            .toEpochMilli()
    }

    public fun atStartOfDay(
        zone: ZoneId,
    ): ZonedDateTime {
        return localDate.atStartOfDay(zone)
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
        zoneId: ZoneId = DEFAULT_ZONE_ID,
    ): String {
        return DateTimeFormatter
            .ofPattern("dd MMM, yyyy")
            .withZone(zoneId)
            .format(localDate)
    }

    public fun withDayOfMonth(
        dayOfMonth: Int,
    ): MyLocalDate {
        return MyLocalDate(
            localDate = localDate.withDayOfMonth(dayOfMonth),
        )
    }

    public fun withMonth(
        month: Int,
    ): MyLocalDate {
        return MyLocalDate(
            localDate = localDate.withMonth(month),
        )
    }

    public companion object {
        private val DEFAULT_ZONE_ID: ZoneId by lazy {
            ZoneId.systemDefault()
        }

        public val MIN: MyLocalDate = MyLocalDate(
            localDate = LocalDate.MIN,
        )

        public fun now(): MyLocalDate {
            return MyLocalDate(
                localDate = LocalDate.now(),
            )
        }

        public fun of(
            year: Int,
            month: Int,
            dayOfMonth: Int,
        ): MyLocalDate {
            return MyLocalDate(
                localDate = LocalDate.of(
                    year,
                    month,
                    dayOfMonth
                ),
            )
        }
    }
}

public fun MyLocalDate?.orMin(): MyLocalDate {
    return this ?: MyLocalDate.MIN
}
