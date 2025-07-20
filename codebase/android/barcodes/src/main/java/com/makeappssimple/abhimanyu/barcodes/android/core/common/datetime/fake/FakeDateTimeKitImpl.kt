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

package com.makeappssimple.abhimanyu.barcodes.android.core.common.datetime.fake

import com.makeappssimple.abhimanyu.barcodes.android.core.common.datetime.DateTimeKit
import java.time.ZoneId

internal class FakeDateTimeKitImpl(
    private var currentTimeMillis: Long = 0L,
    private var zoneId: ZoneId = ZoneId.of("UTC"),
) : DateTimeKit {
    override fun getCurrentTimeMillis(): Long {
        return currentTimeMillis
    }

    override fun getFormattedDateAndTime(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        return "formatted-$timestamp-${zoneId.id}"
    }

    override fun getSystemDefaultZoneId(): ZoneId {
        return zoneId
    }

    fun setCurrentTimeMillis(
        value: Long,
    ) {
        currentTimeMillis = value
    }

    fun setZoneId(
        value: ZoneId,
    ) {
        zoneId = value
    }
}
