package com.bazar.bane.bazarshahr.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.bazar.bane.bazarshahr.api.main.ApiSuccessResponse
import com.bazar.bane.bazarshahr.api.main.GenericApiResponse
import com.bazar.bane.bazarshahr.api.main.MyRetrofitBuilder
import com.bazar.bane.bazarshahr.api.main.NetworkBoundResource
import com.bazar.bane.bazarshahr.api.response.*
import com.bazar.bane.bazarshahr.state.CityState

object CityRepository {
    fun getCities(): LiveData<CityState> {
        return object : NetworkBoundResource<CitiesResponse, CityState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<CitiesResponse>) {
                result.value = CityState.GetCities(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = CityState.ErrorGetCities(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<CitiesResponse>> {
                return MyRetrofitBuilder.apiService.getCities()
            }

        }.asLiveData()
    }

}