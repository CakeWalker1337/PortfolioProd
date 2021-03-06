package com.retroblade.portfolioprod.feature.viewer.ui

import com.retroblade.portfolioprod.base.BaseView
import com.retroblade.portfolioprod.feature.content.domain.Photo
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * View interface represents some methods for viewer screen
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface ViewerView : BaseView {

    /**
     * Displays [photo] and its details on screen
     */
    fun showPhoto(photo: Photo)

    /**
     * Displays error if something goes wrong
     */
    fun showError()
}