package com.bazar.bane.bazarshahr.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.bazar.bane.bazarshahr.api.main.ApiSuccessResponse
import com.bazar.bane.bazarshahr.api.main.GenericApiResponse
import com.bazar.bane.bazarshahr.api.main.MyRetrofitBuilder
import com.bazar.bane.bazarshahr.api.main.NetworkBoundResource
import com.bazar.bane.bazarshahr.api.response.*
import com.bazar.bane.bazarshahr.state.ReserveAddState

object ReserveAddRepository {

    fun getReserveAddDescription(): LiveData<ReserveAddState> {
        return object : NetworkBoundResource<ReserveAddDescriptionResponse, ReserveAddState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<ReserveAddDescriptionResponse>) {
                result.value = ReserveAddState.GetReserveAddDescription(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = ReserveAddState.ErrorGetReserveAddDescription(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<ReserveAddDescriptionResponse>> {
                return MyRetrofitBuilder.apiService.getReserveAddDescription()
            }

        }.asLiveData()
    }

}