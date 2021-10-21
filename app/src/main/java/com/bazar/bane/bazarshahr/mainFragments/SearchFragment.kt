package com.bazar.bane.bazarshahr.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.databinding.FragmentSearchBinding
import com.mancj.materialsearchbar.MaterialSearchBar


class SearchFragment : Fragment(), FragmentFunction, ToolbarFunction,
    MaterialSearchBar.OnSearchActionListener {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        val view = binding.root
        /*viewModel = ExploreViewModel()
        binding.exploreViewModel = viewModel*/
        binding.lifecycleOwner = this
        initialData()
        subscribeObservers()
        setToolbar()
        return view
    }

    override fun initialData() {

    }

    override fun subscribeObservers() {

    }

    override fun setToolbar() {
        binding.searchBar.setOnSearchActionListener(this);
    }

    override fun onSearchConfirmed(text: CharSequence?) {

    }

    override fun onSearchStateChanged(enabled: Boolean) {
    }

    override fun onButtonClicked(buttonCode: Int) {
    }


}