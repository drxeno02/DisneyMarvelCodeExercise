package com.disney.interview.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.disney.framework.http.client.interfaces.DisneyApiClientInterfaces
import com.disney.framework.http.provider.DisneyApiClientProvider
import com.disney.framework.http.requests.GetComicByIdRequest
import com.disney.framework.http.requests.GetComicsRequest
import com.disney.framework.http.responses.GetComicByIdResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {
    private val _viewState = MutableStateFlow<ViewState>(
        // set default state
        ViewState.ContentAvailable(GetComicByIdResponse.EMPTY)
    )
    val viewState = _viewState.asStateFlow()

    // api client
    private val apiClient: DisneyApiClientInterfaces = DisneyApiClientProvider.getInstance()

    fun getComicById() {
        viewModelScope.launch {
            // the CoroutineDispatcher that is designed for offloading blocking IO
            // tasks to a shared pool of threads
            val getComicsResponse = async {
                apiClient.getComics(
                    GetComicsRequest.Builder().create()
                )
            }.await()

            val getComicByIdResponse = async {
                apiClient.getComicById(
                    GetComicByIdRequest.Builder()
                        .setComicId(getComicsResponse.data?.results?.get(0)?.id)
                        .create()
                )
            }.await()
            // update UI state
            _viewState.value = ViewState.ContentAvailable(getComicByIdResponse)
        }
    }
}
