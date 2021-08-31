package com.retroblade.hirasawaprod.content.ui

import com.retroblade.hirasawaprod.base.BaseView
import com.retroblade.hirasawaprod.content.ui.entity.ContentStatus
import com.retroblade.hirasawaprod.content.ui.entity.PhotoItem
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * @author m.a.kovalev
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface ContentView : BaseView {

    fun setRecentPhotosItems(items: List<PhotoItem>)

    fun setPopularPhotosItems(items: List<PhotoItem>)

    fun setRelevantPhotosItems(items: List<PhotoItem>)

    fun setPagerItems(items: List<PhotoItem>)

    fun showContent(status: ContentStatus)

    fun showError()
}