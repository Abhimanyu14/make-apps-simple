package com.makeappssimple.abhimanyu.kmp.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.kmp.ui.components.SocialIconLink
import com.makeappssimple.abhimanyu.kmp.ui.theme.AppColors
import com.makeappssimple.abhimanyu.kmp.ui.theme.AppTypography
import make_apps_simple.composeapp.generated.resources.Res
import make_apps_simple.composeapp.generated.resources.ic_github
import make_apps_simple.composeapp.generated.resources.ic_google_play_store
import make_apps_simple.composeapp.generated.resources.ic_hashnode
import make_apps_simple.composeapp.generated.resources.ic_leetcode
import make_apps_simple.composeapp.generated.resources.ic_linkedin
import make_apps_simple.composeapp.generated.resources.ic_medium
import make_apps_simple.composeapp.generated.resources.ic_pdf
import make_apps_simple.composeapp.generated.resources.ic_stackoverflow
import make_apps_simple.composeapp.generated.resources.ic_threads
import make_apps_simple.composeapp.generated.resources.ic_x
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

private data class SocialLink(
    val icon: DrawableResource,
    val url: String,
    val title: String,
)

private fun getSocialLinks(): List<SocialLink> {
    return listOf(
        SocialLink(
            icon = Res.drawable.ic_linkedin,
            url = "https://www.linkedin.com/in/abhimanyu-n/",
            title = "Linkedin",
        ),
        SocialLink(
            icon = Res.drawable.ic_google_play_store,
            url = "https://play.google.com/store/apps/developer?id=Abhimanyu",
            title = "Google Play Store",
        ),
        SocialLink(
            icon = Res.drawable.ic_github,
            url = "https://github.com/Abhimanyu14",
            title = "GitHub",
        ),
        SocialLink(
            icon = Res.drawable.ic_stackoverflow,
            url = "https://stackoverflow.com/users/9636037/abhimanyu",
            title = "StackOverflow",
        ),
        SocialLink(
            icon = Res.drawable.ic_medium,
            url = "https://abhimanyu14.medium.com/",
            title = "Medium",
        ),
        SocialLink(
            icon = Res.drawable.ic_leetcode,
            url = "https://leetcode.com/Abhimanyu14/",
            title = "Leetcode",
        ),
        SocialLink(
            icon = Res.drawable.ic_pdf,
            url = "https://my.visualcv.com/abhimanyu14/",
            title = "Resume",
        ),
        SocialLink(
            icon = Res.drawable.ic_hashnode,
            url = "https://abhimanyu14.hashnode.dev/",
            title = "hashnode",
        ),
        SocialLink(
            icon = Res.drawable.ic_threads,
            url = "https://www.threads.net/@abhimanyu.n14",
            title = "Threads",
        ),
        SocialLink(
            icon = Res.drawable.ic_x,
            url = "https://twitter.com/AbhimanyuN14",
            title = "Twitter",
        ),
    )
}

@Composable
fun PageFooter(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppColors.GrayDark,
            )
            .padding(
                vertical = 24.dp,
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 22.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Social Links : ",
                    style = AppTypography.bodyLarge,
                    color = AppColors.TextDarkTheme,
                )
                Spacer(
                    modifier = Modifier.width(
                        width = 8.dp,
                    ),
                )
                getSocialLinks().forEach { socialLink ->
                    SocialIconLink(
                        icon = {
                            Image(
                                painter = painterResource(
                                    resource = socialLink.icon,
                                ),
                                contentDescription = socialLink.title,
                                colorFilter = ColorFilter.tint(
                                    color = AppColors.TextDarkTheme,
                                ),
                                modifier = Modifier
                                    .size(
                                        size = 24.dp,
                                    ),
                            )
                        },
                        url = socialLink.url,
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(
                    height = 48.dp,
                ),
            )

            Text(
                text = "Developed using Compose Multiplatform",
                style = AppTypography.bodyMedium,
                color = AppColors.TextPale,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                ),
            )
        }
    }
}

