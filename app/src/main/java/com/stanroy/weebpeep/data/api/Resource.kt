package com.stanroy.weebpeep.data.api

sealed class Resource<out T> {

    data class Success<out T>(val data: T, val message: String? = null, val type: Status? = null) :
        Resource<T>()

    data class Error<T>(val message: String?, val data: T? = null, val type: Status? = null) :
        Resource<T>()
}

enum class Status {
    ERR_API, ERR_CACHE_INTERNET, ERR_UNKNOWN, SUCCESS, LOADING
}