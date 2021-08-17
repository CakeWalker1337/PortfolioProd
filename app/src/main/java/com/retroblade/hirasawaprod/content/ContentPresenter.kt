package com.retroblade.hirasawaprod.content

import com.retroblade.hirasawaprod.base.BasePresenter
import com.retroblade.hirasawaprod.content.usecase.GetAllPhotosUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi
import moxy.InjectViewState
import timber.log.Timber

/**
 * @author m.a.kovalev
 */
@InjectViewState
class ContentPresenter : BasePresenter<ContentView>() {

    private val getAllPhotos = GetAllPhotosUseCase(ContentRepository())
    private val contentItemsFactory = ContentItemsFactory()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    @ExperimentalSerializationApi
    fun loadData() {
        getAllPhotos()
            .map { photos ->
                val recentItems = contentItemsFactory.createRecentItems(photos)
                val popularItems = contentItemsFactory.createPopularItems(photos)
                val relevantItems = contentItemsFactory.createRelevantItems(recentItems, photos)
                Triple(recentItems, popularItems, relevantItems)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ (recentItems, popularItems, relevantItems) ->
                viewState.setRecentPhotosItems(recentItems)
                viewState.setPopularPhotosItems(popularItems)
                viewState.setRelevantPhotosItems(relevantItems)
            }, {
                Timber.e(it)
            }).disposeOnFinish()

    }
}