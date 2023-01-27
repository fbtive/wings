package com.example.wingsgroup.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tb_cart")
data class CartModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val email: String,
    @ColumnInfo(name = "product_code")
    val product_code: String,
    val name: String,
    val price: Double,
    val quantity: Int,
    val unit: String,
    @ColumnInfo(name = "sub_total")
    val total: Double,
    val currency: String
)
