package com.disney.framework.http.interfaces

import com.disney.framework.http.okhttp.OkHttpRequestExecutor
import com.disney.framework.http.requests.internal.HttpRequest

/**
 * This interface establishes a common contract for hiding HTTP library dependencies.
 *
 * @see [OkHttpRequestExecutor]
 */
interface HttpRequestExecutor {

    /**
     * Execute a HTTP request.
     *
     * @param httpRequest Collection of HTTP request models - method, headers and body.
     * @param callback HTTP callback for the call-site to receive the HTTP response.
     */
    fun execute(httpRequest: HttpRequest, callback: HttpResponseCallback?)

    /**
     * Cancel ongoing HTTP request, if applicable.
     */
    fun cancel()
}
