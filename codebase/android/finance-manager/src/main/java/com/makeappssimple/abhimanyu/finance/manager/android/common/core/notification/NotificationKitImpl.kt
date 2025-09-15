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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.graphics.toColorInt
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.app.AppKit
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

public class NotificationKitImpl(
    private val appKit: AppKit,
    private val context: Context,
    private val logKit: LogKit,
) : NotificationKit {
    override fun scheduleNotification() {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
                ?: return

        val intent = appKit.getMainActivityIntent().apply {
            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

        logKit.logError(
            message = "Sending Notification : ${System.currentTimeMillis()}",
        )
        val notification =
            NotificationCompat.Builder(
                context,
                NotificationConstants.CHANNEL_ID_REMINDER
            )
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.notification)
                .setColor("#DAF5D7".toColorInt())
                .setColorized(true)
                .setContentTitle(context.getString(R.string.finance_manager_notification_title))
                .setContentText(context.getString(R.string.finance_manager_notification_text))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
        notificationManager.notify(1, notification)
        logKit.logError(
            message = "Notification : ${System.currentTimeMillis()}",
        )
    }
}
