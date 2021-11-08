package com.bazar.bane.bazarshahr.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.bazar.bane.bazarshahr.api.main.ApiSuccessResponse
import com.bazar.bane.bazarshahr.api.main.GenericApiResponse
import com.bazar.bane.bazarshahr.api.main.MyRetrofitBuilder
import com.bazar.bane.bazarshahr.api.main.NetworkBoundResource
import com.bazar.bane.bazarshahr.api.request.MessagesRequest
import com.bazar.bane.bazarshahr.api.request.SendMessageRequest
import com.bazar.bane.bazarshahr.api.response.*
import com.bazar.bane.bazarshahr.state.MessageState


object MessageRepository {

    fun sendMessage(request: SendMessageRequest): LiveData<MessageState> {
        return object : NetworkBoundResource<MainResponse, MessageState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<MainResponse>) {
                result.value = MessageState.SendMessage(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = MessageState.ErrorSendMessage(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<MainResponse>> {
                return MyRetrofitBuilder.apiService.sendMessage(request)
            }

        }.asLiveData()
    }

    fun getMessage(request: MessagesRequest): LiveData<MessageState> {
        return object : NetworkBoundResource<MessagesResponse, MessageState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<MessagesResponse>) {
                result.value = MessageState.GetMessage(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = MessageState.ErrorGetMessage(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<MessagesResponse>> {
                return MyRetrofitBuilder.apiService.getMessage(request)
            }

        }.asLiveData()
    }

}