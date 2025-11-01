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

package com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

internal class MyLocalDate(
    val localDate: LocalDate,
) : Comparable<MyLocalDate> {
    val year: Int
        get() = localDate.year

    val month: Int
        get() = localDate.monthValue

    val day: Int
        get() = localDate.dayOfMonth

    override fun compareTo(
        other: MyLocalDate,
    ): Int {
        return localDate.compareTo(other.localDate)
    }

    fun atEndOfDay(): MyLocalDateTime {
        return MyLocalDateTime(
            localDateTime = localDate.atTime(LocalTime.MAX),
        )
    }

    fun atStartOfDay(): MyLocalDateTime {
        return MyLocalDateTime(
            localDateTime = localDate.atStartOfDay(),
        )
    }

    fun atStartOfDay(
        zone: ZoneId,
    ): ZonedDateTime {
        return localDate.atStartOfDay(zone)
    }

    fun atTime(
        time: MyLocalTime,
    ): MyLocalDateTime {
        return MyLocalDateTime(
            localDateTime = localDate.atTime(time.localTime),
        )
    }

    /**
     * Sample format - 30 Mar, 2023.
     */
    fun formattedDate(
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String {
        return DateTimeFormatter
            .ofPattern("dd MMM, yyyy")
            .withZone(zoneId)
            .format(localDate)
    }

    fun withDayOfMonth(
        dayOfMonth: Int,
    ): MyLocalDate {
        return MyLocalDate(
            localDate = localDate.withDayOfMonth(dayOfMonth),
        )
    }

    fun withMonth(
        month: Int,
    ): MyLocalDate {
        return MyLocalDate(
            localDate = localDate.withMonth(month),
        )
    }

    companion object {
        val MIN: MyLocalDate = MyLocalDate(
            localDate = LocalDate.MIN,
        )

        fun now(): MyLocalDate {
            return MyLocalDate(
                localDate = LocalDate.now(),
            )
        }

        fun of(
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

internal fun ZonedDateTime.toMyLocalDate(): MyLocalDate {
    return MyLocalDate(
        localDate = this.toLocalDate(),
    )
}

internal fun MyLocalDate?.orMin(): MyLocalDate {
    return this ?: MyLocalDate.MIN
}
