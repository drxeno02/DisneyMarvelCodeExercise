package com.disney.framework.http.requests

/**
 * This represents a configuration that collects information necessary to perform /v1/public/comics
 * request operations.
 */
class GetComicsRequest private constructor() {

    class Builder {

        /**
         * Create the request.
         *
         * @return [GetComicsRequest]
         */
        fun create(): GetComicsRequest {
            return GetComicsRequest()
        }
    }
}

/**
 * A dsl-like builder for [GetComicsRequest]. Internally uses [GetComicsRequest.Builder] to
 * create the [GetComicsRequest].
 */
inline fun getComicsRequest(buildRequest: GetComicsRequest.Builder.() -> Unit): GetComicsRequest {
    val builder = GetComicsRequest.Builder()
    builder.buildRequest()
    return builder.create()
}
