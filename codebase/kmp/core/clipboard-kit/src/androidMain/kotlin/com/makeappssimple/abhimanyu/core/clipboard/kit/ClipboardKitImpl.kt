/*
 * Copyright 2025-2026 Abhimanyu
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

package com.makeappssimple.abhimanyu.core.clipboard.kit

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import org.koin.core.annotation.Single

@Single(
    binds = [ClipboardKit::class],
)
public class ClipboardKitImpl(
    private val context: Context,
) : ClipboardKit {
    override fun copyToClipboard(
        label: String,
        text: String,
    ): Boolean {
        return try {
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(
                label,
                text,
            )
            clipboardManager.setPrimaryClip(clip)
            true
        } catch (e: Exception) {
            false
        }
    }
}