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

package com.makeappssimple.abhimanyu.finance.manager.android.core.common.extensions

import com.makeappssimple.abhimanyu.finance.manager.android.core.common.datetime.getSystemDefaultZoneId
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

public fun LocalDate?.orMin(): LocalDate {
    return this ?: LocalDate.MIN
}

/**
 * [LocalDate] to [LocalDateTime].
 * Time: 23:59:59
 */
public fun LocalDate.atEndOfDay(): LocalDateTime {
    return this.atTime(LocalTime.MAX)
}

/**
 * Sample format - 30 Mar, 2023.
 */
public fun LocalDate.formattedDate(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("dd MMM, yyyy")
        .withZone(zoneId)
        .format(this)
}
