# WearAI: Gemini on your wrist

<p align="center">
  <img src="app/src/main/res/mipmap-anydpi-v26/ic_launcher_round.xml" alt="WearAI App Icon" width="150"/>
</p>

<p align="center">
  <strong>A modern, open-source Wear OS application that brings the power of Google's Gemini AI to your smartwatch.</strong>
</p>

---

## Overview

WearAI is a beautifully designed and user-friendly application for Wear OS devices, built with the latest Material 3 components. It allows you to interact with Google's powerful Gemini generative AI models directly from your wrist, providing a seamless and convenient way to get answers to your questions, brainstorm ideas, and more.

The app is built with a focus on a clean, intuitive user interface and a robust, modern architecture. It serves as an excellent example of a well-structured, modern Android application for wearable devices.

## Features

- **Seamless Gemini Integration:** Interact with the `gemini-2.5-flash` model for fast and accurate responses.
- **Modern Material 3 UI:** A beautifully crafted interface built with the latest Wear Compose Material 3 components, providing a polished and intuitive user experience.
- **Conversation History:** Keep track of your past conversations with the AI.
- **Customizable Themes:** Switch between a sleek dark theme and a vibrant "classic" green theme to personalize your experience.
- **Secure API Key Handling:** Your Gemini API key is stored securely in a local properties file and is not tracked by version control.
- **Splash Screen:** A smooth and elegant splash screen to welcome you to the app.

## Technology Stack

- **Kotlin:** The official language for modern Android development.
- **Jetpack Compose for Wear OS:** A modern declarative UI toolkit for building beautiful, native Wear OS apps.
-   **Wear Compose Material 3:** The latest design system for creating modern, beautiful user interfaces on wearable devices.
- **Google Generative AI for Android:** The official SDK for integrating Gemini models into your Android applications.
- **Gradle with Kotlin DSL:** A modern and flexible build system.

## Setup and Installation

To get the WearAI project up and running on your local machine, please follow these steps:

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/your-username/wear-ai.git
    ```

2.  **Create `local.properties`:**
    In the root of the project, create a file named `local.properties`. This file is intentionally not tracked by Git to keep your API key secure.

3.  **Add Your Gemini API Key:**
    Inside the `local.properties` file, add your Gemini API key in the following format:
    ```properties
    gemini_api_key="YOUR_GEMINI_API_KEY"
    ```
    You can obtain a free API key from the [Google AI Studio](https://aistudio.google.com/app/apikey).

4.  **Open in Android Studio:**
    Open the project in the latest version of Android Studio.

5.  **Build and Run:**
    Build the project and run it on a Wear OS emulator or a physical device.

## Screenshots

*(Coming soon!)*

## Contributing

Contributions are always welcome! If you have any ideas, suggestions, or bug reports, please feel free to open an issue or submit a pull request.

---

<p align="center">
  <em>This project was developed with the assistance of an AI software engineer.</em>
</p>
