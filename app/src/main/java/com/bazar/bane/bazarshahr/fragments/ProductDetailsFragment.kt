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
import com.bazar.bane.bazarshahr.api.request.ProductDetailsRequest
import com.bazar.bane.bazarshahr.databinding.FragmentJobDetailsBinding
import com.bazar.bane.bazarshahr.databinding.FragmentProductDetailsBinding
import com.bazar.bane.bazarshahr.intent.JobIntent
import com.bazar.bane.bazarshahr.intent.ProductIntent
import com.bazar.bane.bazarshahr.mainFragments.FragmentFunction
import com.bazar.bane.bazarshahr.mainFragments.ToolbarFunction
import com.bazar.bane.bazarshahr.state.JobState
import com.bazar.bane.bazarshahr.state.ProductState
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.PRODUCT_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.TITLE
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.JobViewModel
import com.bazar.bane.bazarshahr.viewModel.ProductViewModel
import com.texonapp.oneringgit.adapter.GallerySliderAdapter


class ProductDetailsFragment : Fragment(), FragmentFunction, ToolbarFunction {

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var productId: String
    private lateinit var title: String
    private lateinit var galleryAdapter: GallerySliderAdapter
    private var galleryItems: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productId = arguments?.getString(PRODUCT_ID)!!
        title = arguments?.getString(TITLE)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false)
        val view = binding.root
        viewModel = ProductViewModel()
        binding.productViewModel = viewModel
        binding.lifecycleOwner = this
        setToolbar()
        initialData()
        subscribeObservers()
        viewModel.setStateEvent(ProductIntent.ProductDetails(ProductDetailsRequest(productId)))
        return view
    }

    private fun initGalleryView() {
        val sliderView = binding.galleryView
        galleryAdapter = GallerySliderAdapter(requireContext(), galleryItems)
        sliderView.setSliderAdapter(galleryAdapter)
    }

    override fun initialData() {

    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is ProductState.GetProductDetails -> {
                    viewModel.setMainLoadingState(false)
                    viewModel.setProduct(dataState.response.product!!)
                    galleryItems.addAll(dataState.response.product.gallery!!)
                    initGalleryView()
                }

                is ProductState.ErrorGetProductDetails -> {
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