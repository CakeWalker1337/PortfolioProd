package com.retroblade.hirasawaprod.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.retroblade.hirasawaprod.R
import com.retroblade.hirasawaprod.utils.dpToPx
import com.retroblade.hirasawaprod.utils.setCurrentItem
import kotlinx.android.synthetic.main.fragment_content.*

/**
 * @author m.a.kovalev
 */
class ContentFragment : Fragment() {

    private var offsetY: Int = 0
    private val pagerAdapter = CarouselViewPagerAdapter()
    private var currentItemPos: Int = 0

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
        pagerAdapter.setItems(
            listOf(
                PictureItem("1", "https://i.imgur.com/a7s4g5B.jpeg"),
                PictureItem("2", "https://i.imgur.com/9ubEX26.jpeg"),
                PictureItem("3", "https://i.imgur.com/rJqtaQU.png"),
                PictureItem("4", "https://i.imgur.com/wKtAi7D.jpeg"),
            )
        )
        recursiveScrolling()
    }

    fun recursiveScrolling() {
        carouselPager.postDelayed({
            if (currentItemPos == carouselPager.adapter!!.itemCount) {
                currentItemPos = 0
            } else {
                currentItemPos++
            }
            carouselPager.setCurrentItem(currentItemPos, 1000L)
            recursiveScrolling()
        }, 5000L)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_content, container, false)
    }

    private companion object {

        private const val OFFSET_LIMIT = 220F
    }
}