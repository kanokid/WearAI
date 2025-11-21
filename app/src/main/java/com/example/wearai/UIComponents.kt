package com.example.wearai

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
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
import androidx.wear.compose.material3.*
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.RemoteInput

/**
 * Reusable UI components for the WearAI application
 */

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
    val scale = animateFloatAsState(if (isPressed) 0.9f else 1f, label = "scale")

    Button(
        onClick = onClick,
        modifier = modifier
            .size(UIConstants.ACTION_BUTTON_SIZE)
            .scale(scale.value),
        shape = CircleShape,
        interactionSource = interactionSource,
        content = {
            Icon(
                painter = painterResource(id = iconResourceId),
                contentDescription = contentDescription,
                modifier = Modifier.size(UIConstants.ICON_SIZE)
            )
        }
    )
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
    Chip(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text) },
        icon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(UIConstants.ICON_SIZE)
            )
        },
        onClick = onClick
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
            val intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
            val remoteInputs = listOf(
                RemoteInput.Builder("input_text")
                    .setLabel(placeholder)
                    .build()
            )
            RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)
            launcher.launch(intent)
        },
        modifier = modifier.size(64.dp),
        shape = CircleShape,
        content = {
            Text(
                text = if (value.isEmpty()) placeholder else value,
                color = textColor,
                maxLines = 2,
                style = MaterialTheme.typography.caption1,
                textAlign = TextAlign.Center
            )
        }
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
    val bubbleColor = if (message.isUser) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.secondaryContainer
    }
    
    val bubbleShape = if (message.isUser) {
        RoundedCornerShape(16.dp, 16.dp, 4.dp, 16.dp)
    } else {
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 4.dp)
    }
    
    val alignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 200.dp)
                .clip(bubbleShape)
                .background(bubbleColor)
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                color = if (message.isUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.body1
            )
        }
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

    Button(
        onClick = onClick,
        modifier = Modifier
            .size(UIConstants.ACTION_BUTTON_SIZE)
            .scale(scale.value),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        interactionSource = interactionSource,
        content = {
            Icon(
                painter = painterResource(id = iconResourceId),
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier.size(UIConstants.ICON_SIZE)
            )
        }
    )
}

/**
 * A data class representing a message in the conversation
 */
data class Message(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)
