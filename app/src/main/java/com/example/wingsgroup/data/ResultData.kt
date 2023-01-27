package com.example.wingsgroup.data

sealed class ResultData<out R> {
    data class Success<out T>(val data: T): ResultData<T>()
    data class Error<T>(val exception: Exception): ResultData<T>()
    class Loading<T>: ResultData<T>()

    override fun toString(): String {
        return when(this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[error=$exception]"
            is Loading -> "Loading"
        }
    }
}