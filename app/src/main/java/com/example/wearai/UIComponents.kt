package com.example.wearai

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.*
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.RemoteInput

/**
 * Reusable UI components for the WearAI application
 */

/**
 * A simple action button with a circular background
 */
@Composable
fun SimpleActionButton(
    onClick: () -> Unit,
    iconResourceId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(UIConstants.ACTION_BUTTON_SIZE)
            .clip(CircleShape)
            .background(UIConstants.BUTTON_BACKGROUND)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconResourceId),
            contentDescription = contentDescription,
            modifier = Modifier.size(UIConstants.ICON_SIZE)
        )
    }
}

/**
 * An animated action button that scales when pressed
 */
@Composable
fun AnimatedActionIconButton(
    onClick: () -> Unit,
    iconResourceId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = animateFloatAsState(if (isPressed) 0.9f else 1f)

    Box(
        modifier = modifier
            .size(UIConstants.ACTION_BUTTON_SIZE)
            .clip(CircleShape)
            .background(UIConstants.BUTTON_BACKGROUND)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .scale(scale.value),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconResourceId),
            contentDescription = contentDescription,
            modifier = Modifier.size(UIConstants.ICON_SIZE)
        )
    }
}

/**
 * A modern chip for settings options
 */
@Composable
fun SettingsChip(
    text: String,
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    textColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(UIConstants.BUTTON_BACKGROUND)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(24.dp)
            )
            
            Text(
                text = text,
                color = textColor,
                fontSize = UIConstants.BODY_TEXT_SIZE,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * A modern message bubble with gradient background
 */
@Composable
fun GradientMessageBubble(
    message: Message,
    isDarkTheme: Boolean,
    textColor: Color
) {
    val bubbleColor = if (message.isUser) {
        if (isDarkTheme) UIConstants.PRIMARY_DARK else UIConstants.PRIMARY_LIGHT
    } else {
        if (isDarkTheme) UIConstants.BUTTON_BACKGROUND else UIConstants.BUTTON_BACKGROUND_LIGHT
    }
    
    val bubbleShape = if (message.isUser) {
        RoundedCornerShape(16.dp, 16.dp, 4.dp, 16.dp)
    } else {
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 4.dp)
    }
    
    val alignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = alignment
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            shape = bubbleShape,
            colors = CardDefaults.cardColors(
                containerColor = bubbleColor
            ),
            content = {
                Box(
                    modifier = Modifier
                        .padding(12.dp)
                ) {
                    Text(
                        text = message.text,
                        color = textColor,
                        fontSize = UIConstants.BODY_TEXT_SIZE
                    )
                }
            },
            onClick = {}
        )
    }
}

/**
 * A circular FAB for the main actions
 */
@Composable
fun CircularActionButton(
    onClick: () -> Unit,
    @DrawableRes iconResourceId: Int,
    contentDescription: String,
    backgroundColor: Color
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isPressed) 0.9f else 1f, label = "")
    
    Box(
        modifier = Modifier
            .scale(scale)
            .size(64.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    onClick()
                    isPressed = false
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconResourceId),
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(32.dp)
        )
    }
}

/**
 * An animated action button with background color
 */
@Composable
fun AnimatedActionButton(
    onClick: () -> Unit,
    @DrawableRes iconResourceId: Int,
    contentDescription: String,
    backgroundColor: Color
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = animateFloatAsState(if (isPressed) 0.9f else 1f, label = "")

    Box(
        modifier = Modifier
            .size(UIConstants.ACTION_BUTTON_SIZE)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .scale(scale.value),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconResourceId),
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(UIConstants.ICON_SIZE)
        )
    }
}

/**
 * A color selector chip for the settings screen
 */
@Composable
fun ColorSelectionChip(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderSize = if (isSelected) 3.dp else 0.dp
    
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Color.White)
            .padding(borderSize)
            .clip(CircleShape)
            .background(color)
            .clickable(onClick = onClick)
    )
}

/**
 * A circular text input field for the watch that launches the system input
 */
@Composable
fun TextInputCircle(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val results = RemoteInput.getResultsFromIntent(result.data)
            results?.let {
                val input = it.getCharSequence("input_text")?.toString() ?: ""
                if (input.isNotEmpty()) {
                    onValueChange(input)
                }
            }
        }
    }

    Button(
        onClick = {
            // Launch the system text input
            val intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
            val remoteInputs = listOf(
                RemoteInput.Builder("input_text")
                    .setLabel(placeholder)
                    .build()
            )
            RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)
            launcher.launch(intent)
        },
        label = {
            Text(
                text = if (value.isEmpty()) placeholder else value,
                color = textColor,
                maxLines = 2,
                fontSize = 12.sp
            )
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = UIConstants.BUTTON_BACKGROUND
        ),
        modifier = modifier
    )
}

/**
 * A message bubble for displaying chat messages
 */
@Composable
fun MessageBubble(
    message: Message,
    isDarkTheme: Boolean,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    val userGradient = Brush.linearGradient(
        colors = if (isDarkTheme) 
            listOf(UIConstants.USER_BUBBLE_COLOR, UIConstants.USER_BUBBLE_COLOR.copy(alpha = 0.7f)) 
        else 
            listOf(UIConstants.LIGHT_USER_BUBBLE_COLOR, UIConstants.LIGHT_USER_BUBBLE_COLOR.copy(alpha = 0.7f))
    )
    
    val aiGradient = Brush.linearGradient(
        colors = if (isDarkTheme) 
            listOf(UIConstants.AI_BUBBLE_COLOR, UIConstants.AI_BUBBLE_COLOR.copy(alpha = 0.7f)) 
        else 
            listOf(UIConstants.LIGHT_AI_BUBBLE_COLOR, UIConstants.LIGHT_AI_BUBBLE_COLOR.copy(alpha = 0.7f))
    )
    
    val bubbleAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    
    val bubbleShape = RoundedCornerShape(
        topStart = if (message.isUser) 8.dp else 2.dp,
        topEnd = if (message.isUser) 2.dp else 8.dp,
        bottomStart = 8.dp,
        bottomEnd = 8.dp
    )
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = bubbleAlignment
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 200.dp)
                .clip(bubbleShape)
                .background(if (message.isUser) userGradient else aiGradient)
                .padding(8.dp)
        ) {
            Text(
                text = message.text,
                color = textColor,
                fontSize = 12.sp
            )
        }
    }
}

/**
 * A data class representing a message in the conversation
 */
data class Message(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
) 