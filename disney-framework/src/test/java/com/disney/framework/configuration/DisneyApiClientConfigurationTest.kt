package com.disney.framework.configuration

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.work.Configuration
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.disney.framework.constants.TEST_API_KEY_PRIVATE
import com.disney.framework.constants.TEST_API_KEY_PRIVATE_UPDATE
import com.disney.framework.constants.TEST_API_KEY_PUBLIC
import com.disney.framework.constants.TEST_API_KEY_PUBLIC_UPDATE
import com.disney.framework.http.configuration.ClientConfiguration
import com.disney.framework.http.provider.DisneyApiClientProvider
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DisneyApiClientConfigurationTest {
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()

        // set config
        val config = Configuration.Builder()
            // use a SynchronousExecutor here to make it easier to write tests
            .setExecutor(SynchronousExecutor())
            .build()

        // initialize WorkManager for instrumentation tests
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
        // reset any pre-initialized instances of DisneyApiClient
        DisneyApiClientProvider.destroy()

        // initialize ClientConfiguration
        val clientConfiguration = ClientConfiguration.Builder()
            .setApiKeyPublic(TEST_API_KEY_PUBLIC)
            .setApiKeyPrivate(TEST_API_KEY_PRIVATE)
            .create()
        // initialize DisneyApiClientProvider
        DisneyApiClientProvider.initialize(
            context,
            clientConfiguration
        )
    }

    @After
    fun destroy() {
        // reset instance for DisneyApiClient
        DisneyApiClientProvider.destroy()
    }

    @Test
    fun valid_ClientConfiguration_ReturnsTrue() {
        val apiClient = DisneyApiClientProvider.getInstance()
        // retrieve ClientConfiguration
        val clientConfiguration = apiClient.getClientConfiguration()
        // client configuration should not be null
        assertNotNull(clientConfiguration)

        // confirm information
        assertEquals(TEST_API_KEY_PUBLIC, clientConfiguration.apiKeyPublic)
        assertEquals(TEST_API_KEY_PRIVATE, clientConfiguration.apiKeyPrivate)
    }

    @Test
    fun valid_UpdateClientConfiguration_ReturnsTrue() {
        val apiClient = DisneyApiClientProvider.getInstance()
        // create updated ClientConfiguration
        val clientConfiguration = ClientConfiguration.Builder()
            .setApiKeyPublic(TEST_API_KEY_PUBLIC_UPDATE)
            .setApiKeyPrivate(TEST_API_KEY_PRIVATE_UPDATE)
            .create()
        // update api client with new configuration
        apiClient.updateClientConfiguration(clientConfiguration)
        // retrieve ClientConfiguration
        val updatedClientConfiguration = apiClient.getClientConfiguration()

        // confirm information
        assertEquals(TEST_API_KEY_PUBLIC_UPDATE, updatedClientConfiguration.apiKeyPublic)
        assertEquals(TEST_API_KEY_PRIVATE_UPDATE, updatedClientConfiguration.apiKeyPrivate)
    }
}
