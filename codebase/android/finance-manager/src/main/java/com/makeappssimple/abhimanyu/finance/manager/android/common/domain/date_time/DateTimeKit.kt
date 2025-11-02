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

import java.time.ZoneId

internal interface DateTimeKit {
    fun getCurrentFormattedDateAndTime(): String

    fun getCurrentTimeMillis(): Long

    fun getSystemDefaultZoneId(): ZoneId

    /**
     * Sample format - 30 Mar, 2023.
     */
    fun getFormattedDate(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - 30 Mar, 2023 (Monday).
     */
    fun getFormattedDateWithDayOfWeek(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - March, 2023.
     */
    fun getFormattedMonth(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - 2023.
     */
    fun getFormattedYear(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - 30 Mar, 2023 at 08:24 AM.
     */
    fun getReadableDateAndTime(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    fun getTimestamp(
        date: MyLocalDate = getLocalDate(),
        time: MyLocalTime = getLocalTime(),
    ): Long

    fun getStartOfDayTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    fun getEndOfDayTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    fun getStartOfMonthTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    fun getEndOfMonthTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    fun getStartOfYearTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    fun getEndOfYearTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    fun getCurrentLocalTime(): MyLocalTime

    fun getLocalTime(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): MyLocalTime

    fun getCurrentLocalDate(): MyLocalDate

    fun getNextDayTimestamp(
        timestamp: Long,
    ): Long

    fun getNextMonthTimestamp(
        timestamp: Long,
    ): Long

    fun getNextYearTimestamp(
        timestamp: Long,
    ): Long

    fun getPreviousDayTimestamp(
        timestamp: Long,
    ): Long

    fun getPreviousMonthTimestamp(
        timestamp: Long,
    ): Long

    fun getPreviousYearTimestamp(
        timestamp: Long,
    ): Long

    fun getLocalDate(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): MyLocalDate

    fun getStartOfMonthLocalDate(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): MyLocalDate

    fun getStartOfYearLocalDate(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): MyLocalDate
}
