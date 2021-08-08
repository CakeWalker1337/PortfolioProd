package com.retroblade.hirasawaprod.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.retroblade.hirasawaprod.R
import kotlinx.android.synthetic.main.fragment_content.*
import kotlin.math.roundToInt

/**
 * @author m.a.kovalev
 */
class ContentFragment : Fragment() {

    var offsetY: Int = 0
    val pagerAdapter = CarouselViewPagerAdapter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        offsetY = (OFFSET_LIMIT * requireContext().resources.displayMetrics.density).roundToInt()
        nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY > offsetY) {
                carouselContainer.translationY = -(scrollY - offsetY).toFloat()
            } else {
                carouselContainer.translationY = 0F
            }
        }
        carouselContainer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        carouselPager.adapter = CarouselViewPagerAdapter()
//        pagerAdapter.setItems()
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