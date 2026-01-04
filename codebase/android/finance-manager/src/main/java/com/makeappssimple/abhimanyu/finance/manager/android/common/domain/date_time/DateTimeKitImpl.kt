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

package com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time

import com.makeappssimple.abhimanyu.cosmos.design.system.android.date_time.MyLocalDate
import com.makeappssimple.abhimanyu.cosmos.design.system.android.date_time.MyLocalDateTime
import com.makeappssimple.abhimanyu.cosmos.design.system.android.date_time.MyLocalTime
import com.makeappssimple.abhimanyu.common.core.extensions.formattedDate
import com.makeappssimple.abhimanyu.common.core.extensions.formattedDateAndTime
import com.makeappssimple.abhimanyu.common.core.extensions.formattedDayOfWeek
import com.makeappssimple.abhimanyu.common.core.extensions.formattedMonth
import com.makeappssimple.abhimanyu.common.core.extensions.formattedReadableDateAndTime
import com.makeappssimple.abhimanyu.common.core.extensions.formattedYear
import com.makeappssimple.abhimanyu.common.core.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.common.core.extensions.toZonedDateTime
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZonedDateTime

private object DateTimeUtilImplConstants {
    const val LAST_MONTH_OF_YEAR = 12
}

internal class DateTimeKitImpl() : DateTimeKit {
    override fun getCurrentFormattedDateAndTime(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        return getFormattedDateAndTime(
            timestamp = timestamp,
            zoneId = zoneId,
        )
    }

    override fun getCurrentLocalDate(): MyLocalDate {
        return MyLocalDate.now()
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

    override fun getCurrentLocalTime(): MyLocalTime {
        return MyLocalTime.now()
    }

    override fun getCurrentTimeMillis(): Long {
        return getCurrentInstant().toEpochMilli()
    }

    override fun getSystemDefaultZoneId(): ZoneId {
        return ZoneId.systemDefault()
    }

    /**
     * Sample format - 30 Mar, 2023.
     */
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

    override fun getFormattedDateWithDayOfWeek(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        val formattedDate = getFormattedDate(
            timestamp = timestamp,
        )
        val formattedDayOfWeek = getFormattedDayOfWeek(
            timestamp = timestamp,
            zoneId = zoneId,
        )
        return "$formattedDate ($formattedDayOfWeek)"
    }

    /**
     * Sample format - March, 2023.
     */
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

    /**
     * Sample format - 2023.
     */
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

    /**
     * Sample format - 30 Mar, 2023 at 08:24 AM.
     */
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

    /**
     * Sample format - Monday.
     */
    private fun getFormattedDayOfWeek(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        return Instant
            .ofEpochMilli(timestamp)
            .formattedDayOfWeek(
                zoneId = zoneId,
            )
    }

    /**
     * Sample format - 2023-Mar-30, 08-24 AM.
     */
    private fun getFormattedDateAndTime(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        return Instant
            .ofEpochMilli(timestamp)
            .formattedDateAndTime(
                zoneId = zoneId,
            )
    }

    private fun getCurrentInstant(): Instant {
        return Instant.now()
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
