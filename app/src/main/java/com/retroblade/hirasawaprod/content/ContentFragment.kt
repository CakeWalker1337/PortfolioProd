package com.retroblade.hirasawaprod.content

import ContentHorizontalAdapter
import android.os.Bundle
import android.view.View
import com.retroblade.hirasawaprod.R
import com.retroblade.hirasawaprod.base.BaseFragment
import com.retroblade.hirasawaprod.utils.dpToPx
import com.retroblade.hirasawaprod.utils.setCurrentItem
import kotlinx.android.synthetic.main.fragment_content.*
import moxy.ktx.moxyPresenter


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

    private val presenter: ContentPresenter by moxyPresenter { ContentPresenter() }

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
        val items = listOf(
            PhotoItem("1", "https://i.imgur.com/a7s4g5B.jpeg", 1, 1),
            PhotoItem("2", "https://i.imgur.com/9ubEX26.jpeg", 1, 1),
            PhotoItem("3", "https://i.imgur.com/rJqtaQU.png", 1, 1),
            PhotoItem("4", "https://i.imgur.com/wKtAi7D.jpeg", 1, 1),
        )
        pagerAdapter.setItems(items)
        recursiveScrolling()
        recentArtsAdapter = ContentVerticalAdapter(recentArtsRecycler)
        popularArtsAdapter = ContentVerticalAdapter(popularArtsRecycler)
        allArtsAdapter = ContentHorizontalAdapter(allArtsRecycler)

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
    }
}