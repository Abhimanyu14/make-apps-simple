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

package com.makeappssimple.abhimanyu.finance.manager.android.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.makeappssimple.abhimanyu.finance.manager.android.core.notification.NotificationConstants
import com.makeappssimple.abhimanyu.finance.manager.android.di.initKoin
import org.koin.android.ext.koin.androidContext

internal class FinanceManagerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            config = {
                androidContext(
                    androidContext = this@FinanceManagerApplication,
                )
            },
        )

        initNotificationChannel()
    }

    private fun initNotificationChannel() {
        val notificationChannel = NotificationChannel(
            NotificationConstants.CHANNEL_ID_REMINDER,
            NotificationConstants.CHANNEL_NAME_REMINDER,
            NotificationManager.IMPORTANCE_HIGH,
        )
        val notificationManager =
            (getSystemService(NOTIFICATION_SERVICE) as? NotificationManager)
                ?: return
        notificationManager.createNotificationChannel(notificationChannel)
    }
}
