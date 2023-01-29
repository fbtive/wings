package com.example.wingsgroup.data.domain

import java.text.NumberFormat
import java.util.*


data class Cart(
    val email: String,
    val product_code: String,
    val name: String,
    val price: Double,
    var quantity: Int,
    val unit: String,
    var total: Double,
    val currency: String,
    var img: Int
) {
    fun printPrice(): String {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        return "harga: " + numberFormat.format(this.price)
    }

    fun printTotal(): String {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        return "sub-total: " + numberFormat.format(this.total)
    }

    fun printJumlah(): String {
        return "jumlah beli: " + quantity
    }
}