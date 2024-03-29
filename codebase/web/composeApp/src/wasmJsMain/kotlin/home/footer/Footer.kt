package home.footer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Footer(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 2.dp,
        )
        Row {
            Text(
                text = "Created by Abhimanyu",
                fontSize = 20.sp,
                modifier = Modifier.padding(
                    all = 16.dp,
                ),
            )
            Text(
                text = "•",
                fontSize = 20.sp,
                modifier = Modifier.padding(
                    all = 16.dp,
                ),
            )
            Text(
                text = "Using Compose Web",
                fontSize = 20.sp,
                modifier = Modifier.padding(
                    all = 16.dp,
                ),
            )
        }
    }
}
