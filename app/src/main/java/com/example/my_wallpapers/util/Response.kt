package com.example.my_wallpapers.util


sealed class Response<T>(val data : T? = null,
    val message: String? = null) {
    class Success<T>(data: T) : Response<T>(data)
    class Failure<T>(message: String) : Response<T>(message = message)
    class Loading<T> : Response<T>()
    class UnSpecifies<T> : Response<T>()
}