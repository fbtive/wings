package com.example.wingsgroup.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_transaction_detail")
data class TransactionDetailModel (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "product_code")
    val product_code: String,
    val name: String,
    val price: Double,
    val quantity: Int,
    val unit: String,
    @ColumnInfo(name = "sub_total")
    val total: Double,
    val currency: String,
    @ColumnInfo(name = "doc_number")
    val documentNumber: Long,
    @ColumnInfo(name = "doc_code")
    val documentCode: String = "TRX",
)