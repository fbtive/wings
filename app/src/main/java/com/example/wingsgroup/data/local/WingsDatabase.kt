package com.example.wingsgroup.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [UserModel::class, ProductModel::class, CartModel::class, TransactionModel::class, TransactionDetailModel::class],
    version = 2,
    exportSchema = false
)
abstract class WingsDatabase: RoomDatabase() {
    abstract val userDao: UserDao
    abstract val cartDao: CartDao
    abstract val transactionDao: TransactionDao
    abstract val transactionDetailDao: TransactionDetailDao

    companion object {
        @Volatile
        private var INSTANCE: WingsDatabase? = null

        fun getInstance(context: Context): WingsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        WingsDatabase::class.java,
                        "wings_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}