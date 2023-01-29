package com.example.wingsgroup.products

import androidx.lifecycle.*
import com.example.wingsgroup.auth.WingsAuth
import com.example.wingsgroup.data.ProductRepository
import com.example.wingsgroup.data.ResultData
import com.example.wingsgroup.data.domain.Product
import com.example.wingsgroup.data.local.CartModel
import com.example.wingsgroup.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    private val _qty = MutableLiveData<Int>(0)
    val qty: LiveData<Int>
        get() = _qty

    private val _subtotal = MutableLiveData<Double>(0.0)
    val subtotal = Transformations.map(_subtotal) {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        numberFormat.format(it)
    }

    private val _eventBuy = MutableLiveData<Event<Boolean>>()
    val eventBuy: LiveData<Event<Boolean>>
        get() = _eventBuy

    fun findProduct(code: String): Product {
        return repository.findProduct(code)
    }

    fun addQty(product: Product) {
        val jumlah = _qty.value ?: 0
        _qty.value = jumlah + 1
        val total = (product.price * (jumlah+1))
        _subtotal.value = total  - (total * product.discount / 100)
    }

    fun minQty(product: Product) {
        val jumlah: Int = _qty.value ?: 0
        if(jumlah > 0) {
            _qty.value = jumlah - 1
            val total = (product.price * (jumlah-1))
            _subtotal.value = total - (total * product.discount / 100)
        }
    }

    fun addCart(product: Product) {
        viewModelScope.launch {
            val cart = repository.findCart(product.code)
            val qty = _qty.value ?: 1
            val total = _subtotal.value ?: product.price

            var result: ResultData<Boolean> = ResultData.Loading()

            if(qty > 0) {
                if (cart == null) {
                    result = repository.insertNewCart(
                        CartModel(0L, WingsAuth.email!!, product.code, product.name, product.price, qty, product.unit, total, product.currency)
                    )
                }else {
                    cart.quantity += qty
                    cart.total = cart.quantity * cart.price - (cart.quantity * cart.price * product.discount /100)
                    result = repository.updateCart(cart)
                }
            }

            if(result is ResultData.Success) {
                _eventBuy.value = Event(true)
            }
        }

    }
}