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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.JobAdapter
import com.bazar.bane.bazarshahr.adapter.OnClickJob
import com.bazar.bane.bazarshahr.adapter.OnLoadMoreListener
import com.bazar.bane.bazarshahr.api.model.Job

import com.bazar.bane.bazarshahr.databinding.FragmentJobBinding
import com.bazar.bane.bazarshahr.mainFragments.FragmentFunction
import com.bazar.bane.bazarshahr.mainFragments.ToolbarFunction
import com.bazar.bane.bazarshahr.state.JobState
import com.bazar.bane.bazarshahr.util.AppConstants
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.JobViewModel


class JobsFragment : Fragment(), FragmentFunction, ToolbarFunction {

    private lateinit var binding: FragmentJobBinding
    private lateinit var viewModel: JobViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: JobAdapter
    private var items: ArrayList<Any?> = ArrayList()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_job, container, false)
        val view = binding.root
        viewModel = JobViewModel()
        binding.jobViewModel = viewModel
        binding.lifecycleOwner = this
        initialData()
        subscribeObservers()
        setToolbar()
        return view
    }

    override fun initialData() {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val horizontalLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = horizontalLayoutManager
        adapter =
            JobAdapter(requireContext(), items, recyclerView)
        recyclerView.adapter = adapter

        adapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                //viewModel.setStateEvent(ExploreIntent.ActiveAuction)
            }
        })

        adapter.setItemOnClick(object : OnClickJob {
            override fun clickedProducts(job: Job, position: Int) {
                val bundle = Bundle()
                bundle.putString(AppConstants.JOB_ID, job.id)
                findNavController().navigate(
                    R.id.action_jobsFragment_to_jobDetailsFragment,
                    bundle
                )
            }

            override fun clickedInformation(job: Job, position: Int) {
                val bundle = Bundle()
                bundle.putString(AppConstants.JOB_ID, job.id)
                bundle.putString(AppConstants.TITLE, job.name)
                findNavController().navigate(
                    R.id.action_jobsFragment_to_jobDetailsFragment,
                    bundle
                )
            }
        })
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is JobState.GetJobs -> {
                    viewModel.setMainLoadingState(false)
                    items.addAll(dataState.response.jobs!!)
                    adapter.setLoading(adapter.loadingSuccessState)
                    if (adapter.itemCount == 0)
                        viewModel.setMessageVisibilityState(true)
                }

                is JobState.ErrorGetJobs -> {
                    viewModel.setMainLoadingState(false)
                    adapter.setLoading(adapter.loadingFailState)
                    ToastUtil.showToast(dataState.error)
                }
            }
        })
    }

    override fun setToolbar() {
        val toolbar: Toolbar = binding.toolbar as Toolbar
        toolbar.findViewById<TextView>(R.id.title_page).text =
            getString(R.string.jobs_page_title)
        toolbar.findViewById<AppCompatImageView>(R.id.back).setOnClickListener {
            findNavController().popBackStack()
        }
    }

}