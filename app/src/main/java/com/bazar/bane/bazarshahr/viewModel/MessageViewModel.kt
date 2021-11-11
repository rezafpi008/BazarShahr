package com.bazar.bane.bazarshahr.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bazar.bane.bazarshahr.intent.MessageIntent
import com.bazar.bane.bazarshahr.repository.MessageRepository
import com.bazar.bane.bazarshahr.state.MessageState

class MessageViewModel : ViewModel() {
    
    private val _stateIntent: MutableLiveData<MessageIntent> = MutableLiveData()
    private val _mainLoadingState: MutableLiveData<Boolean> = MutableLiveData(true)
    val mainLoadingState: LiveData<Boolean> get() = _mainLoadingState
    private val _messageVisibilityState: MutableLiveData<Boolean> = MutableLiveData(false)
    val messageVisibilityState: LiveData<Boolean> get() = _messageVisibilityState

    var dataState: LiveData<MessageState> = Transformations
        .switchMap(_stateIntent) { stateIntent ->
            stateIntent?.let {
                handleStateEvent(stateIntent)
            }
        }

    private fun handleStateEvent(stateIntent: MessageIntent): LiveData<MessageState> {
        return when (stateIntent) {
            is MessageIntent.Send -> {
                MessageRepository.sendMessage(stateIntent.request)
            }
            is MessageIntent.Get -> {
                MessageRepository.getMessage(stateIntent.request)
            }
        }
    }


    fun setMainLoadingState(state: Boolean) {
        _mainLoadingState.value = state
    }


    fun setMessageVisibilityState(state: Boolean) {
        _messageVisibilityState.value = state
    }

    fun setStateEvent(intent: MessageIntent) {
        _stateIntent.value = intent
    }

}