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
import com.bazar.bane.bazarshahr.databinding.FragmentJobDetailsBinding
import com.bazar.bane.bazarshahr.mainFragments.FragmentFunction
import com.bazar.bane.bazarshahr.mainFragments.ToolbarFunction
import com.bazar.bane.bazarshahr.state.JobCategoryState
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.JOB_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.TITLE
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.JobCategoryViewModel
import com.texonapp.oneringgit.adapter.GallerySliderAdapter

class JobDetailsFragment : Fragment(), FragmentFunction, ToolbarFunction {

    lateinit var binding: FragmentJobDetailsBinding
    lateinit var viewModel: JobCategoryViewModel
    lateinit var auctionId: String
    lateinit var title: String
    private lateinit var galleryAdapter: GallerySliderAdapter
    private var galleryItems: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auctionId = arguments?.getString(JOB_ID)!!
        title = arguments?.getString(TITLE)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_job_details, container, false)
        val view = binding.root
        viewModel = JobCategoryViewModel()
        binding.jobCategoryViewModel = viewModel
        binding.lifecycleOwner = this
        initialData()
        subscribeObservers()
        return view
    }

    private fun initGalleryView() {
        val sliderView = binding.galleryView
        galleryAdapter = GallerySliderAdapter(requireContext(), galleryItems)
        sliderView.setSliderAdapter(galleryAdapter)
    }

    override fun initialData() {
        binding.showProduct.setOnClickListener {

        }
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is JobCategoryState.GetJob -> {
                    viewModel.setMainLoadingState(false)
                    //viewModel.setJob(dataState.response.jobs)
                    //galleryItems.add(dataState.response.data.auction.product?.img!!)
                    //galleryItems.addAll(dataState.response.data.auction.product.gallery)
                    initGalleryView()
                }

                is JobCategoryState.ErrorGetJob -> {
                    viewModel.setMainLoadingState(false)
                    ToastUtil.showToast(dataState.error)
                }
            }
        })
    }

    override fun setToolbar() {
        val toolbar: Toolbar = binding.toolbar as Toolbar
        toolbar.findViewById<TextView>(R.id.title_page).text = title
        binding.toolbar.findViewById<AppCompatImageView>(R.id.back).setOnClickListener {
            findNavController().popBackStack()
        }
    }

}