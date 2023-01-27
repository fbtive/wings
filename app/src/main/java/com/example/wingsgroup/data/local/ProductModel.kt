package com.example.wingsgroup.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_product")
data class ProductModel (
    @PrimaryKey
    val code: String,
    val name: String,
    val price: Double,
    val discount: Double,
    val dimension: String,
    val unit: String = "PCS",
    val currency: String = "IDR"
)