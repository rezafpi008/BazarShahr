package com.bazar.bane.bazarshahr.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.bazar.bane.bazarshahr.api.main.ApiSuccessResponse
import com.bazar.bane.bazarshahr.api.main.GenericApiResponse
import com.bazar.bane.bazarshahr.api.main.MyRetrofitBuilder
import com.bazar.bane.bazarshahr.api.main.NetworkBoundResource
import com.bazar.bane.bazarshahr.api.response.HomeResponse
import com.bazar.bane.bazarshahr.state.HomeState

object HomeRepository {
    fun getHome(page: Int): LiveData<HomeState> {
        return object : NetworkBoundResource<HomeResponse, HomeState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<HomeResponse>) {
                result.value = HomeState.GetHome(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = HomeState.ErrorGetHome(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<HomeResponse>> {
                return MyRetrofitBuilder.apiService.getHome(page)
            }

        }.asLiveData()
    }

}