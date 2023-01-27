package com.example.wingsgroup.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: UserModel)

    @Query("SELECT * FROM tb_user where email = :email")
    suspend fun getAll(email: String): UserModel?

    @Query("DELETE FROM tb_user")
    suspend fun clear()
}