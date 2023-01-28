package com.example.wingsgroup.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wingsgroup.data.ProductRepository
import com.example.wingsgroup.data.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    fun findProduct(code: String): Product {
        return repository.findProduct(code)
    }
}