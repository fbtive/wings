package com.example.wingsgroup.data

import androidx.lifecycle.LiveData
import com.example.wingsgroup.auth.WingsAuth
import com.example.wingsgroup.data.local.TransactionDao
import com.example.wingsgroup.data.local.TransactionDetailDao
import com.example.wingsgroup.data.local.TransactionDetailModel
import com.example.wingsgroup.data.local.TransactionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class TransactionRepository constructor(
    private val transactionDao: TransactionDao,
    private val transactionDetailDao: TransactionDetailDao
) {

    suspend fun insertTransaction(total: Double): ResultData<Long> {
        return withContext(Dispatchers.IO) {
            try {
                val id = transactionDao.insert(TransactionModel(0L, WingsAuth.email!!, WingsAuth.username!!, total ))
                ResultData.Success(id)
            }catch (e: Exception) {
                Timber.i(e.message)
                ResultData.Error(Exception("Error Insert Transaction"))
            }
        }
    }

    suspend fun insertTransactionDetail(transactionDetailModel: TransactionDetailModel): ResultData<Long> {
        return withContext(Dispatchers.IO) {
            try {
                val id = transactionDetailDao.insert(transactionDetailModel)
                ResultData.Success(id)
            }catch (e: Exception) {
                Timber.i(e.message)
                ResultData.Error<Long>(Exception("Error Insert TransactionDetail"))
            }
        }
    }

    fun getAllTransaction(): LiveData<List<TransactionModel>> {
        return transactionDao.getAll(WingsAuth.email!!)
    }
}