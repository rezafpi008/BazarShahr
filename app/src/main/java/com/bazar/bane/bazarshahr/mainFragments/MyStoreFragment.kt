package com.bazar.bane.bazarshahr.mainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.api.model.Job
import com.bazar.bane.bazarshahr.api.model.Slider
import com.bazar.bane.bazarshahr.api.request.UserDetailsRequest
import com.bazar.bane.bazarshahr.databinding.FragmentMyStoreBinding
import com.bazar.bane.bazarshahr.intent.UserIntent
import com.bazar.bane.bazarshahr.state.UserState
import com.bazar.bane.bazarshahr.util.AppConstants
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.JOB_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.TITLE
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.USER_JOB_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.USER_JOB_NAME
import com.bazar.bane.bazarshahr.util.SharedPreferenceUtil
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.UserViewModel
import com.bumptech.glide.Glide


class MyStoreFragment : Fragment(), FragmentFunction {

    private lateinit var binding: FragmentMyStoreBinding
    private val viewModel: UserViewModel by viewModels()
    private var jobItems: ArrayList<Job> = ArrayList()
    private var userJob = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_store, container, false)
        val view = binding.root
        binding.userViewModel = viewModel
        binding.lifecycleOwner = this
        initialData()
        subscribeObservers()
        viewModel.setStateEvent(UserIntent.UserDetails(UserDetailsRequest()))
        return view
    }

    override fun initialData() {
        binding.submitStore.setOnClickListener {
            if (!userJob)
                findNavController().navigate(R.id.action_myStoreFragment_to_addStoreFragment)
            else
                ToastUtil.showToast(R.string.user_submitted_job)
        }

        binding.submitProduct.setOnClickListener {
            if (userJob)
                findNavController().navigate(R.id.action_myStoreFragment_to_addProductFragment)
            else
                ToastUtil.showToast(R.string.no_user_jobs)
        }

        binding.myStore.setOnClickListener {
            if (userJob) {
                val bundle = Bundle()
                bundle.putString(JOB_ID, jobItems[0].id)
                bundle.putString(TITLE, jobItems[0].name)
                findNavController().navigate(
                    R.id.action_myStoreFragment_to_productsFragment,
                    bundle
                )
            } else
                ToastUtil.showToast(R.string.no_user_jobs)
        }

        binding.editProfile.setOnClickListener {
            if (userJob)
                findNavController().navigate(R.id.action_myStoreFragment_to_editProfileFragment)
        }

        binding.suggestions.setOnClickListener {
            findNavController().navigate(R.id.action_myStoreFragment_to_sendSuggestionsFragment)
        }
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is UserState.GetUserDetails -> {
                    viewModel.setMainLoadingState(false)
                    viewModel.setUser(dataState.response.data?.user!!)
                    setUserJob(dataState.response.data.jobs!!)
                    setUserImg(dataState.response.data.user.img)
                }

                is UserState.ErrorGetUserDetails -> {
                    viewModel.setMainLoadingState(false)
                    ToastUtil.showToast(dataState.error)
                }
            }
        })
    }

    private fun setUserImg(url: String?) {
        if (url != null)
            Glide.with(requireContext())
                .load(url)
                .placeholder(R.drawable.image_default_avatar)
                .error(R.drawable.image_default_avatar)
                .into(binding.userImg)
    }

    private fun setUserJob(userJobs: ArrayList<Job>) {
        jobItems.addAll(userJobs)
        if (jobItems.size > 0) {
            userJob = true
            SharedPreferenceUtil.saveStringValue(USER_JOB_ID, jobItems[0].id)
            SharedPreferenceUtil.saveStringValue(USER_JOB_NAME, jobItems[0].name)
        }
    }
}