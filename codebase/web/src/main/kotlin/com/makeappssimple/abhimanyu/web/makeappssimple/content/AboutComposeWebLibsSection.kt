package com.makeappssimple.abhimanyu.web.makeappssimple.content

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtCols
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtOffsets
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtRows
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtSections
import com.makeappssimple.abhimanyu.web.makeappssimple.style.WtTexts
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*
import com.makeappssimple.abhimanyu.web.makeappssimple.components.Card
import com.makeappssimple.abhimanyu.web.makeappssimple.components.ContainerInSection
import com.makeappssimple.abhimanyu.web.makeappssimple.components.LinkOnCard
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.attributes.target
import org.jetbrains.compose.web.css.paddingTop

data class CardWithListPresentation(
    val title: String,
    val list: List<String>,
    val links: List<LinkOnCard> = emptyList()
)

private fun createAboutComposeWebCards(): List<CardWithListPresentation> {
    return listOf(
        CardWithListPresentation(
            title = "Composable DOM API",
            list = listOf(
                "Express your design and layout in terms of DOM elements and HTML tags",
                "Use a type-safe HTML DSL to build your UI representation",
                "Get full control over the look and feel of your application by creating stylesheets with a typesafe CSS DSL",
                "Integrate with other JavaScript libraries via DOM subtrees"
            )
        ),
        CardWithListPresentation(
            title = "Multiplatform Widgets With Web Support",
            list = listOf(
                "Use and build Compose widgets that work on Android, Desktop, and Web by utilizing Kotlin's expect-actual mechanisms to provide platform-specific implementations",
                "Experiment with a set of layout primitives and APIs that mimic the features you already know from Compose for Desktop and Android"
            )
        )
    )
}

private fun createFeaturesList(): List<String> {
    return listOf(
        "Same reactive engine that is used on Android/Desktop allows using a common codebase.",
        "Framework for rich UI creation for Kotlin/JS.",
        "Convenient Kotlin DOM DSL that covers all common frontend development scenarios.",
        "Comprehensive CSS-in-Kotlin/JS API."
    )
}


@Composable
private fun FeatureDescriptionBlock(description: String) {
    Div(attrs = {
        classes(WtCols.wtCol3, WtCols.wtColMd6, WtCols.wtColSm12, WtOffsets.wtTopOffset48)
    }) {
        Img(src = "compose_bullet.svg")
        P(attrs = {
            classes(WtTexts.wtText1, WtTexts.wtText1HardnessHard, WtOffsets.wtTopOffset12)
        }) {
            Text(description)
        }
    }
}

@Composable
fun ComposeWebLibraries() {
    ContainerInSection(WtSections.wtSectionBgGrayLight) {
        H2(attrs = { classes(WtTexts.wtH2) }) {
            Text("Building user interfaces with Compose for Web")
        }

        Div(attrs = {
            classes(WtRows.wtRow, WtRows.wtRowSizeM)
        }) {
            Div(attrs = {
                classes(WtCols.wtCol6, WtCols.wtColMd6, WtCols.wtColSm12, WtOffsets.wtTopOffset24)
            }) {
                P(attrs = {
                    classes(WtTexts.wtText1)
                }) {
                    Text("Compose for Web allows you to build reactive user interfaces for the web in Kotlin, using the concepts and APIs of Jetpack Compose to express the state, behavior, and logic of your application.")
                }
            }
        }

        Div(attrs = {
            classes(WtRows.wtRow, WtRows.wtRowSizeM, WtOffsets.wtTopOffset24)
        }) {
            createFeaturesList().forEach {
                FeatureDescriptionBlock(it)
            }
        }

        A(attrs = {
            classes(WtTexts.wtButton, WtTexts.wtButtonThemeLight, WtOffsets.wtTopOffset48, WtOffsets.wtTopOffsetSm24)
            target(ATarget.Blank)
        }, href = "https://github.com/jetbrains/compose-jb#compose-for-web") {
            Text("See all features")
        }
    }
}

@Composable
private fun CardWithList(card: CardWithListPresentation) {
    Card(
        title = card.title,
        links = card.links
    ) {
        Ul(attrs = {
            classes(WtTexts.wtText2)
        }) {
            card.list.forEachIndexed { _, it ->
                Li({
                    style {
                        paddingTop(24.px)
                    }
                }) { Text(it) }
            }
        }
    }
}
