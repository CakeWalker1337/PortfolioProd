package com.retroblade.hirasawaprod.viewer.ui

import com.retroblade.hirasawaprod.base.BasePresenter
import com.retroblade.hirasawaprod.common.CommonPhotoCacheManager
import moxy.InjectViewState
import javax.inject.Inject

/**
 * @author m.a.kovalev
 */
@InjectViewState
class ViewerPresenter @Inject constructor(
    private val photoCacheManager: CommonPhotoCacheManager
) : BasePresenter<ViewerView>() {

    fun loadData(photoId: String?) {
        if (photoId != null) {
            val photo = photoCacheManager.getPhoto(photoId)
            if (photo != null) {
                viewState.showPhoto(photo)
                return
            }
        }
        viewState.showError()
    }
}