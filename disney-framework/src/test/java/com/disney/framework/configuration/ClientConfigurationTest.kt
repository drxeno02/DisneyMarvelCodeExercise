package com.disney.framework.configuration

import com.disney.framework.constants.TEST_API_KEY_PRIVATE
import com.disney.framework.constants.TEST_API_KEY_PUBLIC
import com.disney.framework.http.configuration.ClientConfiguration
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Tests to confirm ability to create and instantiate [ClientConfiguration] object and that fields
 * set to the object are retrievable and correct.
 */
class ClientConfigurationTest {

    @Test
    fun valid_ClientConfiguration_ReturnsTrue() {
        // initialize ClientConfiguration
        val clientConfiguration = ClientConfiguration.Builder()
            .setApiKeyPublic(TEST_API_KEY_PUBLIC)
            .setApiKeyPrivate(TEST_API_KEY_PRIVATE)
            .create()
        // client configuration should not be null
        assertNotNull(clientConfiguration)

        // confirm information
        assertEquals(TEST_API_KEY_PUBLIC, clientConfiguration.apiKeyPublic)
        assertEquals(TEST_API_KEY_PRIVATE, clientConfiguration.apiKeyPrivate)
    }

    @Test(expected = IllegalArgumentException::class)
    fun initialize_ClientConfiguration_MissingPublicApiKey_IllegalArgumentException() {
        // initialize ClientConfiguration
        ClientConfiguration.Builder()
            .setApiKeyPrivate(TEST_API_KEY_PRIVATE)
            .create()
    }

    @Test(expected = IllegalArgumentException::class)
    fun initialize_ClientConfiguration_MissingPrivateApiKey_IllegalArgumentException() {
        // initialize ClientConfiguration
        ClientConfiguration.Builder()
            .setApiKeyPublic(TEST_API_KEY_PUBLIC)
            .create()
    }

    @Test(expected = IllegalArgumentException::class)
    fun initialize_ClientConfiguration_MissingBothApiKeys_IllegalArgumentException() {
        // initialize ClientConfiguration
        ClientConfiguration.Builder()
            .create()
    }
}
