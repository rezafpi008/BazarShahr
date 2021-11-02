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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.OnClickItem
import com.bazar.bane.bazarshahr.adapter.ProductAdapter
import com.bazar.bane.bazarshahr.adapter.OnLoadMoreListener
import com.bazar.bane.bazarshahr.api.model.Product
import com.bazar.bane.bazarshahr.databinding.FragmentProductsBinding
import com.bazar.bane.bazarshahr.mainFragments.FragmentFunction
import com.bazar.bane.bazarshahr.mainFragments.ToolbarFunction
import com.bazar.bane.bazarshahr.state.ProductState
import com.bazar.bane.bazarshahr.util.AppConstants
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.PRODUCT_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.TITLE
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.ProductViewModel

class ProductsFragment : Fragment(), FragmentFunction, ToolbarFunction {

    private lateinit var binding: FragmentProductsBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var jobId: String
    private lateinit var title: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private var items: ArrayList<Any?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jobId = arguments?.getString(AppConstants.JOB_ID)!!
        title = arguments?.getString(TITLE)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false)
        val view = binding.root
        viewModel = ProductViewModel()
        binding.productViewModel = viewModel
        binding.lifecycleOwner = this
        setToolbar()
        initialData()
        subscribeObservers()
        viewModel.getProducts(jobId)
        return view
    }

    override fun initialData() {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val gridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, true)
        recyclerView.layoutManager = gridLayoutManager
        adapter =
            ProductAdapter(requireContext(), items, recyclerView)
        recyclerView.adapter = adapter

        adapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                viewModel.getProducts(jobId)
            }
        })

        adapter.setItemOnClick(object : OnClickItem<Product> {
            override fun clicked(item: Product, position: Int) {
                val bundle = Bundle()
                bundle.putString(PRODUCT_ID, item.id)
                bundle.putString(TITLE, item.name)
                findNavController().navigate(
                    R.id.action_productsFragment_to_productDetailsFragment,
                    bundle
                )
            }
        })
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is ProductState.GetProducts -> {
                    viewModel.setMainLoadingState(false)
                    items.addAll(dataState.response.products!!)
                    adapter.setLoading(adapter.loadingSuccessState)
                    if (adapter.itemCount == 0)
                        viewModel.setMessageVisibilityState(true)

                }
                is ProductState.ErrorGetProducts -> {
                    viewModel.setMainLoadingState(false)
                    adapter.setLoading(adapter.loadingFailState)
                    ToastUtil.showToast(dataState.error)
                }
            }
        })
    }

    override fun setToolbar() {
        val toolbar: Toolbar = binding.toolbar as Toolbar
        toolbar.findViewById<TextView>(R.id.title_page).text = title
        toolbar.findViewById<AppCompatImageView>(R.id.back).setOnClickListener {
            findNavController().popBackStack()
        }
    }


}