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

package com.makeappssimple.abhimanyu.core.date.time.extensions

import com.makeappssimple.abhimanyu.core.date.time.formatInstant
import com.makeappssimple.abhimanyu.core.date.time.getCurrentSystemDefaultTimeZone
import kotlinx.datetime.TimeZone
import kotlin.time.Instant

/**
 * Sample format - 30 Mar, 2023.
 */
internal fun Instant.formattedDate(
    zoneId: TimeZone = getCurrentSystemDefaultTimeZone(),
): String {
    return formatInstant(
        instant = this,
        zoneId = zoneId,
        pattern = "dd MMM, yyyy",
    )
}

/**
 * Sample format - Monday.
 */
internal fun Instant.formattedDayOfWeek(
    zoneId: TimeZone = getCurrentSystemDefaultTimeZone(),
): String {
    return formatInstant(
        instant = this,
        zoneId = zoneId,
        pattern = "EEEE",
    )
}

/**
 * Sample format - 30 Mar.
 */
internal fun Instant.formattedDay(
    zoneId: TimeZone = getCurrentSystemDefaultTimeZone(),
): String {
    return formatInstant(
        instant = this,
        zoneId = zoneId,
        pattern = "dd MMM",
    )
}

/**
 * Sample format - March, 2023.
 */
internal fun Instant.formattedMonth(
    zoneId: TimeZone = getCurrentSystemDefaultTimeZone(),
): String {
    return formatInstant(
        instant = this,
        zoneId = zoneId,
        pattern = "MMMM, yyyy",
    )
}

/**
 * Sample format - 2023.
 */
internal fun Instant.formattedYear(
    zoneId: TimeZone = getCurrentSystemDefaultTimeZone(),
): String {
    return formatInstant(
        instant = this,
        zoneId = zoneId,
        pattern = "yyyy",
    )
}

/**
 * Sample format - 2023-Mar-30, 08-24 AM.
 */
internal fun Instant.formattedDateAndTime(
    zoneId: TimeZone = getCurrentSystemDefaultTimeZone(),
): String {
    return formatInstant(
        instant = this,
        zoneId = zoneId,
        pattern = "yyyy-MMM-dd, hh-mm a",
    )
        .replace(
            oldValue = "am",
            newValue = "AM",
        )
        .replace(
            oldValue = "pm",
            newValue = "PM",
        )
}

/**
 * Sample format - 30 Mar, 2023 at 08:24 AM.
 */
internal fun Instant.formattedReadableDateAndTime(
    zoneId: TimeZone = getCurrentSystemDefaultTimeZone(),
): String {
    return "${formattedDate(zoneId = zoneId)} at ${formattedTime(zoneId = zoneId)}"
}

/**
 * Sample format - 08:24 AM.
 */
private fun Instant.formattedTime(
    zoneId: TimeZone = getCurrentSystemDefaultTimeZone(),
): String {
    return formatInstant(
        instant = this,
        zoneId = zoneId,
        pattern = "hh:mm a",
    )
        .replace(
            oldValue = "am",
            newValue = "AM",
        )
        .replace(
            oldValue = "pm",
            newValue = "PM",
        )
}
