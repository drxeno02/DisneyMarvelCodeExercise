package com.disney.framework.http.configuration

import com.disney.framework.http.client.DisneyApiClient
import com.disney.framework.http.configuration.base.BaseClientConfiguration
import com.disney.framework.http.configuration.properties.CommonClientConfigurationProperties

// exceptions
internal const val ILLEGAL_ARGUMENT_API_KEY_PUBLIC =
    "Failed to read `apiKeyPublic`. This field can not be empty."
internal const val ILLEGAL_ARGUMENT_API_KEY_PRIVATE =
    "Failed to read `apiKeyPrivate`. This field can not be empty."

/**
 * Client configuration properties used for [DisneyApiClient]. This is an extensible class for
 * setting API client related properties and configuration. A configuration collects information
 * necessary to perform operations and instantiate the [DisneyApiClient].
 *
 * @property commonConfiguration Common configuration properties.
 * @constructor
 */
data class ClientConfiguration(
    private val commonConfiguration: CommonClientConfigurationProperties?
) : BaseClientConfiguration() {

    /**
     * API key is required for performing requests.
     *
     * @property apiKeyPublic API key required for performing requests.
     */
    override var apiKeyPublic: String?
        get() = commonConfiguration?.apiKeyPublic
        set(value) {
            commonConfiguration?.apiKeyPublic = value
        }

    /**
     * API key is required for performing requests.
     *
     * @property apiKeyPrivate API key required for performing requests.
     */
    override var apiKeyPrivate: String?
        get() = commonConfiguration?.apiKeyPrivate
        set(value) {
            commonConfiguration?.apiKeyPrivate = value
        }

    // build encapsulated urls
    internal val getComicsUrl = "$BASE_URL$PATH_GET_COMICS"
    internal val getComicByIdUrl = "$BASE_URL$PATH_GET_COMIC_BY_ID"

    companion object {
        internal const val BASE_URL = "https://gateway.marvel.com/"

        // placeholders
        internal const val PLACEHOLDER_COMIC_ID = "{comicId}"

        // encapsulated url paths
        private const val PATH_GET_COMICS = "v1/public/comics"
        private const val PATH_GET_COMIC_BY_ID = "v1/public/comics/$PLACEHOLDER_COMIC_ID"
    }

    /**
     * Builder pattern is a creational design pattern. It means it solves problems
     * related to object creation.
     *
     * <p>Builder pattern is used to create instance of very complex object having telescoping
     * constructor in easiest way.</p>
     *
     * @property apiKeyPublic REQUIRED: API key required for performing requests.
     * @property apiKeyPrivate REQUIRED: API key required for performing requests.
     *
     * @constructor
     */
    data class Builder(
        private var apiKeyPublic: String? = null,
        private var apiKeyPrivate: String? = null
    ) {

        /**
         * Setter for setting public API key.
         *
         * @param apiKeyPublic REQUIRED: API key required for performing requests.
         * @return [ClientConfiguration.Builder]
         */
        fun setApiKeyPublic(apiKeyPublic: String) = apply {
            this.apiKeyPublic = apiKeyPublic
        }

        /**
         * Setter for setting private API key.
         *
         * @param apiKeyPrivate REQUIRED: API key required for performing requests.
         * @return [ClientConfiguration.Builder]
         */
        fun setApiKeyPrivate(apiKeyPrivate: String) = apply {
            this.apiKeyPrivate = apiKeyPrivate
        }

        /**
         * Create the [ClientConfiguration] object.
         * Will throw [IllegalArgumentException] if required attributes aren't set.
         * REQUIRED: [apiKeyPublic] [apiKeyPrivate]
         * @throws [IllegalArgumentException]
         */
        @Throws(IllegalArgumentException::class)
        fun create(): ClientConfiguration {
            when {
                apiKeyPublic.isNullOrEmpty() -> throw IllegalArgumentException(
                    ILLEGAL_ARGUMENT_API_KEY_PUBLIC
                )

                apiKeyPrivate.isNullOrEmpty() -> throw IllegalArgumentException(
                    ILLEGAL_ARGUMENT_API_KEY_PRIVATE
                )

                else -> {
                    val commonConfiguration = CommonClientConfigurationProperties()
                    commonConfiguration.apiKeyPublic = apiKeyPublic
                    commonConfiguration.apiKeyPrivate = apiKeyPrivate
                    return ClientConfiguration(
                        commonConfiguration = commonConfiguration
                    )
                }
            }
        }
    }
}
