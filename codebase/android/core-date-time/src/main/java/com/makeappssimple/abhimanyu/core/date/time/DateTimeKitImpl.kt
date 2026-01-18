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

package com.makeappssimple.abhimanyu.core.date.time

import com.makeappssimple.abhimanyu.core.date.time.extensions.formattedDate
import com.makeappssimple.abhimanyu.core.date.time.extensions.formattedDateAndTime
import com.makeappssimple.abhimanyu.core.date.time.extensions.formattedDayOfWeek
import com.makeappssimple.abhimanyu.core.date.time.extensions.formattedMonth
import com.makeappssimple.abhimanyu.core.date.time.extensions.formattedReadableDateAndTime
import com.makeappssimple.abhimanyu.core.date.time.extensions.formattedYear
import com.makeappssimple.abhimanyu.core.date.time.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.core.date.time.extensions.toZonedDateTime
import com.makeappssimple.abhimanyu.core.date.time.models.MyLocalDate
import com.makeappssimple.abhimanyu.core.date.time.models.MyLocalDateTime
import com.makeappssimple.abhimanyu.core.date.time.models.MyLocalTime
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

private object DateTimeUtilImplConstants {
    const val LAST_MONTH_OF_YEAR = 12
}

@OptIn(ExperimentalTime::class)
public class DateTimeKitImpl(
    private val clock: Clock = Clock.System,
    private val systemDefaultZoneId: ZoneId = ZoneId.systemDefault(),
) : DateTimeKit {
    override fun getCurrentFormattedDateAndTime(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        return Instant
            .ofEpochMilli(timestamp)
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
        return clock.now().toEpochMilliseconds()
    }

    override fun getFormattedDate(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        return Instant
            .ofEpochMilli(timestamp)
            .formattedDate(
                zoneId = zoneId,
            )
    }

    override fun getFormattedDateAndTime(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        val millis = Instant
            .ofEpochMilli(timestamp)
        val formattedDateAndTime = DateTimeFormatter
            .ofPattern("yyyy-MMM-dd, hh:mm a")
            .withZone(zoneId)
            .format(millis)
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
        zoneId: ZoneId,
    ): String {
        val formattedDate = getFormattedDate(
            timestamp = timestamp,
            zoneId = zoneId,
        )
        val formattedDayOfWeek = Instant
            .ofEpochMilli(timestamp)
            .formattedDayOfWeek(
                zoneId = zoneId,
            )
        return "$formattedDate ($formattedDayOfWeek)"
    }

    override fun getFormattedMonth(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        return Instant
            .ofEpochMilli(timestamp)
            .formattedMonth(
                zoneId = zoneId,
            )
    }

    override fun getFormattedYear(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        return Instant
            .ofEpochMilli(timestamp)
            .formattedYear(
                zoneId = zoneId,
            )
    }

    override fun getLocalDate(
        timestamp: Long,
        zoneId: ZoneId,
    ): MyLocalDate {
        return Instant
            .ofEpochMilli(timestamp)
            .toZonedDateTime(
                zoneId = zoneId,
            )
            .toMyLocalDate()
    }

    override fun getLocalTime(
        timestamp: Long,
        zoneId: ZoneId,
    ): MyLocalTime {
        return Instant
            .ofEpochMilli(timestamp)
            .toZonedDateTime(
                zoneId = zoneId,
            )
            .toMyLocalTime()
    }

    override fun getNextDayTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
    ): Long {
        return Instant
            .ofEpochMilli(timestamp)
            .atZone(zoneId)
            .plusDays(1)
            .toInstant()
            .toEpochMilli()
    }

    override fun getNextMonthTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
    ): Long {
        return Instant
            .ofEpochMilli(timestamp)
            .atZone(zoneId)
            .plusMonths(1)
            .toInstant()
            .toEpochMilli()
    }

    override fun getNextYearTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
    ): Long {
        return Instant
            .ofEpochMilli(timestamp)
            .atZone(zoneId)
            .plusYears(1)
            .toInstant()
            .toEpochMilli()
    }

    override fun getPreviousDayTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
    ): Long {
        return Instant
            .ofEpochMilli(timestamp)
            .atZone(zoneId)
            .minusDays(1)
            .toInstant()
            .toEpochMilli()
    }

    override fun getPreviousMonthTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
    ): Long {
        return Instant
            .ofEpochMilli(timestamp)
            .atZone(zoneId)
            .minusMonths(1)
            .toInstant()
            .toEpochMilli()
    }

    override fun getPreviousYearTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
    ): Long {
        return Instant
            .ofEpochMilli(timestamp)
            .atZone(zoneId)
            .minusYears(1)
            .toInstant()
            .toEpochMilli()
    }

    override fun getReadableDateAndTime(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        return Instant
            .ofEpochMilli(timestamp)
            .formattedReadableDateAndTime(
                zoneId = zoneId,
            )
    }

    override fun getStartOfDayTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
    ): Long {
        return Instant
            .ofEpochMilli(timestamp)
            .toZonedDateTime(
                zoneId = zoneId,
            )
            .toLocalDate()
            .atStartOfDay()
            .toEpochMilli(
                zoneId = zoneId,
            )
    }

    override fun getEndOfDayTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
    ): Long {
        return Instant
            .ofEpochMilli(timestamp)
            .toZonedDateTime(
                zoneId = zoneId,
            )
            .toMyLocalDate()
            .atEndOfDay()
            .toEpochMilli(
                zoneId = zoneId,
            )
    }

    override fun getStartOfMonthTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
    ): Long {
        return Instant
            .ofEpochMilli(timestamp)
            .toZonedDateTime(
                zoneId = zoneId,
            )
            .toLocalDate()
            .withDayOfMonth(1)
            .atStartOfDay()
            .toEpochMilli(
                zoneId = zoneId,
            )
    }

    override fun getStartOfMonthLocalDate(
        timestamp: Long,
        zoneId: ZoneId,
    ): MyLocalDate {
        return Instant
            .ofEpochMilli(timestamp)
            .toZonedDateTime(
                zoneId = zoneId,
            )
            .toMyLocalDate()
            .withDayOfMonth(
                dayOfMonth = 1,
            )
    }

    override fun getEndOfMonthTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
    ): Long {
        val localDate = MyLocalDate(
            localDate = YearMonth
                .from(
                    Instant
                        .ofEpochMilli(timestamp)
                        .toZonedDateTime(
                            zoneId = zoneId,
                        )
                        .toMyLocalDate()
                        .localDate
                )
                .atEndOfMonth(),
        )
        return localDate
            .atEndOfDay()
            .toEpochMilli(
                zoneId = zoneId,
            )
    }

    override fun getStartOfYearTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
    ): Long {
        return Instant
            .ofEpochMilli(timestamp)
            .toZonedDateTime(
                zoneId = zoneId,
            )
            .toLocalDate()
            .withMonth(1)
            .withDayOfMonth(1)
            .atStartOfDay()
            .toEpochMilli(
                zoneId = zoneId,
            )
    }

    override fun getStartOfYearLocalDate(
        timestamp: Long,
        zoneId: ZoneId,
    ): MyLocalDate {
        return Instant
            .ofEpochMilli(timestamp)
            .toZonedDateTime(
                zoneId = zoneId,
            )
            .toMyLocalDate()
            .withMonth(
                month = 1,
            )
            .withDayOfMonth(
                dayOfMonth = 1,
            )
    }

    override fun getEndOfYearTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
    ): Long {
        val localDate = MyLocalDate(
            localDate = YearMonth
                .from(
                    Instant
                        .ofEpochMilli(timestamp)
                        .toZonedDateTime(
                            zoneId = zoneId,
                        )
                        .toMyLocalDate()
                        .withMonth(DateTimeUtilImplConstants.LAST_MONTH_OF_YEAR)
                        .localDate,
                )
                .atEndOfMonth(),
        )
        return localDate
            .atEndOfDay()
            .toEpochMilli(
                zoneId = zoneId,
            )
    }

    override fun getSystemDefaultZoneId(): ZoneId {
        return systemDefaultZoneId
    }

    override fun getTimestamp(
        date: MyLocalDate,
        time: MyLocalTime,
        zoneId: ZoneId,
    ): Long {
        return date
            .atTime(time)
            .toEpochMilli(
                zoneId = zoneId,
            )
    }

    private fun MyLocalDateTime.toEpochMilli(
        zoneId: ZoneId,
    ): Long {
        return this.localDateTime
            .atZone(zoneId)
            .toInstant()
            .toEpochMilli()
    }

    private fun ZonedDateTime.toMyLocalDate(): MyLocalDate {
        return MyLocalDate(
            localDate = this.toLocalDate(),
        )
    }

    private fun ZonedDateTime.toMyLocalTime(): MyLocalTime {
        return MyLocalTime(
            localTime = this.toLocalTime(),
        )
    }
}
