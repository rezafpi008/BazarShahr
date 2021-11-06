package com.bazar.bane.bazarshahr.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bazar.bane.bazarshahr.api.model.Mall
import com.bazar.bane.bazarshahr.api.request.MallDetailsRequest
import com.bazar.bane.bazarshahr.intent.MallIntent
import com.bazar.bane.bazarshahr.repository.MallRepository
import com.bazar.bane.bazarshahr.state.MallState

class MallViewModel : ViewModel() {
    private var page = -1;
    private val _stateIntent: MutableLiveData<MallIntent> = MutableLiveData()

    private val _mall: MutableLiveData<Mall> = MutableLiveData()
    val mall: LiveData<Mall> get() = _mall

    private val _mainLoadingState: MutableLiveData<Boolean> = MutableLiveData(true)
    val mainLoadingState: LiveData<Boolean> get() = _mainLoadingState

    private val _messageVisibilityState: MutableLiveData<Boolean> = MutableLiveData(true)
    val messageVisibilityState: LiveData<Boolean> get() = _messageVisibilityState


    var dataState: LiveData<MallState> = Transformations
        .switchMap(_stateIntent) { stateIntent ->
            stateIntent?.let {
                handleStateEvent(stateIntent)
            }
        }

    private fun handleStateEvent(stateIntent: MallIntent): LiveData<MallState> {
        return when (stateIntent) {
            is MallIntent.MallDetails -> {
                MallRepository.getMallDetails(stateIntent.request)
            }
        }
    }


    fun setMainLoadingState(state: Boolean) {
        _mainLoadingState.value = state
    }


    fun setMall(state: Mall) {
        _mall.value = state
    }

    fun setMessageVisibilityState(state: Boolean) {
        _messageVisibilityState.value = state
    }

    fun setStateEvent(intent: MallIntent) {
        _stateIntent.value = intent
    }

    private fun getPaginate(): Int {
        page += 1
        return page
    }

    fun getMall(mallId: String) {
        setStateEvent(
            MallIntent.MallDetails(
                MallDetailsRequest(
                    mallId
                )
            )
        )

    }
}