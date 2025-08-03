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

package com.makeappssimple.abhimanyu.finance.manager.android.core.common.util.document

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.constants.MimeTypeConstants
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.datetime.getSystemDefaultZoneId
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.extensions.formattedDateAndTime
import java.time.Instant
import java.time.ZoneId

public class CreateJsonDocument : ActivityResultContracts.CreateDocument(
    mimeType = MimeTypeConstants.JSON,
) {
    override fun createIntent(
        context: Context,
        input: String,
    ): Intent {
        return super.createIntent(
            context = context,
            input = input,
        ).apply {
            putExtra(
                Intent.EXTRA_TITLE,
                getFormattedDateAndTime(),
            )
        }
    }
}

// TODO(Abhi): To inject this method
private fun getFormattedDateAndTime(
    timestamp: Long = Instant.now().toEpochMilli(),
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return Instant
        .ofEpochMilli(timestamp)
        .formattedDateAndTime(
            zoneId = zoneId,
        )
}
