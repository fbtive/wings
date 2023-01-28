package com.example.wingsgroup.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Insert
    suspend fun insert(cart: CartModel)

    @Delete
    suspend fun delete(cart: CartModel)

    @Query("SELECT * FROM tb_cart WHERE email = :email and product_code = :code")
    suspend fun get(email: String, code: String): CartModel?

    @Query("SELECT * FROM tb_cart WHERE email = :email")
    fun getAllCart(email:String): LiveData<List<CartModel>>

    @Query("SELECT COUNT(*) FROM tb_cart")
    fun getCount(): LiveData<Int>
}