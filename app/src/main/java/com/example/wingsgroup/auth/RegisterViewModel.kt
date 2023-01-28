package com.example.wingsgroup.auth

import androidx.lifecycle.AndroidViewModel
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
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {
    val allUser = repository.getAllUser()
    val model = RegistrationForm()

    private val _userAuthEvent = MutableLiveData<Event<Boolean>>()
    val userAuthEvent: LiveData<Event<Boolean>>
        get() = _userAuthEvent

    fun registerUser(username: String, email: String, password: String, phone: String) {
        val user = UserModel(email, username, password, phone)
        viewModelScope.launch {
            val result = repository.registerUser(user)

            if(result is ResultData.Success) {
                _userAuthEvent.value = Event(true)
            }
        }
    }
}