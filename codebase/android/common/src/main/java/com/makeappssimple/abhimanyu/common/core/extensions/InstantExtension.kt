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

package com.makeappssimple.abhimanyu.common.core.extensions

import com.makeappssimple.abhimanyu.common.core.date_time.getSystemDefaultZoneId
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * [Instant] to [ZonedDateTime].
 */
public fun Instant.toZonedDateTime(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): ZonedDateTime {
    return this.atZone(zoneId)
}

/**
 * [Instant] to [Instant].
 */
public fun Instant.atEndOfDay(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): Instant {
    return this
        .atZone(zoneId)
        .toLocalDate()
        .atEndOfDay()
        .toInstant(
            zoneId = zoneId,
        )
}

/**
 * Sample format - 30 Mar, 2023.
 */
public fun Instant.formattedDate(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("dd MMM, yyyy")
        .withZone(zoneId)
        .format(this)
}

/**
 * Sample format - Monday.
 */
public fun Instant.formattedDayOfWeek(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("EEEE")
        .withZone(zoneId)
        .format(this)
}

/**
 * Sample format - 30 Mar.
 */
public fun Instant.formattedDay(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("dd MMM")
        .withZone(zoneId)
        .format(this)
}

/**
 * Sample format - March, 2023.
 */
public fun Instant.formattedMonth(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("MMMM, yyyy")
        .withZone(zoneId)
        .format(this)
}

/**
 * Sample format - 2023.
 */
public fun Instant.formattedYear(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("yyyy")
        .withZone(zoneId)
        .format(this)
}

/**
 * Sample format - 2023-Mar-30, 08-24 AM.
 */
public fun Instant.formattedDateAndTime(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("yyyy-MMM-dd, hh-mm a")
        .withZone(zoneId)
        .format(this)
        .replace(
            "am",
            "AM"
        )
        .replace(
            "pm",
            "PM"
        )
}

/**
 * Sample format - 30 Mar, 2023 at 08:24 AM.
 */
public fun Instant.formattedReadableDateAndTime(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return "${formattedDate(zoneId)} at ${formattedTime(zoneId)}"
}

/**
 * Sample format - 08:24 AM.
 */
public fun Instant.formattedTime(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("hh:mm a")
        .withZone(zoneId)
        .format(this)
        .replace(
            "am",
            "AM"
        )
        .replace(
            "pm",
            "PM"
        )
}

/**
 * [Instant] to [Instant].
 */
public fun Instant.atStartOfDay(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): Instant {
    return this
        .atZone(zoneId)
        .toLocalDate()
        .atStartOfDay()
        .toInstant(
            zoneId = zoneId,
        )
}
