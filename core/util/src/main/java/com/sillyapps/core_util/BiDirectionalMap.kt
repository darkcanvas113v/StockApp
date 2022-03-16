package com.sillyapps.core_util

/**
 * Bi-directional map, first is the key, second is the value
 */
class BiDirectionalMap<T, M> {

  private val keyToValues = HashMap<T, M>()
  private val valuesToKeys = HashMap<M, T>()

  fun getByKey(key: T): M? {
    return keyToValues[key]
  }

  fun getByValue(value: M): T? {
    return valuesToKeys[value]
  }

  fun put(key: T, value: M) {
    keyToValues[key] = value
    valuesToKeys[value] = key
  }

}