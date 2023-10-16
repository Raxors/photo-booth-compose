package com.raxors.photobooth.core.utils.network

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val throwable: Throwable) : ResultWrapper<Nothing>()
}