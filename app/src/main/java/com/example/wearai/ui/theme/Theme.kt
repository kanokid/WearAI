package com.example.wearai.ui.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material3.MaterialTheme

@Composable
fun WearAITheme(
    darkTheme: Boolean = true,
    classicTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (classicTheme) {
        ClassicColorScheme
    } else {
        if (darkTheme) DarkColorScheme else LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
