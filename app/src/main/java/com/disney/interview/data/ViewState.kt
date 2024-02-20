package com.disney.interview.data

import com.disney.framework.http.responses.GetComicByIdResponse

sealed class ViewState {
    data object Loading : ViewState()
    data class ContentAvailable(
        val getComicByIdResponse: GetComicByIdResponse?
    ) : ViewState()
}
