package com.makeappssimple.abhimanyu.kmp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.cosmos.design.system.resources.Res
import com.makeappssimple.abhimanyu.cosmos.design.system.resources.lexend
import org.jetbrains.compose.resources.Font

@Composable
fun getAppTypography(): Typography {
    val LexendFontFamily = FontFamily(
        Font(Res.font.lexend, FontWeight.Light),
        Font(Res.font.lexend, FontWeight.Normal),
        Font(Res.font.lexend, FontWeight.Bold),
        Font(Res.font.lexend, FontWeight.Black),
    )

    return Typography(
        displayLarge = TextStyle(
            fontFamily = LexendFontFamily,
            fontWeight = FontWeight.Black,
            fontSize = 60.sp,
            lineHeight = 64.sp,
            letterSpacing = (-1.5).sp,
        ),
        displayMedium = TextStyle(
            fontFamily = LexendFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 31.sp,
            lineHeight = 40.sp,
            letterSpacing = (-0.5).sp,
        ),
        headlineLarge = TextStyle(
            fontFamily = LexendFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 21.sp,
            lineHeight = 28.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = LexendFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 28.sp,
            lineHeight = 40.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = LexendFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 28.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = LexendFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            lineHeight = 24.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = LexendFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
        ),
        labelLarge = TextStyle(
            fontFamily = LexendFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            lineHeight = 24.sp,
        ),
    )
}

