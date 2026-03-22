package com.makeappssimple.abhimanyu.kmp.ui.content

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.kmp.ui.components.SocialIconLink
import com.makeappssimple.abhimanyu.kmp.ui.theme.AppColors
import com.makeappssimple.abhimanyu.kmp.ui.theme.AppTypography

private data class SocialLink(
    val id: String,
    val url: String,
    val title: String,
)

private fun getSocialLinks(): List<SocialLink> {
    return listOf(
        SocialLink(
            id = "linkedin",
            url = "https://www.linkedin.com/in/abhimanyu-n/",
            title = "Linkedin",
        ),
        SocialLink(
            id = "googleplaystore",
            url = "https://play.google.com/store/apps/developer?id=Abhimanyu",
            title = "Google Play Store",
        ),
        SocialLink(
            id = "github",
            url = "https://github.com/Abhimanyu14",
            title = "GitHub",
        ),
        SocialLink(
            id = "stackoverflow",
            url = "https://stackoverflow.com/users/9636037/abhimanyu",
            title = "StackOverflow",
        ),
        SocialLink(
            id = "medium",
            url = "https://abhimanyu14.medium.com/",
            title = "Medium",
        ),
        SocialLink(
            id = "leetcode",
            url = "https://leetcode.com/Abhimanyu14/",
            title = "Leetcode",
        ),
        SocialLink(
            id = "resume",
            url = "https://my.visualcv.com/abhimanyu14/",
            title = "Resume",
        ),
        SocialLink(
            id = "hashnode",
            url = "https://abhimanyu14.hashnode.dev/",
            title = "hashnode",
        ),
        SocialLink(
            id = "threads",
            url = "https://www.threads.net/@abhimanyu.n14",
            title = "Threads",
        ),
        SocialLink(
            id = "twitter",
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
                            SocialIconPlaceholder(
                                iconId = socialLink.id,
                                contentDescription = socialLink.title,
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

@Composable
private fun SocialIconPlaceholder(
    iconId: String,
    contentDescription: String,
) {
    Box(
        modifier = Modifier
            .size(
                size = 24.dp,
            )
            .clip(
                shape = CircleShape,
            )
            .background(
                color = AppColors.TextDarkTheme.copy(
                    alpha = 0.5f,
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = iconId.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
            style = AppTypography.bodyMedium,
            color = Color.White,
        )
    }
}
