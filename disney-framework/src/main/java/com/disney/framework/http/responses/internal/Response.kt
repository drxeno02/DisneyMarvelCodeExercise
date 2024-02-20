package com.disney.framework.http.responses.internal

/**
 * HTTP calls will return the appropriate response object wrapped inside the [Response].
 *
 * [Response] categorizes response objects into two states.
 * - [Success]: Denotes that an API call finished successfully; note that [Success]
 *              has it's own states as well.
 * - [Failure]: An API call failed during execution; probable errors are categorized as a
 *              part of [ErrorItem].
 *
 * For best results consume the [Response] inside of `when`:
 * ```
 *  when(response) {
 *      Success -> ...
 *      Failure -> ...
 *  }
 * ```
 */
sealed class Response<T : EmptyStateInfo> {

    /**
     * Represents a successful request.
     *
     * @property httpStatusCode Request status code for the API request.
     * @property response Object of the API request.
     * @property identifier Description text or label for the HTTP request.
     */
    data class Success<T : EmptyStateInfo>(
        val httpStatusCode: HttpStatusCode,
        val response: T,
        val identifier: String? = null
    ) : Response<T>()

    /**
     * Represents a failed request.
     *
     * @property errorItem A request error wrapped inside the [ErrorItem].
     * @property identifier Description text or label for the HTTP request.
     */
    data class Failure<T : EmptyStateInfo>(
        val errorItem: ErrorItem,
        val identifier: String? = null
    ) : Response<T>()
}
