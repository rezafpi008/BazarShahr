package com.bazar.bane.bazarshahr.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
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
import com.bazar.bane.bazarshahr.api.model.Mall
import com.bazar.bane.bazarshahr.api.model.Slider
import com.bazar.bane.bazarshahr.databinding.FragmentHomeBinding
import com.bazar.bane.bazarshahr.intent.HomeIntent
import com.bazar.bane.bazarshahr.popUp.PopUpCallback
import com.bazar.bane.bazarshahr.popUp.SelectCityPopUp
import com.bazar.bane.bazarshahr.state.HomeState
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.CITY_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.CITY_NAME
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.JOB_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.MALL_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.PRODUCT_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.TITLE
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.TYPE_JOB
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.TYPE_MALL
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.TYPE_PRODUCT
import com.bazar.bane.bazarshahr.util.SharedPreferenceUtil
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.HomeViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations


class HomeFragment : Fragment(), FragmentFunction {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var sliderAdapter: SliderAdapter
    private var sliderItems: ArrayList<Slider> = ArrayList()

    private lateinit var mallRecyclerView: RecyclerView
    private lateinit var mallAdapter: MallAdapter
    private var mallItems: ArrayList<Any?> = ArrayList()

    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var jobAdapter: JobAdapter
    private var jobItems: ArrayList<Any?> = ArrayList()
    private var getJob = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val view = binding.root
        binding.homeViewModel = viewModel
        binding.lifecycleOwner = this
        initialData()
        subscribeObservers()
        return view
    }

    override fun initialData() {
        if (!SharedPreferenceUtil.getStringValue(CITY_NAME).isNullOrBlank()) {
            binding.cityName.text = SharedPreferenceUtil.getStringValue(CITY_NAME)
        }
        binding.choiceCity.setOnClickListener {
            SelectCityPopUp(
                requireContext(),
                popUpCallback,
                this
            ).show()
        }

        binding.reserveAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_reserveAddFragment)
        }

        initialMallRecyclerView()
        initialJobRecyclerView()
        initSliderView()
    }

    private val popUpCallback: PopUpCallback = object : PopUpCallback {
        override fun setId(id: String, title: String) {
            binding.cityName.text = title
            SharedPreferenceUtil.saveStringValue(CITY_ID, id)
            SharedPreferenceUtil.saveStringValue(CITY_NAME, title)
            initialData()
            viewModel.changeCity()
        }
    }

    private fun initialMallRecyclerView() {
        mallRecyclerView = binding.mallRecycler
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
                viewModel.getMalls()
            }
        })

        mallAdapter.setItemOnClick(object : OnClickMall {
            override fun clickedJobs(mall: Mall, position: Int) {
                val bundle = Bundle()
                bundle.putString(MALL_ID, mall.id)
                bundle.putString(TITLE, mall.name)
                findNavController().navigate(
                    R.id.action_homeFragment_to_mallCategoryFragment,
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

    private fun initialJobRecyclerView() {
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
                viewModel.getJobs()
            }
        })

        jobAdapter.setItemOnClick(object : OnClickJob {
            override fun clickedProducts(job: Job, position: Int) {
                val bundle = Bundle()
                bundle.putString(JOB_ID, job.id)
                bundle.putString(TITLE, job.name)
                findNavController().navigate(
                    R.id.action_homeFragment_to_productsFragment,
                    bundle
                )
            }

            override fun clickedInformation(job: Job, position: Int) {
                val bundle = Bundle()
                bundle.putString(JOB_ID, job.id)
                bundle.putString(TITLE, job.name)
                findNavController().navigate(
                    R.id.action_homeFragment_to_jobDetailsFragment,
                    bundle
                )
            }
        })
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->

            when (dataState) {
                is HomeState.GetSlider -> {
                    sliderItems.addAll(dataState.response.sliders!!)
                    if (sliderItems.size != 0) {
                        binding.slider.visibility = View.VISIBLE
                        sliderAdapter.notifyDataSetChanged()
                    }
                    viewModel.getMalls()
                }
                is HomeState.ErrorGetSlider -> {
                    viewModel.getMalls()
                }

                is HomeState.GetMalls -> {
                    viewModel.setMainLoadingState(false)
                    mallItems.addAll(dataState.response.malls!!)
                    mallAdapter.setLoading(mallAdapter.loadingSuccessState)
                    if (mallAdapter.itemCount == 0)
                        viewModel.setMessageVisibilityState(true)
                    if (getJob) {
                        getJob = false
                        viewModel.getJobs()
                    } else {
                        viewModel.stateOff()
                    }
                }

                is HomeState.ErrorGetMalls -> {
                    viewModel.setMainLoadingState(false)
                    mallAdapter.setLoading(mallAdapter.loadingFailState)
                    ToastUtil.showToast(dataState.error)
                    viewModel.stateOff()
                }
                is HomeState.GetJobs -> {
                    viewModel.setMainLoadingState(false)
                    jobItems.addAll(dataState.response.jobs!!)
                    jobAdapter.setLoading(jobAdapter.loadingSuccessState)
                    if (jobAdapter.itemCount == 0)
                        viewModel.setMessageVisibilityState(true)
                    viewModel.stateOff()
                }
                is HomeState.ErrorGetJobs -> {
                    viewModel.setMainLoadingState(false)
                    jobAdapter.setLoading(jobAdapter.loadingFailState)
                    ToastUtil.showToast(dataState.error)
                    viewModel.stateOff()
                }

            }
        })
    }

    private fun initSliderView() {
        val sliderView = binding.slider
        sliderAdapter = SliderAdapter(requireContext(), sliderItems)
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION)
        sliderAdapter.setItemOnClick(object : OnClickItem<Slider> {
            override fun clicked(item: Slider, position: Int) {
                val bundle = Bundle()
                when (item.adPostType) {
                    TYPE_JOB -> {
                        bundle.putString(JOB_ID, item.adId)
                        bundle.putString(TITLE, getString(R.string.job_details))
                        findNavController().navigate(
                            R.id.action_homeFragment_to_jobDetailsFragment,
                            bundle
                        )
                    }
                    TYPE_PRODUCT -> {
                        bundle.putString(PRODUCT_ID, item.adId)
                        bundle.putString(TITLE, getString(R.string.product_details))
                        findNavController().navigate(
                            R.id.action_homeFragment_to_productDetailsFragment,
                            bundle
                        )
                    }
                    TYPE_MALL -> {
                        bundle.putString(MALL_ID, item.adId)
                        bundle.putString(TITLE, getString(R.string.mall_details))
                        findNavController().navigate(
                            R.id.action_homeFragment_to_mallDetailsFragment,
                            bundle
                        )
                    }
                }
            }
        })
    }

}