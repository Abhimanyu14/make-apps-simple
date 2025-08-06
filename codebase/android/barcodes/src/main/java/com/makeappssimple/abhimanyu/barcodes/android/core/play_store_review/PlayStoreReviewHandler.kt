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

package com.makeappssimple.abhimanyu.barcodes.android.core.play_store_review

import android.app.Activity
import android.content.Context
import com.google.android.play.core.review.ReviewManagerFactory

internal class PlayStoreReviewHandler(
    private val context: Context,
) {
    fun triggerInAppReview(
        onComplete: () -> Unit
    ) {
        val reviewManager = ReviewManagerFactory.create(context)
        reviewManager
            .requestReviewFlow()
            .addOnCompleteListener { request ->
                if (request.isSuccessful) {
                    // We got the ReviewInfo object
                    val reviewInfo = request.result

                    (context as? Activity)?.let { activity ->
                        reviewManager
                            .launchReviewFlow(activity, reviewInfo)
                            .addOnCompleteListener {
                                onComplete()
                            }
                    } ?: onComplete()
                } else {
                    onComplete()
                }
            }
    }
}
