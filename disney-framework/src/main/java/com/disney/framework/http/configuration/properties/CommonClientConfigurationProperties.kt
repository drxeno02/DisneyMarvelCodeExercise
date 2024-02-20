package com.disney.framework.http.configuration.properties

import com.disney.framework.http.client.DisneyApiClient

/**
 * Common configuration properties. This class prevents redundancy of common configuration
 * attributes. This is an extensible class for setting API client related properties and
 * configuration. A configuration collects information necessary to perform operations and
 * instantiate the [DisneyApiClient].
 */
class CommonClientConfigurationProperties {

    /**
     * API key is required for performing requests.
     *
     * @property apiKeyPublic API key required for performing requests.
     */
    var apiKeyPublic: String? = null

    /**
     * API key is required for performing requests.
     *
     * @property apiKeyPrivate API key required for performing requests.
     */
    var apiKeyPrivate: String? = null
}
