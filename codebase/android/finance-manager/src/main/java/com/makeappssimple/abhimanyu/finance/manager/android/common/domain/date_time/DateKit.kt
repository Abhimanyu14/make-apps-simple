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

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

public interface DateKit {
    public fun getCurrentLocalDate(): LocalDate

    public fun getLocalDate(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): LocalDate

    public fun getStartOfMonthLocalDate(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): LocalDate

    public fun getStartOfYearLocalDate(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): LocalDate

    private fun getCurrentTimeMillis(): Long {
        return Instant.now().toEpochMilli()
    }
}
