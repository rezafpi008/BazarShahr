package com.bazar.bane.bazarshahr.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bazar.bane.bazarshahr.intent.AddIntent
import com.bazar.bane.bazarshahr.repository.AddRepository
import com.bazar.bane.bazarshahr.state.AddState

class AddViewModel : ViewModel() {
    private var page = -1;
    private val _stateIntent: MutableLiveData<AddIntent> = MutableLiveData()
    private val _mainLoadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val mainLoadingState: LiveData<Boolean> get() = _mainLoadingState
    private val _messageVisibilityState: MutableLiveData<Boolean> = MutableLiveData(false)
    val messageVisibilityState: LiveData<Boolean> get() = _messageVisibilityState

    var dataState: LiveData<AddState> = Transformations
        .switchMap(_stateIntent) { stateIntent ->
            stateIntent?.let {
                handleStateEvent(stateIntent)
            }
        }

    private fun handleStateEvent(stateIntent: AddIntent): LiveData<AddState> {
        return when (stateIntent) {
            is AddIntent.AddJob -> {
                AddRepository.createJob(stateIntent.request)
            }
            is AddIntent.AddProduct -> {
                AddRepository.createProduct(stateIntent.request)
            }
        }
    }


    fun setMainLoadingState(state: Boolean) {
        _mainLoadingState.value = state
    }


    fun setMessageVisibilityState(state: Boolean) {
        _messageVisibilityState.value = state
    }

    fun setStateEvent(intent: AddIntent) {
        _stateIntent.value = intent
    }

    private fun getPaginate(): Int {
        page += 1
        return page
    }

}