package com.disney.framework.http.responses

import com.disney.framework.http.responses.internal.EmptyStateInfo
import com.disney.framework.http.responses.models.Data
import com.google.gson.annotations.SerializedName

data class GetComicsResponse(
    @SerializedName("data") val data: Data? = null
) : EmptyStateInfo {

    override fun isEmpty(): Boolean = this == EMPTY

    companion object {

        /**
         * An empty object instance for [GetComicsResponse].
         *
         * If the API were to respond back with a successful response but with an empty body,
         * clients will get back an [EMPTY] instance for [GetComicsResponse].
         */
        val EMPTY = GetComicsResponse()
    }
}
