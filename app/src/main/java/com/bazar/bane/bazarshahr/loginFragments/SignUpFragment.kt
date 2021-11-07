package com.bazar.bane.bazarshahr.loginFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.api.request.SignUpRequest
import com.bazar.bane.bazarshahr.databinding.FragmentSignUpBinding
import com.bazar.bane.bazarshahr.intent.LoginIntent
import com.bazar.bane.bazarshahr.mainFragments.FragmentFunction
import com.bazar.bane.bazarshahr.state.LoginState
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.LoginViewModel


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
            if (check()) {
                viewModel.setMainLoadingState(false)
                viewModel.setStateEvent(
                    LoginIntent.SignUp(
                        SignUpRequest(
                            binding.name.text.toString(),
                            binding.email.text.toString(),
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
                is LoginState.SignUp-> {
                    viewModel.setMainLoadingState(false)
                    viewModel.saveToken(dataState.response.token!!)
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

    private fun check(): Boolean {
        var checkFlag = true

        return checkFlag
    }

}