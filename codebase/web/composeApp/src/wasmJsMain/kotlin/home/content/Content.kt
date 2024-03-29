package home.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Content(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(
                state = rememberScrollState(),
            )
            .fillMaxWidth()
            .padding(
                all = 16.dp,
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        val sites = listOf(
            Site(
                title = "LinkedIn",
                link = "https://www.linkedin.com/in/abhimanyu-n/",
            ),
            Site(
                title = "Google Play Store",
                link = "https://play.google.com/store/apps/developer?id=Abhimanyu",
            ),
            Site(
                title = "GitHub",
                link = "https://github.com/Abhimanyu14",
            ),
            Site(
                title = "Medium",
                link = "https://abhimanyu14.medium.com/",
            ),
            Site(
                title = "Leetcode",
                link = "https://leetcode.com/Abhimanyu14/",
            ),
            Site(
                title = "Hashnode",
                link = "https://abhimanyu14.hashnode.dev/",
            ),
            Site(
                title = "LinkTree",
                link = "https://linktr.ee/abhimanyu.n",
            ),
            Site(
                title = "Resume",
                link = "https://my.visualcv.com/abhimanyu14/",
            ),
        )
        sites.forEach { site ->
            SiteDetails(
                site = site,
                modifier = Modifier.padding(
                    vertical = 16.dp,
                )
            )
        }
    }
}

data class Site(
    val title: String,
    val link: String,
)

@Composable
private fun SiteDetails(
    modifier: Modifier = Modifier,
    site: Site,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = site.title,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
        )
        Text(
            text = site.link,
            fontSize = 16.sp,
        )
    }
}
