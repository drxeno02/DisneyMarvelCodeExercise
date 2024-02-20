package com.disney.framework.http.requests.internal

/**
 * Represents all the required parameters for an HTTP request.
 *
 * @property url the request URL.
 * @property httpMethod type of HTTP request method to be used. For e.g. [HttpMethod.GET],
 * [HttpMethod.POST], etc.
 * @property requestPayload the request parameter to be included as a part of the HTTP request.
 */
class HttpRequest(
    val url: String?,
    val httpMethod: HttpMethod,
    val requestPayload: RequestPayload? = null
)
