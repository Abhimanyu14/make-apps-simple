package com.makeappssimple.abhimanyu.web.makeappssimple.content

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtCols
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtContainer
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtOffsets
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtRows
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtSections
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtTexts
import kotlinx.browser.window
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.attributes.target
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.flexWrap
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingRight
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Text

@Composable
fun Header() {
    Section(
        attrs = {
            classes(WtSections.wtSectionBgGrayDark)
        },
    ) {
        Div(
            attrs = {
                classes(WtContainer.wtContainer)
                style {
                    justifyContent(JustifyContent.SpaceEvenly)
                    flexWrap(FlexWrap.Wrap)
                }
            },
        ) {
            Div(
                attrs = {
                    classes(
                        WtRows.wtRow,
                        WtRows.wtRowSizeM,
                    )
                    style {
                        justifyContent(JustifyContent.SpaceEvenly)
                        flexWrap(FlexWrap.Wrap)
                    }
                },
            ) {
                Div(
                    attrs = {
                        classes(
                            WtCols.wtCol10,
                            WtCols.wtColMd8,
                            WtCols.wtColSm12,
                            WtOffsets.wtTopOffsetSm12
                        )
                        style {
                            justifyContent(JustifyContent.SpaceEvenly)
                            flexWrap(FlexWrap.Wrap)
                        }
                    },
                ) {
                    H1(
                        attrs = {
                            classes(WtTexts.wtHeroLight)
                        },
                    ) {
                        Text(
                            value = "Make Apps Simple",
                        )
                    }
                }
                // Logo()
                // TODO: support i18n
                // LanguageButton()
            }
        }
    }
}

@Composable
private fun Logo() {
    Div(attrs = {
        classes(WtCols.wtColInline)
    }) {
        A(attrs = {
            target(ATarget.Blank)
        }, href = "https://www.jetbrains.com/") {
            Div(attrs = {
                classes("jetbrains-logo", "_logo-jetbrains-square", "_size-3")
            }) {}
        }
    }
}

@Composable
private fun LanguageButton() {
    Div(attrs = {
        classes(WtCols.wtColInline)
    }) {
        Button(attrs = {
            classes(WtTexts.wtButton, WtTexts.wtLangButton)
            onClick { window.alert("Oops! This feature is yet to be implemented") }
        }) {
            Img(src = "ic_lang.svg", attrs = {
                style {
                    paddingLeft(8.px)
                    paddingRight(8.px)
                }
            })
            Text("English")
        }
    }
}
