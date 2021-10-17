package com.bazar.bane.bazarshahr.mainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.databinding.FragmentMyStoreBinding


class MyStoreFragment : Fragment(), ToolbarFunction {

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
        setToolbar()
        return view
    }

    override fun setToolbar() {
        val toolbar: Toolbar = binding.toolbar as Toolbar
        toolbar.findViewById<TextView>(R.id.title_page).text =
            getString(R.string.my_store_page_title)
    }

}