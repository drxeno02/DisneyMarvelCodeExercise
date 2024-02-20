package com.disney.framework.client

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.Configuration
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.disney.framework.constants.TEST_API_KEY_PRIVATE
import com.disney.framework.constants.TEST_API_KEY_PRIVATE_UPDATE
import com.disney.framework.constants.TEST_API_KEY_PUBLIC
import com.disney.framework.constants.TEST_API_KEY_PUBLIC_UPDATE
import com.disney.framework.http.client.DisneyApiClient
import com.disney.framework.http.configuration.ClientConfiguration
import com.disney.framework.http.responses.internal.EmptyStateInfo
import com.disney.framework.http.responses.internal.Response
import com.disney.framework.http.responses.internal.ResponseCallback
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException
import java.util.concurrent.CountDownLatch

@RunWith(RobolectricTestRunner::class)
class DisneyApiClientTest {

    private lateinit var context: Context
    private lateinit var apiClient: DisneyApiClient
    private lateinit var clientConfiguration: ClientConfiguration
    private lateinit var mockServer: MockWebServer

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()

        val config = Configuration.Builder()
            // Use a SynchronousExecutor here to make it easier to write tests
            .setExecutor(SynchronousExecutor())
            .build()

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)

        // custom dispatcher for mock web server
        // the advantage of a dispatcher is that we get to map url paths to specific responses
        mockServer = MockWebServer()
        mockServer.start()

        // instantiate client configuration
        clientConfiguration = ClientConfiguration.Builder()
            .setApiKeyPublic(TEST_API_KEY_PUBLIC)
            .setApiKeyPrivate(TEST_API_KEY_PRIVATE)
            .create()

        // instantiate api client
        apiClient = DisneyApiClient(context, clientConfiguration)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun setClientConfiguration() {
        val newConfig = ClientConfiguration.Builder()
            .setApiKeyPublic(TEST_API_KEY_PUBLIC_UPDATE)
            .setApiKeyPrivate(TEST_API_KEY_PRIVATE_UPDATE)
            .create()
        assertNotEquals(newConfig, apiClient.getClientConfiguration())
        apiClient.updateClientConfiguration(newConfig)
        assertEquals(newConfig, apiClient.getClientConfiguration())
    }
}

/**
 * Method is used for processing request/response.
 */
class LatchedResponseCallback<T : EmptyStateInfo>(
    val responseLatch: CountDownLatch = CountDownLatch(
        1
    )
) : ResponseCallback<T> {
    override fun onSuccess(data: Response.Success<T>) {
        responseLatch.countDown()
    }

    override fun onFailure(data: Response.Failure<T>) {
        responseLatch.countDown()
    }
}
