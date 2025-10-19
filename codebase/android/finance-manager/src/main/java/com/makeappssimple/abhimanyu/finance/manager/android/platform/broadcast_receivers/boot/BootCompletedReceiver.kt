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

package com.makeappssimple.abhimanyu.finance.manager.android.platform.broadcast_receivers.boot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.makeappssimple.abhimanyu.common.core.extensions.orFalse
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.alarm.AlarmKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.repository.preferences.FinanceManagerPreferencesRepository
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val bootCompletedAction = "android.intent.action.BOOT_COMPLETED"

internal class BootCompletedReceiver : BroadcastReceiver(), KoinComponent {
    val alarmKit: AlarmKit by inject()
    val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository by inject()

    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        if (intent?.action == bootCompletedAction) {
            runBlocking {
                doWork()
            }
        }
    }

    private suspend fun doWork() {
        val reminder =
            financeManagerPreferencesRepository.getReminder() ?: return
        if (reminder.isEnabled.orFalse()) {
            alarmKit.scheduleReminderAlarm()
        }
    }
}
