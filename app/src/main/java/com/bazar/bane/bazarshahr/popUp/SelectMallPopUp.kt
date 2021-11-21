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
import com.bazar.bane.bazarshahr.api.model.Mall
import com.bazar.bane.bazarshahr.databinding.SelectMallPopUpBinding
import com.bazar.bane.bazarshahr.state.JobCategoryState
import com.bazar.bane.bazarshahr.util.AppConstants
import com.bazar.bane.bazarshahr.util.SharedPreferenceUtil
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.JobCategoryViewModel

class SelectMallPopUp(
    context: Context,
    cityId: String,
    popUpCallback: PopUpCallback,
    owner: LifecycleOwner
) :
    Dialog(context) {

    lateinit var binding: SelectMallPopUpBinding
    lateinit var viewModel: JobCategoryViewModel
    var cityId = cityId
    var callback: PopUpCallback = popUpCallback
    var lifecycleOwner = owner

    private lateinit var mallRecyclerView: RecyclerView
    private lateinit var mallAdapter: SelectedMallAdapter
    private var mallItems: ArrayList<Any?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.select_mall_pop_up,
            null,
            false
        )
        setContentView(binding.root)
        viewModel = JobCategoryViewModel()
        binding.categoryViewModel = viewModel
        binding.lifecycleOwner = lifecycleOwner
        initialData()
        subscribeObservers()
        viewModel.getMalls(cityId)
    }

    private fun initialData() {
        mallRecyclerView = binding.mallRecyclerView
        mallRecyclerView.layoutManager = LinearLayoutManager(context)
        val mallGridLayoutManager =
            GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        mallRecyclerView.layoutManager = mallGridLayoutManager
        mallAdapter =
            SelectedMallAdapter(context, mallItems, mallRecyclerView)
        mallRecyclerView.adapter = mallAdapter
        mallAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                viewModel.getMalls(cityId)
            }
        })

        mallAdapter.setItemOnClick(object : OnClickMall {
            override fun clickedJobs(mall: Mall, position: Int) {

            }

            override fun clickedInformation(mall: Mall, position: Int) {
                callback.setId(mall.id!!, mall.name!!)
                dismiss()
            }
        })

    }

    fun subscribeObservers() {
        viewModel.dataState.observe(lifecycleOwner, { dataState ->
            when (dataState) {
                is JobCategoryState.GetMalls -> {
                    viewModel.setMainLoadingState(false)
                    mallItems.addAll(dataState.response.malls!!)
                    mallAdapter.setLoading(mallAdapter.loadingSuccessState)
                    if (mallAdapter.itemCount == 0)
                        viewModel.setMessageVisibilityState(true)
                }

                is JobCategoryState.ErrorGetMalls -> {
                    viewModel.setMainLoadingState(false)
                    mallAdapter.setLoading(mallAdapter.loadingFailState)
                    ToastUtil.showToast(dataState.error)
                }

            }
        })
    }

}
