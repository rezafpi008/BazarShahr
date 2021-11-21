package com.bazar.bane.bazarshahr.popUp

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.*
import com.bazar.bane.bazarshahr.api.model.Job
import com.bazar.bane.bazarshahr.api.request.UserDetailsRequest
import com.bazar.bane.bazarshahr.databinding.SelectUserJobPopUpBinding
import com.bazar.bane.bazarshahr.intent.UserIntent
import com.bazar.bane.bazarshahr.state.UserState
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.UserViewModel

class SelectUserJobPopUp(
    context: Context,
    popUpCallback: PopUpCallback,
    owner: LifecycleOwner
) :
    Dialog(context) {

    lateinit var binding: SelectUserJobPopUpBinding
    lateinit var viewModel: UserViewModel
    var callback: PopUpCallback = popUpCallback
    var lifecycleOwner = owner

    private lateinit var mallRecyclerView: RecyclerView
    private lateinit var mallAdapter: SelectUserJobAdapter
    private var mallItems: ArrayList<Any?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.select_user_job_pop_up,
            null,
            false
        )
        setContentView(binding.root)
        viewModel = UserViewModel()
        binding.userViewModel = viewModel
        binding.lifecycleOwner = lifecycleOwner
        initialData()
        subscribeObservers()
        viewModel.setStateEvent(UserIntent.UserDetails(UserDetailsRequest()))
    }

    private fun initialData() {
        mallRecyclerView = binding.mallRecyclerView
        mallRecyclerView.layoutManager = LinearLayoutManager(context)
        val mallGridLayoutManager =
            GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        mallRecyclerView.layoutManager = mallGridLayoutManager
        mallAdapter =
            SelectUserJobAdapter(context, mallItems)
        mallRecyclerView.adapter = mallAdapter

        mallAdapter.setItemOnClick(object : OnClickJob {
            override fun clickedProducts(job: Job, position: Int) {

            }

            override fun clickedInformation(job: Job, position: Int) {
                callback.setId(job.id!!, job.name!!)
                dismiss()
            }
        })

    }

    fun subscribeObservers() {
        viewModel.dataState.observe(lifecycleOwner, { dataState ->
            when (dataState) {
                is UserState.GetUserDetails -> {
                    viewModel.setMainLoadingState(false)
                    mallItems.addAll(dataState.response.data?.jobs!!)
                    mallAdapter.setLoading()
                    if (mallAdapter.itemCount == 0)
                        viewModel.setMessageVisibilityState(true)
                }

                is UserState.ErrorGetUserDetails -> {
                    viewModel.setMainLoadingState(false)
                    mallAdapter.setLoading()
                    ToastUtil.showToast(dataState.error)
                }

            }
        })
    }

}
