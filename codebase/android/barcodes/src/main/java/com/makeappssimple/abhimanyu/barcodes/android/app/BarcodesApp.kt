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

package com.makeappssimple.abhimanyu.barcodes.android.app

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.koin.compose.viewmodel.koinViewModel

// TODO(Abhi): Change to 5 once we can set app update priority when releasing from console
internal const val HIGH_PRIORITY_APP_UPDATE = 0

@Composable
internal fun BarcodesApp(
    barcodesActivityViewModel: BarcodesActivityViewModel = koinViewModel(),
) {
    rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) { activityResult ->
        barcodesActivityViewModel.logKit.logError(
            message = "activityResult $activityResult",
        )
        /*
        activityResult?.let { activityResultValue ->
            if (activityResultValue.resultCode == Activity.RESULT_OK) {
                activityViewModel.setIsUserSignedIn(true)
            } else {
                finishActivity()
            }
        }
        */
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        /*
        inAppUpdatesSetup(
            context = context,
        )
        */
    }

    BarcodesAppUI(
        barcodesActivityViewModel = barcodesActivityViewModel,
    )
}

// TODO(Abhi): In-App Updates
/*
private fun inAppUpdatesSetup(
    context: Context,
) {
    val appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(context)

    // Create a listener to track request state updates.
    val appUpdateListener = { state: InstallState ->
        if (state.installStatus() == InstallStatus.DOWNLOADING) {
            val bytesDownloaded = state.bytesDownloaded()
            val totalBytesToDownload = state.totalBytesToDownload()
            // Show update progress bar.
        } else if (state.installStatus() == InstallStatus.DOWNLOADED) {
            // unregisterAppUpdateListener(
            //     appUpdateManager = appUpdateManager,
            // )

            // After the update is downloaded, show a notification
            // and request user confirmation to restart the app.
            popupSnackbarForCompleteUpdate(
                appUpdateManager = appUpdateManager,
            )
        }
    }

    // Returns an intent object that you use to check for an update.
    val appUpdateInfoTask = appUpdateManager.appUpdateInfo

    appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
        val updateAvailability = appUpdateInfo.updateAvailability()

        if (updateAvailability == UpdateAvailability.UPDATE_AVAILABLE) {
            if (appUpdateInfo.updatePriority() >= HIGH_PRIORITY_APP_UPDATE) {
                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    startAppUpdateFlow(
                        context = context,
                        appUpdateManager = appUpdateManager,
                        appUpdateInfo = appUpdateInfo,
                        appUpdateType = AppUpdateType.IMMEDIATE,
                    )
                }
            } else {
                if ((appUpdateInfo.clientVersionStalenessDays() ?: -1) >=
                    getDaysForFlexibleAppUpdate(appUpdateInfo.updatePriority()) &&
                    appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                ) {
                    registerAppUpdateListener(
                        appUpdateManager = appUpdateManager,
                        appUpdateListener = appUpdateListener,
                    )
                    startAppUpdateFlow(
                        context = context,
                        appUpdateManager = appUpdateManager,
                        appUpdateInfo = appUpdateInfo,
                        appUpdateType = AppUpdateType.FLEXIBLE,
                    )
                }
            }
        } else if (updateAvailability == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
            startAppUpdateFlow(
                context = context,
                appUpdateManager = appUpdateManager,
                appUpdateInfo = appUpdateInfo,
                appUpdateType = AppUpdateType.IMMEDIATE,
            )
        }
    }
}

private fun startAppUpdateFlow(
    context: Context,
    appUpdateManager: AppUpdateManager,
    appUpdateInfo: AppUpdateInfo,
    appUpdateType: Int,
) {
    appUpdateManager.startUpdateFlowForResult(
        appUpdateInfo,
        appUpdateType,
        context as Activity,
        123,
    )
}

private fun getDaysForFlexibleAppUpdate(
    updatePriority: Int,
): Int {
    when (updatePriority) {
        1 -> {
            28
        }

        2 -> {
            21
        }

        3 -> {
            14
        }

        else -> {
            5
        }
    }
    return 0
}

private fun registerAppUpdateListener(
    appUpdateManager: AppUpdateManager,
    appUpdateListener: (InstallState) -> Unit,
) {
    // Before starting an update, register a listener for updates.
    appUpdateManager.registerListener(appUpdateListener)
}

private fun unregisterAppUpdateListener(
    appUpdateManager: AppUpdateManager,
    appUpdateListener: (InstallState) -> Unit,
) {
    // When status updates are no longer needed, unregister the listener.
    appUpdateManager.unregisterListener(appUpdateListener)
}

private fun popupSnackbarForCompleteUpdate(
    appUpdateManager: AppUpdateManager,
) {
    */
/*
    Snackbar.make(
        activityViewBinding.root,
        "An update has just been downloaded.",
        Snackbar.LENGTH_INDEFINITE
    ).apply {
        setAction("RESTART") {
            appUpdateManager.completeUpdate()
        }
        // setActionTextColor(resources.getColor(R.color.snackbar_action_text_color))
        show()
    }
    *//*

}
*/
