package com.disney.framework.http.responses.internal

/**
 * Response callback for requests. Each request could finish in the following state:
 * - `onSuccess`: a request has successfully completed with [Response.Success]
 * - `onFailure`: a request failed with [Response.Failure]
 * - `onCancelled`: a request was cancelled
 */
interface ResponseCallback<T : EmptyStateInfo> {

    /**
     * Represents that a request concluded successfully.
     *
     * @param data A successful variant of the [Response]
     */
    fun onSuccess(data: Response.Success<T>)

    /**
     * Represents that a request failed.
     *
     * @param data A failed variant of the [Response]
     */
    fun onFailure(data: Response.Failure<T>)
}
