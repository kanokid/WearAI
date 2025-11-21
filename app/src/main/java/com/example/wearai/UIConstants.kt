package com.example.wearai

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

/**
 * Constants used throughout the application's UI
 */
object UIConstants {
    // Sizing
    val ACTION_BUTTON_SIZE = 48.dp
    val ICON_SIZE = 24.dp
    val ITEM_SPACING = 8.dp

    // Typography (kept for reference, but prefer MaterialTheme.typography)
    val TITLE_TEXT_SIZE = 20.sp
    val BODY_TEXT_SIZE = 14.sp
    val CAPTION_TEXT_SIZE = 12.sp
    
    // Legacy Colors (kept for reference, but prefer MaterialTheme.colorScheme)
    val BACKGROUND_DARK = Color(0xFF121212)
    val BACKGROUND_LIGHT = Color(0xFFF5F5F5)
    val BUTTON_BACKGROUND = Color(0xFF333333)
    val PRIMARY_DARK = Color(0xFF00C853)
}
