package com.retroblade.hirasawaprod.content.ui

import com.retroblade.hirasawaprod.base.BasePresenter
import com.retroblade.hirasawaprod.common.CommonPhotoCacheManager
import com.retroblade.hirasawaprod.content.ui.entity.ContentStatus
import com.retroblade.hirasawaprod.content.usecase.GetAllPhotosUseCase
import com.retroblade.hirasawaprod.content.usecase.GetPagerPhotosUseCase
import com.retroblade.hirasawaprod.utils.NetworkManager
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi
import moxy.InjectViewState
import timber.log.Timber
import javax.inject.Inject

/**
 * Presenter for content screen. Contains business logic for loading and processing data
 */
@InjectViewState
class ContentPresenter @Inject constructor(
    private val getAllPhotos: GetAllPhotosUseCase,
    private val getPagerPhotos: GetPagerPhotosUseCase,
    private val networkManager: NetworkManager,
    private val contentItemsFactory: ContentItemsFactory,
    private val photoCacheManager: CommonPhotoCacheManager
) : BasePresenter<ContentView>() {

    override fun onFirstViewAttach() {
        loadData()
    }

    /**
     * Launches data loading and calls view methods as the response
     */
    @ExperimentalSerializationApi
    fun loadData() {
        // execute two requests in parallel
        Single.zip(
            getAllPhotos().subscribeOn(Schedulers.io()),
            getPagerPhotos().subscribeOn(Schedulers.io())
        ) { contentPhotos, pagerPhotos ->
            val recentItems = contentItemsFactory.createRecentItems(contentPhotos)
            val popularItems = contentItemsFactory.createPopularItems(contentPhotos)
            val relevantItems = contentItemsFactory.createRelevantItems(recentItems, contentPhotos)
            val pagerItems = contentItemsFactory.createPagerItems(pagerPhotos)

            photoCacheManager.fillCache(contentPhotos + pagerPhotos)

            Triple(recentItems, popularItems, relevantItems) to pagerItems
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ (contentItems, pagerItems) ->
                viewState.setRecentPhotosItems(contentItems.first)
                viewState.setPopularPhotosItems(contentItems.second)
                viewState.setRelevantPhotosItems(contentItems.third)
                viewState.setPagerItems(pagerItems)

                val status = if (networkManager.isNetworkAvailable()) ContentStatus.ACTUAL else ContentStatus.CACHED
                viewState.showContent(status)
            }, { ex ->
                Timber.e(ex)
                viewState.showError()
            }).disposeOnFinish()
    }
}