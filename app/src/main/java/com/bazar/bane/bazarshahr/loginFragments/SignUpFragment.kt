package com.bazar.bane.bazarshahr.loginFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.api.request.SignUpRequest
import com.bazar.bane.bazarshahr.databinding.FragmentSignUpBinding
import com.bazar.bane.bazarshahr.intent.LoginIntent
import com.bazar.bane.bazarshahr.mainFragments.FragmentFunction
import com.bazar.bane.bazarshahr.state.LoginState
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.LoginViewModel
import com.hbb20.CountryCodePicker


class SignUpFragment : Fragment(), FragmentFunction {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        val view = binding.root
        viewModel = LoginViewModel()
        binding.loginViewModel = viewModel
        binding.lifecycleOwner = this
        initialData()
        subscribeObservers()
        return view
    }

    override fun initialData() {
        binding.login.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.register.setOnClickListener {
            if (checkRegister()) {
                viewModel.setMainLoadingState(true)
                viewModel.setStateEvent(
                    LoginIntent.SignUp(
                        SignUpRequest(
                            "09" + binding.edtMobile.text.toString(),
                            binding.userName.text.toString(),
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
                is LoginState.SignUp -> {
                    viewModel.setMainLoadingState(false)
                    viewModel.saveToken(dataState.response.data?.token!!)
                    viewModel.saveUserId(dataState.response.data.userId!!)
                    findNavController().navigate(R.id.action_signUpFragment_to_mainActivity)
                    requireActivity().finish()
                }

                is LoginState.ErrorSignUp -> {
                    viewModel.setMainLoadingState(false)
                    ToastUtil.showToast(dataState.error)
                }
            }
        })
    }

    private fun checkRegister(): Boolean {
        var flag = true
        if (binding.edtMobile.text.length != 9) {
            flag = false
            ToastUtil.showToast(R.string.incorrect_phone_number)
        }
        if (binding.userName.text.toString() == "") {
            binding.userName.error = getString(R.string.please_fill_this_field)
            flag = false
        }
        if (binding.password.text.toString() == "") {
            binding.password.error = getString(R.string.please_fill_this_field)
            flag = false
        } else {
            if (binding.password.text.toString() != binding.userConfirmPassword.text.toString()
            ) {
                binding.userConfirmPassword.error = getString(R.string.confirm_password_match)
                flag = false
            }
        }
        return flag
    }

}