package com.bazar.bane.bazarshahr.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.*
import com.bazar.bane.bazarshahr.api.model.Job
import com.bazar.bane.bazarshahr.api.model.JobCategory
import com.bazar.bane.bazarshahr.databinding.FragmentMallCategoryBinding
import com.bazar.bane.bazarshahr.mainFragments.FragmentFunction
import com.bazar.bane.bazarshahr.mainFragments.ToolbarFunction
import com.bazar.bane.bazarshahr.state.MallCategoryState
import com.bazar.bane.bazarshahr.util.AppConstants
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.MALL_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.TITLE
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.MallCategoryViewModel


class MallCategoryFragment : Fragment(), FragmentFunction, ToolbarFunction {
    private lateinit var binding: FragmentMallCategoryBinding
    private lateinit var viewModel: MallCategoryViewModel
    private lateinit var mallId: String
    private lateinit var categoryId: String
    private lateinit var title: String

    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: JobCategorySelectedAdapter
    private var categoryItems: ArrayList<Any?> = ArrayList()

    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var jobAdapter: JobAdapter
    private var jobItems: ArrayList<Any?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mallId = arguments?.getString(MALL_ID)!!
        title = arguments?.getString(TITLE)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_mall_category, container, false)
        val view = binding.root
        viewModel = MallCategoryViewModel()
        binding.mallCategoryViewModel = viewModel
        binding.lifecycleOwner = this
        initialData()
        subscribeObservers()
        setToolbar()
        viewModel.getMallCategory(mallId)
        return view
    }


    override fun initialData() {
        categoryItems.clear()
        categoryRecyclerView = binding.categoryRecyclerView
        categoryRecyclerView.layoutManager = LinearLayoutManager(context)
        val gridLayoutManager = GridLayoutManager(context, 1, LinearLayoutManager.HORIZONTAL, true)
        categoryRecyclerView.layoutManager = gridLayoutManager
        categoryAdapter =
            JobCategorySelectedAdapter(requireContext(), categoryItems, categoryRecyclerView)
        categoryRecyclerView.adapter = categoryAdapter

        categoryAdapter.setItemOnClick(object : OnClickItem<JobCategory> {
            override fun clicked(item: JobCategory, position: Int) {
                categoryId = item.id!!
                getNewJobs()
            }
        })


        jobItems.clear()
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
                viewModel.getJobs(categoryId, mallId)
            }
        })

        jobAdapter.setItemOnClick(object : OnClickJob {
            override fun clickedProducts(job: Job, position: Int) {
                val bundle = Bundle()
                bundle.putString(AppConstants.JOB_ID, job.id)
                bundle.putString(TITLE, job.name)
                findNavController().navigate(
                    R.id.action_mallCategoryFragment_to_productsFragment,
                    bundle
                )
            }

            override fun clickedInformation(job: Job, position: Int) {
                val bundle = Bundle()
                bundle.putString(AppConstants.JOB_ID, job.id)
                bundle.putString(TITLE, job.name)
                findNavController().navigate(
                    R.id.action_mallCategoryFragment_to_jobDetailsFragment,
                    bundle
                )
            }
        })

    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is MallCategoryState.GetMallCategories -> {
                    viewModel.setMainLoadingState(false)
                    categoryItems.addAll(dataState.response.data?.categories!!)
                    categoryAdapter.setLoading(categoryAdapter.loadingSuccessState)
                    if (categoryAdapter.itemCount == 0)
                        viewModel.setMessageVisibilityState(true)
                    else {
                        categoryId = dataState.response.data.categories[0].id!!
                        viewModel.setMainViewState(true)
                        categoryAdapter.selectCategory(0)
                        getNewJobs()
                    }

                }
                is MallCategoryState.ErrorGetMallCategories -> {
                    viewModel.setMainLoadingState(false)
                    categoryAdapter.setLoading(categoryAdapter.loadingFailState)
                    ToastUtil.showToast(dataState.error)
                }

                is MallCategoryState.GetJobs -> {
                    viewModel.setJobLoadingState(false)
                    jobItems.addAll(dataState.response.jobs!!)
                    jobAdapter.setLoading(jobAdapter.loadingSuccessState)
                    if (jobAdapter.itemCount == 0)
                        viewModel.setMessageVisibilityState(true)
                }

                is MallCategoryState.ErrorGetJobs -> {
                    viewModel.setJobLoadingState(false)
                    jobAdapter.setLoading(jobAdapter.loadingFailState)
                    ToastUtil.showToast(dataState.error)
                }
            }
        })
    }

    override fun setToolbar() {
        val toolbar: Toolbar = binding.toolbar as Toolbar
        toolbar.findViewById<TextView>(R.id.title_page).text = title
        toolbar.findViewById<AppCompatImageView>(R.id.back).setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getNewJobs() {
        jobItems.clear()
        viewModel.resetJobsPaginate()
        viewModel.setJobLoadingState(true)
        viewModel.getJobs(categoryId, mallId)
    }

}