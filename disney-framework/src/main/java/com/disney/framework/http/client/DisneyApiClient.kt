package com.disney.framework.http.client

import android.content.Context
import com.disney.framework.http.client.enums.EndpointIdentifier
import com.disney.framework.http.client.interfaces.DisneyApiClientInterfaces
import com.disney.framework.http.configuration.ClientConfiguration
import com.disney.framework.http.provider.DisneyApiClientProvider
import com.disney.framework.http.requests.GetComicByIdRequest
import com.disney.framework.http.requests.GetComicsRequest
import com.disney.framework.http.requests.internal.HttpMethod
import com.disney.framework.http.requests.internal.HttpRequest
import com.disney.framework.http.requests.internal.RequestPayload
import com.disney.framework.http.responses.GetComicByIdResponse
import com.disney.framework.http.responses.GetComicsResponse
import com.disney.framework.http.responses.internal.Response
import com.disney.framework.http.responses.internal.ResponseCallback
import com.disney.framework.http.utils.md5
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

// query parameters
private const val TIMESTAMP = "ts"
private const val API_KEY_PUBLIC = "apikey"
private const val HASH = "hash"

/**
 * Used to access Disney APIs. The [DisneyApiClient] is a common entry point to Disney API services
 * and automatically manages network connections. The [DisneyApiClient] is cheap to create, and
 * is accessible through the singleton provider, [DisneyApiClientProvider].
 *
 * @property context Interface to global information about an application environment.
 * @property clientConfiguration Contains necessary configuration data for making requests.
 * @constructor
 */
open class DisneyApiClient(
    private val context: Context,
    private var clientConfiguration: ClientConfiguration
) : DisneyApiClientInterfaces, BaseApiClient<ClientConfiguration>(
    configuration = clientConfiguration
) {

    /**
     * Method is used make /v1/public/comics request.
     *
     * <p>This is an Internal interface. Integrators should not have access to this method.</p>
     *
     * @param request Provided request model for /v1/public/comics request.
     */
    override suspend fun getComics(
        request: GetComicsRequest
    ): GetComicsResponse = suspendCoroutine { continuation ->
        // compose HTTP request
        val httpRequest = HttpRequest(
            url = clientConfiguration.getComicsUrl,
            httpMethod = HttpMethod.GET,
            requestPayload = RequestPayload.UrlQueryParameters(
                getQueryParameters()
            )
        )

        // execute GET request
        okHttpRequestExecutor.execute(
            httpRequest = httpRequest,
            callback = getHttpResponseCallback(
                emptyResponse = GetComicsResponse.EMPTY,
                identifier = EndpointIdentifier.GET_COMICS.toString(),
                responseCallback = object : ResponseCallback<GetComicsResponse> {
                    override fun onSuccess(data: Response.Success<GetComicsResponse>) {
                        continuation.resume(data.response)
                    }

                    override fun onFailure(data: Response.Failure<GetComicsResponse>) {
                        continuation.resumeWithException(data.errorItem.exception)
                    }
                }
            )
        )
    }

    /**
     * Method is used make /v1/public/comics/{comicId} request.
     *
     * <p>This is an Internal interface. Integrators should not have access to this method.</p>
     *
     * @param request Provided request model for /v1/public/comics/{comicId} request.
     */
    override suspend fun getComicById(
        request: GetComicByIdRequest
    ): GetComicByIdResponse = suspendCoroutine { continuation ->
        // compose HTTP request
        val httpRequest = HttpRequest(
            url = clientConfiguration.getComicByIdUrl.replace(
                ClientConfiguration.PLACEHOLDER_COMIC_ID, request.comicId.toString()
            ),
            httpMethod = HttpMethod.GET,
            requestPayload = RequestPayload.UrlQueryParameters(
                getQueryParameters()
            )
        )

        // execute GET request
        okHttpRequestExecutor.execute(
            httpRequest = httpRequest,
            callback = getHttpResponseCallback(
                emptyResponse = GetComicByIdResponse.EMPTY,
                identifier = EndpointIdentifier.GET_COMIC_BY_ID.toString(),
                responseCallback = object : ResponseCallback<GetComicByIdResponse> {
                    override fun onSuccess(data: Response.Success<GetComicByIdResponse>) {
                        continuation.resume(data.response)
                    }

                    override fun onFailure(data: Response.Failure<GetComicByIdResponse>) {
                        continuation.resumeWithException(data.errorItem.exception)
                    }
                }
            )
        )
    }

    /**
     * Server-side applications must pass two parameters in addition to the apikey parameter:
     *
     * ts - a timestamp (or other long string which can change on a request-by-request basis)
     * hash - a md5 digest of the ts parameter, your private key and your public key
     * (e.g. md5(ts+privateKey+publicKey).
     *
     * For example, a user with a public key of "1234" and a private key of "abcd" could construct
     * a valid call as follows: <baseUrl>?ts=1&apikey=1234&hash=ffd275c5130566a2916217b101f26150
     * (the hash value is the md5 digest of 1abcd1234)
     *
     * Ref- https://developer.marvel.com/documentation/authorization
     *
     * @return HashMap<String, String> Query parameters that contain the necessary parameters
     * to make a request.
     */
    private fun getQueryParameters(): HashMap<String, String> {
        // timestamp
        val timestamp = System.currentTimeMillis().toString()
        // query parameters
        val queryParameters = HashMap<String, String>()
        queryParameters[TIMESTAMP] = timestamp
        clientConfiguration.apiKeyPublic?.let {
            queryParameters[API_KEY_PUBLIC] = it
        }
        val hashValue = timestamp +
                clientConfiguration.apiKeyPrivate +
                clientConfiguration.apiKeyPublic
        queryParameters[HASH] = md5(hashValue)
        return queryParameters
    }

    /**
     * Returns [ClientConfiguration] information used by the [DisneyApiClient].
     *
     * @return [ClientConfiguration]
     */
    override fun getClientConfiguration(): ClientConfiguration {
        return this.clientConfiguration
    }

    /**
     * Update client configuration.
     *
     * <p>The updated changes only apply if [DisneyApiClient] is initialized.</p>
     *
     * @param clientConfiguration REQUIRED: Configuration that collects information necessary to
     * perform request operations.
     */
    override fun updateClientConfiguration(
        clientConfiguration: ClientConfiguration
    ) {
        // update client configuration
        this.clientConfiguration = clientConfiguration
        super.configuration = clientConfiguration
    }

    /**
     * FOR TESTING ONLY
     */
    internal fun updatePostRunnableHook(
        postRunnableHook: () -> Unit
    ) {
        this.postRunnableHook = postRunnableHook
    }
}
