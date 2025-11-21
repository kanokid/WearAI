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
 */

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

        val models = listOf("gemini-2.5-flash", "gemini-2.5-flash-experimental")
        val apiKey = stringResource(id = R.string.gemini_api_key)

        val generativeModel = remember(selectedModel.value, apiKey) {
            GenerativeModel(modelName = selectedModel.value, apiKey = apiKey)
        }

        val sendMessage = {
            if (question.value.isNotEmpty()) {
                conversationHistory.add(Message(text = question.value, isUser = true))
                isLoading.value = true

                scope.launch {
                    try {
                        val response = generativeModel.generateContent(question.value)
                        conversationHistory.add(Message(text = response.text ?: "No response", isUser = false))
                    } catch (e: Exception) {
                        conversationHistory.add(Message(text = "Error: ${e.message}", isUser = false))
                    } finally {
                        isLoading.value = false
                        question.value = ""
                        navController.navigate(Routes.CONVERSATION)
                    }
                }
            }
        }

        SwipeDismissableNavHost(navController = navController, startDestination = Routes.HOME) {
            composable(Routes.HOME) {
                HomeScreen(
                    question = question,
                    isLoading = isLoading,
                    onSendQuestion = sendMessage,
                    onViewConversation = { navController.navigate(Routes.CONVERSATION) },
                    onOpenSettings = { navController.navigate(Routes.SETTINGS) }
                )
            }
            composable(Routes.CONVERSATION) {
                ConversationScreen(
                    messages = conversationHistory,
                    onBackClick = { navController.popBackStack() },
                    isDarkTheme = isDarkTheme.value
                )
            }
            composable(Routes.SETTINGS) {
                SettingsScreen(
                    isDarkTheme = isDarkTheme,
                    useClassicTheme = useClassicTheme,
                    selectedModel = selectedModel,
                    models = models,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    question: MutableState<String>,
    isLoading: MutableState<Boolean>,
    onSendQuestion: () -> Unit,
    onViewConversation: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val scrollState = rememberScrollState()

    ScreenScaffold(
        timeText = { TimeText() },
        scrollIndicator = { ScrollIndicator(state = scrollState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Center)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AnimatedActionIconButton(
                    onClick = onViewConversation,
                    iconResourceId = R.drawable.ic_history,
                    contentDescription = "View conversation history"
                )
                AnimatedActionIconButton(
                    onClick = onOpenSettings,
                    iconResourceId = R.drawable.ic_settings,
                    contentDescription = "Open settings"
                )
            }

            TextInputCircle(
                placeholder = "Ask Gemini...",
                value = question.value,
                onValueChange = { question.value = it },
                textColor = Color.White
            )

            if (question.value.isNotEmpty()) {
                AnimatedActionIconButton(
                    onClick = onSendQuestion,
                    iconResourceId = R.drawable.ic_send,
                    contentDescription = "Send message"
                )
            }

            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    indicatorColor = MaterialTheme.colorScheme.primary,
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
    isDarkTheme: Boolean
) {
    val listState = rememberScalingLazyListState()

    ScreenScaffold(
        timeText = { TimeText() },
        scrollIndicator = { PositionIndicator(scalingLazyListState = listState) }
    ) {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            autoCentering = AutoCenteringParams(itemIndex = 0)
        ) {
            item {
                Button(
                    onClick = onBackClick,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Go back"
                    )
                }
            }
            items(messages) { message ->
                MessageBubble(
                    message = message,
                    isDarkTheme = isDarkTheme,
                    textColor = Color.White
                )
            }
        }
    }
}

@Composable
fun SettingsScreen(
    isDarkTheme: MutableState<Boolean>,
    useClassicTheme: MutableState<Boolean>,
    selectedModel: MutableState<String>,
    models: List<String>,
    onNavigateBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    ScreenScaffold(
        timeText = { TimeText() },
        scrollIndicator = { ScrollIndicator(state = scrollState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(UIConstants.ITEM_SPACING)
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.title3,
                textAlign = TextAlign.Center
            )

            SettingsChip(
                text = if (isDarkTheme.value) "Dark Theme" else "Light Theme",
                icon = R.drawable.ic_theme_toggle,
                onClick = { isDarkTheme.value = !isDarkTheme.value },
                textColor = Color.White
            )

            ToggleChip(
                checked = useClassicTheme.value,
                onCheckedChange = { useClassicTheme.value = it },
                label = { Text("Use Classic Theme") }
            )

            Text(
                text = "Model",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )

            models.forEach { model ->
                val isSelected = selectedModel.value == model
                CompactButton(
                    onClick = { selectedModel.value = model },
                    label = { Text(model.substringAfter("gemini-")) },
                    colors = if (isSelected) ButtonDefaults.primaryButtonColors() else ButtonDefaults.secondaryButtonColors()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedActionButton(
                onClick = onNavigateBack,
                iconResourceId = R.drawable.ic_back,
                contentDescription = "Back to home",
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}