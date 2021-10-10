package com.retroblade.portfolioprod.utils.exceptions

/**
 * A custom exception for the case when something receiving data from cache goes wrong
 * @param message exception message
 */
class InvalidCacheException(message: String) : Exception(message)