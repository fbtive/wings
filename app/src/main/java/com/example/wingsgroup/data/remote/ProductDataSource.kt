package com.example.wingsgroup.data.remote

import com.example.wingsgroup.R
import com.example.wingsgroup.data.domain.Product

class ProductDataSource {
    private var WINGS_PRODUCTS = mutableListOf<Product>()

    init {
        WINGS_PRODUCTS.add(Product("DAAPTH", "Daia Putih", 19700.0, 0.0, "1,7 kg", R.drawable.product_daia ))
        WINGS_PRODUCTS.add(Product("NOACC", "Nuvo Active Clean", 40000.0, 10.0, "800 ml", R.drawable.nuvo_clean))
        WINGS_PRODUCTS.add(Product("NUACC", "Nuvo Active Cool", 35000.0, 10.0, "800 ml", R.drawable.nuvo_cool))
        WINGS_PRODUCTS.add(Product("MIEGRG", "Mie Sedap Goreng", 2500.0, 0.0, "130 gr", R.drawable.mie_s_goreng))
        WINGS_PRODUCTS.add(Product("MIESKA", "Mie Sedap Kari Ayam", 2600.0, 0.0, "130 gr", R.drawable.mie_s_kari_ayam))
        WINGS_PRODUCTS.add(Product("MIESTO", "Mie Sedap SOTO", 5800.0, 0.0, "220 gr", R.drawable.mie_s_soto))
        WINGS_PRODUCTS.add(Product("MIESWC", "Mie Sedap White Curry", 2900.0, 0.0, "130 gr", R.drawable.mie_s_white_curry))
        WINGS_PRODUCTS.add(Product("COFTMU", "Top Coffee Murni", 11000.0, 0.0, "158 gr", R.drawable.top_coffee_murni))
        WINGS_PRODUCTS.add(Product("COFTCP", "Top Coffee Cappuccino", 1500.0, 0.0, "25 gr", R.drawable.top_coffee_c))
        WINGS_PRODUCTS.add(Product("COFTMC", "Top Coffee Mocca", 1500.0, 0.0, "25 gr", R.drawable.top_coffee_mocca))
    }

    fun getProduct(): List<Product> = WINGS_PRODUCTS.toList()

    fun findProduct(code: String): Product {
        return WINGS_PRODUCTS.first { it.code == code }
    }
}