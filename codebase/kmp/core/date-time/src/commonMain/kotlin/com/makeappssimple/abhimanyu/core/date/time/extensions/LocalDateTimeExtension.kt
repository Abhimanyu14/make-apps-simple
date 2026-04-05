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
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

/**
 * [LocalDateTime] to [Long].
 */
internal fun LocalDateTime.toEpochMilli(
    zoneId: TimeZone = getCurrentSystemDefaultTimeZone(),
): Long {
    return this
        .toInstant(
            timeZone = zoneId,
        )
        .toEpochMilliseconds()
}
