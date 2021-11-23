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
import com.bazar.bane.bazarshahr.databinding.FragmentJobDetailsBinding
import com.bazar.bane.bazarshahr.databinding.FragmentMallDetailsBinding
import com.bazar.bane.bazarshahr.intent.JobIntent
import com.bazar.bane.bazarshahr.mainFragments.FragmentFunction
import com.bazar.bane.bazarshahr.mainFragments.ToolbarFunction
import com.bazar.bane.bazarshahr.state.JobState
import com.bazar.bane.bazarshahr.state.MallState
import com.bazar.bane.bazarshahr.util.AppConstants
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.MALL_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.TITLE
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.JobViewModel
import com.bazar.bane.bazarshahr.viewModel.MallViewModel
import com.bumptech.glide.Glide
import com.texonapp.oneringgit.adapter.GallerySliderAdapter

class MallDetailsFragment : Fragment(), FragmentFunction, ToolbarFunction {


    private lateinit var binding: FragmentMallDetailsBinding
    private lateinit var viewModel: MallViewModel
    private lateinit var mallId: String
    private lateinit var title: String


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
            DataBindingUtil.inflate(inflater, R.layout.fragment_mall_details, container, false)
        val view = binding.root
        viewModel = MallViewModel()
        binding.mallViewModel = viewModel
        binding.lifecycleOwner = this
        setToolbar()
        initialData()
        subscribeObservers()
        viewModel.getMall(mallId)
        return view
    }

    override fun initialData() {
        binding.showJobs.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(MALL_ID, mallId)
            bundle.putString(TITLE, title)
            findNavController().navigate(
                R.id.action_mallDetailsFragment_to_mallCategoryFragment,
                bundle
            )
        }
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is MallState.GetMallDetails -> {
                    viewModel.setMainLoadingState(false)
                    viewModel.setMall(dataState.response.mall!!)
                    setMallImage(dataState.response.mall.img!!)
                }

                is MallState.ErrorGetMallDetails -> {
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

    private fun setMallImage(img:String){
        Glide.with(requireContext())
            .load(img)
            .placeholder(R.drawable.image_default)
            .error(R.drawable.image_default)
            .into(binding.mallImage)
    }

}