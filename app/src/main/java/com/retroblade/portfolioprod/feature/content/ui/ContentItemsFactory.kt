package com.retroblade.portfolioprod.feature.content.ui

import com.retroblade.portfolioprod.feature.content.domain.Photo
import com.retroblade.portfolioprod.feature.content.ui.model.PhotoItem
import javax.inject.Inject

/**
 * The delegate class for creating items for different ui components
 */
class ContentItemsFactory @Inject constructor() {

    /**
     * Takes the most recent photos from all [photos] and wraps them to items
     * @return list of photo items
     */
    fun createRecentItems(photos: List<Photo>): List<PhotoItem> {
        return photos.take(RECENT_ITEMS_MAX_COUNT)
            .map { PhotoItem(it.id, it.photoUrl, it.viewsCount, it.likesCount) }
    }

    /**
     * Picks the most popular photos from all [photos] and wraps them to items
     * @return list of photo items
     */
    fun createPopularItems(photos: List<Photo>): List<PhotoItem> {
        return photos.sortedByDescending { calculatePopularity(it.viewsCount, it.likesCount) }
            .take(POPULAR_ITEMS_MAX_COUNT)
            .map { PhotoItem(it.id, it.photoUrl, it.viewsCount, it.likesCount) }
    }

    /**
     * Picks the most relevant photos from all [photos] and wraps them to items
     * @return list of photo items
     */
    fun createRelevantItems(recentItems: List<PhotoItem>, photos: List<Photo>): List<PhotoItem> {
        val itemIds = recentItems.map { it.id }
        return photos.take(RELEVANT_ITEMS_MAX_COUNT)
            .filter { it.id !in itemIds }
            .map { PhotoItem(it.id, it.photoUrl, it.viewsCount, it.likesCount) }
            .let { it + recentItems.take(RELEVANT_ITEMS_MAX_COUNT - it.size) }
    }

    /**
     * Chooses photos for pager carousel from all pager [photos] and wraps them to items
     * @return list of photo items
     */
    fun createPagerItems(photos: List<Photo>): List<PhotoItem> {
        return photos.take(PAGER_ITEMS_MAX_COUNT)
            .map { PhotoItem(it.id, it.photoUrl, it.viewsCount, it.likesCount) }
    }

    /**
     * Calculates the popularity of photo by its [viewsCount] and [likesCount]
     */
    private fun calculatePopularity(viewsCount: Int, likesCount: Int): Int {
        return viewsCount + likesCount * 10
    }

    private companion object {
        const val RECENT_ITEMS_MAX_COUNT = 4
        const val POPULAR_ITEMS_MAX_COUNT = 4
        const val RELEVANT_ITEMS_MAX_COUNT = 10
        const val PAGER_ITEMS_MAX_COUNT = 4
    }
}