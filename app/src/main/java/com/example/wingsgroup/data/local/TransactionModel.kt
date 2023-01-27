package com.example.wingsgroup.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tb_transaction")
data class TransactionModel (
    @ColumnInfo(name = "doc_code")
    val documentCode: String = "TRX",
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "doc_number")
    val documentNumber: Int,
    val email: String,
    val username: String,
    val total: Double,
    val date: Long = System.currentTimeMillis()
)