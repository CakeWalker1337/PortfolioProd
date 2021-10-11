package com.retroblade.portfolioprod.feature.content.ui

import com.retroblade.portfolioprod.base.BasePresenter
import com.retroblade.portfolioprod.common.CommonPhotoCacheManager
import com.retroblade.portfolioprod.feature.content.domain.usecase.GetAllPhotosUseCase
import com.retroblade.portfolioprod.feature.content.domain.usecase.GetPagerPhotosUseCase
import com.retroblade.portfolioprod.feature.content.ui.model.ContentStatus
import com.retroblade.portfolioprod.utils.NetworkManager
import com.retroblade.portfolioprod.utils.ui.isNotEmpty
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi
import moxy.InjectViewState
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
            .subscribe { (contentItems, pagerItems) ->
                if (contentItems.isNotEmpty() && pagerItems.isNotEmpty()) {
                    viewState.setRecentPhotosItems(contentItems.first)
                    viewState.setPopularPhotosItems(contentItems.second)
                    viewState.setRelevantPhotosItems(contentItems.third)
                    viewState.setPagerItems(pagerItems)

                    val status = if (networkManager.isNetworkAvailable()) ContentStatus.ACTUAL else ContentStatus.CACHED
                    viewState.showContent(status)
                } else {
                    viewState.showError()
                }
            }.disposeOnFinish()
    }
}