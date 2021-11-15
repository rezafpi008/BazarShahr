package com.bazar.bane.bazarshahr.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bazar.bane.bazarshahr.api.request.JobsRequest
import com.bazar.bane.bazarshahr.api.request.MallCategoriesRequest
import com.bazar.bane.bazarshahr.intent.MallCategoryIntent
import com.bazar.bane.bazarshahr.repository.JobRepository
import com.bazar.bane.bazarshahr.repository.MallCategoryRepository
import com.bazar.bane.bazarshahr.state.MallCategoryState
import com.bazar.bane.bazarshahr.util.AppConstants

class MallCategoryViewModel : ViewModel() {
    private var jobsPage = -1
    private val _stateIntent: MutableLiveData<MallCategoryIntent> = MutableLiveData()

    private val _mainLoadingState: MutableLiveData<Boolean> = MutableLiveData(true)
    val mainLoadingState: LiveData<Boolean> get() = _mainLoadingState

    private val _mainViewState: MutableLiveData<Boolean> = MutableLiveData(true)
    val mainViewState: LiveData<Boolean> get() = _mainViewState

    private val _jobLoadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val jobLoadingState: LiveData<Boolean> get() = _jobLoadingState

    private val _messageVisibilityState: MutableLiveData<Boolean> = MutableLiveData(false)
    val messageVisibilityState: LiveData<Boolean> get() = _messageVisibilityState


    var dataState: LiveData<MallCategoryState> = Transformations
        .switchMap(_stateIntent) { stateIntent ->
            stateIntent?.let {
                handleStateEvent(stateIntent)
            }
        }

    private fun handleStateEvent(stateIntent: MallCategoryIntent): LiveData<MallCategoryState> {
        return when (stateIntent) {
            is MallCategoryIntent.MallCategories -> {
                MallCategoryRepository.getMallCategories(stateIntent.request)
            }
            is MallCategoryIntent.Jobs -> {
                JobRepository.getJobsMallState(stateIntent.request)
            }
        }
    }


    fun setMainLoadingState(state: Boolean) {
        _mainLoadingState.value = state
    }


    fun setMainViewState(state: Boolean) {
        _mainViewState.value = state
    }

    fun setJobLoadingState(state: Boolean) {
        _jobLoadingState.value = state
    }

    fun setMessageVisibilityState(state: Boolean) {
        _messageVisibilityState.value = state
    }

    fun setStateEvent(intent: MallCategoryIntent) {
        _stateIntent.value = intent
    }

    private fun getJobsPaginate(): Int {
        jobsPage += 1
        return jobsPage
    }

    fun resetJobsPaginate() {
        jobsPage = -1
    }

    fun getMallCategory(mallId: String) {
        setStateEvent(
            MallCategoryIntent.MallCategories(
                MallCategoriesRequest(mallId)
            )
        )
    }

    fun getJobs(categoryId: String, mallId: String) {
        setStateEvent(
            MallCategoryIntent.Jobs(
                JobsRequest(
                    AppConstants.PER_PAGE_ITEM,
                    categoryId,
                    getJobsPaginate(),
                    mallId
                )
            )
        )
    }
}