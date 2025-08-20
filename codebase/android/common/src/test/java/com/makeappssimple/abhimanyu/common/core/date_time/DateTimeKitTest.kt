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

package com.makeappssimple.abhimanyu.common.core.date_time

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.time.ZoneId
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
internal class DateTimeKitTest {
    private val testEpochMilliseconds = 1680155040000L
    private val testZoneId = ZoneId.of("Asia/Kolkata")

    private lateinit var dateTimeKit: DateTimeKit

    @Before
    fun setUp() {
        val testInstant = Instant.fromEpochMilliseconds(
            epochMilliseconds = testEpochMilliseconds,
        ) // 2023-Mar-30, 11:14 AM IST
        val fakeClock = object : Clock {
            override fun now(): Instant = testInstant
        }
        dateTimeKit = DateTimeKitImpl(
            clock = fakeClock,
            systemDefaultZoneId = testZoneId,
        )
    }

    @Test
    fun getCurrentTimeMillis_returnsFixedTime() {
        val result = dateTimeKit.getCurrentTimeMillis()

        assertThat(result).isEqualTo(testEpochMilliseconds)
    }

    @Test
    fun getFormattedDateAndTime_withSystemDefaultZone_returnsExpectedFormat() {
        val formatted = dateTimeKit.getFormattedDateAndTime(
            timestamp = testEpochMilliseconds,
        )

        assertThat(formatted).isEqualTo("2023-Mar-30, 11:14 AM")
    }

    @Test
    fun getFormattedDateAndTime_withDifferentZone_returnsExpectedFormat() {
        val utcZoneId = ZoneId.of("UTC")

        val formatted = dateTimeKit.getFormattedDateAndTime(
            timestamp = testEpochMilliseconds,
            zoneId = utcZoneId,
        )

        assertThat(formatted).isEqualTo("2023-Mar-30, 05:44 AM")
    }

    @Test
    fun getFormattedDateAndTime_withDifferentZone_returnsPmFormat() {
        val tokyoZoneId = ZoneId.of("Asia/Tokyo")

        val formatted = dateTimeKit.getFormattedDateAndTime(
            timestamp = testEpochMilliseconds,
            zoneId = tokyoZoneId,
        )

        assertThat(formatted).isEqualTo("2023-Mar-30, 02:44 PM")
    }

    @Test
    fun getSystemDefaultZoneId_returnsInjectedZoneId() {
        val result = dateTimeKit.getSystemDefaultZoneId()

        assertThat(result).isEqualTo(testZoneId)
    }
}
