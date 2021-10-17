package com.bazar.bane.bazarshahr.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), ToolbarFunction {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val view = binding.root
        /*viewModel = ExploreViewModel()
        binding.exploreViewModel = viewModel*/
        binding.lifecycleOwner = this
        setToolbar()
        return view
    }

    override fun setToolbar() {
        val toolbar: Toolbar = binding.toolbar as Toolbar
        toolbar.findViewById<TextView>(R.id.title_page).text = getString(R.string.home_page_title)
    }
}