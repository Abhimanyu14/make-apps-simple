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

package com.makeappssimple.abhimanyu.common.core.json_writer

import android.content.Context
import android.net.Uri
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

private object MyJsonWriterImplConstants {
    const val WRITE_MODE = "w"
}

public class JsonWriterKitImpl(
    private val context: Context,
) : JsonWriterKit {
    override fun writeJsonToFile(
        jsonString: String,
        uri: Uri,
    ): Boolean {
        return try {
            context.contentResolver.openFileDescriptor(
                uri,
                MyJsonWriterImplConstants.WRITE_MODE,
            )?.use {
                FileOutputStream(it.fileDescriptor).use { fileOutputStream ->
                    fileOutputStream.write(jsonString.toByteArray())
                }
            } ?: return false
            true
        } catch (
            fileNotFoundException: FileNotFoundException,
        ) {
            fileNotFoundException.printStackTrace()
            false
        } catch (
            ioException: IOException,
        ) {
            ioException.printStackTrace()
            false
        }
    }
}
