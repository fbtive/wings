package com.example.wingsgroup.di

import android.content.Context
import com.example.wingsgroup.data.ProductRepository
import com.example.wingsgroup.data.TransactionRepository
import com.example.wingsgroup.data.UserRepository
import com.example.wingsgroup.data.local.CartDao
import com.example.wingsgroup.data.local.TransactionDao
import com.example.wingsgroup.data.local.TransactionDetailDao
import com.example.wingsgroup.data.local.UserDao
import com.example.wingsgroup.data.remote.ProductDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideUserRepository(
        userDao: UserDao,
        @ApplicationContext context: Context
    ): UserRepository = UserRepository(userDao, context)

    @Provides
    @ViewModelScoped
    fun provideProductRepository(cartDao: CartDao): ProductRepository = ProductRepository(cartDao, ProductDataSource())

    @Provides
    @ViewModelScoped
    fun provideTransactionRepository(
        transactionDao: TransactionDao,
        transactionDetailDao: TransactionDetailDao
    ) : TransactionRepository = TransactionRepository(transactionDao, transactionDetailDao)
}