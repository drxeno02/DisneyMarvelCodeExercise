package com.disney.framework.configuration.properties

import com.disney.framework.constants.TEST_API_KEY_PRIVATE
import com.disney.framework.constants.TEST_API_KEY_PRIVATE_UPDATE
import com.disney.framework.constants.TEST_API_KEY_PUBLIC
import com.disney.framework.constants.TEST_API_KEY_PUBLIC_UPDATE
import com.disney.framework.http.configuration.properties.CommonClientConfigurationProperties
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Tests to confirm ability to create and instantiate [CommonClientConfigurationPropertiesTest]
 * object and that fields set to the object are retrievable and correct.
 */
class CommonClientConfigurationPropertiesTest {

    private var commonClientConfigurationProperties: CommonClientConfigurationProperties =
        CommonClientConfigurationProperties()

    @Test
    fun setPublicApiKey_ReturnTrue() {
        // set common configuration properties
        commonClientConfigurationProperties.apiKeyPublic = TEST_API_KEY_PUBLIC
        // confirm that property is not null
        assertNotNull(commonClientConfigurationProperties.apiKeyPublic)
        // confirm that property was set correctly
        assertEquals(commonClientConfigurationProperties.apiKeyPublic, TEST_API_KEY_PUBLIC)
        // update property
        commonClientConfigurationProperties.apiKeyPublic = TEST_API_KEY_PUBLIC_UPDATE
        // confirm that property is not null
        assertNotNull(commonClientConfigurationProperties.apiKeyPublic)
        // confirm that property was set correctly
        assertEquals(commonClientConfigurationProperties.apiKeyPublic, TEST_API_KEY_PUBLIC_UPDATE)
    }

    @Test
    fun setPrivateApiKey_ReturnTrue() {
        // set common configuration properties
        commonClientConfigurationProperties.apiKeyPrivate = TEST_API_KEY_PRIVATE
        // confirm that property is not null
        assertNotNull(commonClientConfigurationProperties.apiKeyPrivate)
        // confirm that property was set correctly
        assertEquals(commonClientConfigurationProperties.apiKeyPrivate, TEST_API_KEY_PRIVATE)
        // update property
        commonClientConfigurationProperties.apiKeyPrivate = TEST_API_KEY_PRIVATE_UPDATE
        // confirm that property is not null
        assertNotNull(commonClientConfigurationProperties.apiKeyPrivate)
        // confirm that property was set correctly
        assertEquals(commonClientConfigurationProperties.apiKeyPrivate, TEST_API_KEY_PRIVATE_UPDATE)
    }
}
