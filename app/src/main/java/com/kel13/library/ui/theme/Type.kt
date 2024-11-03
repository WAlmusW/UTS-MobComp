package com.kel13.library.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kel13.library.R

// Create a FontFamily for Kaushan Script
val KaushanScript = FontFamily(
    Font(R.font.kaushan_script, FontWeight.Bold, FontStyle.Normal)
)

val Imprima = FontFamily(
    Font(R.font.imprima, FontWeight.Bold, FontStyle.Normal)
)

// Set of Material typography styles with Kaushan Script for titles
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = KaushanScript,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Imprima,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Imprima,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)
