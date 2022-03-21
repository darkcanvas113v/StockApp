package com.sillyapps.core_network

import retrofit2.HttpException
import java.io.IOException

suspend fun<T> tryToLoad(
  tryBlock: suspend () -> T,
  onIOException: () -> Unit,
  onHttpException: () -> Unit,
): T? {
  try {
    return tryBlock()
  } catch (e: HttpException) {
    onHttpException()
  } catch (e: IOException) {
    onIOException()
  }
  return null
}