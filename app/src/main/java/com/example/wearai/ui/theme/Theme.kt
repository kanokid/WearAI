package com.example.wearai.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ColorScheme

// Define expressive dark theme colors
private val DarkExpressiveColorScheme = ColorScheme(
    primary = Color(0xFF52DBCB),
    onPrimary = Color(0xFF003730),
    primaryContainer = Color(0xFF005046),
    onPrimaryContainer = Color(0xFF73F8E7),
    secondary = Color(0xFFB1CCC5),
    onSecondary = Color(0xFF1E352F),
    secondaryContainer = Color(0xFF344C45),
    onSecondaryContainer = Color(0xFFCCE8E1),
    tertiary = Color(0xFFABC7E8),
    onTertiary = Color(0xFF16314D),
    tertiaryContainer = Color(0xFF2E4865),
    onTertiaryContainer = Color(0xFFD3E6FF),
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
    primary = Color(0xFF006A5F),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF73F8E7),
    onPrimaryContainer = Color(0xFF00201C),
    secondary = Color(0xFF4B635D),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFCCE8E1),
    onSecondaryContainer = Color(0xFF07201B),
    tertiary = Color(0xFF46607E),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFD3E6FF),
    onTertiaryContainer = Color(0xFF001D36),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    background = Color(0xFFFBFDF9),
    onBackground = Color(0xFF191C1B),
    surface = Color(0xFFFBFDF9),
    onSurface = Color(0xFF191C1B),
    onSurfaceVariant = Color(0xFFDBE5E0),
    outline = Color(0xFF6F7975)
)

private val ClassicColorScheme = ColorScheme(
    primary = Color(0xFF1CE9B6),
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


@Composable
fun WearAITheme(
    darkTheme: Boolean = true,
    classicTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (classicTheme) {
        ClassicColorScheme
    } else if (darkTheme) {
        DarkExpressiveColorScheme
    } else {
        LightExpressiveColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
