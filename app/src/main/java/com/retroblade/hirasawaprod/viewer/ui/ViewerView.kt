package com.retroblade.hirasawaprod.viewer.ui

import com.retroblade.hirasawaprod.base.BaseView
import com.retroblade.hirasawaprod.content.domain.Photo
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * @author m.a.kovalev
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface ViewerView : BaseView {

    fun showPhoto(photo: Photo)

    fun showError()
}