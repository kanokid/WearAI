package com.example.wearai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.*
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.wearai.ui.theme.WearAITheme
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

/**
 * WearAI Application
 * 
 * A Wear OS app that uses Gemini AI to generate responses to user questions.
 * Features include:
 * - Multiple model selection
 * - Dark/light theme toggle
 * - Text color customization
 * - Conversation history
 * - Rotary scrolling support (via standard Wear OS scrolling)
 */

// API key will be retrieved from resources
// This is safer than hardcoding it in the source code

// Navigation routes
private object Routes {
    const val HOME = "home"
    const val CONVERSATION = "conversation"
    const val SETTINGS = "settings"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            // App state
            val isDarkTheme = remember { mutableStateOf(true) }
            val useClassicTheme = remember { mutableStateOf(false) }
            val selectedModel = remember { mutableStateOf("gemini-2.5-flash") }
            val conversationHistory = remember { mutableStateListOf<Message>() }
            
            WearAIApp(
                isDarkTheme = isDarkTheme,
                useClassicTheme = useClassicTheme,
                selectedModel = selectedModel,
                conversationHistory = conversationHistory
            )
        }
    }
}

@Composable
fun ModelSelector(
    models: List<String>,
    selectedModel: String,
    onModelSelected: (String) -> Unit,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    if (expanded) {
        ScalingLazyColumn(
            modifier = modifier.fillMaxSize(),
        ) {
            items(models) { model ->
                CompactButton(
                    onClick = { 
                        onModelSelected(model)
                        expanded = false
                    },
                    label = {
                        Text(
                            text = model,
                            color = textColor,
                            style = MaterialTheme.typography.caption1
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = UIConstants.BUTTON_BACKGROUND
                    )
                )
            }
        }
    } else {
        CompactButton(
            onClick = { expanded = true },
            label = {
                Text(
                    text = "Model: ${selectedModel.substringAfter("gemini-")}",
                    color = textColor,
                    maxLines = 1,
                    style = MaterialTheme.typography.caption1
                )
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_model),
                    contentDescription = "Select model",
                    modifier = Modifier.size(UIConstants.ICON_SIZE)
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = UIConstants.BUTTON_BACKGROUND
            )
        )
    }
}

@Composable
fun WearAIApp(
    isDarkTheme: MutableState<Boolean>,
    useClassicTheme: MutableState<Boolean>,
    selectedModel: MutableState<String>,
    conversationHistory: SnapshotStateList<Message>
) {
    WearAITheme(darkTheme = isDarkTheme.value, classicTheme = useClassicTheme.value) {
        val navController = rememberSwipeDismissableNavController()
        val scope = rememberCoroutineScope()
        val question = remember { mutableStateOf("") }
        val isLoading = remember { mutableStateOf(false) }
        val textColor = Color.White
        val backgroundColor = if (isDarkTheme.value) Color.Black else Color.White
        
        // Available models
        val models = listOf("gemini-2.5-flash", "gemini-2.5-flash-experimental")

        // Get API key from resources
        val apiKey = stringResource(id = R.string.gemini_api_key)

        // Set up AI model
        val generativeModel = remember(selectedModel.value, apiKey) {
            GenerativeModel(
                modelName = selectedModel.value,
                apiKey = apiKey
            )
        }
        
        // Function to handle sending messages
        val sendMessage = {
            if (question.value.isNotEmpty()) {
                // Add user message to history
                val userMessage = Message(
                    text = question.value,
                    isUser = true
                )
                conversationHistory.add(userMessage)
                
                // Start loading
                isLoading.value = true
                
                // Use coroutine to get AI response
                scope.launch {
                    try {
                        val response = generativeModel.generateContent(question.value)
                        val aiMessage = Message(
                            text = response.text ?: "Sorry, I couldn't generate a response.",
                            isUser = false
                        )
                        conversationHistory.add(aiMessage)
                    } catch (e: Exception) {
                        val errorMessage = Message(
                            text = "Error: ${e.message}",
                            isUser = false
                        )
                        conversationHistory.add(errorMessage)
                    } finally {
                        isLoading.value = false
                        question.value = ""
                        navController.navigate(Routes.CONVERSATION)
                    }
                }
            }
        }
        
        AppScaffold {
            SwipeDismissableNavHost(
                navController = navController,
                startDestination = Routes.HOME
            ) {
                composable(Routes.HOME) {
                    HomeScreen(
                        models = models,
                        selectedModel = selectedModel.value,
                        onModelSelected = { selectedModel.value = it },
                        textColor = textColor,
                        backgroundColor = backgroundColor,
                        question = question,
                        isLoading = isLoading,
                        onSendQuestion = sendMessage,
                        onThemeToggle = { isDarkTheme.value = !isDarkTheme.value },
                        onViewConversation = { navController.navigate(Routes.CONVERSATION) },
                        onOpenSettings = { navController.navigate(Routes.SETTINGS) }
                    )
                }

                composable(Routes.CONVERSATION) {
                    ConversationScreen(
                        messages = conversationHistory,
                        onBackClick = { navController.popBackStack() },
                        isDarkTheme = isDarkTheme.value,
                        textColor = textColor
                    )
                }

                composable(Routes.SETTINGS) {
                    SettingsScreen(
                        isDarkTheme = isDarkTheme,
                        useClassicTheme = useClassicTheme,
                        selectedModel = selectedModel,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    models: List<String>,
    selectedModel: String,
    onModelSelected: (String) -> Unit,
    textColor: Color,
    @Suppress("UNUSED_PARAMETER") backgroundColor: Color, // Kept for API compatibility
    question: MutableState<String>,
    isLoading: MutableState<Boolean>,
    onSendQuestion: () -> Unit,
    onThemeToggle: () -> Unit,
    onViewConversation: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val scrollState = rememberScrollState()

    ScreenScaffold(
        timeText = { TimeText() },
        scrollIndicator = {
            ScrollIndicator(state = scrollState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Action buttons row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Theme toggle button
                AnimatedActionIconButton(
                    onClick = onThemeToggle,
                    iconResourceId = R.drawable.ic_theme_toggle,
                    contentDescription = "Toggle theme"
                )


                // History button
                AnimatedActionIconButton(
                    onClick = onViewConversation,
                    iconResourceId = R.drawable.ic_history,
                    contentDescription = "View conversation history"
                )

                // Settings button
                AnimatedActionIconButton(
                    onClick = onOpenSettings,
                    iconResourceId = R.drawable.ic_settings,
                    contentDescription = "Open settings"
                )
            }
            
            // Text input
            TextInputCircle(
                placeholder = "Ask Gemini...",
                value = question.value,
                onValueChange = { question.value = it },
                textColor = textColor
            )
            
            // Send button
            if (question.value.isNotEmpty()) {
                AnimatedActionIconButton(
                    onClick = onSendQuestion,
                    iconResourceId = R.drawable.ic_send,
                    contentDescription = "Send message"
                )
            }
            
            // Loading indicator
            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = textColor,
                    strokeWidth = 2.dp
                )
            }
        }
    }
}

@Composable
fun ConversationScreen(
    messages: List<Message>,
    onBackClick: () -> Unit,
    isDarkTheme: Boolean,
    textColor: Color
) {
    val listState = rememberScalingLazyListState()
    
    ScreenScaffold(
        timeText = { TimeText() },
        scrollIndicator = { ScrollIndicator(state = listState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Back button
            Button(
                onClick = { onBackClick() },
                modifier = Modifier.padding(PaddingValues(top = 8.dp, start = 8.dp))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Go back"
                )
            }
            
            // Message list with standard Wear OS scrolling
            ScalingLazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                state = listState,
                autoCentering = AutoCenteringParams(itemIndex = 0)
            ) {
                items(messages) { message ->
                    MessageBubble(
                        message = message,
                        isDarkTheme = isDarkTheme,
                        textColor = textColor
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(
    isDarkTheme: MutableState<Boolean>,
    useClassicTheme: MutableState<Boolean>,
    selectedModel: MutableState<String>,
    onNavigateBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    // Use the same models as defined in WearAIApp
    val models = listOf("gemini-2.5-flash", "gemini-2.5-flash-experimental")
    
    val backgroundColor = if (isDarkTheme.value) UIConstants.BACKGROUND_DARK else UIConstants.BACKGROUND_LIGHT
    val textColor = Color.White
    
    ScreenScaffold(
        timeText = {
            TimeText()
        },
        scrollIndicator = {
            ScrollIndicator(
                state = scrollState
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PaddingValues(top = 40.dp, bottom = 16.dp, start = 16.dp, end = 16.dp))
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(UIConstants.ITEM_SPACING)
            ) {
                // Settings title
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.title3,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Theme selector
                SettingsChip(
                    text = if (isDarkTheme.value) "Dark Theme" else "Light Theme",
                    icon = R.drawable.ic_theme_toggle,
                    onClick = { isDarkTheme.value = !isDarkTheme.value },
                    textColor = textColor
                )
                
                // Classic theme toggle
                ToggleChip(
                    checked = useClassicTheme.value,
                    onCheckedChange = { useClassicTheme.value = it },
                    label = { Text("Use Classic Theme") },
                    colors = ToggleChipDefaults.toggleChipColors(
                        checkedEndBackgroundColor = MaterialTheme.colorScheme.primary
                    )
                )

                // Model selector section
                Text(
                    text = "Model",
                    style = MaterialTheme.typography.body1,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Model selection chips
                models.forEach { model ->
                    val isSelected = selectedModel.value == model
                    val chipColor = if (isSelected) UIConstants.PRIMARY_DARK else UIConstants.BUTTON_BACKGROUND
                    
                    CompactButton(
                        onClick = { selectedModel.value = model },
                        label = {
                            Text(
                                text = model.substringAfter("gemini-"),
                                color = textColor,
                                style = MaterialTheme.typography.caption1
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = chipColor
                        )
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Back button
                AnimatedActionButton(
                    onClick = onNavigateBack,
                    iconResourceId = R.drawable.ic_back,
                    contentDescription = "Back to home",
                    backgroundColor = UIConstants.PRIMARY_DARK
                )
            }
        }
    }
}
