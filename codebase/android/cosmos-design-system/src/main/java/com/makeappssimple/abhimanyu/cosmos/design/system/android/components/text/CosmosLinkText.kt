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

package com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import kotlinx.collections.immutable.ImmutableList

// TODO(Abhi): Remove data class
public data class CosmosLinkTextData(
    val text: String,
    val tag: String? = null,
    val annotation: String? = null,
    val onClick: ((str: AnnotatedString.Range<String>) -> Unit)? = null,
)

@Composable
public fun CosmosLinkText(
    modifier: Modifier = Modifier,
    cosmosLinkTextData: ImmutableList<CosmosLinkTextData>,
) {
    val annotatedString = createAnnotatedString(
        data = cosmosLinkTextData,
    )

    ClickableText(
        text = annotatedString,
        style = CosmosAppTheme.typography.bodyMedium,
        onClick = { offset ->
            cosmosLinkTextData.forEach { annotatedStringData ->
                if (annotatedStringData.tag != null && annotatedStringData.annotation != null) {
                    annotatedString.getStringAnnotations(
                        tag = annotatedStringData.tag,
                        start = offset,
                        end = offset,
                    )
                        .firstOrNull()
                        ?.let {
                            annotatedStringData.onClick?.invoke(it)
                        }
                }
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun createAnnotatedString(
    data: ImmutableList<CosmosLinkTextData>,
): AnnotatedString {
    return buildAnnotatedString {
        data.forEach { linkTextData ->
            if (linkTextData.tag != null && linkTextData.annotation != null) {
                pushStringAnnotation(
                    tag = linkTextData.tag,
                    annotation = linkTextData.annotation,
                )
                withStyle(
                    style = SpanStyle(
                        color = CosmosAppTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                    ),
                ) {
                    append(linkTextData.text)
                }
                pop()
            } else {
                append(linkTextData.text)
            }
        }
    }
}
