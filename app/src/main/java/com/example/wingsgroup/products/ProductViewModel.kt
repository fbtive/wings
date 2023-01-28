package com.example.wingsgroup.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wingsgroup.data.ProductRepository
import com.example.wingsgroup.data.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    private val _allProduct = MutableLiveData<List<Product>>(repository.getAllProduct())
    val allProduct: LiveData<List<Product>>
        get() = _allProduct


}