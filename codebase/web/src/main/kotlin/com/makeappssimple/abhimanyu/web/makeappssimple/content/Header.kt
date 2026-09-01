package com.makeappssimple.abhimanyu.web.makeappssimple.content

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtCols
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtContainer
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtOffsets
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtRows
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtSections
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtTexts
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.flexWrap
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
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
