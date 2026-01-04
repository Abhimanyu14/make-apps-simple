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

package com.makeappssimple.abhimanyu.cosmos.design.system.android.resource

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource

public sealed class CosmosStringResource : CosmosResource {
    public data class Id(
        val id: Int,
        val args: List<Any> = emptyList(),
    ) : CosmosStringResource()

    public data class Plural(
        val id: Int,
        val count: Int,
        val args: List<Any> = emptyList(),
    ) : CosmosStringResource()

    public data class Text(
        val text: String,
    ) : CosmosStringResource()
}

public val CosmosStringResource.text: String
    @Composable
    get() {
        return when (this) {
            is CosmosStringResource.Id -> {
                stringResource(
                    id = id,
                    formatArgs = args.toTypedArray(),
                )
            }

            is CosmosStringResource.Plural -> {
                pluralStringResource(
                    id = id,
                    count = count,
                    formatArgs = args.toTypedArray(),
                )
            }

            is CosmosStringResource.Text -> {
                text
            }
        }
    }

public val emptyCosmosStringResource: CosmosStringResource =
    CosmosStringResource.Text(
        text = "",
    )

public fun CosmosStringResource?.orEmpty(): CosmosStringResource {
    return emptyCosmosStringResource
}
