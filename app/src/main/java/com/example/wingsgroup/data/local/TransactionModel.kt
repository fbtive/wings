package com.example.wingsgroup.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wingsgroup.utils.convertLongToDateFormat
import java.text.NumberFormat
import java.util.*

@Entity(tableName = "tb_transaction")
data class TransactionModel (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "doc_number")
    val documentNumber: Long,
    val email: String,
    val username: String,
    val total: Double,
    @ColumnInfo(name = "doc_code")
    val documentCode: String = "TRX",
    val date: Long = System.currentTimeMillis()
) {
    fun printCode() : String {
        return documentCode + String.format("%03d", documentNumber)
    }

    fun printTotal(): String {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        return "Total: ${numberFormat.format(total)}"
    }

    fun printDate(): String {
        return convertLongToDateFormat(date, "dd, MMM yyyy")
    }
}