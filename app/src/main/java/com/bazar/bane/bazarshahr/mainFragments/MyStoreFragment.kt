package com.bazar.bane.bazarshahr.mainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.api.request.UserDetailsRequest
import com.bazar.bane.bazarshahr.databinding.FragmentMyStoreBinding
import com.bazar.bane.bazarshahr.intent.UserIntent
import com.bazar.bane.bazarshahr.state.UserState
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.UserViewModel
import com.bumptech.glide.Glide


class MyStoreFragment : Fragment(), FragmentFunction {

    private lateinit var binding: FragmentMyStoreBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_store, container, false)
        val view = binding.root
        viewModel = UserViewModel()
        binding.userViewModel = viewModel
        binding.lifecycleOwner = this
        initialData()
        subscribeObservers()
        viewModel.setStateEvent(UserIntent.UserDetails(UserDetailsRequest()))
        return view
    }

    override fun initialData() {
        binding.submitStore.setOnClickListener {
            findNavController().navigate(R.id.action_myStoreFragment_to_addStoreFragment)
        }

        binding.submitProduct.setOnClickListener {
            findNavController().navigate(R.id.action_myStoreFragment_to_addProductFragment)
        }
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is UserState.GetUserDetails -> {
                    viewModel.setMainLoadingState(false)
                    viewModel.setUser(dataState.response.data?.user!!)
                    setUserImg(dataState.response.data.user.img)
                }

                is UserState.ErrorGetUserDetails -> {
                    viewModel.setMainLoadingState(false)
                    ToastUtil.showToast(dataState.error)
                }

                is UserState.EditUserDetails -> {

                }
                is UserState.ErrorEditUserDetails -> {

                }
            }
        })
    }

    private fun setUserImg(url: String?) {
        if (url != null)
            Glide.with(requireContext())
                .load(url)
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_default)
                .into(binding.userImg)
    }
}