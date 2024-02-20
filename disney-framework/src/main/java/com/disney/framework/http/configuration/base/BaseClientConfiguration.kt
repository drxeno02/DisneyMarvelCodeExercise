package com.disney.framework.http.configuration.base

import com.disney.framework.http.client.BaseApiClient

/**
 * Used to abstract the attributes used in HTTP request in [BaseApiClient].
 */
abstract class BaseClientConfiguration {

    /**
     * API key is required for performing requests.
     *
     * @property apiKeyPublic API key required for performing requests.
     */
    abstract var apiKeyPublic: String?

    /**
     * API key is required for performing requests.
     *
     * @property apiKeyPrivate API key required for performing requests.
     */
    abstract var apiKeyPrivate: String?
}
