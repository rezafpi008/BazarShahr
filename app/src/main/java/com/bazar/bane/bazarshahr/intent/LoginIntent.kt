package com.bazar.bane.bazarshahr.intent

import com.bazar.bane.bazarshahr.api.request.SignInRequest
import com.bazar.bane.bazarshahr.api.request.SignUpRequest

sealed class LoginIntent {

    class SignIn(
        val request: SignInRequest
    ) : LoginIntent()

    class SignUp(
        val request: SignUpRequest
    ) : LoginIntent()

}