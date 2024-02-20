package com.disney.framework.http.requests

// exceptions
internal const val ILLEGAL_ARGUMENT_COMIC_ID =
    "Failed to read `comicId`. This field can not be empty."

/**
 * This represents a configuration that collects information necessary to perform
 * /v1/public/comics/{comicId} request operations.
 *
 * @param builder BaseBuilder for forming request.
 */
class GetComicByIdRequest private constructor(builder: Builder) {

    /**
     * The collection UUID used to retrieve comic book details.
     *
     * @property comicId UUID used to retrieve comic book details.
     */
    val comicId: Long?

    init {
        comicId = builder.comicId ?: throw IllegalArgumentException(ILLEGAL_ARGUMENT_COMIC_ID)
    }

    /**
     * Embedded builder class used to simplify request object creation.
     */
    class Builder {
        // request values
        internal var comicId: Long? = null
            private set

        /**
         * Setter for comic id.
         *
         * @param comicId UUID used to retrieve comic book details.
         * @return [GetComicByIdRequest.Builder]
         */
        fun setComicId(comicId: Long?): Builder = apply {
            this.comicId = comicId
        }

        /**
         * Create the request.
         *
         * Will throw [IllegalArgumentException] if required attributes aren't set.
         * REQUIRED: [comicId]
         * @return [GetComicByIdRequest]
         */
        fun create(): GetComicByIdRequest {
            return GetComicByIdRequest(this)
        }
    }
}

/**
 * A dsl-like builder for [GetComicByIdRequest]. Internally uses [GetComicByIdRequest.Builder] to
 * create the [GetComicByIdRequest].
 */
inline fun getComicByIdRequest(buildRequest: GetComicByIdRequest.Builder.() -> Unit): GetComicByIdRequest {
    val builder = GetComicByIdRequest.Builder()
    builder.buildRequest()
    return builder.create()
}
