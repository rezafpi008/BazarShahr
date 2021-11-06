package com.bazar.bane.bazarshahr.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bazar.bane.bazarshahr.api.request.SearchRequest
import com.bazar.bane.bazarshahr.api.response.SearchResponse
import com.bazar.bane.bazarshahr.intent.SearchIntent
import com.bazar.bane.bazarshahr.repository.SearchRepository
import com.bazar.bane.bazarshahr.state.SearchState

class SearchViewModel : ViewModel() {
    private var page = 0;
    private val _stateIntent: MutableLiveData<SearchIntent> = MutableLiveData()
    private val _mainLoadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val mainLoadingState: LiveData<Boolean> get() = _mainLoadingState

    private val _messageVisibilityState: MutableLiveData<Boolean> = MutableLiveData(false)
    val messageVisibilityState: LiveData<Boolean> get() = _messageVisibilityState

    private val _searchResponse: MutableLiveData<SearchResponse> = MutableLiveData()
    val searchResponse: LiveData<SearchResponse> get() = _searchResponse

    var dataState: LiveData<SearchState> = Transformations
        .switchMap(_stateIntent) { stateIntent ->
            stateIntent?.let {
                handleStateEvent(stateIntent)
            }
        }

    private fun handleStateEvent(stateIntent: SearchIntent): LiveData<SearchState> {
        when (stateIntent) {
            is SearchIntent.Search -> {
                return SearchRepository.search(stateIntent.request)
            }
        }
    }


    fun setSearchResponse(state: SearchResponse) {
        _searchResponse.value = state
    }

    fun setMainLoadingState(state: Boolean) {
        _mainLoadingState.value = state
    }


    fun setMessageVisibilityState(state: Boolean) {
        _messageVisibilityState.value = state
    }

    fun setStateEvent(intent: SearchIntent) {
        _stateIntent.value = intent
    }

    private fun getPaginate(): Int {
        page += 1
        return page
    }

    fun search(searchKey: String) {
        setStateEvent(SearchIntent.Search(SearchRequest(searchKey)))
    }
}