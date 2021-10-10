package com.retroblade.portfolioprod.common

import com.retroblade.portfolioprod.feature.content.domain.Photo
import javax.inject.Inject

/**
 * A support class that provides in-memory cache for photo objects
 */
class CommonPhotoCacheManager @Inject constructor() {

    private val cachedPhotos: MutableList<Photo> = mutableListOf()

    /**
     * Sets [newPhotos] to cache
     */
    fun fillCache(newPhotos: List<Photo>) {
        cachedPhotos.clear()
        cachedPhotos.addAll(newPhotos)
    }

    /**
     * Get photo from cache by [id]
     */
    fun getPhoto(id: String): Photo? {
        return cachedPhotos.find { it.id == id }
    }
}