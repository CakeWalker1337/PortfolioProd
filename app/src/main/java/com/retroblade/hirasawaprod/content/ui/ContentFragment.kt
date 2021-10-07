package com.retroblade.hirasawaprod.content.ui

import android.content.res.Configuration
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar
import com.retroblade.hirasawaprod.App
import com.retroblade.hirasawaprod.R
import com.retroblade.hirasawaprod.base.BaseFragment
import com.retroblade.hirasawaprod.common.navigation.NavigatorHolder
import com.retroblade.hirasawaprod.common.navigation.ViewerScreen
import com.retroblade.hirasawaprod.common.ui.AnimationManager
import com.retroblade.hirasawaprod.content.carousel.CarouselViewPagerAdapter
import com.retroblade.hirasawaprod.content.di.component.DaggerContentComponent
import com.retroblade.hirasawaprod.content.ui.adapter.ContentHorizontalAdapter
import com.retroblade.hirasawaprod.content.ui.adapter.ContentVerticalAdapter
import com.retroblade.hirasawaprod.content.ui.animation.*
import com.retroblade.hirasawaprod.content.ui.entity.ContentStatus
import com.retroblade.hirasawaprod.content.ui.entity.PhotoItem
import com.retroblade.hirasawaprod.utils.dpToPx
import com.retroblade.hirasawaprod.utils.setCurrentItem
import com.retroblade.hirasawaprod.utils.setTextViewParams
import kotlinx.android.synthetic.main.fragment_content.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


/**
 * @author m.a.kovalev
 */
class ContentFragment : BaseFragment(), ContentView {

    private var offsetY: Int = 0
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

    private fun initPager() {
        offsetY = requireContext().dpToPx(OFFSET_LIMIT)
        nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY > offsetY) {
                carouselContainer.translationY = -(scrollY - offsetY).toFloat()
            } else {
                carouselContainer.translationY = 0F
            }
        }

        carouselContainer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        carouselPager.offscreenPageLimit = 3
        carouselPager.adapter = pagerAdapter
    }

    private fun initContent() {
        recentArtsAdapter = ContentVerticalAdapter(recentArtsRecycler).apply { setOnItemClickListener(::onPhotoClickListener) }
        popularArtsAdapter = ContentVerticalAdapter(popularArtsRecycler).apply { setOnItemClickListener(::onPhotoClickListener) }
        allArtsAdapter = ContentHorizontalAdapter(allArtsRecycler).apply { setOnItemClickListener(::onPhotoClickListener) }

        popularArtsRecycler.post {
            val lp = popularArtsRecycler.layoutParams as ViewGroup.MarginLayoutParams
            lp.bottomMargin = relevantContainer.height + requireContext().dpToPx(MARGIN_OFFSET)
            popularArtsRecycler.layoutParams = lp
        }
        followTitle.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun initPullToRefresh() {
        retryButton.setOnClickListener(::onRetryClickListener)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            overlay.isVisible = true
            if (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) != Configuration.UI_MODE_NIGHT_YES) {
                activity?.window?.decorView?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
            }
            animationManager.startAnimation(overlay, HideContent) {
                animationManager.startAnimation(progressBar, ShowProgressBar) {
                    presenter.loadData()
                }
            }
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
        pagerAdapter.setItems(items)
        if (isScrollingStarted.not()) {
            recursiveScrolling()
            isScrollingStarted = true
        }
    }

    override fun showContent(status: ContentStatus) {
        animationManager.startAnimation(progressBar, HideProgressBar) {
            animationManager.startAnimation(overlay, ShowContent) {
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

    private fun showSnackbarMessage() {
        Snackbar.make(requireView(), R.string.snackbar_content_message, 5000)
            .setBackgroundTint(resources.getColor(R.color.content_snackbar_color, requireContext().theme))
            .setTextColor(resources.getColor(R.color.default_text_color, requireContext().theme))
            .setAnimationMode(ANIMATION_MODE_SLIDE)
            .setTextViewParams { setTextAppearance(R.style.RegularText_Size14) }
            .show()
    }

    private fun onRetryClickListener(v: View) {
        with(animationManager) {
            startAnimation(errorMessageContainer, HideErrorMessage) {
                startAnimation(progressBar, ShowProgressBar) {
                    presenter.loadData()
                }
            }
        }
    }

    private fun onPhotoClickListener(photoId: String) {
        navigatorHolder.getNavigator()?.executeNavigation(requireContext(), ViewerScreen(requireContext(), photoId))
    }

    private fun enableStartAnimation() {
        animationManager.startAnimation(progressBar, ShowProgressBar)
    }

    private fun recursiveScrolling() {
        carouselPager?.postDelayed({
            carouselPager?.adapter?.let { adapter ->
                if (currentItemPos == adapter.itemCount) {
                    currentItemPos = 0
                } else {
                    currentItemPos++
                }
                carouselPager.setCurrentItem(currentItemPos, 1000L)
                recursiveScrolling()
            }
        }, 5000L)
    }

    companion object {

        private const val OFFSET_LIMIT = 220F
        private const val MARGIN_OFFSET = 12F

        fun newInstance(): ContentFragment {
            return ContentFragment()
        }
    }
}