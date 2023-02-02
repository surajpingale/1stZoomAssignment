package com.example.a1stzoomassignment.Utils

/**
 * class for getting response state from api
 */
open class Resource<T> (val data : T? = null, val errorMessage : String? = null) {
    // for loading state
    class Loading<T> : Resource<T>()
    // for success state
    class Success<T>(data: T) : Resource<T>(data = data)
    // for error state
    class Error<T>(errorMessage: String) : Resource<T>(errorMessage = errorMessage)
    // for empty state
    class Empty<T> : Resource<T>()
}