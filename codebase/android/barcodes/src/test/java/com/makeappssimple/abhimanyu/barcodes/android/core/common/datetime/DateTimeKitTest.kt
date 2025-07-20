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

package com.makeappssimple.abhimanyu.barcodes.android.core.common.datetime

import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.barcodes.android.core.common.datetime.fake.FakeDateTimeKitImpl
import org.junit.Before
import org.junit.Test
import java.time.ZoneId

internal class DateTimeKitTest {
    private lateinit var fakeDateTimeKit: FakeDateTimeKitImpl

    @Before
    fun setUp() {
        fakeDateTimeKit = FakeDateTimeKitImpl()
    }

    @Test
    fun getCurrentTimeMillis_returnsSetValue() {
        fakeDateTimeKit.setCurrentTimeMillis(
            value = 123456789L,
        )

        val result = fakeDateTimeKit.getCurrentTimeMillis()

        assertThat(result).isEqualTo(123456789L)
    }

    @Test
    fun getSystemDefaultZoneId_returnsSetZoneId() {
        val zone = ZoneId.of("Asia/Kolkata")
        fakeDateTimeKit.setZoneId(
            value = zone,
        )

        val result = fakeDateTimeKit.getSystemDefaultZoneId()

        assertThat(result).isEqualTo(zone)
    }

    @Test
    fun getFormattedDateAndTime_withDefaultZoneId() {
        val timestamp = 987654321L
        val zone = ZoneId.of("UTC")
        fakeDateTimeKit.setZoneId(
            value = zone,
        )

        val formatted = fakeDateTimeKit.getFormattedDateAndTime(
            timestamp = timestamp,
            zoneId = fakeDateTimeKit.getSystemDefaultZoneId(),
        )

        assertThat(formatted).isEqualTo("formatted-987654321-UTC")
    }

    @Test
    fun getFormattedDateAndTime_withCustomZoneId() {
        val timestamp = 555555555L
        val customZone = ZoneId.of("Europe/London")

        val formatted = fakeDateTimeKit.getFormattedDateAndTime(
            timestamp = timestamp,
            zoneId = customZone,
        )

        assertThat(formatted).isEqualTo("formatted-555555555-Europe/London")
    }
}
