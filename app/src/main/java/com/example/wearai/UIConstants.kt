package com.example.wearai

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Constants used across the UI
 */
object UIConstants {
    // Button & Icon sizes
    val ACTION_BUTTON_SIZE = 48.dp
    val ICON_SIZE = 24.dp
    val LARGE_ICON_SIZE = 32.dp
    
    // Padding and sizing
    val SCREEN_PADDING = 8.dp
    val ITEM_SPACING = 8.dp
    val BUTTON_PADDING = 12.dp
    val BUBBLE_PADDING = 12.dp
    val CHIP_PADDING = 4.dp
    
    // Font sizes
    val TITLE_TEXT_SIZE = 16.sp
    val BODY_TEXT_SIZE = 14.sp
    val CAPTION_TEXT_SIZE = 12.sp
    
    // Colors
    val BUTTON_BACKGROUND = Color(0xFF005141)
    val BUTTON_BACKGROUND_PRESSED = Color(0xFF00382D)
    val BUTTON_BACKGROUND_LIGHT = Color(0xFFCCE8DE)
    
    // Theme Colors
    val PRIMARY_DARK = Color(0xFF1CE9B6)  // Green
    val PRIMARY_LIGHT = Color(0xFF00897B)  // Light Green
    
    // User Message Bubble Colors
    val USER_BUBBLE_COLOR = Color(0xFF005141)
    val LIGHT_USER_BUBBLE_COLOR = Color(0xFFCCE8DE)
    
    // AI Message Bubble Colors
    val AI_BUBBLE_COLOR = Color(0xFF344C44)
    val LIGHT_AI_BUBBLE_COLOR = Color(0xFFB0CCC2)
    
    // Gradient colors for user messages
    val USER_GRADIENT_START_DARK = Color(0xFF005141)
    val USER_GRADIENT_END_DARK = Color(0xFF00382D)
    val USER_GRADIENT_START_LIGHT = Color(0xFFCCE8DE)
    val USER_GRADIENT_END_LIGHT = Color(0xFFB0CCC2)
    
    // Gradient colors for AI messages
    val AI_GRADIENT_START_DARK = Color(0xFF344C44)
    val AI_GRADIENT_END_DARK = Color(0xFF1D352E)
    val AI_GRADIENT_START_LIGHT = Color(0xFFB0CCC2)
    val AI_GRADIENT_END_LIGHT = Color(0xFFCCE8DE)
    
    // Background colors
    val BACKGROUND_DARK = Color(0xFF191C1B)
    val BACKGROUND_LIGHT = Color(0xFFFBFDF9)
    
    // Text colors
    val TEXT_PRIMARY_DARK = Color(0xFFE1E3E1)
    val TEXT_SECONDARY_DARK = Color(0xFFBFC9C4)
    val TEXT_PRIMARY_LIGHT = Color(0xFF191C1B)
    val TEXT_SECONDARY_LIGHT = Color(0xFF3F4945)
    
    // Custom text colors
    val TEXT_GREEN = Color(0xFF1CE9B6)
    val TEXT_PURPLE = Color(0xFFADC6E8)
    val TEXT_BLUE = Color(0xFF93C5FD)
    val TEXT_ORANGE = Color(0xFFFBBF24)
    val TEXT_PINK = Color(0xFFF472B6)
} 