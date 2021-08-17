package com.retroblade.hirasawaprod.content

import com.retroblade.hirasawaprod.content.domain.Photo

/**
 * @author m.a.kovalev
 */
class ContentItemsFactory {

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
}