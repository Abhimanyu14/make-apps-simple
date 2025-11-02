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

import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

internal class MyLocalTime(
    val localTime: LocalTime,
) {
    /**
     * Sample format - 08:24 AM.
     */
    fun formattedTime(
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String {
        return DateTimeFormatter
            .ofPattern("hh:mm a")
            .withZone(zoneId)
            .format(localTime)
            .uppercase()
    }

    companion object {
        val MAX: MyLocalTime = MyLocalTime(
            localTime = LocalTime.MAX,
        )

        val MIN: MyLocalTime = MyLocalTime(
            localTime = LocalTime.MIN,
        )

        fun now(): MyLocalTime {
            return MyLocalTime(
                localTime = LocalTime.now(),
            )
        }

        fun of(
            hour: Int,
            minute: Int,
        ): MyLocalTime {
            return MyLocalTime(
                localTime = LocalTime.of(
                    hour,
                    minute,
                ),
            )
        }
    }
}
