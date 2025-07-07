package com.makeappssimple.abhimanyu.barcodes.android.core.common.datetime

import java.time.ZoneId

public interface DateTimeKit {
    /**
     * Sample format - 2023-Mar-30, 08-24 AM
     */
    public fun getFormattedDateAndTime(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    public fun getSystemDefaultZoneId(): ZoneId
}
