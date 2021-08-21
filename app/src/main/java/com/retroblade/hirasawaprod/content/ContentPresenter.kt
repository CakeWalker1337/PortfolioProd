package com.retroblade.hirasawaprod.content

import com.retroblade.hirasawaprod.base.BasePresenter
import com.retroblade.hirasawaprod.content.data.ContentRepository
import com.retroblade.hirasawaprod.content.usecase.GetAllPhotosUseCase
import com.retroblade.hirasawaprod.content.usecase.GetPagerPhotosUseCase
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

    private val contentRepository = ContentRepository()
    private val getAllPhotos = GetAllPhotosUseCase(contentRepository)
    private val getPagerPhotos = GetPagerPhotosUseCase(contentRepository)
    private val contentItemsFactory = ContentItemsFactory()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    @ExperimentalSerializationApi
    fun loadData() {
        getAllPhotos().subscribeOn(Schedulers.io()).zipWith(
            getPagerPhotos().subscribeOn(Schedulers.io())
        ) { contentPhotos, pagerPhotos ->
            val recentItems = contentItemsFactory.createRecentItems(contentPhotos)
            val popularItems = contentItemsFactory.createPopularItems(contentPhotos)
            val relevantItems = contentItemsFactory.createRelevantItems(recentItems, contentPhotos)
            val pagerItems = contentItemsFactory.createPagerItems(pagerPhotos)
            Triple(recentItems, popularItems, relevantItems) to pagerItems
        }

            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ (contentItems, pagerItems) ->
                viewState.setRecentPhotosItems(contentItems.first)
                viewState.setPopularPhotosItems(contentItems.second)
                viewState.setRelevantPhotosItems(contentItems.third)
                viewState.setPagerItems(pagerItems)
                viewState.showContent()
            }, {
                Timber.e(it)
            }).disposeOnFinish()

    }
}