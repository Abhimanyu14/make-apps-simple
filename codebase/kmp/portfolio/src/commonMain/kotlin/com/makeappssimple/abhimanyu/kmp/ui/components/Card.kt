package com.makeappssimple.abhimanyu.kmp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp

@Composable
fun SocialIconLink(
    icon: @Composable () -> Unit,
    url: String,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

    IconButton(
        onClick = {
            uriHandler.openUri(url)
        },
        modifier = modifier
            .padding(
                all = 12.dp,
            ),
    ) {
        icon()
    }
}
