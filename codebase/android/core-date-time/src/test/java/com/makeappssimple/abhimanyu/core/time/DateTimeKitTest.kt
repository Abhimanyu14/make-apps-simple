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

package com.makeappssimple.abhimanyu.core.time

import com.makeappssimple.abhimanyu.core.date.time.DateTimeKit
import com.makeappssimple.abhimanyu.core.date.time.DateTimeKitImpl
import io.kotest.matchers.shouldBe
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import org.junit.Before
import org.junit.Test

internal class DateTimeKitTest {
    private val testEpochMilliseconds = 1680155040000L
    private val testTimeZone = TimeZone.of(
        zoneId = "Asia/Kolkata",
    )

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
            systemDefaultTimeZone = testTimeZone,
        )
    }

    @Test
    fun getCurrentTimeMillis_returnsFixedTime() {
        val result = dateTimeKit.getCurrentTimeMillis()

        result.shouldBe(
            expected = testEpochMilliseconds,
        )
    }

    @Test
    fun getFormattedDateAndTime_withSystemDefaultZone_returnsExpectedFormat() {
        val formatted = dateTimeKit.getFormattedDateAndTime(
            timestamp = testEpochMilliseconds,
        )

        formatted.shouldBe(
            expected = "2023-Mar-30, 11:14 AM",
        )
    }

    @Test
    fun getFormattedDateAndTime_withDifferentZone_returnsExpectedFormat() {
        val utcTimeZone = TimeZone.of(
            zoneId = "UTC",
        )

        val formatted = dateTimeKit.getFormattedDateAndTime(
            timestamp = testEpochMilliseconds,
            zoneId = utcTimeZone,
        )

        formatted.shouldBe(
            expected = "2023-Mar-30, 05:44 AM",
        )
    }

    @Test
    fun getFormattedDateAndTime_withDifferentZone_returnsPmFormat() {
        val tokyoTimeZone = TimeZone.of(
            zoneId = "Asia/Tokyo",
        )

        val formatted = dateTimeKit.getFormattedDateAndTime(
            timestamp = testEpochMilliseconds,
            zoneId = tokyoTimeZone,
        )

        formatted.shouldBe(
            expected = "2023-Mar-30, 02:44 PM",
        )
    }

    @Test
    fun getSystemDefaultTimeZone_returnsInjectedTimeZone() {
        val result = dateTimeKit.getSystemDefaultTimeZone()

        result.shouldBe(
            expected = testTimeZone,
        )
    }
}
