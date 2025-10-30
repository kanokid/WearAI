package com.example.wearai

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import com.google.common.truth.Truth.assertThat

@RunWith(RobolectricTestRunner::class)
class ApiKeyTest {

    @Test
    fun testApiKeyIsNotEmpty() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val apiKey = context.getString(R.string.gemini_api_key)
        assertThat(apiKey).isNotEmpty()
    }
}
