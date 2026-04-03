package com.makeappssimple.abhimanyu.kmp.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.kmp.ui.theme.AppColors

@Composable
fun Header(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppColors.GrayDark,
            )
            .padding(
                vertical = 22.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 22.dp,
                ),
        ) {
            Text(
                text = "Make Apps Simple",
                style = MaterialTheme.typography.displayLarge,
                color = AppColors.GrayLight,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    vertical = 24.dp,
                ),
            )
        }
    }
}
