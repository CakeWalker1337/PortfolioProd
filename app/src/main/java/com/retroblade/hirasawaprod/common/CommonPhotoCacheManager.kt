package com.retroblade.hirasawaprod.common

import com.retroblade.hirasawaprod.content.domain.Photo
import javax.inject.Inject

/**
 * @author m.a.kovalev
 */
class CommonPhotoCacheManager @Inject constructor() {

    private val cachedPhotos: MutableList<Photo> = mutableListOf()

    fun fillCache(newPhotos: List<Photo>) {
        cachedPhotos.clear()
        cachedPhotos.addAll(newPhotos)
    }

    fun getPhoto(id: String): Photo? {
        return cachedPhotos.find { it.id == id }
    }
}