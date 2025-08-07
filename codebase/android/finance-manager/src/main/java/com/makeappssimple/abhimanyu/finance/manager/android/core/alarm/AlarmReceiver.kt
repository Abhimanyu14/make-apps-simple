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

package com.makeappssimple.abhimanyu.finance.manager.android.core.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.makeappssimple.abhimanyu.common.log_kit.LogKit
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

public class AlarmReceiver : BroadcastReceiver(), KoinComponent {
    public val logKit: LogKit by inject()

    // @Inject
    // public lateinit var notificationKit: NotificationKit

    override fun onReceive(
        context: Context,
        intent: Intent?,
    ) {
        logKit.logError(
            message = "Alarm received : ${System.currentTimeMillis()}",
        )
        // notificationKit.scheduleNotification()
    }
}
