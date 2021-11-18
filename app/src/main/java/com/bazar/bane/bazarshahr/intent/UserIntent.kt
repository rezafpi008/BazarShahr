package com.bazar.bane.bazarshahr.intent

import com.bazar.bane.bazarshahr.api.request.EditUserRequest
import com.bazar.bane.bazarshahr.api.request.UserDetailsRequest

sealed class UserIntent {

    class UserDetails(
        val request: UserDetailsRequest
    ) : UserIntent()

    class EditUser(
        val request: EditUserRequest
    ) : UserIntent()

}