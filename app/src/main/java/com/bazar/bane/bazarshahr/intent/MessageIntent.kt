package com.bazar.bane.bazarshahr.intent

import com.bazar.bane.bazarshahr.api.request.MessagesRequest
import com.bazar.bane.bazarshahr.api.request.SendMessageRequest

sealed class MessageIntent {

    class Send(
        val request: SendMessageRequest
    ) : MessageIntent()

    class Get(
        val request: MessagesRequest
    ) : MessageIntent()

}