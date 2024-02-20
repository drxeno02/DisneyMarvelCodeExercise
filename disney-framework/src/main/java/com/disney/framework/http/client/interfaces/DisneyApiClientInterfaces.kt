package com.disney.framework.http.client.interfaces

import com.disney.framework.http.interfaces.ClientConfigurationInterfaces
import com.disney.framework.http.requests.GetComicByIdRequest
import com.disney.framework.http.requests.GetComicsRequest
import com.disney.framework.http.responses.GetComicByIdResponse
import com.disney.framework.http.responses.GetComicsResponse
import okhttp3.OkHttpClient

/**
 * Interface for HTTP request executor implementation for [OkHttpClient].
 */
interface DisneyApiClientInterfaces : ClientConfigurationInterfaces {

    /**
     * Method is used make /v1/public/comics request.
     *
     * <p>This is an Internal interface. Integrators should not have access to this method.</p>
     *
     * @param request Provided request model for /v1/public/comics request.
     */
    suspend fun getComics(
        request: GetComicsRequest
    ): GetComicsResponse

    /**
     * Method is used make /v1/public/comics/{comicId} request.
     *
     * <p>This is an Internal interface. Integrators should not have access to this method.</p>
     *
     * @param request Provided request model for /v1/public/comics/{comicId} request.
     */
    suspend fun getComicById(
        request: GetComicByIdRequest
    ): GetComicByIdResponse
}
