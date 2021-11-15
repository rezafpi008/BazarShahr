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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.MainActivity
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.JobCategorySelectedAdapter
import com.bazar.bane.bazarshahr.adapter.OnClickItem
import com.bazar.bane.bazarshahr.adapter.OnLoadMoreListener
import com.bazar.bane.bazarshahr.api.model.JobCategory
import com.bazar.bane.bazarshahr.api.request.JobCategoryRequest
import com.bazar.bane.bazarshahr.databinding.SelectCategoryPopUpBinding
import com.bazar.bane.bazarshahr.intent.JobCategoryIntent
import com.bazar.bane.bazarshahr.state.JobCategoryState
import com.bazar.bane.bazarshahr.util.AppConstants
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.JobCategoryViewModel

class SelectCategoryPopUp(
    context: Context,
    popUpCallback: PopUpCallback,
    owner: LifecycleOwner
) :
    Dialog(context) {

    lateinit var binding: SelectCategoryPopUpBinding
    lateinit var viewModel: JobCategoryViewModel
    var callback: PopUpCallback = popUpCallback
    var lifecycleOwner = owner
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: JobCategorySelectedAdapter
    private var categoryItems: ArrayList<Any?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.select_category_pop_up,
            null,
            false
        )
        setContentView(binding.root)
        viewModel = JobCategoryViewModel()
        binding.categoryViewModel = viewModel
        binding.lifecycleOwner = lifecycleOwner
        initialData()
        subscribeObservers()
        viewModel.setStateEvent(
            JobCategoryIntent.Categories(
                JobCategoryRequest(
                    100,
                    viewModel.getCategoriesPaginate(),
                )
            )
        )

    }

    private fun initialData() {
        categoryRecyclerView = binding.categoryRecyclerView
        categoryRecyclerView.layoutManager = LinearLayoutManager(context)
        val gridLayoutManager =
            GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        categoryRecyclerView.layoutManager = gridLayoutManager
        categoryAdapter =
            JobCategorySelectedAdapter(context, categoryItems, categoryRecyclerView)
        categoryAdapter.horizontalItemFixed = true
        categoryRecyclerView.adapter = categoryAdapter

        categoryAdapter.setItemOnClick(object : OnClickItem<JobCategory> {
            override fun clicked(item: JobCategory, position: Int) {
                callback.setId(item.id!!, item.name!!)
                dismiss()
            }
        })
    }

    fun subscribeObservers() {
        viewModel.dataState.observe(lifecycleOwner, { dataState ->
            when (dataState) {
                is JobCategoryState.GetCategories -> {
                    viewModel.setMainLoadingState(false)
                    categoryItems.addAll(dataState.response.categories!!)
                    categoryAdapter.setLoading(categoryAdapter.loadingSuccessState)
                }
                is JobCategoryState.ErrorGetCategories -> {
                    viewModel.setMainLoadingState(false)
                    categoryAdapter.setLoading(categoryAdapter.loadingFailState)
                    ToastUtil.showToast(dataState.error)
                }
            }
        })
    }

}
