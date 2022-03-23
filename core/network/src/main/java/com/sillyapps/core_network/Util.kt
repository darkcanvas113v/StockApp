package com.sillyapps.core_network

import retrofit2.HttpException
import java.io.IOException

suspend fun<T> tryToLoad(
  tryBlock: suspend () -> T,
  onHttpException: (HttpException) -> Unit,
  onIOException: (IOException) -> Unit,
): T? {
  try {
    return tryBlock()
  } catch (e: HttpException) {
    onHttpException(e)
  } catch (e: IOException) {
    onIOException(e)
  }
  return null
}