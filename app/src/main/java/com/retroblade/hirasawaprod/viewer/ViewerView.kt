package com.retroblade.hirasawaprod.viewer

import com.retroblade.hirasawaprod.base.BaseView
import com.retroblade.hirasawaprod.content.domain.Photo
import moxy.viewstate.strategy.alias.AddToEndSingle

/**
 * @author m.a.kovalev
 */
interface ViewerView : BaseView {

    @AddToEndSingle
    fun showPhoto(photo: Photo)

    @AddToEndSingle
    fun showError()
}