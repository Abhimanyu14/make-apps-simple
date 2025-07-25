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

package com.makeappssimple.abhimanyu.makeappssimple.android.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.makeappssimple.abhimanyu.makeappssimple.android.app.LauncherApp
import com.makeappssimple.abhimanyu.makeappssimple.android.app.LauncherViewModel
import org.koin.compose.viewmodel.koinViewModel

public class LauncherActivity : ComponentActivity() {
    override fun onCreate(
        savedInstanceState: Bundle?,
    ) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val launcherViewModel: LauncherViewModel = koinViewModel()
            LauncherApp(
                launcherViewModel = launcherViewModel,
                handlePlatformEvent = { platformEvent ->
                    launcherViewModel.platformEventHandler.handlePlatformEvent(
                        activity = this,
                        platformEvent = platformEvent,
                    )
                },
            )
        }
    }
}
