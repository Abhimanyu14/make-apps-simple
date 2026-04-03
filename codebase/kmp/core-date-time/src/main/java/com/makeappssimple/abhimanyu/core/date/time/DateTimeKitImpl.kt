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

package com.makeappssimple.abhimanyu.core.date.time

import com.makeappssimple.abhimanyu.core.date.time.extensions.formattedDate
import com.makeappssimple.abhimanyu.core.date.time.extensions.formattedDateAndTime
import com.makeappssimple.abhimanyu.core.date.time.extensions.formattedDayOfWeek
import com.makeappssimple.abhimanyu.core.date.time.extensions.formattedMonth
import com.makeappssimple.abhimanyu.core.date.time.extensions.formattedReadableDateAndTime
import com.makeappssimple.abhimanyu.core.date.time.extensions.formattedYear
import com.makeappssimple.abhimanyu.core.date.time.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.core.date.time.models.MyLocalDate
import com.makeappssimple.abhimanyu.core.date.time.models.MyLocalTime
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toJavaZoneId
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Clock
import kotlin.time.Instant
import java.time.Instant as JavaInstant

private fun Instant.toJava(): JavaInstant {
    return JavaInstant.ofEpochSecond(
        this.epochSeconds,
    )
}

public class DateTimeKitImpl(
    private val clock: Clock = Clock.System,
    private val systemDefaultTimeZone: TimeZone = getCurrentSystemDefaultTimeZone(),
) : DateTimeKit {
    override fun getCurrentFormattedDateAndTime(
        timestamp: Long,
        zoneId: TimeZone,
    ): String {
        return Instant
            .fromEpochMilliseconds(
                epochMilliseconds = timestamp,
            )
            .formattedDateAndTime(
                zoneId = zoneId,
            )
    }

    override fun getCurrentLocalDate(): MyLocalDate {
        return MyLocalDate.now()
    }

    override fun getCurrentLocalTime(): MyLocalTime {
        return MyLocalTime.now()
    }

    override fun getCurrentTimeMillis(): Long {
        return clock
            .now()
            .toEpochMilliseconds()
    }

    override fun getFormattedDate(
        timestamp: Long,
        zoneId: TimeZone,
    ): String {
        return Instant
            .fromEpochMilliseconds(
                epochMilliseconds = timestamp,
            )
            .formattedDate(
                zoneId = zoneId,
            )
    }

    override fun getFormattedDateAndTime(
        timestamp: Long,
        zoneId: TimeZone,
    ): String {
        val instant = Instant.fromEpochMilliseconds(
            epochMilliseconds = timestamp,
        )
        val formattedDateAndTime = DateTimeFormatter
            .ofPattern("yyyy-MMM-dd, hh:mm a")
            .withZone(zoneId.toJavaZoneId())
            .format(instant.toJava())
            .replace(
                oldValue = "am",
                newValue = "AM",
            )
            .replace(
                oldValue = "pm",
                newValue = "PM",
            )
        return formattedDateAndTime
    }

    override fun getFormattedDateWithDayOfWeek(
        timestamp: Long,
        zoneId: TimeZone,
    ): String {
        val formattedDate = getFormattedDate(
            timestamp = timestamp,
            zoneId = zoneId,
        )
        val formattedDayOfWeek = Instant
            .fromEpochMilliseconds(
                epochMilliseconds = timestamp,
            )
            .formattedDayOfWeek(
                zoneId = zoneId,
            )
        return "$formattedDate ($formattedDayOfWeek)"
    }

    override fun getFormattedMonth(
        timestamp: Long,
        zoneId: TimeZone,
    ): String {
        return Instant
            .fromEpochMilliseconds(
                epochMilliseconds = timestamp,
            )
            .formattedMonth(
                zoneId = zoneId,
            )
    }

    override fun getFormattedYear(
        timestamp: Long,
        zoneId: TimeZone,
    ): String {
        return Instant
            .fromEpochMilliseconds(
                epochMilliseconds = timestamp,
            )
            .formattedYear(
                zoneId = zoneId,
            )
    }

    override fun getLocalDate(
        timestamp: Long,
        zoneId: TimeZone,
    ): MyLocalDate {
        return MyLocalDate(
            timestamp = timestamp,
            zoneId = zoneId,
        )
    }

    override fun getLocalTime(
        timestamp: Long,
        zoneId: TimeZone,
    ): MyLocalTime {
        return MyLocalTime(
            localTime = Instant
                .fromEpochMilliseconds(
                    epochMilliseconds = timestamp,
                )
                .toLocalDateTime(
                    timeZone = zoneId,
                )
                .time,
        )
    }

    override fun getNextDayTimestamp(
        timestamp: Long,
        zoneId: TimeZone,
    ): Long {
        return Instant
            .fromEpochMilliseconds(
                epochMilliseconds = timestamp,
            )
            .plus(
                value = 1,
                unit = DateTimeUnit.DAY,
                timeZone = zoneId,
            )
            .toEpochMilliseconds()
    }

    override fun getNextMonthTimestamp(
        timestamp: Long,
        zoneId: TimeZone,
    ): Long {
        return Instant
            .fromEpochMilliseconds(
                epochMilliseconds = timestamp,
            )
            .plus(
                value = 1,
                unit = DateTimeUnit.MONTH,
                timeZone = zoneId,
            )
            .toEpochMilliseconds()
    }

    override fun getNextYearTimestamp(
        timestamp: Long,
        zoneId: TimeZone,
    ): Long {
        return Instant
            .fromEpochMilliseconds(
                epochMilliseconds = timestamp,
            )
            .plus(
                value = 1,
                unit = DateTimeUnit.YEAR,
                timeZone = zoneId,
            )
            .toEpochMilliseconds()
    }

    override fun getPreviousDayTimestamp(
        timestamp: Long,
        zoneId: TimeZone,
    ): Long {
        return Instant
            .fromEpochMilliseconds(
                epochMilliseconds = timestamp,
            )
            .minus(
                value = 1,
                unit = DateTimeUnit.DAY,
                timeZone = zoneId,
            )
            .toEpochMilliseconds()
    }

    override fun getPreviousMonthTimestamp(
        timestamp: Long,
        zoneId: TimeZone,
    ): Long {
        return Instant
            .fromEpochMilliseconds(
                epochMilliseconds = timestamp,
            )
            .minus(
                value = 1,
                unit = DateTimeUnit.MONTH,
                timeZone = zoneId,
            )
            .toEpochMilliseconds()
    }

    override fun getPreviousYearTimestamp(
        timestamp: Long,
        zoneId: TimeZone,
    ): Long {
        return Instant
            .fromEpochMilliseconds(
                epochMilliseconds = timestamp,
            )
            .minus(
                value = 1,
                unit = DateTimeUnit.YEAR,
                timeZone = zoneId,
            )
            .toEpochMilliseconds()
    }

    override fun getReadableDateAndTime(
        timestamp: Long,
        zoneId: TimeZone,
    ): String {
        return Instant
            .fromEpochMilliseconds(
                epochMilliseconds = timestamp,
            )
            .formattedReadableDateAndTime(
                zoneId = zoneId,
            )
    }

    override fun getStartOfDayTimestamp(
        timestamp: Long,
        zoneId: TimeZone,
    ): Long {
        return MyLocalDate(
            timestamp = timestamp,
            zoneId = zoneId,
        )
            .toStartOfLocalDayEpochMilli(
                zoneId = zoneId,
            )
    }

    override fun getEndOfDayTimestamp(
        timestamp: Long,
        zoneId: TimeZone,
    ): Long {
        return MyLocalDate(
            timestamp = timestamp,
            zoneId = zoneId,
        )
            .atEndOfDay()
            .localDateTime
            .toEpochMilli(
                zoneId = zoneId,
            )
    }

    override fun getStartOfMonthTimestamp(
        timestamp: Long,
        zoneId: TimeZone,
    ): Long {
        return MyLocalDate(
            timestamp = timestamp,
            zoneId = zoneId,
        )
            .withDayOfMonth(
                dayOfMonth = 1,
            )
            .toStartOfLocalDayEpochMilli(
                zoneId = zoneId,
            )
    }

    override fun getStartOfMonthLocalDate(
        timestamp: Long,
        zoneId: TimeZone,
    ): MyLocalDate {
        return MyLocalDate(
            timestamp = timestamp,
            zoneId = zoneId,
        )
            .withDayOfMonth(
                dayOfMonth = 1,
            )
    }

    override fun getEndOfMonthTimestamp(
        timestamp: Long,
        zoneId: TimeZone,
    ): Long {
        val nextMonthFirstDay = MyLocalDate(
            timestamp = timestamp,
            zoneId = zoneId,
        )
            .withDayOfMonth(
                dayOfMonth = 1,
            )
            .localDate
            .plus(
                value = 1,
                unit = DateTimeUnit.MONTH,
            )
        val endOfMonth = nextMonthFirstDay
            .minus(
                value = 1,
                unit = DateTimeUnit.DAY,
            )

        return MyLocalDate(
            localDate = endOfMonth,
        )
            .atEndOfDay()
            .localDateTime
            .toEpochMilli(
                zoneId = zoneId,
            )
    }

    override fun getStartOfYearTimestamp(
        timestamp: Long,
        zoneId: TimeZone,
    ): Long {
        return MyLocalDate(
            timestamp = timestamp,
            zoneId = zoneId,
        )
            .withMonth(
                month = 1,
            )
            .withDayOfMonth(
                dayOfMonth = 1,
            )
            .toStartOfLocalDayEpochMilli(
                zoneId = zoneId,
            )
    }

    override fun getStartOfYearLocalDate(
        timestamp: Long,
        zoneId: TimeZone,
    ): MyLocalDate {
        return MyLocalDate(
            timestamp = timestamp,
            zoneId = zoneId,
        )
            .withMonth(
                month = 1,
            )
            .withDayOfMonth(
                dayOfMonth = 1,
            )
    }

    override fun getEndOfYearTimestamp(
        timestamp: Long,
        zoneId: TimeZone,
    ): Long {
        val nextYearFirstDay = MyLocalDate(
            timestamp = timestamp,
            zoneId = zoneId,
        )
            .withMonth(
                month = 1,
            )
            .withDayOfMonth(
                dayOfMonth = 1,
            )
            .localDate
            .plus(
                value = 1,
                unit = DateTimeUnit.YEAR,
            )
        val endOfYear = nextYearFirstDay
            .minus(
                value = 1,
                unit = DateTimeUnit.DAY,
            )

        return MyLocalDate(
            localDate = endOfYear,
        )
            .atEndOfDay()
            .localDateTime
            .toEpochMilli(
                zoneId = zoneId,
            )
    }

    override fun getSystemDefaultTimeZone(): TimeZone {
        return systemDefaultTimeZone
    }

    override fun getTimestamp(
        date: MyLocalDate,
        time: MyLocalTime,
        zoneId: TimeZone,
    ): Long {
        return date
            .atTime(
                time = time,
            )
            .localDateTime
            .toEpochMilli(
                zoneId = zoneId,
            )
    }
}
