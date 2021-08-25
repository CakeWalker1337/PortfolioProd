package com.retroblade.hirasawaprod.content

import com.retroblade.hirasawaprod.base.BasePresenter
import com.retroblade.hirasawaprod.content.usecase.GetAllPhotosUseCase
import com.retroblade.hirasawaprod.content.usecase.GetPagerPhotosUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi
import moxy.InjectViewState
import timber.log.Timber
import javax.inject.Inject

/**
 * @author m.a.kovalev
 */
@InjectViewState
class ContentPresenter @Inject constructor(
    private val getAllPhotos: GetAllPhotosUseCase,
    private val getPagerPhotos: GetPagerPhotosUseCase,
    private val contentItemsFactory: ContentItemsFactory
) : BasePresenter<ContentView>() {

    @ExperimentalSerializationApi
    fun loadData() {
        getAllPhotos().zipWith(getPagerPhotos()) { contentPhotos, pagerPhotos ->
            val recentItems = contentItemsFactory.createRecentItems(contentPhotos)
            val popularItems = contentItemsFactory.createPopularItems(contentPhotos)
            val relevantItems = contentItemsFactory.createRelevantItems(recentItems, contentPhotos)
            val pagerItems = contentItemsFactory.createPagerItems(pagerPhotos)
            Triple(recentItems, popularItems, relevantItems) to pagerItems
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ (contentItems, pagerItems) ->
                viewState.setRecentPhotosItems(contentItems.first)
                viewState.setPopularPhotosItems(contentItems.second)
                viewState.setRelevantPhotosItems(contentItems.third)
                viewState.setPagerItems(pagerItems)
                viewState.showContent()
            }, {
                Timber.e(it)
                viewState.showError()
            }).disposeOnFinish()

    }
}