package com.disney.framework.http.responses.models

import com.disney.framework.http.responses.internal.EmptyStateInfo
import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("variantDescription") val variantDescription: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("thumbnail") val thumbnail: Thumbnail? = null
) : EmptyStateInfo {

    override fun isEmpty(): Boolean = this == EMPTY

    companion object {

        /**
         * An empty object instance for [Results].
         *
         * If the API were to respond back with a successful response but with an empty body,
         * clients will get back an [EMPTY] instance for [Results].
         */
        val EMPTY = Results()
    }
}
