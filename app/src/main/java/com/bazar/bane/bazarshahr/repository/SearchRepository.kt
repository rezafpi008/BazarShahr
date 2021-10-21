package com.bazar.bane.bazarshahr.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.bazar.bane.bazarshahr.api.main.ApiSuccessResponse
import com.bazar.bane.bazarshahr.api.main.GenericApiResponse
import com.bazar.bane.bazarshahr.api.main.MyRetrofitBuilder
import com.bazar.bane.bazarshahr.api.main.NetworkBoundResource
import com.bazar.bane.bazarshahr.api.response.SearchResponse
import com.bazar.bane.bazarshahr.state.SearchState

object SearchRepository {
    fun search(searchQuery: String, page: Int): LiveData<SearchState> {
        return object : NetworkBoundResource<SearchResponse, SearchState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<SearchResponse>) {
                result.value = SearchState.GetSearch(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = SearchState.ErrorGetSearch(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<SearchResponse>> {
                return MyRetrofitBuilder.apiService.search(searchQuery, page)
            }

        }.asLiveData()
    }

}