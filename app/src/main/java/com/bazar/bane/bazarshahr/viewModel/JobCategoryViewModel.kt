package com.bazar.bane.bazarshahr.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bazar.bane.bazarshahr.api.request.JobCategoryRequest
import com.bazar.bane.bazarshahr.api.request.JobsRequest
import com.bazar.bane.bazarshahr.api.request.MallsRequest
import com.bazar.bane.bazarshahr.intent.HomeIntent
import com.bazar.bane.bazarshahr.intent.JobCategoryIntent
import com.bazar.bane.bazarshahr.repository.JobCategoryRepository
import com.bazar.bane.bazarshahr.repository.JobRepository
import com.bazar.bane.bazarshahr.repository.MallRepository
import com.bazar.bane.bazarshahr.state.JobCategoryState
import com.bazar.bane.bazarshahr.util.AppConstants
import java.text.FieldPosition

class JobCategoryViewModel : ViewModel() {
    private var categoriesPage = -1
    private var mallsPage = -1
    private val _stateIntent: MutableLiveData<JobCategoryIntent> = MutableLiveData()

    private val _mainLoadingState: MutableLiveData<Boolean> = MutableLiveData(true)
    val mainLoadingState: LiveData<Boolean> get() = _mainLoadingState

    private val _mallLoadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val mallLoadingState: LiveData<Boolean> get() = _mallLoadingState

    private val _messageVisibilityState: MutableLiveData<Boolean> = MutableLiveData(false)
    val messageVisibilityState: LiveData<Boolean> get() = _messageVisibilityState


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
            is JobCategoryIntent.Malls -> {
                MallRepository.getMallsCategoryState(stateIntent.request)
            }
        }
    }


    fun setMainLoadingState(state: Boolean) {
        _mainLoadingState.value = state
    }


    fun setMallLoadingState(state: Boolean) {
        _mallLoadingState.value = state
    }

    fun setMessageVisibilityState(state: Boolean) {
        _messageVisibilityState.value = state
    }

    fun setStateEvent(intent: JobCategoryIntent) {
        _stateIntent.value = intent
    }

    fun getCategoriesPaginate(): Int {
        categoriesPage += 1
        return categoriesPage
    }

    private fun getMallsPaginate(): Int {
        mallsPage += 1
        return mallsPage
    }

    fun getCategories() {
        setStateEvent(
            JobCategoryIntent.Categories(
                JobCategoryRequest(
                    AppConstants.PER_PAGE_ITEM,
                    getCategoriesPaginate()
                )
            )
        )
    }

    fun getMalls() {
        setStateEvent(
            JobCategoryIntent.Malls(
                MallsRequest(
                    AppConstants.PER_PAGE_ITEM,
                    null, getMallsPaginate()
                )
            )
        )
    }
}