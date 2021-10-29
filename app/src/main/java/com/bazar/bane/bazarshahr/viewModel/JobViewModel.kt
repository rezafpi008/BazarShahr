package com.bazar.bane.bazarshahr.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bazar.bane.bazarshahr.api.model.Job
import com.bazar.bane.bazarshahr.intent.JobIntent
import com.bazar.bane.bazarshahr.repository.JobRepository
import com.bazar.bane.bazarshahr.state.JobState

class JobViewModel : ViewModel() {
    private var page = 0;
    private val _stateIntent: MutableLiveData<JobIntent> = MutableLiveData()

    private val _job: MutableLiveData<Job> = MutableLiveData()
    val job: LiveData<Job> get() = _job

    private val _mainLoadingState: MutableLiveData<Boolean> = MutableLiveData(true)
    val mainLoadingState: LiveData<Boolean> get() = _mainLoadingState

    private val _messageVisibilityState: MutableLiveData<Boolean> = MutableLiveData(true)
    val messageVisibilityState: LiveData<Boolean> get() = _messageVisibilityState


    var dataState: LiveData<JobState> = Transformations
        .switchMap(_stateIntent) { stateIntent ->
            stateIntent?.let {
                handleStateEvent(stateIntent)
            }
        }

    private fun handleStateEvent(stateIntent: JobIntent): LiveData<JobState> {
        return when (stateIntent) {
            is JobIntent.Jobs -> {
                JobRepository.getJobsJobState(stateIntent.request)
            }
            is JobIntent.JobDetails -> {
                JobRepository.getJobDetails(stateIntent.request)
            }
        }
    }


    fun setMainLoadingState(state: Boolean) {
        _mainLoadingState.value = state
    }


    fun setJob(state: Job) {
        _job.value = state
    }

    fun setMessageVisibilityState(state: Boolean) {
        _messageVisibilityState.value = state
    }

    fun setStateEvent(intent: JobIntent) {
        _stateIntent.value = intent
    }

    private fun getPaginate(): Int {
        page += 1
        return page
    }
}