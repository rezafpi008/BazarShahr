package com.bazar.bane.bazarshahr.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.*
import com.bazar.bane.bazarshahr.api.model.Job
import com.bazar.bane.bazarshahr.api.model.JobCategory
import com.bazar.bane.bazarshahr.api.model.Mall
import com.bazar.bane.bazarshahr.api.model.Product
import com.bazar.bane.bazarshahr.api.response.SearchResponse
import com.bazar.bane.bazarshahr.databinding.FragmentSearchBinding
import com.bazar.bane.bazarshahr.state.SearchState
import com.bazar.bane.bazarshahr.util.AppConstants
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.CATEGORY_ID
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.SearchViewModel
import com.mancj.materialsearchbar.MaterialSearchBar


class SearchFragment : Fragment(), FragmentFunction, ToolbarFunction,
    MaterialSearchBar.OnSearchActionListener {

    private lateinit var binding: FragmentSearchBinding
    //private lateinit var viewModel: SearchViewModel
    val viewModel by viewModels<SearchViewModel>()
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private var productItems: ArrayList<Any?> = ArrayList()

    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: JobCategorySelectedAdapter
    private var categoryItems: ArrayList<Any?> = ArrayList()

    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var jobAdapter: JobAdapter
    private var jobItems: ArrayList<Any?> = ArrayList()

    private lateinit var mallRecyclerView: RecyclerView
    private lateinit var mallAdapter: MallAdapter
    private var mallItems: ArrayList<Any?> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        val view = binding.root
        //viewModel = SearchViewModel()
        binding.searchViewModel = viewModel
        binding.lifecycleOwner = this
        initialData()
        subscribeObservers()
        setToolbar()
        return view
    }

    override fun initialData() {
        initialProductRecyclerView()
        initialCategoryRecyclerView()
        initialJobRecyclerView()
        initialMallRecyclerView()
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is SearchState.GetSearch -> {
                    viewModel.setSearchResponse(dataState.response)
                    setSearchData(dataState.response)
                    viewModel.setMainLoadingState(false)
                }

                is SearchState.ErrorGetSearch -> {
                    viewModel.setMainLoadingState(false)
                    ToastUtil.showToast(dataState.error)
                }

            }
        })
    }

    override fun setToolbar() {
        binding.searchBar.setOnSearchActionListener(this);
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        search(text.toString())
    }

    override fun onSearchStateChanged(enabled: Boolean) {

    }

    override fun onButtonClicked(buttonCode: Int) {

    }

    private fun initialProductRecyclerView() {
        productItems.clear()
        productRecyclerView = binding.categoryRecyclerView
        productRecyclerView.layoutManager = LinearLayoutManager(context)
        val gridLayoutManager =
            GridLayoutManager(requireContext(), 1, LinearLayoutManager.HORIZONTAL, true)
        productRecyclerView.layoutManager = gridLayoutManager
        productAdapter =
            ProductAdapter(requireContext(), productItems, productRecyclerView)
        productRecyclerView.adapter = productAdapter

        productAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {

            }
        })

        productAdapter.setItemOnClick(object : OnClickItem<Product> {
            override fun clicked(item: Product, position: Int) {
                val bundle = Bundle()
                bundle.putString(AppConstants.PRODUCT_ID, item.id)
                bundle.putString(AppConstants.TITLE, item.name)
                findNavController().navigate(
                    R.id.action_searchFragment_to_productDetailsFragment,
                    bundle
                )
            }
        })
    }

    private fun initialCategoryRecyclerView() {
        categoryItems.clear()
        categoryRecyclerView = binding.categoryRecyclerView
        categoryRecyclerView.layoutManager = LinearLayoutManager(context)
        val gridLayoutManager = GridLayoutManager(context, 1, LinearLayoutManager.HORIZONTAL, true)
        categoryRecyclerView.layoutManager = gridLayoutManager
        categoryAdapter =
            JobCategorySelectedAdapter(requireContext(), categoryItems, categoryRecyclerView)
        categoryRecyclerView.adapter = categoryAdapter
        categoryAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {

            }
        })
        categoryAdapter.setItemOnClick(object : OnClickItem<JobCategory> {
            override fun clicked(item: JobCategory, position: Int) {
                val bundle = Bundle()
                bundle.putString(CATEGORY_ID, item.id)
                bundle.putString(AppConstants.TITLE, item.title)
                findNavController().navigate(
                    R.id.action_searchFragment_to_jobsFragment,
                    bundle
                )
            }
        })
    }

    private fun initialJobRecyclerView() {
        jobItems.clear()
        jobRecyclerView = binding.jobRecyclerView
        jobRecyclerView.layoutManager = LinearLayoutManager(context)
        val horizontalLayoutManager =
            GridLayoutManager(requireContext(), 1, LinearLayoutManager.HORIZONTAL, true)
        jobRecyclerView.layoutManager = horizontalLayoutManager
        jobAdapter =
            JobAdapter(requireContext(), jobItems, jobRecyclerView)
        jobAdapter.horizontalItem = true
        jobRecyclerView.adapter = jobAdapter

        jobAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {

            }
        })

        jobAdapter.setItemOnClick(object : OnClickJob {
            override fun clickedProducts(job: Job, position: Int) {
                val bundle = Bundle()
                bundle.putString(AppConstants.JOB_ID, job.id)
                bundle.putString(AppConstants.TITLE, job.name)
                findNavController().navigate(
                    R.id.action_searchFragment_to_productsFragment,
                    bundle
                )
            }

            override fun clickedInformation(job: Job, position: Int) {
                val bundle = Bundle()
                bundle.putString(AppConstants.JOB_ID, job.id)
                bundle.putString(AppConstants.TITLE, job.name)
                findNavController().navigate(
                    R.id.action_searchFragment_to_jobDetailsFragment,
                    bundle
                )
            }
        })
    }

    private fun initialMallRecyclerView() {
        mallItems.clear()
        mallRecyclerView = binding.mallRecyclerView
        mallRecyclerView.layoutManager = LinearLayoutManager(context)
        val horizontalLayoutManager =
            GridLayoutManager(requireContext(), 1, LinearLayoutManager.HORIZONTAL, true)
        mallRecyclerView.layoutManager = horizontalLayoutManager
        mallAdapter =
            MallAdapter(requireContext(), mallItems, mallRecyclerView)
        mallAdapter.horizontalItem = true
        mallRecyclerView.adapter = mallAdapter

        mallAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {

            }
        })

        mallAdapter.setItemOnClick(object : OnClickMall {
            override fun clickedJobs(mall: Mall, position: Int) {
                val bundle = Bundle()
                bundle.putString(AppConstants.MALL_ID, mall.id)
                bundle.putString(AppConstants.TITLE, mall.name)
                findNavController().navigate(
                    R.id.action_searchFragment_to_jobsFragment,
                    bundle
                )
            }

            override fun clickedInformation(mall: Mall, position: Int) {
                val bundle = Bundle()
                bundle.putString(AppConstants.MALL_ID, mall.id)
                bundle.putString(AppConstants.TITLE, mall.name)
                findNavController().navigate(
                    R.id.action_searchFragment_to_mallDetailsFragment,
                    bundle
                )
            }
        })
    }

    private fun search(searchKey: String) {
        viewModel.setMainLoadingState(true)
        productItems.clear()
        categoryItems.clear()
        jobItems.clear()
        mallItems.clear()
        viewModel.search(searchKey)
    }

    private fun setSearchData(response: SearchResponse?){
            productItems.addAll(response?.data?.products!!)
            productAdapter.setLoading(productAdapter.loadingSuccessState)
            if (productAdapter.itemCount != 0)
                binding.productLayout.visibility = View.VISIBLE
            else binding.productLayout.visibility = View.GONE

            categoryItems.addAll(response.data.categories!!)
            categoryAdapter.setLoading(categoryAdapter.loadingSuccessState)
            if (categoryAdapter.itemCount != 0)
                binding.categoryLayout.visibility = View.VISIBLE
            else binding.categoryLayout.visibility = View.GONE

            jobItems.addAll(response.data.jobs!!)
            jobAdapter.setLoading(jobAdapter.loadingSuccessState)
            if (jobAdapter.itemCount != 0)
                binding.jobLayout.visibility = View.VISIBLE
            else binding.jobLayout.visibility = View.GONE

            mallItems.addAll(response.data.malls!!)
            mallAdapter.setLoading(mallAdapter.loadingSuccessState)
            if (mallAdapter.itemCount != 0)
                binding.mallLayout.visibility = View.VISIBLE
            else binding.mallLayout.visibility = View.GONE
    }
}