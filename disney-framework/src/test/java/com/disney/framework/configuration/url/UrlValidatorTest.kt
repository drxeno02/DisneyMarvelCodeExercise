package com.disney.framework.configuration.url

import android.content.Context
import android.util.Patterns
import androidx.test.core.app.ApplicationProvider
import androidx.work.Configuration
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.disney.framework.constants.TEST_API_KEY_PRIVATE
import com.disney.framework.constants.TEST_API_KEY_PUBLIC
import com.disney.framework.constants.TEST_COMIC_ID
import com.disney.framework.http.client.DisneyApiClient
import com.disney.framework.http.configuration.ClientConfiguration
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*

@RunWith(RobolectricTestRunner::class)
class UrlValidatorTest {

    private lateinit var context: Context
    private lateinit var apiClient: DisneyApiClient
    private lateinit var clientConfiguration: ClientConfiguration

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()

        val config = Configuration.Builder()
            // Use a SynchronousExecutor here to make it easier to write tests
            .setExecutor(SynchronousExecutor())
            .build()

        // initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)

        // initialize ClientConfiguration
        clientConfiguration = ClientConfiguration.Builder()
            .setApiKeyPublic(TEST_API_KEY_PUBLIC)
            .setApiKeyPrivate(TEST_API_KEY_PRIVATE)
            .create()

        // instantiate api client
        apiClient = DisneyApiClient(
            ApplicationProvider.getApplicationContext(),
            clientConfiguration
        )
    }

    /**
     * Method is used to validate url structure and creates a matcher that will match the
     * given input against [Patterns.WEB_URL] pattern.
     *
     * @param url The url to validate.
     */
    private fun isUrlValid(url: String): Boolean {
        val urlParts: List<String> = url.split("//")
        // confirm url structure
        val isUrlStructureValid = urlParts.size < 3 &&
                (urlParts[0].equals("http:", ignoreCase = true) ||
                        urlParts[0].equals("https:", ignoreCase = true))

        return isUrlStructureValid && Patterns.WEB_URL.matcher(
            url.lowercase(Locale.getDefault())
        ).matches()
    }

    @Test
    fun validateAllRequestUrls_ReturnsTrue() {
        assertTrue(isUrlValid(clientConfiguration.getComicsUrl))
        assertTrue(isUrlValid(clientConfiguration.getComicByIdUrl.replace(
            ClientConfiguration.PLACEHOLDER_COMIC_ID, TEST_COMIC_ID
        )))
    }
}
