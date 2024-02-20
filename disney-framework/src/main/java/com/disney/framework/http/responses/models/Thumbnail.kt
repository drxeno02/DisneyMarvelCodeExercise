package com.disney.framework.http.responses.models

import com.disney.framework.http.responses.internal.EmptyStateInfo
import com.google.gson.annotations.SerializedName

data class Thumbnail(
    @SerializedName("path") val path: String? = null
) : EmptyStateInfo {

    override fun isEmpty(): Boolean = this == EMPTY

    companion object {

        /**
         * An empty object instance for [Thumbnail].
         *
         * If the API were to respond back with a successful response but with an empty body,
         * clients will get back an [EMPTY] instance for [Thumbnail].
         */
        val EMPTY = Thumbnail()
    }
}
