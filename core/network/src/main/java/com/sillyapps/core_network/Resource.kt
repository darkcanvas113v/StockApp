package com.sillyapps.core_network

sealed class Resource<T>(
  val data: T? = null,
  val message: String? = null
) {
  class Success<T>(data: T): Resource<T>(data)

  class Loading<T>(data: T? = null): Resource<T>(data)

  class Error<T>(val type: Type = Type.UNKNOWN, message: String, data: T? = null): Resource<T>(data, message) {
    enum class Type {
      BAD_CONNECTION, UNKNOWN
    }
  }
}