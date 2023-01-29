package com.example.wingsgroup.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wingsgroup.auth.WingsAuth
import com.example.wingsgroup.data.domain.Product
import com.example.wingsgroup.data.local.CartDao
import com.example.wingsgroup.data.local.CartModel
import com.example.wingsgroup.data.remote.ProductDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ProductRepository (
    private val cartDao: CartDao,
    private val dataSource: ProductDataSource
) {

    fun getAllProduct(): List<Product> {
        return dataSource.getProduct()
    }

    fun findProduct(code: String): Product {
        return dataSource.findProduct(code)
    }

    suspend fun findCart(code: String) : CartModel? {
        return withContext(Dispatchers.IO) {
            cartDao.get(WingsAuth.email!!, code)
        }
    }

    suspend fun insertNewCart(cartModel: CartModel) : ResultData<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                cartDao.insert(cartModel)

                ResultData.Success(true)
            } catch (e: Exception) {
                Timber.i(e.message)
                ResultData.Error(Exception("something went wrong"))
            }
        }
    }

    suspend fun updateCart(cartModel: CartModel) : ResultData<Boolean> {
        return  withContext(Dispatchers.IO) {
            try {
                cartDao.update(cartModel)

                ResultData.Success(true)
            }catch (e: Exception) {
                Timber.i(e.message)
                ResultData.Error(Exception("something went wrong"))
            }
        }
    }

    suspend fun deleteCart(cartModel: CartModel) : ResultData<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                cartDao.delete(cartModel)

                ResultData.Success(true)
            }catch (e: Exception) {
                Timber.i(e.message)
                ResultData.Error(Exception("something went wrong"))
            }
        }
    }

    suspend fun deleteCart(code: String): ResultData<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                cartDao.deleteByCode(code, WingsAuth.email!!)

                ResultData.Success(true)
            }catch (e: Exception) {
                Timber.i(e.message)
                ResultData.Error(Exception("something went wrong"))
            }
        }
    }

    fun getCartCount(): LiveData<Int> {
        return cartDao.getCount(WingsAuth.email!!)
    }

    fun getAllCart(): LiveData<List<CartModel>> {
        return cartDao.getAllCart(WingsAuth.email!!)
    }
}