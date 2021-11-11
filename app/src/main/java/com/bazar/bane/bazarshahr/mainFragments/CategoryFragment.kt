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
import com.bazar.bane.bazarshahr.api.model.JobCategory
import com.bazar.bane.bazarshahr.api.model.Mall
import com.bazar.bane.bazarshahr.databinding.FragmentCategoryBinding
import com.bazar.bane.bazarshahr.state.JobCategoryState
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.CATEGORY_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.MALL_ID
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

    private lateinit var mallRecyclerView: RecyclerView
    private lateinit var mallAdapter: MallAdapter
    private var mallItems: ArrayList<Any?> = ArrayList()
    private var getMall = true

    private lateinit var categoryId: String;

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
        initialData()
        subscribeObservers()
        setToolbar()
        viewModel.getCategories()
        return view
    }

    override fun initialData() {
        getMall = true
        categoryItems.clear()
        categoryRecyclerView = binding.categoryRecyclerView
        categoryRecyclerView.layoutManager = LinearLayoutManager(context)
        val gridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL, true)
        categoryRecyclerView.layoutManager = gridLayoutManager
        categoryAdapter =
            JobCategorySelectedAdapter(requireContext(), categoryItems, categoryRecyclerView)
        categoryRecyclerView.adapter = categoryAdapter
        categoryAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                viewModel.getCategories()
            }
        })
        categoryAdapter.setItemOnClick(object : OnClickItem<JobCategory> {
            override fun clicked(item: JobCategory, position: Int) {
                val bundle = Bundle()
                bundle.putString(CATEGORY_ID, item.id)
                bundle.putString(TITLE, item.name)
                findNavController().navigate(
                    R.id.action_categoryFragment_to_jobsFragment,
                    bundle
                )
            }
        })


        mallItems.clear()
        mallRecyclerView = binding.mallRecyclerView
        mallRecyclerView.layoutManager = LinearLayoutManager(context)
        val mallGridLayoutManager =
            GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        mallRecyclerView.layoutManager = mallGridLayoutManager
        mallAdapter =
            MallAdapter(requireContext(), mallItems, mallRecyclerView)
        mallAdapter.horizontalItemFixed = true
        mallRecyclerView.adapter = mallAdapter
        mallAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                viewModel.getMalls()
            }
        })

        mallAdapter.setItemOnClick(object : OnClickMall {
            override fun clickedJobs(mall: Mall, position: Int) {
                val bundle = Bundle()
                bundle.putString(MALL_ID, mall.id)
                bundle.putString(TITLE, mall.name)
                findNavController().navigate(
                    R.id.action_categoryFragment_to_jobsFragment,
                    bundle
                )
            }

            override fun clickedInformation(mall: Mall, position: Int) {
                val bundle = Bundle()
                bundle.putString(MALL_ID, mall.id)
                bundle.putString(TITLE, mall.name)
                findNavController().navigate(
                    R.id.action_categoryFragment_to_mallDetailsFragment,
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
                    if (getMall) {
                        viewModel.setMallLoadingState(true)
                        viewModel.getMalls()
                        getMall = false
                    }

                }
                is JobCategoryState.ErrorGetCategories -> {
                    viewModel.setMainLoadingState(false)
                    categoryAdapter.setLoading(categoryAdapter.loadingFailState)
                    ToastUtil.showToast(dataState.error)
                }

                is JobCategoryState.GetMalls -> {
                    viewModel.setMallLoadingState(false)
                    mallItems.addAll(dataState.response.malls!!)
                    mallAdapter.setLoading(mallAdapter.loadingSuccessState)
                    if (mallAdapter.itemCount == 0)
                        viewModel.setMessageVisibilityState(true)
                }

                is JobCategoryState.ErrorGetMalls -> {
                    viewModel.setMallLoadingState(false)
                    mallAdapter.setLoading(mallAdapter.loadingFailState)
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

}