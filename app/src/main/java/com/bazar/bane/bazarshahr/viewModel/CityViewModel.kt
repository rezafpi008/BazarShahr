package com.bazar.bane.bazarshahr.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bazar.bane.bazarshahr.intent.CityIntent
import com.bazar.bane.bazarshahr.repository.CityRepository
import com.bazar.bane.bazarshahr.state.CityState

class CityViewModel : ViewModel() {
    private val _stateIntent: MutableLiveData<CityIntent> = MutableLiveData()

    private val _mainLoadingState: MutableLiveData<Boolean> = MutableLiveData(true)
    val mainLoadingState: LiveData<Boolean> get() = _mainLoadingState


    var dataState: LiveData<CityState> = Transformations
        .switchMap(_stateIntent) { stateIntent ->
            stateIntent?.let {
                handleStateEvent(stateIntent)
            }
        }

    private fun handleStateEvent(stateIntent: CityIntent): LiveData<CityState> {
        return when (stateIntent) {
            CityIntent.City -> {
                CityRepository.getCities()
            }
        }
    }


    fun setMainLoadingState(state: Boolean) {
        _mainLoadingState.value = state
    }

    fun setStateEvent(intent: CityIntent) {
        _stateIntent.value = intent
    }

    fun getCities() {
        setStateEvent(CityIntent.City)
    }

}