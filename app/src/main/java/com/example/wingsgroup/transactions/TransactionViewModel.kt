package com.example.wingsgroup.transactions

import androidx.lifecycle.ViewModel
import com.example.wingsgroup.data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    repository: TransactionRepository
): ViewModel() {

    val transactionList = repository.getAllTransaction()

}