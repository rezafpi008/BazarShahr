package com.bazar.bane.bazarshahr.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.bazar.bane.bazarshahr.api.main.ApiSuccessResponse
import com.bazar.bane.bazarshahr.api.main.GenericApiResponse
import com.bazar.bane.bazarshahr.api.main.MyRetrofitBuilder
import com.bazar.bane.bazarshahr.api.main.NetworkBoundResource
import com.bazar.bane.bazarshahr.api.request.MallCategoriesRequest
import com.bazar.bane.bazarshahr.api.response.*
import com.bazar.bane.bazarshahr.state.MallCategoryState


object MallCategoryRepository {

    fun getMallCategories(request: MallCategoriesRequest): LiveData<MallCategoryState> {
        return object : NetworkBoundResource<MallCategoriesResponse, MallCategoryState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<MallCategoriesResponse>) {
                result.value = MallCategoryState.GetMallCategories(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = MallCategoryState.ErrorGetMallCategories(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<MallCategoriesResponse>> {
                return MyRetrofitBuilder.apiService.getMallCategories(request)
            }

        }.asLiveData()
    }

}