package com.sillyapps.core_util

fun<T> List<T>.modify(
  pos: Int,
  value: T
): List<T> = toMutableList().apply { this[pos] = value }