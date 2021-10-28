package com.bazar.bane.bazarshahr.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.*
import com.bazar.bane.bazarshahr.api.model.Job
import com.bazar.bane.bazarshahr.api.model.JobCategory
import com.bazar.bane.bazarshahr.databinding.FragmentCategoryBinding
import com.bazar.bane.bazarshahr.state.JobCategoryState
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.JOB_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.TITLE
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.JobCategoryViewModel
import kotlin.collections.ArrayList


class CategoryFragment : Fragment(), FragmentFunction, ToolbarFunction {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var viewModel: JobCategoryViewModel
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: JobCategorySelectedAdapter
    private var categoryItems: ArrayList<Any?> = ArrayList()

    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var jobAdapter: JobAdapter
    private var jobItems: ArrayList<Any?> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        val view = binding.root
        viewModel = JobCategoryViewModel()
        binding.jobcategoryViewModel = viewModel
        binding.lifecycleOwner = this
        setToolbar()
        return view
    }

    override fun initialData() {
        categoryRecyclerView = binding.categoryRecyclerView
        categoryRecyclerView.layoutManager = LinearLayoutManager(context)
        val gridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.layoutManager = gridLayoutManager
        categoryAdapter =
            JobCategorySelectedAdapter(requireContext(), categoryItems, categoryRecyclerView)
        categoryRecyclerView.adapter = categoryAdapter
        categoryAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                //viewModel.setStateEvent(ExploreIntent.ActiveAuction)
            }
        })
        categoryAdapter.setItemOnClick(object : OnClickItem<JobCategory> {
            override fun clicked(item: JobCategory, position: Int) {

            }
        })


        jobRecyclerView = binding.jobRecyclerView
        jobRecyclerView.layoutManager = LinearLayoutManager(context)
        val horizontalLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        jobRecyclerView.layoutManager = horizontalLayoutManager
        jobAdapter =
            JobAdapter(requireContext(), jobItems, jobRecyclerView)
        jobRecyclerView.adapter = jobAdapter

        jobAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                //viewModel.setStateEvent(ExploreIntent.ActiveAuction)
            }
        })

        jobAdapter.setItemOnClick(object : OnClickJob {
            override fun clickedProducts(job: Job, position: Int) {

            }

            override fun clickedInformation(job: Job, position: Int) {
                val bundle = Bundle()
                bundle.putString(JOB_ID, job.id)
                bundle.putString(TITLE, job.name)
                findNavController().navigate(
                    R.id.action_categoryFragment_to_jobDetailsFragment,
                    bundle
                )
            }
        })
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
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

                is JobCategoryState.GetJobs -> {
                    viewModel.setJobLoadingState(false)
                    jobItems.addAll(dataState.response.jobs!!)
                    jobAdapter.setLoading(jobAdapter.loadingSuccessState)
                    if (jobAdapter.itemCount == 0)
                        viewModel.setMessageVisibilityState(true)
                }

                is JobCategoryState.ErrorGetJobs -> {
                    viewModel.setJobLoadingState(false)
                    jobAdapter.setLoading(categoryAdapter.loadingFailState)
                    ToastUtil.showToast(dataState.error)
                }

            }
        })
    }

    override fun setToolbar() {
        val toolbar: Toolbar = binding.toolbar as Toolbar
        toolbar.findViewById<TextView>(R.id.title_page).text =
            getString(R.string.category_page_title)
    }

    private fun setCategoryLoading(){
        jobItems.clear()
        viewModel.setMessageVisibilityState(false)
        viewModel.setJobLoadingState(true)
    }
}