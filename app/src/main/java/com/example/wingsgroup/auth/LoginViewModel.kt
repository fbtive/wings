package com.example.wingsgroup.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wingsgroup.data.ResultData
import com.example.wingsgroup.data.UserRepository
import com.example.wingsgroup.data.local.UserModel
import com.example.wingsgroup.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    private val _userAuthEvent = MutableLiveData<Event<Boolean>>()
    val userAuthEvent: LiveData<Event<Boolean>>
        get() = _userAuthEvent

    private val _errorEvent = MutableLiveData<Event<String>>()
    val errorEvent: LiveData<Event<String>>
        get() = _errorEvent

    val allUser = repository.getAllUser()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.authenticate(email, password)

            if(result is ResultData.Success) {
                _userAuthEvent.value = Event(true)
            }

            if(result is  ResultData.Error) {
                _errorEvent.value = Event(result.exception.message.toString())
            }
        }
    }

    fun checkAllUser() {

    }

}