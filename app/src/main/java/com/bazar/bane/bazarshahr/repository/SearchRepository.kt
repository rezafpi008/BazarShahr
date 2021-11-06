package com.bazar.bane.bazarshahr.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.bazar.bane.bazarshahr.api.main.ApiSuccessResponse
import com.bazar.bane.bazarshahr.api.main.GenericApiResponse
import com.bazar.bane.bazarshahr.api.main.MyRetrofitBuilder
import com.bazar.bane.bazarshahr.api.main.NetworkBoundResource
import com.bazar.bane.bazarshahr.api.request.SearchRequest
import com.bazar.bane.bazarshahr.api.response.SearchResponse
import com.bazar.bane.bazarshahr.state.SearchState

object SearchRepository {
    fun search(request: SearchRequest): LiveData<SearchState> {
        return object : NetworkBoundResource<SearchResponse, SearchState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<SearchResponse>) {
                result.value = SearchState.GetSearch(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = SearchState.ErrorGetSearch(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<SearchResponse>> {
                return MyRetrofitBuilder.apiService.search(request)
            }

        }.asLiveData()
    }

}