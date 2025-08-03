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

package com.makeappssimple.abhimanyu.finance.manager.android.core.boot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.makeappssimple.abhimanyu.finance.manager.android.core.alarm.AlarmKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.MyPreferencesRepository
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

public class BootCompletedReceiver : BroadcastReceiver(), KoinComponent {
    public val alarmKit: AlarmKit by inject()
    public val myPreferencesRepository: MyPreferencesRepository by inject()

    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            runBlocking {
                doWork()
            }
        }
    }

    private suspend fun doWork() {
        val reminder = myPreferencesRepository.getReminder() ?: return
        if (reminder.isEnabled.orFalse()) {
            alarmKit.scheduleReminderAlarm()
        }
    }
}
