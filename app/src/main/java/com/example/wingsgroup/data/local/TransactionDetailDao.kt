package com.example.wingsgroup.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface TransactionDetailDao {
    @Insert
    suspend fun insert(transactionDetail: TransactionDetailModel)

    @Query("SELECT * FROM tb_transaction_detail where doc_number = :documentNumber")
    suspend fun getAllTransDetail(documentNumber: Long) : List<TransactionDetailModel>
}