package com.retroblade.hirasawaprod.content.ui

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.github.terrakok.cicerone.Router
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar
import com.retroblade.hirasawaprod.R
import com.retroblade.hirasawaprod.Screens
import com.retroblade.hirasawaprod.base.BaseFragment
import com.retroblade.hirasawaprod.content.CarouselViewPagerAdapter
import com.retroblade.hirasawaprod.content.di.ContentModule
import com.retroblade.hirasawaprod.content.ui.adapter.ContentHorizontalAdapter
import com.retroblade.hirasawaprod.content.ui.adapter.ContentVerticalAdapter
import com.retroblade.hirasawaprod.content.ui.entity.ContentStatus
import com.retroblade.hirasawaprod.content.ui.entity.PhotoItem
import com.retroblade.hirasawaprod.utils.dpToPx
import com.retroblade.hirasawaprod.utils.setCurrentItem
import com.retroblade.hirasawaprod.utils.setTextViewParams
import kotlinx.android.synthetic.main.fragment_content.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope
import toothpick.ktp.delegate.inject


/**
 * @author m.a.kovalev
 */
class ContentFragment : BaseFragment(), ContentView {

    private var offsetY: Int = 0
    private val pagerAdapter = CarouselViewPagerAdapter()
    private lateinit var recentArtsAdapter: ContentVerticalAdapter
    private lateinit var popularArtsAdapter: ContentVerticalAdapter
    private lateinit var allArtsAdapter: ContentHorizontalAdapter
    private var currentItemPos: Int = 0
    private var isScrollingStarted: Boolean = false
    private var _view: View? = null

    private val router: Router by inject<Router>()

    @InjectPresenter
    lateinit var presenter: ContentPresenter

    @ProvidePresenter
    fun providePresenter(): ContentPresenter = scope.getInstance(ContentPresenter::class.java)

    override fun installModules(scope: Scope) {
        scope.installModules(moduleHolder.getModule<ContentModule>(scope))
    }

    override fun getLayoutRes(): Int = R.layout.fragment_content

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (_view == null) {
            _view = inflater.inflate(getLayoutRes(), container, false)
        }
        return _view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        scope.inject(this)
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

        recentArtsAdapter = ContentVerticalAdapter(recentArtsRecycler).apply { setOnItemClickListener(::onPhotoClickListener) }
        popularArtsAdapter = ContentVerticalAdapter(popularArtsRecycler).apply { setOnItemClickListener(::onPhotoClickListener) }
        allArtsAdapter = ContentHorizontalAdapter(allArtsRecycler).apply { setOnItemClickListener(::onPhotoClickListener) }

        followTitle.movementMethod = LinkMovementMethod.getInstance()
        popularArtsRecycler.post {
            val lp = popularArtsRecycler.layoutParams as ViewGroup.MarginLayoutParams
            lp.bottomMargin = relevantContainer.height + requireContext().dpToPx(MARGIN_OFFSET)
            popularArtsRecycler.layoutParams = lp
        }
        retryButton.setOnClickListener(::onRetryClickListener)
        enableStartAnimation { presenter.loadData() }
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            progress.isVisible = true
            progress.animate().setStartDelay(0L).alpha(1.0F).setDuration(400L).withEndAction {
                presenter.loadData()
            }.start()
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
        progress.animate()
            .setStartDelay(2000L)
            .alpha(0.0F)
            .setDuration(400L)
            .withEndAction {
                progress.isVisible = false
                if (status == ContentStatus.CACHED) {
                    Snackbar.make(requireView(), R.string.snackbar_content_message, 5000)
                        .setBackgroundTint(resources.getColor(R.color.white, requireContext().theme))
                        .setTextColor(resources.getColor(R.color.default_text_color, requireContext().theme))
                        .setAnimationMode(ANIMATION_MODE_SLIDE)
                        .setTextViewParams { setTextAppearance(R.style.RegularText_Size14) }
                        .show()
                }
            }
            .start()
    }

    override fun showError() {
        progressBar.animate().setStartDelay(0L).setDuration(500L).alpha(0.0F).withEndAction {
            errorMessageContainer.animate().setStartDelay(200L).setDuration(1000L).alpha(1.0F).start()
        }.start()
    }

    private fun onRetryClickListener(v: View) {
        errorMessageContainer.animate().setStartDelay(0L).setDuration(500L).alpha(0.0F).withEndAction {
            progressBar.animate().setStartDelay(200L).setDuration(1000L).alpha(1.0F).withEndAction {
                presenter.loadData()
            }.start()
        }.start()
    }

    private fun onPhotoClickListener(photoId: String) {
        router.navigateTo(Screens.Viewer(photoId))
    }

    private fun enableStartAnimation(callback: () -> Unit) {
        progressBar.animate()
            .setStartDelay(500L)
            .alpha(1.0F)
            .setDuration(1000L)
            .withEndAction { callback.invoke() }
            .start()
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