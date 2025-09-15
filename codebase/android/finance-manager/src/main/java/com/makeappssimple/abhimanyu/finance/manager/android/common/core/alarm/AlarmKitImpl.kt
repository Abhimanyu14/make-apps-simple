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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.boot.BootCompletedReceiver
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Reminder
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.time.TimeChangedReceiver
import java.time.LocalTime

public class AlarmKitImpl(
    private val context: Context,
    private val dateTimeKit: DateTimeKit,
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val logKit: LogKit,
) : AlarmKit {
    override suspend fun cancelReminderAlarm(): Boolean {
        var isAlarmCancelled = cancelAlarm()
        if (!isAlarmCancelled) {
            return false
        }

        disableBroadcastReceivers()

        isAlarmCancelled = updateIsReminderEnabledInPreferences(
            isReminderEnabled = false,
        )

        if (isAlarmCancelled) {
            logKit.logError(
                message = "Alarm cancelled",
            )
        }
        return isAlarmCancelled
    }

    override suspend fun scheduleReminderAlarm(): Boolean {
        val reminder =
            financeManagerPreferencesRepository.getReminder() ?: return false
        val initialAlarmTimestamp = getInitialAlarmTimestamp(
            reminder = reminder,
        )
        var isAlarmSet = scheduleAlarm(
            initialAlarmTimestamp = initialAlarmTimestamp,
        )
        if (!isAlarmSet) {
            return false
        }

        enableBroadcastReceivers()

        isAlarmSet = updateIsReminderEnabledInPreferences(
            isReminderEnabled = true,
        )

        if (isAlarmSet) {
            logKit.logError(
                message = "Alarm set for : ${reminder.hour}:${reminder.min}",
            )
        }
        return isAlarmSet
    }

    // region alarm
    private fun scheduleAlarm(
        initialAlarmTimestamp: Long,
    ): Boolean {
        val alarmManager = getAlarmManager() ?: return false
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            getAlarmReceiverIntent(),
            PendingIntent.FLAG_IMMUTABLE,
        )
        alarmManager.setRepeating(
            AlarmManager.RTC,
            initialAlarmTimestamp,
            AlarmManager.INTERVAL_DAY,
            pendingIntent,
        )
        return true
    }

    private fun cancelAlarm(): Boolean {
        val alarmManager = getAlarmManager() ?: return false
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            getAlarmReceiverIntent(),
            PendingIntent.FLAG_IMMUTABLE,
        )
        alarmManager.cancel(pendingIntent)
        return true
    }

    private fun getAlarmManager(): AlarmManager? {
        return context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    }

    private fun getAlarmReceiverIntent(): Intent {
        return Intent(context, AlarmReceiver::class.java)
    }
    // endregion

    // region broadcast receivers
    private fun enableBroadcastReceivers() {
        enableBootCompleteReceiver()
        enableTimeChangedReceiver()
    }

    private fun disableBroadcastReceivers() {
        disableBootCompleteReceiver()
        disableTimeChangedReceiver()
    }

    private fun enableBootCompleteReceiver() {
        context.packageManager.setComponentEnabledSetting(
            ComponentName(context, BootCompletedReceiver::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun disableBootCompleteReceiver() {
        context.packageManager.setComponentEnabledSetting(
            ComponentName(context, BootCompletedReceiver::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun enableTimeChangedReceiver() {
        context.packageManager.setComponentEnabledSetting(
            ComponentName(context, TimeChangedReceiver::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun disableTimeChangedReceiver() {
        context.packageManager.setComponentEnabledSetting(
            ComponentName(context, TimeChangedReceiver::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }
    // endregion

    // region preferences
    private suspend fun updateIsReminderEnabledInPreferences(
        isReminderEnabled: Boolean,
    ): Boolean {
        return financeManagerPreferencesRepository.updateIsReminderEnabled(
            isReminderEnabled = isReminderEnabled,
        )
    }
    // endregion

    // region date time
    private fun getInitialAlarmTimestamp(
        reminder: Reminder,
    ): Long {
        return dateTimeKit.getTimestamp(
            time = LocalTime.of(reminder.hour, reminder.min),
        )
    }
    // endregion
}
