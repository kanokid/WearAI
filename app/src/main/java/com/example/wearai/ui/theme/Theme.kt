package com.example.wearai.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ColorScheme

// Define expressive dark theme colors
private val DarkExpressiveColorScheme = ColorScheme(
    primary = Color(0xFF50E3C2),
    onPrimary = Color(0xFF00382D),
    primaryContainer = Color(0xFF005141),
    onPrimaryContainer = Color(0xFF72FEE0),
    secondary = Color(0xFFB0CCC2),
    onSecondary = Color(0xFF1D352E),
    secondaryContainer = Color(0xFF344C44),
    onSecondaryContainer = Color(0xFFCCE8DE),
    tertiary = Color(0xFFADC6E8),
    onTertiary = Color(0xFF16314D),
    tertiaryContainer = Color(0xFF2E4865),
    onTertiaryContainer = Color(0xFFCCE8DE),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    background = Color(0xFF191C1B),
    onBackground = Color(0xFFE1E3E1),
    surface = Color(0xFF191C1B),
    onSurface = Color(0xFFE1E3E1),
    onSurfaceVariant = Color(0xFFBFC9C4),
    outline = Color(0xFF89938E)
)

// Define expressive light theme colors
private val LightExpressiveColorScheme = ColorScheme(
    primary = Color(0xFF006B58),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF72FEE0),
    onPrimaryContainer = Color(0xFF002019),
    secondary = Color(0xFF4B635B),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFCCE8DE),
    onSecondaryContainer = Color(0xFF08201A),
    tertiary = Color(0xFF45607E),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFCCE8DE),
    onTertiaryContainer = Color(0xFF001D36),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    background = Color(0xFFFBFDF9),
    onBackground = Color(0xFF191C1B),
    surface = Color(0xFFFBFDF9),
    onSurface = Color(0xFF191C1B),
    onSurfaceVariant = Color(0xFF3F4945),
    outline = Color(0xFF6F7975)
)

@Composable
fun WearAITheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkExpressiveColorScheme
    } else {
        LightExpressiveColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
