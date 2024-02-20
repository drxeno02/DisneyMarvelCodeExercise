package com.disney.framework.http.client.enums

/**
 * Enumeration to add request identifier to [Response.Success] and [Response.Failure].
 *
 * <p>When debugging and tracking HTTP errors, this identifier is a simple name to quickly
 * identify problematic HTTP requests.</p>
 *
 * @property requestName Request name intended to be used as identifier at [Response.Success] and
 * [Response.Failure].
 * @constructor
 */
internal enum class EndpointIdentifier(private val requestName: String) {
    GET_COMICS("GetComics"),
    GET_COMIC_BY_ID("GetComicById");

    override fun toString(): String {
        return requestName
    }
}
