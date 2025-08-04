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

package com.makeappssimple.abhimanyu.common.core.jsonreader.fake

import android.net.Uri
import com.makeappssimple.abhimanyu.common.core.jsonreader.JsonReaderKit

public class FakeJsonReaderKitImpl : JsonReaderKit {
    private val assetsJson = """
        {
          "account": "assets"
        }
    """.trimIndent()
    private val fileJson = """
        {
          "account": "assets"
        }
    """.trimIndent()

    override fun readJsonFromAssets(
        fileName: String,
    ): String {
        return assetsJson
    }

    override fun readJsonFromFile(
        uri: Uri,
    ): String {
        return fileJson
    }
}
