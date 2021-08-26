package com.retroblade.hirasawaprod.content.ui

import com.retroblade.hirasawaprod.content.domain.Photo
import com.retroblade.hirasawaprod.content.ui.entity.PhotoItem
import javax.inject.Inject

/**
 * @author m.a.kovalev
 */
class ContentItemsFactory @Inject constructor() {

    fun createRecentItems(photos: List<Photo>): List<PhotoItem> {
        return photos.take(4).map { PhotoItem(it.id, it.photoUrl, it.viewsCount, it.likesCount) }
    }

    fun createPopularItems(photos: List<Photo>): List<PhotoItem> {
        return photos.sortedByDescending { it.viewsCount + it.likesCount * 10 }
            .take(4)
            .map { PhotoItem(it.id, it.photoUrl, it.viewsCount, it.likesCount) }
    }

    fun createRelevantItems(recentItems: List<PhotoItem>, photos: List<Photo>): List<PhotoItem> {
        val itemIds = recentItems.map { it.id }
        return photos.take(10)
            .filter { it.id !in itemIds }
            .map { PhotoItem(it.id, it.photoUrl, it.viewsCount, it.likesCount) }
            .let { it + recentItems.take(10 - it.size) }
    }

    fun createPagerItems(photos: List<Photo>): List<PhotoItem> {
        return photos.map { PhotoItem(it.id, it.photoUrl, it.viewsCount, it.likesCount) }
    }

}