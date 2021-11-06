package com.bazar.bane.bazarshahr.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bazar.bane.bazarshahr.api.request.JobCategoryRequest
import com.bazar.bane.bazarshahr.api.request.JobsRequest
import com.bazar.bane.bazarshahr.intent.JobCategoryIntent
import com.bazar.bane.bazarshahr.repository.JobCategoryRepository
import com.bazar.bane.bazarshahr.repository.JobRepository
import com.bazar.bane.bazarshahr.state.JobCategoryState
import com.bazar.bane.bazarshahr.util.AppConstants
import java.text.FieldPosition

class JobCategoryViewModel : ViewModel() {
    private var categoriesPage = -1
    private var jobsPage = -1
    private val _stateIntent: MutableLiveData<JobCategoryIntent> = MutableLiveData()

    private val _mainLoadingState: MutableLiveData<Boolean> = MutableLiveData(true)
    val mainLoadingState: LiveData<Boolean> get() = _mainLoadingState

    private val _jobLoadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val jobLoadingState: LiveData<Boolean> get() = _jobLoadingState

    private val _messageVisibilityState: MutableLiveData<Boolean> = MutableLiveData(true)
    val messageVisibilityState: LiveData<Boolean> get() = _messageVisibilityState

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> get() = _message


    var dataState: LiveData<JobCategoryState> = Transformations
        .switchMap(_stateIntent) { stateIntent ->
            stateIntent?.let {
                handleStateEvent(stateIntent)
            }
        }

    private fun handleStateEvent(stateIntent: JobCategoryIntent): LiveData<JobCategoryState> {
        return when (stateIntent) {
            is JobCategoryIntent.Categories -> {
                JobCategoryRepository.getCategories(stateIntent.request)
            }
            is JobCategoryIntent.Jobs -> {
                JobRepository.getJobsCategoryState(stateIntent.request)
            }
        }
    }


    fun setMainLoadingState(state: Boolean) {
        _mainLoadingState.value = state
    }


    fun setJobLoadingState(state: Boolean) {
        _jobLoadingState.value = state
    }

    fun setMessageVisibilityState(state: Boolean) {
        _messageVisibilityState.value = state
    }

    fun setMessage(state: String) {
        _message.value = state
    }

    fun setStateEvent(intent: JobCategoryIntent) {
        _stateIntent.value = intent
    }

    private fun getCategoriesPaginate(): Int {
        categoriesPage += 1
        return categoriesPage
    }

    private fun getJobsPaginate(): Int {
        jobsPage += 1
        return jobsPage
    }

    fun resetJobsPaginate() {
        jobsPage = -1
    }

    fun getCategories() {
        setStateEvent(
            JobCategoryIntent.Categories(
                JobCategoryRequest(
                    AppConstants.PER_PAGE_ITEM,
                    getCategoriesPaginate(),
                    true
                )
            )
        )
    }

    fun getJobs(categoryId: String) {
        setStateEvent(
            JobCategoryIntent.Jobs(
                JobsRequest(
                    AppConstants.PER_PAGE_ITEM,
                    categoryId,
                    getJobsPaginate(),
                    null
                )
            )
        )
    }
}