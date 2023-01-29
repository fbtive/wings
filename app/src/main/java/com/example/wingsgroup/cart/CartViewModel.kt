package com.example.wingsgroup.cart

import androidx.lifecycle.*
import com.example.wingsgroup.data.ProductRepository
import com.example.wingsgroup.data.ResultData
import com.example.wingsgroup.data.TransactionRepository
import com.example.wingsgroup.data.domain.Cart
import com.example.wingsgroup.data.domain.Product
import com.example.wingsgroup.data.local.CartModel
import com.example.wingsgroup.data.local.TransactionDetailModel
import com.example.wingsgroup.data.local.asDomainModel
import com.example.wingsgroup.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val productList = MutableLiveData<List<Product>>(productRepository.getAllProduct())
    val cartModelList = productRepository.getAllCart()
    val cartList = MediatorLiveData<List<Cart>>()

    private val _totalBeli = MutableLiveData<Double>(0.0)
    val total = Transformations.map(_totalBeli) {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        numberFormat.format(it)
    }

    private val _eventBuy = MutableLiveData<Event<Boolean>>()
    val eventBuy: LiveData<Event<Boolean>>
        get() = _eventBuy

    init {
        cartList.addSource(productList) {
            it?.let {
                compareCartAndProduct(it, cartModelList.value)
            }
        }

        cartList.addSource(cartModelList) {
            it?.let {
                compareCartAndProduct(productList.value, it)
            }
        }
    }

    private fun compareCartAndProduct(products: List<Product>?, carts: List<CartModel>?) {
        CoroutineScope(Dispatchers.Default).launch {
            carts?.let {
                var result = carts.asDomainModel()
                var total = 0.0
                products?.let {
                    result = result.map {cart ->
                        val search = products.first{ it.code == cart.product_code}
                        cart.img = search.img
                        total += cart.total
                        cart
                    }
                }

                withContext(Dispatchers.Main) {
                    cartList.value = result
                    _totalBeli.value = total
                }
            }
        }
    }

    fun removeItemCart(code: String) {
        viewModelScope.launch {
            val cart = productRepository.findCart(code)
            if(cart != null) {
                val result = productRepository.deleteCart(cart)
            }
        }
    }

    fun executeTransaction() {
        viewModelScope.launch {
            _totalBeli.value?.let { total ->
                cartList.value?.let {carts ->
                    if(carts.isNullOrEmpty()) return@launch
                    val transaction = transactionRepository.insertTransaction(total)
                    if (transaction is ResultData.Success) {
                        carts.forEach {
                            val detail = TransactionDetailModel(
                                0L,
                                it.product_code,
                                it.name,
                                it.price,
                                it.quantity,
                                it.unit,
                                it.total,
                                it.currency,
                                transaction.data
                            )
                            Timber.i("trans id = ${transaction.data}")
                            val transDetail = transactionRepository.insertTransactionDetail(detail)
                            if(transDetail is ResultData.Success) {
                                Timber.i("trans detail id = ${transDetail.data}")
                                val removeCart = productRepository.deleteCart(it.product_code)
                                if(removeCart is ResultData.Success) {
                                    _eventBuy.value = Event(true)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}