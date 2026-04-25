package com.makeappssimple.abhimanyu.kmp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.kmp.ui.content.Header
import com.makeappssimple.abhimanyu.kmp.ui.content.PageFooter
import com.makeappssimple.abhimanyu.kmp.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Header()
            PageFooter()
        }
    }
}
