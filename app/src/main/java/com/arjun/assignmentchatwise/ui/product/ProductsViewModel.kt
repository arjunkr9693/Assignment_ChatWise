package com.arjun.assignmentchatwise.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjun.assignmentchatwise.data.ApiService
import com.arjun.assignmentchatwise.data.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError

    fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = false
            try {
                _products.value = ApiService.getProducts()
                _isError.value = false
            } catch (e: Exception) {
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}
