package com.example.wingsgroup.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: TransactionModel)

    @Delete
    suspend fun delete(transaction: TransactionModel)

    @Query("SELECT * FROM tb_transaction WHERE email = :email")
    fun getAll(email: String): LiveData<List<TransactionModel>>

    @Query("SELECT * FROM tb_transaction WHERE doc_number = :documentNumber")
    suspend fun get(documentNumber: Int) : TransactionModel?

}