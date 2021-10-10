package com.retroblade.hirasawaprod.content.ui

import com.retroblade.hirasawaprod.base.BaseView
import com.retroblade.hirasawaprod.content.ui.entity.ContentStatus
import com.retroblade.hirasawaprod.content.ui.entity.PhotoItem
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * View interface represents some methods for content screen
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface ContentView : BaseView {

    /**
     * Shows recent photo [items] on screen
     */
    fun setRecentPhotosItems(items: List<PhotoItem>)

    /**
     * Shows popular photo [items] on screen
     */
    fun setPopularPhotosItems(items: List<PhotoItem>)

    /**
     * Shows relevant photo [items] on screen
     */
    fun setRelevantPhotosItems(items: List<PhotoItem>)

    /**
     * Shows pager photo [items] on screen
     */
    fun setPagerItems(items: List<PhotoItem>)

    /**
     * Shows content by its [status]
     */
    fun showContent(status: ContentStatus)

    /**
     * Shows error
     */
    fun showError()
}