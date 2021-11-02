package com.bazar.bane.bazarshahr.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.OnClickItem
import com.bazar.bane.bazarshahr.adapter.SliderAdapter
import com.bazar.bane.bazarshahr.api.model.Slider
import com.bazar.bane.bazarshahr.databinding.FragmentHomeBinding
import com.bazar.bane.bazarshahr.intent.HomeIntent
import com.bazar.bane.bazarshahr.state.HomeState
import com.bazar.bane.bazarshahr.viewModel.HomeViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations


class HomeFragment : Fragment(), FragmentFunction, ToolbarFunction {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    lateinit var sliderAdapter: SliderAdapter
    private var sliderItems: ArrayList<Slider> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val view = binding.root
        viewModel = HomeViewModel()
        binding.homeViewModel = viewModel
        binding.lifecycleOwner = this
        initialData()
        subscribeObservers()
        setToolbar()
        viewModel.setStateEvent(HomeIntent.Slider)
        return view
    }

    override fun initialData() {

    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner,  { dataState ->

            when (dataState) {
                is HomeState.GetSlider -> {
                    sliderItems.addAll(dataState.response.sliders!!)
                    initSliderView()
                }
                is HomeState.ErrorGetSlider -> {
                    viewModel.setMainLoadingState(false)
                }
                is HomeState.GetHome -> {

                }
                is HomeState.ErrorGetHome -> {

                }
            }
        })
    }

    override fun setToolbar() {
        val toolbar: Toolbar = binding.toolbar as Toolbar
        toolbar.findViewById<TextView>(R.id.title_page).text = getString(R.string.home_page_title)
    }

    private fun initSliderView() {
        val sliderView = binding.slider
        sliderAdapter = SliderAdapter(requireContext(), sliderItems)
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION)
        sliderAdapter.setItemOnClick(object : OnClickItem<Slider> {
            override fun clicked(item: Slider, position: Int) {

            }
        })
    }

}