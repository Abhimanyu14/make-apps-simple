package com.makeappssimple.abhimanyu.barcodes.android.core.playstorereview

import android.app.Activity
import android.content.Context
import com.google.android.play.core.review.ReviewManagerFactory

public class PlayStoreReviewHandler(
    private val context: Context,
) {
    public fun triggerInAppReview(
        onComplete: () -> Unit
    ) {
        val reviewManager = ReviewManagerFactory.create(context)
        reviewManager
            .requestReviewFlow()
            .addOnCompleteListener { request ->
                if (request.isSuccessful) {
                    // We got the ReviewInfo object
                    val reviewInfo = request.result

                    reviewManager
                        .launchReviewFlow(context as Activity, reviewInfo)
                        .addOnCompleteListener {
                            onComplete()
                        }
                } else {
                    onComplete()
                }
            }
    }
}
