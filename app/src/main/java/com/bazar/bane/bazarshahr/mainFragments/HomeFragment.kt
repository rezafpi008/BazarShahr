package com.bazar.bane.bazarshahr.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.*
import com.bazar.bane.bazarshahr.api.model.Job
import com.bazar.bane.bazarshahr.api.model.Mall
import com.bazar.bane.bazarshahr.api.model.Slider
import com.bazar.bane.bazarshahr.databinding.FragmentHomeBinding
import com.bazar.bane.bazarshahr.intent.HomeIntent
import com.bazar.bane.bazarshahr.state.HomeState
import com.bazar.bane.bazarshahr.util.AppConstants
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.MALL_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.TITLE
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.HomeViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations


class HomeFragment : Fragment(), FragmentFunction, ToolbarFunction {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var sliderAdapter: SliderAdapter
    private var sliderItems: ArrayList<Slider> = ArrayList()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MallAdapter
    private var items: ArrayList<Any?> = ArrayList()

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
        viewModel.getMalls()
        return view
    }

    override fun initialData() {
        items.clear()
        recyclerView = binding.mallRecycler
        recyclerView.layoutManager = LinearLayoutManager(context)
        val horizontalLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = horizontalLayoutManager
        adapter =
            MallAdapter(requireContext(), items, recyclerView)
        recyclerView.adapter = adapter

        adapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                viewModel.getMalls()
            }
        })

        adapter.setItemOnClick(object : OnClickMall {
            override fun clickedJobs(mall: Mall, position: Int) {
                val bundle = Bundle()
                bundle.putString(MALL_ID, mall.id)
                bundle.putString(TITLE, mall.name)
                findNavController().navigate(
                    R.id.action_homeFragment_to_jobsFragment,
                    bundle
                )
            }

            override fun clickedInformation(mall: Mall, position: Int) {
                val bundle = Bundle()
                bundle.putString(MALL_ID, mall.id)
                bundle.putString(TITLE, mall.name)
                findNavController().navigate(
                    R.id.action_homeFragment_to_mallDetailsFragment,
                    bundle
                )
            }
        })
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->

            when (dataState) {
                is HomeState.GetSlider -> {
                    sliderItems.clear()
                    sliderItems.add(
                        Slider(
                            "23",
                            "https://root-nation.com/wp-content/uploads/2020/04/galaxy-s20ultra-1.jpg"
                        )
                    )
                    sliderItems.add(
                        Slider(
                            "233",
                            "https://saymandigital.com/wp-content/uploads/2020/04/DSC01299.jpg"
                        )
                    )
                    sliderItems.addAll(dataState.response.sliders!!)
                    if (sliderItems.size != 0) {
                        viewModel.setMainLoadingState(false)
                        binding.slider.visibility = View.VISIBLE
                        initSliderView()
                    }

                }
                is HomeState.ErrorGetSlider -> {

                }

                is HomeState.GetMalls -> {
                    viewModel.setStateEvent(HomeIntent.Slider)
                    viewModel.setMainLoadingState(false)
                    items.addAll(dataState.response.malls!!)
                    adapter.setLoading(adapter.loadingSuccessState)
                    if (adapter.itemCount == 0)
                        viewModel.setMessageVisibilityState(true)
                }

                is HomeState.ErrorGetMalls -> {
                    viewModel.setMainLoadingState(false)
                    adapter.setLoading(adapter.loadingFailState)
                    ToastUtil.showToast(dataState.error)
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