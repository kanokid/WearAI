import java.util.Properties
import java.io.FileInputStream
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.wearai"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.wearai"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
        
        // Define API key as a string resource with the provided key
        resValue("string", "gemini_api_key", localProperties.getProperty("gemini_api_key"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }
    
    kotlin {
        // Use the Kotlin DSL for compiler options instead of kotlinOptions
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            freeCompilerArgs.addAll(listOf(
                "-opt-in=kotlin.RequiresOptIn",
                "-Xskip-prerelease-check"
            ))
            languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
        }
    }
    
    buildFeatures {
        // Enable Compose
        compose = true
        buildConfig = true
    }
    
    // Compose compiler is now handled by the Kotlin Compose plugin
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/versions/9/previous-compilation-data.bin"
        }
    }
}

dependencies {
    // Core Android dependencies
    // Core Android dependencies
    implementation(libs.core.ktx)
    implementation(libs.wear)
    implementation(libs.activity.compose)

    // Compose dependencies from BOM

    // Compose dependencies from BOM
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material)
    implementation(libs.foundation)
    implementation(libs.runtime)
    implementation(libs.runtime.livedata)

    // Lifecycle and ViewModel

    // Lifecycle and ViewModel
    implementation(libs.lifecycle.viewmodel.compose)

    // Generative AI

    // Generative AI
    implementation(libs.generativeai)
    implementation(libs.kotlinx.coroutines.android)

    // Wear OS specific dependencies
    implementation("androidx.wear.compose:compose-material3:1.0.1")
    implementation(libs.compose.foundation)
    implementation(libs.compose.navigation)
    implementation("androidx.wear:wear-input:1.1.0")

    // Horologist library for rotary input support
    implementation("com.google.android.horologist:horologist-compose-material:0.6.23")

    // Material icons (using BOM for version management)
    implementation("androidx.compose.material:material-icons-extended")

    // Splash screen API
    implementation(libs.core.splashscreen)

    // Desugaring for newer Java APIs on older Android versions

    coreLibraryDesugaring(libs.desugar)

    // Debug tools
    debugImplementation(libs.ui.tooling)

    // Recommended to add test dependencies
    // testImplementation(libs.junit)
    // androidTestImplementation(libs.androidx.test.ext.junit)
}