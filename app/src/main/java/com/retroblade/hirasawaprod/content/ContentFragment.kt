package com.retroblade.hirasawaprod.content

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.retroblade.hirasawaprod.R
import com.retroblade.hirasawaprod.base.BaseFragment
import com.retroblade.hirasawaprod.content.di.ContentModule
import com.retroblade.hirasawaprod.utils.dpToPx
import com.retroblade.hirasawaprod.utils.setCurrentItem
import kotlinx.android.synthetic.main.fragment_content.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope


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

    @InjectPresenter
    lateinit var presenter: ContentPresenter

    @ProvidePresenter
    fun providePresenter(): ContentPresenter = scope.getInstance(ContentPresenter::class.java)

    override fun installModules(scope: Scope) {
        scope.installModules(moduleHolder.getModule<ContentModule>(scope))
    }

    override fun getLayoutRes(): Int = R.layout.fragment_content

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        offsetY = requireContext().dpToPx(OFFSET_LIMIT)
        nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY > offsetY) {
                carouselContainer.translationY = -(scrollY - offsetY).toFloat()
            } else {
                carouselContainer.translationY = 0F
            }
        }
        carouselContainer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        carouselPager.setOffscreenPageLimit(3)
        carouselPager.adapter = pagerAdapter

        recentArtsAdapter = ContentVerticalAdapter(recentArtsRecycler).apply { setOnItemClickListener(::onClickListener) }
        popularArtsAdapter = ContentVerticalAdapter(popularArtsRecycler).apply { setOnItemClickListener(::onClickListener) }
        allArtsAdapter = ContentHorizontalAdapter(allArtsRecycler).apply { setOnItemClickListener(::onClickListener) }

        followTitle.movementMethod = LinkMovementMethod.getInstance()
        popularArtsRecycler.post {
            val lp = popularArtsRecycler.layoutParams as ViewGroup.MarginLayoutParams
            lp.bottomMargin = relevantContainer.height + requireContext().dpToPx(MARGIN_OFFSET)
            popularArtsRecycler.layoutParams = lp
        }
        enableStartAnimation()
        presenter.loadData()
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
        recursiveScrolling()
    }

    override fun showContent() {
        progress.animate()
            .setStartDelay(2000L)
            .alpha(0.0F)
            .setDuration(400L)
            .withEndAction { progress.isVisible = false }
            .start()
    }

    private fun onClickListener() {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.type = "image/*"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun enableStartAnimation() {
        progressBar.animate()
            .setStartDelay(500L)
            .alpha(1.0F)
            .setDuration(1000L)
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

    private companion object {

        private const val OFFSET_LIMIT = 220F
        private const val MARGIN_OFFSET = 12F
    }
}