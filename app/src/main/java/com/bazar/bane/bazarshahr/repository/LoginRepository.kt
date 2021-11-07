package com.bazar.bane.bazarshahr.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.bazar.bane.bazarshahr.api.main.ApiSuccessResponse
import com.bazar.bane.bazarshahr.api.main.GenericApiResponse
import com.bazar.bane.bazarshahr.api.main.MyRetrofitBuilder
import com.bazar.bane.bazarshahr.api.main.NetworkBoundResource
import com.bazar.bane.bazarshahr.api.request.SignInRequest
import com.bazar.bane.bazarshahr.api.request.SignUpRequest
import com.bazar.bane.bazarshahr.api.response.*
import com.bazar.bane.bazarshahr.state.LoginState

object LoginRepository {
    fun signIn(request: SignInRequest): LiveData<LoginState> {
        return object : NetworkBoundResource<SignInResponse, LoginState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<SignInResponse>) {
                result.value = LoginState.SignIn(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = LoginState.ErrorSignIn(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<SignInResponse>> {
                return MyRetrofitBuilder.apiService.signIn(request)
            }

        }.asLiveData()
    }

    fun signUp(request: SignUpRequest): LiveData<LoginState> {
        return object : NetworkBoundResource<SignUpResponse, LoginState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<SignUpResponse>) {
                result.value = LoginState.SignUp(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = LoginState.ErrorSignUp(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<SignUpResponse>> {
                return MyRetrofitBuilder.apiService.signUp(request)
            }

        }.asLiveData()
    }

}