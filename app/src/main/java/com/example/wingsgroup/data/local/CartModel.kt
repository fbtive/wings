package com.example.wingsgroup.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wingsgroup.data.domain.Cart


@Entity(tableName = "tb_cart")
data class CartModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val email: String,
    @ColumnInfo(name = "product_code")
    val product_code: String,
    val name: String,
    val price: Double,
    var quantity: Int,
    val unit: String,
    @ColumnInfo(name = "sub_total")
    var total: Double,
    val currency: String
)

fun List<CartModel>.asDomainModel(): List<Cart> {
    return map {
        Cart(
            it.email,
            it.product_code,
            it.name,
            it.price,
            it.quantity,
            it.unit,
            it.total,
            it.currency,
            0
        )
    }
}
