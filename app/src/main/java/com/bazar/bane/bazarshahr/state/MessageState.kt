package com.bazar.bane.bazarshahr.state

import com.bazar.bane.bazarshahr.api.response.*

sealed class MessageState {

    data class SendMessage(val response: MainResponse) : MessageState()

    data class ErrorSendMessage(val error: String?) : MessageState()

    data class GetMessage(val response: MessagesResponse) : MessageState()

    data class ErrorGetMessage(val error: String?) : MessageState()
}