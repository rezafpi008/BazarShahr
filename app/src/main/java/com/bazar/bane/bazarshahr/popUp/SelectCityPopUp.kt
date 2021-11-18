package com.bazar.bane.bazarshahr.popUp

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.CityAdapter
import com.bazar.bane.bazarshahr.adapter.OnClickItem
import com.bazar.bane.bazarshahr.api.model.City
import com.bazar.bane.bazarshahr.databinding.SelectCityPopUpBinding
import com.bazar.bane.bazarshahr.state.CityState
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.CityViewModel

class SelectCityPopUp(
    context: Context,
    popUpCallback: PopUpCallback,
    owner: LifecycleOwner
) :
    Dialog(context) {

    lateinit var binding: SelectCityPopUpBinding
    lateinit var viewModel: CityViewModel
    var callback: PopUpCallback = popUpCallback
    var lifecycleOwner = owner
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter
    private var items: ArrayList<Any?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.select_city_pop_up,
            null,
            false
        )
        setContentView(binding.root)
        viewModel = CityViewModel()
        binding.cityViewModel = viewModel
        binding.lifecycleOwner = lifecycleOwner
        initialData()
        subscribeObservers()
        viewModel.getCities()
    }

    private fun initialData() {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        adapter =
            CityAdapter(context, items)
        adapter.horizontalItemFixed = true
        recyclerView.adapter = adapter

        adapter.setItemOnClick(object : OnClickItem<City> {
            override fun clicked(item: City, position: Int) {
                if (position == 0)
                    callback.setId("", "")
                else
                    callback.setId(item.id!!, item.name!!)
                dismiss()
            }
        })
    }

    fun subscribeObservers() {
        viewModel.dataState.observe(lifecycleOwner, { dataState ->
            when (dataState) {
                is CityState.GetCities -> {
                    viewModel.setMainLoadingState(false)
                    items.add(City("", "تمامی شهرها"))
                    items.addAll(dataState.response.cities!!)
                    adapter.setLoading()
                }
                is CityState.ErrorGetCities -> {
                    viewModel.setMainLoadingState(false)
                    adapter.setLoading()
                    ToastUtil.showToast(dataState.error)
                }
            }
        })
    }

}
