package com.bazar.bane.bazarshahr.state

import com.bazar.bane.bazarshahr.api.response.SignInResponse
import com.bazar.bane.bazarshahr.api.response.SignUpResponse

sealed class LoginState {

    data class SignIn(val response: SignInResponse) : LoginState()

    data class ErrorSignIn(val error: String?) : LoginState()

    data class SignUp(val response: SignUpResponse) : LoginState()

    data class ErrorSignUp(val error: String?) : LoginState()
}