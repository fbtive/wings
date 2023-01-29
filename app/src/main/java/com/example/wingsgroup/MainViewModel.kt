package com.example.wingsgroup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.wingsgroup.auth.WingsAuth
import com.example.wingsgroup.data.ProductRepository
import com.example.wingsgroup.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {
    private var authState = MutableLiveData<Event<Boolean>>()
    var cartCount = Transformations.switchMap(authState) {
        it.getContentIfNotHandled()
        if(!WingsAuth.email.isNullOrEmpty())
            productRepository.getCartCount()
        else null
    }

    fun refreshCartCount() {
        authState.value = Event(true)
    }
}