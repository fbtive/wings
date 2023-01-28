package com.example.wingsgroup.data

import com.example.wingsgroup.data.domain.Product
import com.example.wingsgroup.data.local.CartDao
import com.example.wingsgroup.data.remote.ProductDataSource

class ProductRepository (
    private val cartDao: CartDao,
    private val dataSource: ProductDataSource
) {

    fun getAllProduct(): List<Product> {
        return dataSource.getProduct()
    }

    fun findProduct(code: String): Product {
        return dataSource.findProduct(code)
    }
}