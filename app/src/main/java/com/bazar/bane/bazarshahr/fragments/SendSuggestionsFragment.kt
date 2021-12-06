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
import com.bazar.bane.bazarshahr.api.request.SuggestionsRequest
import com.bazar.bane.bazarshahr.databinding.FragmentSendSuggestionsBinding
import com.bazar.bane.bazarshahr.intent.UserIntent
import com.bazar.bane.bazarshahr.mainFragments.FragmentFunction
import com.bazar.bane.bazarshahr.mainFragments.ToolbarFunction
import com.bazar.bane.bazarshahr.state.UserState
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.UserViewModel


class SendSuggestionsFragment : Fragment(), FragmentFunction, ToolbarFunction {

    private lateinit var binding: FragmentSendSuggestionsBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_send_suggestions, container, false)
        val view = binding.root
        viewModel = UserViewModel()
        binding.userViewModel = viewModel
        binding.lifecycleOwner = this
        setToolbar()
        initialData()
        subscribeObservers()
        return view
    }

    override fun initialData() {
        binding.submit.setOnClickListener {
            if (!binding.description.text.toString().isNullOrEmpty()) {
                viewModel.setSubmitLoadingState(true)
                viewModel.setStateEvent(
                    UserIntent.SendSuggestions
                        (SuggestionsRequest(binding.description.text.toString()))
                )
            } else
                binding.description.error = getString(R.string.please_fill_this_field)
        }
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is UserState.SendSuggestions -> {
                    viewModel.setSubmitLoadingState(false)
                    ToastUtil.showToast("از شما بابت ثبت نظر خود متشکریم.")
                }

                is UserState.ErrorSendSuggestions -> {
                    viewModel.setSubmitLoadingState(false)
                    ToastUtil.showToast(R.string.try_again)
                }
            }
        })
    }

    override fun setToolbar() {
        val toolbar: Toolbar = binding.toolbar as Toolbar
        toolbar.findViewById<TextView>(R.id.title_page).text =
            getString(R.string.critics_and_suggestions)
        toolbar.findViewById<AppCompatImageView>(R.id.back).setOnClickListener {
            findNavController().popBackStack()
        }
    }

}