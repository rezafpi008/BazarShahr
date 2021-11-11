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
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.api.request.JobDetailsRequest
import com.bazar.bane.bazarshahr.api.request.JobsRequest
import com.bazar.bane.bazarshahr.databinding.FragmentJobDetailsBinding
import com.bazar.bane.bazarshahr.intent.JobIntent
import com.bazar.bane.bazarshahr.mainFragments.FragmentFunction
import com.bazar.bane.bazarshahr.mainFragments.ToolbarFunction
import com.bazar.bane.bazarshahr.state.JobState
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.JOB_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.TITLE
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.JobViewModel
import com.texonapp.oneringgit.adapter.GallerySliderAdapter

class JobDetailsFragment : Fragment(), FragmentFunction, ToolbarFunction {

    private lateinit var binding: FragmentJobDetailsBinding
    private lateinit var viewModel: JobViewModel
    private lateinit var jobId: String
    private lateinit var title: String
    private lateinit var galleryAdapter: GallerySliderAdapter
    private var galleryItems: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jobId = arguments?.getString(JOB_ID)!!
        title = arguments?.getString(TITLE)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_job_details, container, false)
        val view = binding.root
        viewModel = JobViewModel()
        binding.jobViewModel = viewModel
        binding.lifecycleOwner = this
        setToolbar()
        initialData()
        subscribeObservers()
        viewModel.setStateEvent(JobIntent.JobDetails(JobDetailsRequest(jobId)))
        return view
    }

    private fun initGalleryView() {
        val sliderView = binding.galleryView
        galleryAdapter = GallerySliderAdapter(requireContext(), galleryItems)
        sliderView.setSliderAdapter(galleryAdapter)
    }

    override fun initialData() {
        binding.showProduct.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(JOB_ID, jobId)
            bundle.putString(TITLE, title)
            findNavController().navigate(
                R.id.action_jobDetailsFragment_to_productsFragment,
                bundle
            )
        }

        binding.sendMessage.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(JOB_ID, jobId)
            bundle.putString(TITLE, title)
            findNavController().navigate(
                R.id.action_jobDetailsFragment_to_chat,
                bundle
            )
        }
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is JobState.GetJobDetails -> {
                    viewModel.setMainLoadingState(false)
                    viewModel.setJob(dataState.response.job!!)
                    //galleryItems.add(dataState.response.job.img!!)
                    galleryItems.addAll(dataState.response.job.gallery!!)
                    initGalleryView()
                }

                is JobState.ErrorGetJob -> {
                    viewModel.setMainLoadingState(false)
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

}