package com.retroblade.hirasawaprod.feature.viewer.ui

import com.retroblade.hirasawaprod.base.BasePresenter
import com.retroblade.hirasawaprod.common.CommonPhotoCacheManager
import moxy.InjectViewState
import javax.inject.Inject

/**
 * Presenter for viewer activity. Contains business logic for loading and processing data
 */
@InjectViewState
class ViewerPresenter @Inject constructor(
    private val photoCacheManager: CommonPhotoCacheManager
) : BasePresenter<ViewerView>() {

    /**
     * Loads photo data by passed [photoId]
     */
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