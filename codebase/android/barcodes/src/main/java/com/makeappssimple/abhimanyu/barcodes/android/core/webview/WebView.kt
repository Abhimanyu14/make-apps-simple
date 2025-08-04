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

package com.makeappssimple.abhimanyu.barcodes.android.core.webview

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import com.makeappssimple.abhimanyu.barcodes.android.core.common.constants.TestTags
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNullOrBlank

@Composable
internal fun WebView(
    modifier: Modifier = Modifier,
    url: String,
    onPageLoadingCompleted: (title: String) -> Unit
) {
    var currentPageUrl by remember {
        mutableStateOf("")
    }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = (object : WebViewClient() {
                    override fun onPageFinished(
                        view: WebView,
                        url: String,
                    ) {
                        onPageLoadingCompleted(view.title.orEmpty())
                    }
                })
            }
        },
        update = { webView ->
            if (url.isNotNullOrBlank() && currentPageUrl != url) {
                currentPageUrl = url
                webView.loadUrl(url)
            }
        },
        modifier = modifier
            .fillMaxSize()
            .testTag(
                tag = TestTags.SCREEN_CONTENT_WEB_VIEW,
            ),
    )
}
