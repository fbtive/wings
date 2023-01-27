package com.example.wingsgroup.di

import android.content.Context
import com.example.wingsgroup.data.local.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WingsDatabase = WingsDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideUserDao(db: WingsDatabase): UserDao = db.userDao

    @Provides
    @Singleton
    fun provideCartDao(db: WingsDatabase): CartDao = db.cartDao

    @Provides
    @Singleton
    fun provideTransactionDao(db: WingsDatabase): TransactionDao = db.transactionDao

    @Provides
    @Singleton
    fun provideTransactionDetailDao(db: WingsDatabase): TransactionDetailDao = db.transactionDetailDao
}