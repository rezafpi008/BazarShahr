package com.bazar.bane.bazarshahr.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bazar.bane.bazarshahr.api.request.JobsRequest
import com.bazar.bane.bazarshahr.api.request.MallsRequest
import com.bazar.bane.bazarshahr.intent.HomeIntent
import com.bazar.bane.bazarshahr.repository.HomeRepository
import com.bazar.bane.bazarshahr.repository.JobRepository
import com.bazar.bane.bazarshahr.repository.MallRepository
import com.bazar.bane.bazarshahr.state.HomeState
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.PER_PAGE_ITEM

class HomeViewModel : ViewModel() {
    private var page = -1;
    private var jobPage = -1;
    private val _stateIntent: MutableLiveData<HomeIntent> = MutableLiveData()
    private val _mainLoadingState: MutableLiveData<Boolean> = MutableLiveData(true)
    val mainLoadingState: LiveData<Boolean> get() = _mainLoadingState
    private val _messageVisibilityState: MutableLiveData<Boolean> = MutableLiveData(false)
    val messageVisibilityState: LiveData<Boolean> get() = _messageVisibilityState

    var dataState: LiveData<HomeState> = Transformations
        .switchMap(_stateIntent) { stateIntent ->
            stateIntent?.let {
                handleStateEvent(stateIntent)
            }
        }

    private fun handleStateEvent(stateIntent: HomeIntent): LiveData<HomeState> {
        return when (stateIntent) {
            is HomeIntent.Jobs -> {
                JobRepository.getJobsHomeState(stateIntent.request)
            }
            HomeIntent.Slider -> {
                HomeRepository.getSlider()
            }
            is HomeIntent.Malls -> {
                MallRepository.getMallsHomeState(stateIntent.request)
            }
        }
    }


    fun setMainLoadingState(state: Boolean) {
        _mainLoadingState.value = state
    }


    fun setMessageVisibilityState(state: Boolean) {
        _messageVisibilityState.value = state
    }

    fun setStateEvent(intent: HomeIntent) {
        _stateIntent.value = intent
    }

    private fun getPaginate(): Int {
        page += 1
        return page
    }

    private fun getJobPaginate(): Int {
        jobPage += 1
        return jobPage
    }

    fun getMalls() {
        setStateEvent(
            HomeIntent.Malls(
                MallsRequest(
                    PER_PAGE_ITEM,
                    null, getPaginate()
                )
            )
        )
    }

    fun getJobs() {
        setStateEvent(
            HomeIntent.Jobs(
                JobsRequest(
                    PER_PAGE_ITEM,
                    null,
                    getJobPaginate(),
                    null
                )
            )
        )

    }
}