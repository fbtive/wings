package com.example.wingsgroup.data.domain

import java.text.NumberFormat
import java.util.*

data class Product(
    val code: String,
    val name: String,
    val price: Double,
    val discount: Double,
    val dimension: String,
    val img: Int,
    val unit: String = "PCS",
    val currency: String = "IDR"
) {
    fun printPrice(): String {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        return numberFormat.format(this.price)
    }

    fun printDiscount(): String {
        return "Discount: ${discount.toInt()} %"
    }

    fun printDetail(): String {
        return "Ukuran : ${dimension} dan Unit: ${unit}"
    }

}
