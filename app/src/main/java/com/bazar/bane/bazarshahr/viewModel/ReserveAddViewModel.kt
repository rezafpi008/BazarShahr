package com.bazar.bane.bazarshahr.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bazar.bane.bazarshahr.intent.ReserveAddIntent
import com.bazar.bane.bazarshahr.repository.ReserveAddRepository
import com.bazar.bane.bazarshahr.state.ReserveAddState

class ReserveAddViewModel : ViewModel() {

    private val _stateIntent: MutableLiveData<ReserveAddIntent> = MutableLiveData()

    private val _description: MutableLiveData<String> = MutableLiveData()
    val description: LiveData<String> get() = _description

    private val _mainLoadingState: MutableLiveData<Boolean> = MutableLiveData(true)
    val mainLoadingState: LiveData<Boolean> get() = _mainLoadingState


    var dataState: LiveData<ReserveAddState> = Transformations
        .switchMap(_stateIntent) { stateIntent ->
            stateIntent?.let {
                handleStateEvent(stateIntent)
            }
        }

    private fun handleStateEvent(stateIntent: ReserveAddIntent): LiveData<ReserveAddState> {
        return when (stateIntent) {
            is ReserveAddIntent.ReserveAddDescription -> {
                ReserveAddRepository.getReserveAddDescription()
            }
        }
    }


    fun setMainLoadingState(state: Boolean) {
        _mainLoadingState.value = state
    }


    fun setDescription(state: String) {
        _description.value = state
    }


    fun setStateEvent(intent: ReserveAddIntent) {
        _stateIntent.value = intent
    }

}