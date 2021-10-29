package com.bazar.bane.bazarshahr.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bazar.bane.bazarshahr.api.model.Product
import com.bazar.bane.bazarshahr.intent.ProductIntent
import com.bazar.bane.bazarshahr.repository.ProductRepository
import com.bazar.bane.bazarshahr.state.ProductState

class ProductViewModel : ViewModel() {
    private var page = 0;
    private val _stateIntent: MutableLiveData<ProductIntent> = MutableLiveData()

    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    private val _mainLoadingState: MutableLiveData<Boolean> = MutableLiveData(true)
    val mainLoadingState: LiveData<Boolean> get() = _mainLoadingState

    private val _messageVisibilityState: MutableLiveData<Boolean> = MutableLiveData(true)
    val messageVisibilityState: LiveData<Boolean> get() = _messageVisibilityState

    var dataState: LiveData<ProductState> = Transformations
        .switchMap(_stateIntent) { stateIntent ->
            stateIntent?.let {
                handleStateEvent(stateIntent)
            }
        }

    private fun handleStateEvent(stateIntent: ProductIntent): LiveData<ProductState> {
        return when (stateIntent) {
            is ProductIntent.Products -> {
                ProductRepository.getProducts(stateIntent.request)
            }
            is ProductIntent.ProductDetails -> {
                ProductRepository.getProductDetails(stateIntent.request)
            }
        }
    }


    fun setMainLoadingState(state: Boolean) {
        _mainLoadingState.value = state
    }

    fun setJob(state: Product) {
        _product.value = state
    }

    fun setMessageVisibilityState(state: Boolean) {
        _messageVisibilityState.value = state
    }

    fun setStateEvent(intent: ProductIntent) {
        _stateIntent.value = intent
    }

    private fun getPaginate(): Int {
        page += 1
        return page
    }
}