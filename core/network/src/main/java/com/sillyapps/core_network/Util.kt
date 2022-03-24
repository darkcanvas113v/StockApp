package com.sillyapps.core_network

import retrofit2.HttpException
import java.io.IOException

suspend fun<T> tryToLoad(
  tryBlock: suspend () -> T,
  onHttpException: (HttpException) -> Unit,
  onIOException: (IOException) -> Unit,
  onErrorBlock: suspend () -> T? = { null }
): T? {
  return try {
    tryBlock()
  } catch (e: HttpException) {
    onHttpException(e)
    onErrorBlock()
  } catch (e: IOException) {
    onIOException(e)
    onErrorBlock()
  }
}