package com.example.wearai.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Colors

// Dark theme colors
private val DarkColorPalette = Colors(
    primary = Color(0xFF1DE9B6),
    primaryVariant = Color(0xFF00B686),
    secondary = Color(0xFF03DAC5),
    secondaryVariant = Color(0xFF0AC9BE),
    error = Color(0xFFCF6679),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onError = Color.Black
)

// Light theme colors
private val LightColorPalette = Colors(
    primary = Color(0xFF00897B),
    primaryVariant = Color(0xFF005B4F),
    secondary = Color(0xFF03DAC5),
    secondaryVariant = Color(0xFF0AC9BE),
    error = Color(0xFFB00020),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onError = Color.White
)

@Composable
fun WearAITheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
} 