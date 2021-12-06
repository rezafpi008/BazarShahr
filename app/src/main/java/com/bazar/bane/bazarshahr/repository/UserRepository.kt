package com.bazar.bane.bazarshahr.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.bazar.bane.bazarshahr.api.main.ApiSuccessResponse
import com.bazar.bane.bazarshahr.api.main.GenericApiResponse
import com.bazar.bane.bazarshahr.api.main.MyRetrofitBuilder
import com.bazar.bane.bazarshahr.api.main.NetworkBoundResource
import com.bazar.bane.bazarshahr.api.request.EditUserRequest
import com.bazar.bane.bazarshahr.api.request.SuggestionsRequest
import com.bazar.bane.bazarshahr.api.request.UserDetailsRequest
import com.bazar.bane.bazarshahr.api.response.*
import com.bazar.bane.bazarshahr.state.UserState


object UserRepository {

    fun getUserDetails(request: UserDetailsRequest): LiveData<UserState> {
        return object : NetworkBoundResource<UserDetailsResponse, UserState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<UserDetailsResponse>) {
                result.value = UserState.GetUserDetails(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = UserState.ErrorGetUserDetails(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<UserDetailsResponse>> {
                return MyRetrofitBuilder.apiService.getUserDetails(request)
            }

        }.asLiveData()
    }

    fun editUserDetails(request: EditUserRequest): LiveData<UserState> {
        return object : NetworkBoundResource<MainResponse, UserState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<MainResponse>) {
                result.value = UserState.EditUserDetails(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = UserState.ErrorEditUserDetails(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<MainResponse>> {
                return MyRetrofitBuilder.apiService.editUserDetails(request)
            }

        }.asLiveData()
    }

    fun sendSuggestions(request: SuggestionsRequest): LiveData<UserState> {
        return object : NetworkBoundResource<MainResponse, UserState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<MainResponse>) {
                result.value = UserState.SendSuggestions(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = UserState.ErrorSendSuggestions(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<MainResponse>> {
                return MyRetrofitBuilder.apiService.sendSuggestions(request)
            }

        }.asLiveData()
    }

}