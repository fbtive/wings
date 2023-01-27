package com.example.wingsgroup.data.domain

data class Product(
    val code: String,
    val name: String,
    val price: Double,
    val discount: Double,
    val dimension: String,
    val img: Int,
    val unit: String = "PCS",
    val currency: String = "IDR"
)
