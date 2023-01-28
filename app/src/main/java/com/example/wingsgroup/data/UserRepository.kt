package com.example.wingsgroup.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.wingsgroup.auth.WingsAuth
import com.example.wingsgroup.data.local.UserDao
import com.example.wingsgroup.data.local.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class UserRepository (private val userDao: UserDao, val context: Context) {

    fun getAllUser(): LiveData<List<UserModel>> = userDao.getAll()

    suspend fun registerUser(user: UserModel): ResultData<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                userDao.insert(user)
                WingsAuth.authenticate(context, user)
                ResultData.Success(true)
            } catch (e: Exception) {
                Timber.i(e.message)
                ResultData.Error(Exception("Something went wrong"))
            }
        }
    }

    suspend fun authenticate(email: String, password: String): ResultData<UserModel> {
        return withContext(Dispatchers.IO) {
            try {
                val user = userDao.get(email)
                if(password == user?.password) {
                    WingsAuth.authenticate(context, user)
                    ResultData.Success(user)
                }else {
                    ResultData.Error(Exception("Invalid user credentials"))
                }
            } catch (e: Exception) {
                ResultData.Error(Exception("User not found"))
            }
        }
    }
}