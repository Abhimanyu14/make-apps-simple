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

import java.time.ZoneId

/**
 * Interface for date and time utilities.
 *
 * Provides methods to get the current time in milliseconds, format timestamps,
 * and get the system's default time zone.
 */
public interface DateTimeKit {
    /**
     * Returns the current time in milliseconds since the Unix epoch.
     */
    public fun getCurrentTimeMillis(): Long

    /**
     * Formats the given [timestamp] (in milliseconds since the Unix epoch) into a human-readable date and time string.
     *
     * @param timestamp The time in milliseconds since the Unix epoch to format.
     * @param zoneId The time zone to use for formatting. Defaults to [getSystemDefaultZoneId()].
     * @return A formatted date and time string, e.g., "2023-Mar-30, 08-24 AM".
     */
    public fun getFormattedDateAndTime(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Returns the system default [ZoneId].
     */
    public fun getSystemDefaultZoneId(): ZoneId
}
