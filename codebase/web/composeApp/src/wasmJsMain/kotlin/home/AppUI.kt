package home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import home.content.Content
import home.footer.Footer
import home.header.Header
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal fun AppUI() {
    Column(
        modifier = Modifier
            .background(
                color = Color.Transparent,
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(
            modifier = Modifier,
        )
        Content(
            modifier = Modifier
                .weight(
                    weight = 1F,
                ),
        )
        Footer(
            modifier = Modifier,
        )
    }
}
