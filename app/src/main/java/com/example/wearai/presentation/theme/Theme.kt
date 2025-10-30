package com.example.wearai.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme
import com.example.wearai.UIConstants

// Define custom theme colors for the application using our new color constants
private val WearAILightColors = Colors(
    primary = UIConstants.PRIMARY_LIGHT,
    primaryVariant = UIConstants.PRIMARY_LIGHT.copy(alpha = 0.8f),
    secondary = UIConstants.SECONDARY_LIGHT,
    secondaryVariant = UIConstants.SECONDARY_LIGHT.copy(alpha = 0.8f),
    error = Color(0xFFFF5252),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onError = Color.Black,
    background = UIConstants.BACKGROUND_LIGHT,
    onBackground = UIConstants.TEXT_PRIMARY_LIGHT,
    surface = UIConstants.BACKGROUND_LIGHT,
    onSurface = UIConstants.TEXT_PRIMARY_LIGHT
)

private val WearAIDarkColors = Colors(
    primary = UIConstants.PRIMARY_DARK,
    primaryVariant = UIConstants.PRIMARY_DARK.copy(alpha = 0.8f),
    secondary = UIConstants.SECONDARY_DARK,
    secondaryVariant = UIConstants.SECONDARY_DARK.copy(alpha = 0.8f),
    error = Color(0xFFFF8A80),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onError = Color.White,
    background = UIConstants.BACKGROUND_DARK,
    onBackground = UIConstants.TEXT_PRIMARY_DARK,
    surface = UIConstants.BACKGROUND_DARK,
    onSurface = UIConstants.TEXT_PRIMARY_DARK
)

/**
 * Custom theme for the WearAI application
 * 
 * @param darkTheme Whether to use dark theme colors
 * @param content The content to be themed
 */
@Composable
fun WearAITheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    // Use dark theme by default for Wear OS
    val colors = if (darkTheme) WearAIDarkColors else WearAILightColors
    
    MaterialTheme(
        colors = colors,
        content = content
    )
}