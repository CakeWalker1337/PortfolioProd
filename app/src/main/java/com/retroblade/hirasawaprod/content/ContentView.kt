package com.retroblade.hirasawaprod.content

import com.retroblade.hirasawaprod.base.BaseView
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
}