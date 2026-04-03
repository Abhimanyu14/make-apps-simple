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

package com.makeappssimple.abhimanyu.core.date.time.extensions

import com.makeappssimple.abhimanyu.core.date.time.getCurrentSystemDefaultTimeZone
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaZoneId
import java.time.format.DateTimeFormatter
import kotlin.time.Instant

private fun Instant.toJava(): java.time.Instant {
    return java.time.Instant.ofEpochSecond(
        this.epochSeconds,
    )
}

/**
 * Sample format - 30 Mar, 2023.
 */
internal fun Instant.formattedDate(
    zoneId: TimeZone = getCurrentSystemDefaultTimeZone(),
): String {
    return DateTimeFormatter
        .ofPattern(
            "dd MMM, yyyy",
        )
        .withZone(
            zoneId.toJavaZoneId(),
        )
        .format(
            this.toJava(),
        )
}

/**
 * Sample format - Monday.
 */
internal fun Instant.formattedDayOfWeek(
    zoneId: TimeZone = getCurrentSystemDefaultTimeZone(),
): String {
    return DateTimeFormatter
        .ofPattern(
            "EEEE",
        )
        .withZone(
            zoneId.toJavaZoneId(),
        )
        .format(
            this.toJava(),
        )
}

/**
 * Sample format - 30 Mar.
 */
internal fun Instant.formattedDay(
    zoneId: TimeZone = getCurrentSystemDefaultTimeZone(),
): String {
    return DateTimeFormatter
        .ofPattern(
            "dd MMM",
        )
        .withZone(
            zoneId.toJavaZoneId(),
        )
        .format(
            this.toJava(),
        )
}

/**
 * Sample format - March, 2023.
 */
internal fun Instant.formattedMonth(
    zoneId: TimeZone = getCurrentSystemDefaultTimeZone(),
): String {
    return DateTimeFormatter
        .ofPattern(
            "MMMM, yyyy",
        )
        .withZone(
            zoneId.toJavaZoneId(),
        )
        .format(
            this.toJava(),
        )
}

/**
 * Sample format - 2023.
 */
internal fun Instant.formattedYear(
    zoneId: TimeZone = getCurrentSystemDefaultTimeZone(),
): String {
    return DateTimeFormatter
        .ofPattern(
            "yyyy",
        )
        .withZone(
            zoneId.toJavaZoneId(),
        )
        .format(
            this.toJava(),
        )
}

/**
 * Sample format - 2023-Mar-30, 08-24 AM.
 */
internal fun Instant.formattedDateAndTime(
    zoneId: TimeZone = getCurrentSystemDefaultTimeZone(),
): String {
    return DateTimeFormatter
        .ofPattern(
            "yyyy-MMM-dd, hh-mm a",
        )
        .withZone(
            zoneId.toJavaZoneId(),
        )
        .format(
            this.toJava(),
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
    return DateTimeFormatter
        .ofPattern(
            "hh:mm a",
        )
        .withZone(
            zoneId.toJavaZoneId(),
        )
        .format(
            this.toJava(),
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
