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

package com.makeappssimple.abhimanyu.finance.manager.android.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Daily 09:30 PM.
 */
public object ReminderConstants {
    public const val DEFAULT_REMINDER_HOUR: Int = 21
    public const val DEFAULT_REMINDER_MIN: Int = 30
}

@Serializable
public data class Reminder(
    @SerialName(value = "is_enabled")
    val isEnabled: Boolean = false,

    @SerialName(value = "hour")
    val hour: Int = ReminderConstants.DEFAULT_REMINDER_HOUR,

    @SerialName(value = "min")
    val min: Int = ReminderConstants.DEFAULT_REMINDER_MIN,
)
