package com.example.wingsgroup.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartDao {
    @Insert
    suspend fun insert(cart: CartModel)

    @Delete
    suspend fun delete(cart: CartModel)

    @Update
    suspend fun update(cart: CartModel)

    @Query("SELECT * FROM tb_cart WHERE email = :email and product_code = :code")
    suspend fun get(email: String, code: String): CartModel?

    @Query("SELECT * FROM tb_cart WHERE email = :email")
    fun getAllCart(email:String): LiveData<List<CartModel>>

    @Query("SELECT SUM(quantity) FROM tb_cart where email = :email")
    fun getCount(email: String): LiveData<Int>

    @Query("DELETE FROM tb_cart where product_code = :code AND email = :email")
    suspend fun deleteByCode(code: String, email: String)
}