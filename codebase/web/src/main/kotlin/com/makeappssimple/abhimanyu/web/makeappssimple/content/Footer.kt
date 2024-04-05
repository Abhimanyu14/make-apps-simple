package com.makeappssimple.abhimanyu.web.makeappssimple.content

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtCols
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtContainer
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtOffsets
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtRows
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtSections
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtTexts
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.attributes.target
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.boxSizing
import org.jetbrains.compose.web.css.flexShrink
import org.jetbrains.compose.web.css.flexWrap
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Footer
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun PageFooter() {
    Footer(
        attrs = {
            style {
                flexShrink(0)
                boxSizing("border-box")
            }
        },
    ) {
        Section(
            attrs = {
                classes(WtSections.wtSectionBgGrayDark)
                style {
                    padding(24.px, 0.px)
                }
            },
        ) {
            Div(
                attrs = { classes(WtContainer.wtContainer) },
            ) {
                Div(
                    attrs = {
                        classes(WtRows.wtRow, WtRows.wtRowSizeM, WtRows.wtRowSmAlignItemsCenter)
                        style {
                            justifyContent(JustifyContent.Center)
                            flexWrap(FlexWrap.Wrap)
                        }
                    },
                ) {
                    Div(
                        attrs = {
                            classes(WtCols.wtColInline)
                        },
                    ) {
                        P(
                            attrs = {
                                classes(WtTexts.wtText1, WtTexts.wtText1ThemeDark)
                            },
                        ) {
                            Text(
                                value = "Social Links : ",
                            )
                        }
                    }
                    Div(
                        attrs = {
                            classes(WtCols.wtColInline)
                        },
                    ) {
                        getSocialLinks().map { socialLink ->
                            SocialIconLink(
                                socialLink = socialLink,
                            )
                        }
                    }
                }
                CopyrightInFooter()
            }
        }
    }
}

@Composable
private fun CopyrightInFooter() {
    Div(
        attrs = {
            classes(
                WtRows.wtRow,
                WtRows.wtRowSizeM,
                WtRows.wtRowSmAlignItemsCenter,
                WtOffsets.wtTopOffset48,
            )
            style {
                justifyContent(JustifyContent.SpaceEvenly)
                flexWrap(FlexWrap.Wrap)
                padding(0.px, 12.px)
            }
        },
    ) {
        Span(
            attrs = {
                classes(
                    WtTexts.wtText3,
                    WtTexts.wtTextPale,
                )
            },
        ) {
            Text(
                value = "Developed using Compose HTML",
            )
        }
    }
}

@Composable
private fun SocialIconLink(
    socialLink: SocialLink,
) {
    A(
        attrs = {
            classes(WtTexts.wtSocialButtonItem)
            target(ATarget.Blank)
        },
        href = socialLink.url,
    ) {
        Img(
            src = socialLink.iconSvg,
            attrs = {
                classes(WtTexts.wtSocialIcon)
            },
        )
    }
}

private data class SocialLink(
    val id: String,
    val url: String,
    val title: String,
    val iconSvg: String
)

private fun getSocialLinks(): List<SocialLink> {
    return listOf(
        SocialLink(
            id = "linkedin",
            url = "https://www.linkedin.com/in/abhimanyu-n/",
            title = "Linkedin",
            iconSvg = "ic_linkedin.svg",
        ),
        SocialLink(
            id = "googleplaystore",
            url = "https://play.google.com/store/apps/developer?id=Abhimanyu",
            title = "Google Play Store",
            iconSvg = "ic_google_play_store.svg",
        ),
        SocialLink(
            id = "github",
            url = "https://github.com/Abhimanyu14",
            title = "GitHub",
            iconSvg = "ic_github.svg",
        ),
        SocialLink(
            id = "stackoverflow",
            url = "https://stackoverflow.com/users/9636037/abhimanyu",
            title = "StackOverflow",
            iconSvg = "ic_stackoverflow.svg",
        ),
        SocialLink(
            id = "medium",
            url = "https://abhimanyu14.medium.com/",
            title = "Medium",
            iconSvg = "ic_medium.svg",
        ),
        SocialLink(
            id = "leetcode",
            url = "https://leetcode.com/Abhimanyu14/",
            title = "Leetcode",
            iconSvg = "ic_leetcode.png",
        ),
        SocialLink(
            id = "resume",
            url = "https://my.visualcv.com/abhimanyu14/",
            title = "Resume",
            iconSvg = "ic_pdf.png",
        ),
        SocialLink(
            id = "hashnode",
            url = "https://abhimanyu14.hashnode.dev/",
            title = "hashnode",
            iconSvg = "ic_hashnode.svg",
        ),
        SocialLink(
            id = "threads",
            url = "https://www.threads.net/@abhimanyu.n14",
            title = "Threads",
            iconSvg = "ic_threads.svg",
        ),
        SocialLink(
            id = "twitter",
            url = "https://twitter.com/AbhimanyuN14",
            title = "Twitter",
            iconSvg = "ic_x.svg",
        ),
    )
}
