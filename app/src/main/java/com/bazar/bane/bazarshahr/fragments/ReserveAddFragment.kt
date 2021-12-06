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
import com.bazar.bane.bazarshahr.databinding.FragmentReserveAddBinding
import com.bazar.bane.bazarshahr.intent.ReserveAddIntent
import com.bazar.bane.bazarshahr.mainFragments.FragmentFunction
import com.bazar.bane.bazarshahr.mainFragments.ToolbarFunction
import com.bazar.bane.bazarshahr.state.ReserveAddState
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.ReserveAddViewModel

class ReserveAddFragment : Fragment(), FragmentFunction, ToolbarFunction {

    private lateinit var binding: FragmentReserveAddBinding
    private lateinit var viewModel: ReserveAddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_reserve_add, container, false)
        val view = binding.root
        viewModel = ReserveAddViewModel()
        binding.reserveAddViewModel = viewModel
        binding.lifecycleOwner = this
        setToolbar()
        initialData()
        subscribeObservers()
        viewModel.setStateEvent(ReserveAddIntent.ReserveAddDescription)
        return view
    }

    override fun initialData() {

    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is ReserveAddState.GetReserveAddDescription -> {
                    viewModel.setMainLoadingState(false)
                    viewModel.setDescription(dataState.response.description!!)
                }

                is ReserveAddState.ErrorGetReserveAddDescription -> {
                    viewModel.setMainLoadingState(false)
                    ToastUtil.showToast(dataState.error)
                }
            }
        })
    }

    override fun setToolbar() {
        val toolbar: Toolbar = binding.toolbar as Toolbar
        toolbar.findViewById<TextView>(R.id.title_page).text =
            getString(R.string.reserve_add)
        toolbar.findViewById<AppCompatImageView>(R.id.back).setOnClickListener {
            findNavController().popBackStack()
        }
    }

}