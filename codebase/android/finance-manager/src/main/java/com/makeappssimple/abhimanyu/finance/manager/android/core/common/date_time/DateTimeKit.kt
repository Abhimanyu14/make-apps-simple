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

package com.makeappssimple.abhimanyu.finance.manager.android.core.common.date_time

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

public interface DateTimeKit : DateKit, TimeKit {
    public fun getCurrentLocalDateTime(): LocalDateTime

    public fun getCurrentInstant(): Instant

    public fun getCurrentTimeMillis(): Long

    public fun getSystemDefaultZoneId(): ZoneId

    /**
     * Sample format - 30 Mar, 2023.
     */
    public fun getFormattedDate(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - Monday.
     */
    public fun getFormattedDayOfWeek(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - 30 Mar.
     */
    public fun getFormattedDay(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - March, 2023.
     */
    public fun getFormattedMonth(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - 2023.
     */
    public fun getFormattedYear(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - 2023-Mar-30, 08-24 AM.
     */
    public fun getFormattedDateAndTime(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - 30 Mar, 2023 at 08:24 AM.
     */
    public fun getReadableDateAndTime(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    public fun getTimestamp(
        date: LocalDate = getLocalDate(),
        time: LocalTime = getLocalTime(),
    ): Long

    public fun getStartOfDayTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    public fun getEndOfDayTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    public fun getStartOfMonthTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    public fun getEndOfMonthTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    public fun getStartOfYearTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    public fun getEndOfYearTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long
}
