package com.bazar.bane.bazarshahr.loginFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.api.request.SignInRequest
import com.bazar.bane.bazarshahr.databinding.FragmentSignInBinding
import com.bazar.bane.bazarshahr.intent.LoginIntent
import com.bazar.bane.bazarshahr.mainFragments.FragmentFunction
import com.bazar.bane.bazarshahr.state.LoginState
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.LoginViewModel
import com.hbb20.CountryCodePicker


class SignInFragment : Fragment(), FragmentFunction {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        val view = binding.root
        viewModel = LoginViewModel()
        binding.loginViewModel = viewModel
        binding.lifecycleOwner = this
        initialData()
        subscribeObservers()
        return view
    }

    override fun initialData() {

        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.login.setOnClickListener {
            if (checkLogin()) {
                viewModel.setMainLoadingState(true)
                viewModel.setStateEvent(
                    LoginIntent.SignIn(
                        SignInRequest(
                            binding.phoneNumber.text.toString(),
                            binding.password.text.toString()
                        )
                    )
                )
            }
        }
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is LoginState.SignIn -> {
                    viewModel.setMainLoadingState(false)
                    viewModel.saveToken(dataState.response.data?.token!!)
                    viewModel.saveUserId(dataState.response.data.userId!!)
                    findNavController().navigate(R.id.action_signInFragment_to_mainActivity)
                    requireActivity().finish()
                }

                is LoginState.ErrorSignIn -> {
                    viewModel.setMainLoadingState(false)
                    ToastUtil.showToast(getString(R.string.error_sign_in))
                }
            }
        })
    }

    private fun checkLogin(): Boolean {
        var flag = true
        if (binding.phoneNumber.text.length != 11) {
            ToastUtil.showToast(R.string.incorrect_phone_number)
            flag = false
        }
        if (binding.password.text.toString() == "") {
            binding.password.error = getString(R.string.please_fill_this_field)
            flag = false
        }
        return flag
    }

}