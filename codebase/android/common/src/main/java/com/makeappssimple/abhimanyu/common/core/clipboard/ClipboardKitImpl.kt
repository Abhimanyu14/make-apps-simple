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

package com.makeappssimple.abhimanyu.common.core.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

internal class ClipboardKitImpl(
    private val context: Context,
) : ClipboardKit {
    /**
     * Copies the given text to the clipboard with the specified label.
     *
     * @param context The context used to access the clipboard service.
     * @param label The user-visible label for the clipboard content.
     * @param text The actual text to copy.
     * @return True if the copy succeeded, false otherwise.
     */
    override fun copyToClipboard(
        label: String,
        text: String,
    ): Boolean {
        if (text.isBlank()) {
            return false
        }
        val clipboardManager =
            (context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)
                ?: return false
        val clipData = ClipData.newPlainText(
            label,
            text
        )
        clipboardManager.setPrimaryClip(clipData)
        return true
    }
}
