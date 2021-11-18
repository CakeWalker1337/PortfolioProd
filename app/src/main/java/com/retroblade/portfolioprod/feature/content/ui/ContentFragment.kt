package com.retroblade.portfolioprod.feature.content.ui

import android.content.res.Configuration
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar
import com.retroblade.portfolioprod.App
import com.retroblade.portfolioprod.R
import com.retroblade.portfolioprod.base.BaseFragment
import com.retroblade.portfolioprod.common.navigation.NavigatorHolder
import com.retroblade.portfolioprod.common.navigation.ViewerScreen
import com.retroblade.portfolioprod.common.ui.AnimationManager
import com.retroblade.portfolioprod.feature.content.di.component.DaggerContentComponent
import com.retroblade.portfolioprod.feature.content.ui.adapter.ContentHorizontalAdapter
import com.retroblade.portfolioprod.feature.content.ui.adapter.ContentVerticalAdapter
import com.retroblade.portfolioprod.feature.content.ui.adapter.carousel.CarouselViewPagerAdapter
import com.retroblade.portfolioprod.feature.content.ui.animation.*
import com.retroblade.portfolioprod.feature.content.ui.model.ContentStatus
import com.retroblade.portfolioprod.feature.content.ui.model.PhotoItem
import com.retroblade.portfolioprod.utils.dpToPx
import com.retroblade.portfolioprod.utils.setCurrentItem
import com.retroblade.portfolioprod.utils.setTextViewParams
import kotlinx.android.synthetic.main.fragment_content.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


/**
 * A fragment class for representing main content
 */
class ContentFragment : BaseFragment(), ContentView {

    //
    private var offsetY: Int = 0

    // pager state variables
    private var currentItemPos: Int = 0
    private var isScrollingStarted: Boolean = false

    private val pagerAdapter = CarouselViewPagerAdapter()
    private lateinit var recentArtsAdapter: ContentVerticalAdapter
    private lateinit var popularArtsAdapter: ContentVerticalAdapter
    private lateinit var allArtsAdapter: ContentHorizontalAdapter

    @Inject
    lateinit var animationManager: AnimationManager

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var presenterProvider: Provider<ContentPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun getLayoutRes(): Int = R.layout.fragment_content

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (requireContext().applicationContext as App).appComponent
        DaggerContentComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initPager()
        initContent()
        initPullToRefresh()
        enableStartAnimation()
    }

    /**
     * Inits viewpager component
     */
    private fun initPager() {
        offsetY = requireContext().dpToPx(OFFSET_LIMIT)
        nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            // if main card gets scrolled up and touches status bar, we must push carousel up as well
            if (scrollY > offsetY) {
                carouselContainer.translationY = -(scrollY - offsetY).toFloat()
            } else {
                carouselContainer.translationY = 0F
            }
        }

        // pager must be "under" status bar, so we need to apply fullscreen mode
        carouselContainer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        carouselPager.adapter = pagerAdapter
    }

    /**
     * Inits main content components (cards)
     */
    private fun initContent() {
        recentArtsAdapter = ContentVerticalAdapter(recentArtsRecycler).apply { setOnItemClickListener(::onPhotoClickListener) }
        popularArtsAdapter = ContentVerticalAdapter(popularArtsRecycler).apply { setOnItemClickListener(::onPhotoClickListener) }
        allArtsAdapter = ContentHorizontalAdapter(allArtsRecycler).apply { setOnItemClickListener(::onPhotoClickListener) }

        // create the effect like relevant works card "covers" popular works card
        popularArtsRecycler.post {
            val lp = popularArtsRecycler.layoutParams as ViewGroup.MarginLayoutParams
            lp.bottomMargin = relevantContainer.height + requireContext().dpToPx(MARGIN_OFFSET)
            popularArtsRecycler.layoutParams = lp
        }
        // make twitter link clickable inside the textview
        followTitle.movementMethod = LinkMovementMethod.getInstance()
    }

    /**
     * Inits pull-to-refresh feature
     */
    private fun initPullToRefresh() {
        retryButton.setOnClickListener(::onRetryClickListener)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            overlay.isVisible = true
            // when we launch the update animation (progress), the status bar color must be changed to dark,
            // otherwise status bar elements will not be visible on white layer (if it's not a night mode)
            if (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) != Configuration.UI_MODE_NIGHT_YES) {
                activity?.window?.decorView?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
            }
            // start change layer animation and launch refreshing data
            animationManager.startAnimation(overlay, HideContent) {
                animationManager.startAnimation(progressBar, ShowProgressBar) {
                    presenter.loadData()
                }
            }
        }
    }

    /**
     * Launches start progress animation
     */
    private fun enableStartAnimation() {
        animationManager.startAnimation(progressBar, ShowProgressBar) {
            presenter.loadData()
        }
    }

    override fun setRecentPhotosItems(items: List<PhotoItem>) {
        recentArtsAdapter.setItems(items)
    }

    override fun setPopularPhotosItems(items: List<PhotoItem>) {
        popularArtsAdapter.setItems(items)
    }

    override fun setRelevantPhotosItems(items: List<PhotoItem>) {
        allArtsAdapter.setItems(items)
    }

    override fun setPagerItems(items: List<PhotoItem>) {
        // preload items in carousel to avoid loading as soon as the item is being scrolled
        carouselPager.offscreenPageLimit = items.size
        pagerAdapter.setItems(items)
        // launch recursive scrolling if it's not launched already (for example if we execute pull-to-refresh,
        // the scrolling is already launched and no need to launch it again)
        if (isScrollingStarted.not()) {
            recursiveScrolling()
            isScrollingStarted = true
        }
    }

    override fun showContent(status: ContentStatus) {
        animationManager.startAnimation(progressBar, HideProgressBar) {
            animationManager.startAnimation(overlay, ShowContent) {
                // change system navbar color to dark after returning to content state (if it's not a night mode)
                if (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) != Configuration.UI_MODE_NIGHT_YES) {
                    activity?.window?.decorView?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
                }
                overlay.isVisible = false
                if (status == ContentStatus.CACHED) {
                    showSnackbarMessage()
                }
            }
        }
    }

    override fun showError() {
        with(animationManager) {
            startAnimation(progressBar, HideProgressBar) {
                startAnimation(errorMessageContainer, ShowErrorMessage)
            }
        }
    }

    /**
     * Creates error message and displays it on screen as a snackbar
     */
    private fun showSnackbarMessage() {
        Snackbar.make(requireView(), R.string.content_snackbar_content_message, 5000)
            .setBackgroundTint(resources.getColor(R.color.content_snackbar_color, requireContext().theme))
            .setTextColor(resources.getColor(R.color.default_text_color, requireContext().theme))
            .setAnimationMode(ANIMATION_MODE_SLIDE)
            .setTextViewParams { setTextAppearance(R.style.RegularText_Size14) }
            .show()
    }

    /**
     * Retry button click listener
     */
    private fun onRetryClickListener(v: View) {
        with(animationManager) {
            startAnimation(errorMessageContainer, HideErrorMessage) {
                startAnimation(progressBar, ShowProgressBar) {
                    presenter.loadData()
                }
            }
        }
    }

    /**
     * Processes click on photo wiht [photoId]
     */
    private fun onPhotoClickListener(photoId: String) {
        navigatorHolder.getNavigator()?.executeNavigation(requireContext(), ViewerScreen(requireContext(), photoId))
    }

    /**
     * Inits recursive carousel scrolling
     */
    private fun recursiveScrolling() {
        carouselPager?.postDelayed({
            carouselPager?.adapter?.let { adapter ->
                if (currentItemPos == adapter.itemCount) {
                    currentItemPos = 0
                } else {
                    currentItemPos++
                }
                carouselPager.setCurrentItem(currentItemPos, CAROUSEL_CHANGE_ITEM_DURATION)
                recursiveScrolling()
            }
        }, CAROUSEL_HOLD_ITEM_DURATION)
    }

    companion object {

        private const val OFFSET_LIMIT = 220F
        private const val MARGIN_OFFSET = 12F

        private const val CAROUSEL_CHANGE_ITEM_DURATION = 1000L
        private const val CAROUSEL_HOLD_ITEM_DURATION = 5000L

        fun newInstance(): ContentFragment {
            return ContentFragment()
        }
    }
}