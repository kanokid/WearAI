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
    val BUTTON_BACKGROUND = Color(0xFF2C2F33)
    val BUTTON_BACKGROUND_PRESSED = Color(0xFF3F4347)
    val BUTTON_BACKGROUND_LIGHT = Color(0xFFDEDEDE)
    
    // Theme Colors
    val PRIMARY_DARK = Color(0xFF4F46E5)  // Indigo
    val PRIMARY_LIGHT = Color(0xFF818CF8)  // Light Indigo
    val SECONDARY_DARK = Color(0xFF10B981)  // Emerald
    val SECONDARY_LIGHT = Color(0xFF34D399)  // Light Emerald
    
    // User Message Bubble Colors
    val USER_BUBBLE_COLOR = Color(0xFF4F46E5)  // Indigo
    val LIGHT_USER_BUBBLE_COLOR = Color(0xFF818CF8)  // Light Indigo
    
    // AI Message Bubble Colors
    val AI_BUBBLE_COLOR = Color(0xFF10B981)  // Emerald
    val LIGHT_AI_BUBBLE_COLOR = Color(0xFF34D399)  // Light Emerald
    
    // Gradient colors for user messages
    val USER_GRADIENT_START_DARK = Color(0xFF4F46E5)
    val USER_GRADIENT_END_DARK = Color(0xFF3730A3)
    val USER_GRADIENT_START_LIGHT = Color(0xFF818CF8)
    val USER_GRADIENT_END_LIGHT = Color(0xFF6366F1)
    
    // Gradient colors for AI messages
    val AI_GRADIENT_START_DARK = Color(0xFF10B981)
    val AI_GRADIENT_END_DARK = Color(0xFF059669)
    val AI_GRADIENT_START_LIGHT = Color(0xFF34D399)
    val AI_GRADIENT_END_LIGHT = Color(0xFF10B981)
    
    // Background colors
    val BACKGROUND_DARK = Color(0xFF111827)
    val BACKGROUND_LIGHT = Color(0xFFF3F4F6)
    
    // Text colors
    val TEXT_PRIMARY_DARK = Color(0xFFF9FAFB)
    val TEXT_SECONDARY_DARK = Color(0xFFD1D5DB)
    val TEXT_PRIMARY_LIGHT = Color(0xFF111827)
    val TEXT_SECONDARY_LIGHT = Color(0xFF4B5563)
    
    // Custom text colors
    val TEXT_GREEN = Color(0xFF4ADE80)
    val TEXT_PURPLE = Color(0xFFA78BFA)
    val TEXT_BLUE = Color(0xFF93C5FD)
    val TEXT_ORANGE = Color(0xFFFBBF24)
    val TEXT_PINK = Color(0xFFF472B6)
} 