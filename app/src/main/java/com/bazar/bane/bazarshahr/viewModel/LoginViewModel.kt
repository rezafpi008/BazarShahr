package com.bazar.bane.bazarshahr.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bazar.bane.bazarshahr.intent.LoginIntent
import com.bazar.bane.bazarshahr.repository.AddRepository
import com.bazar.bane.bazarshahr.repository.LoginRepository
import com.bazar.bane.bazarshahr.state.LoginState
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.USER_TOKEN
import com.bazar.bane.bazarshahr.util.SharedPreferenceUtil

class LoginViewModel : ViewModel() {
    private val _stateIntent: MutableLiveData<LoginIntent> = MutableLiveData()
    private val _mainLoadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val mainLoadingState: LiveData<Boolean> get() = _mainLoadingState

    var dataState: LiveData<LoginState> = Transformations
        .switchMap(_stateIntent) { stateIntent ->
            stateIntent?.let {
                handleStateEvent(stateIntent)
            }
        }

    private fun handleStateEvent(stateIntent: LoginIntent): LiveData<LoginState> {
        return when (stateIntent) {
            is LoginIntent.SignIn -> {
                LoginRepository.signIn(stateIntent.request)
            }
            is LoginIntent.SignUp -> {
                LoginRepository.signUp(stateIntent.request)
            }
        }
    }


    fun setMainLoadingState(state: Boolean) {
        _mainLoadingState.value = state
    }

    fun setStateEvent(intent: LoginIntent) {
        _stateIntent.value = intent
    }

    fun saveToken(token: String) {
        SharedPreferenceUtil.saveStringValue(USER_TOKEN, token)
    }

}