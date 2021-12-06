package com.bazar.bane.bazarshahr.state

import com.bazar.bane.bazarshahr.api.response.*

sealed class UserState {

    data class GetUserDetails(val response: UserDetailsResponse) : UserState()

    data class ErrorGetUserDetails(val error: String?) : UserState()

    data class EditUserDetails(val response: MainResponse) : UserState()

    data class ErrorEditUserDetails(val error: String?) : UserState()

    data class SendSuggestions(val response: MainResponse) : UserState()

    data class ErrorSendSuggestions(val error: String?) : UserState()
}