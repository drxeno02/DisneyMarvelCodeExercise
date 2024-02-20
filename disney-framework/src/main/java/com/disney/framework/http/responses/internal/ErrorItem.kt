package com.disney.framework.http.responses.internal

/**
 * Distinguishes between a runtime error and a failed HTTP response.
 *
 * <p>For http errors/exceptions the [HttpStatusCode] is optional because only HTTP errors
 * will have a status code, whereas exceptions that is also of type [ErrorItem] will not.</p>
 *
 * @property statusCode Response status code.
 * @property responseTimeInMillis Response time in milli secs to complete the response.
 * @property exception A bad HTTP request error.
 */
sealed class ErrorItem(
    val statusCode: HttpStatusCode? = null,
    val responseTimeInMillis: Long? = null,
    val exception: Exception
) {

    /**
     * Represents an HTTP error response.
     *
     * @property statusCode Response status code.
     * @property responseTimeInMillis Response time in milli secs to complete the response.
     * @property exception A bad HTTP request error.
     */
    class HttpErrorItem(
        statusCode: HttpStatusCode,
        responseTimeInMillis: Long? = null,
        exception: Exception
    ) : ErrorItem(
        statusCode = statusCode,
        responseTimeInMillis = responseTimeInMillis,
        exception = exception
    )

    /**
     * Represents a generic runtime error.
     *
     * @property exception The runtime error that caused the HTTP request to fail.
     */
    class GenericErrorItem(exception: Exception) : ErrorItem(
        exception = exception
    )
}
