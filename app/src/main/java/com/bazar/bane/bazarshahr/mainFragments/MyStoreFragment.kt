package com.bazar.bane.bazarshahr.mainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.databinding.FragmentMyStoreBinding


class MyStoreFragment : Fragment() {

    private lateinit var binding: FragmentMyStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_store, container, false)
        val view = binding.root
        /*viewModel = ExploreViewModel()
        binding.exploreViewModel = viewModel*/
        binding.lifecycleOwner = this
        initialData()

        return view
    }

    private fun initialData(){
        binding.submitStore.setOnClickListener {
            findNavController().navigate(R.id.action_myStoreFragment_to_addStoreFragment)
        }

        binding.submitProduct.setOnClickListener {
            findNavController().navigate(R.id.action_myStoreFragment_to_addProductFragment)
        }
    }
}