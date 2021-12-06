package com.bazar.bane.bazarshahr.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bazar.bane.bazarshahr.api.model.User
import com.bazar.bane.bazarshahr.intent.UserIntent
import com.bazar.bane.bazarshahr.repository.UserRepository
import com.bazar.bane.bazarshahr.state.UserState

class UserViewModel : ViewModel() {
    private val _stateIntent: MutableLiveData<UserIntent> = MutableLiveData()

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> get() = _user

    private val _mainLoadingState: MutableLiveData<Boolean> = MutableLiveData(true)
    val mainLoadingState: LiveData<Boolean> get() = _mainLoadingState

    private val _submitLoadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val submitLoadingState: LiveData<Boolean> get() = _submitLoadingState

    private val _messageVisibilityState: MutableLiveData<Boolean> = MutableLiveData(false)
    val messageVisibilityState: LiveData<Boolean> get() = _messageVisibilityState


    var dataState: LiveData<UserState> = Transformations
        .switchMap(_stateIntent) { stateIntent ->
            stateIntent?.let {
                handleStateEvent(stateIntent)
            }
        }

    private fun handleStateEvent(stateIntent: UserIntent): LiveData<UserState> {
        return when (stateIntent) {
            is UserIntent.UserDetails -> {
                UserRepository.getUserDetails(stateIntent.request)
            }
            is UserIntent.EditUser -> {
                UserRepository.editUserDetails(stateIntent.request)
            }
            is UserIntent.SendSuggestions -> {
                UserRepository.sendSuggestions(stateIntent.request)
            }
        }
    }


    fun setMainLoadingState(state: Boolean) {
        _mainLoadingState.value = state
    }

    fun setSubmitLoadingState(state: Boolean) {
        _submitLoadingState.value = state
    }

    fun setUser(state: User) {
        _user.value = state
    }

    fun setMessageVisibilityState(state: Boolean) {
        _messageVisibilityState.value = state
    }

    fun setStateEvent(intent: UserIntent) {
        _stateIntent.value = intent
    }

}