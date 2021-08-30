package com.retroblade.hirasawaprod.viewer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.github.terrakok.cicerone.Router
import com.retroblade.hirasawaprod.R
import com.retroblade.hirasawaprod.base.BaseFragment
import com.retroblade.hirasawaprod.content.domain.Photo
import com.retroblade.hirasawaprod.utils.GlideImageHelper
import kotlinx.android.synthetic.main.fragment_viewer.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.joda.time.format.DateTimeFormat
import toothpick.ktp.delegate.inject

/**
 * @author m.a.kovalev
 */
class ViewerFragment : BaseFragment(), ViewerView {

    override fun getLayoutRes(): Int = R.layout.fragment_viewer

    private val photoId: String? by lazy { arguments?.getString(EXTRA_PHOTO_ID) }

    private val router: Router by inject<Router>()

    @InjectPresenter
    lateinit var presenter: ViewerPresenter

    @ProvidePresenter
    fun providePresenter(): ViewerPresenter = scope.getInstance(ViewerPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        scope.inject(this)
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                router.exit()
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true);
            supportActionBar?.setDisplayShowHomeEnabled(true);
        }
        viewerContainer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        presenter.loadData(photoId)
    }

    override fun showPhoto(photo: Photo) {
        titleView.text = photo.title
        viewsInfoView.text = photo.viewsCount.toString()
        likesInfoView.text = photo.likesCount.toString()
        uploadDate.text = photo.uploadDate.toString(DateTimeFormat.forPattern("MM/dd/yyyy"))
        GlideImageHelper.loadFlickrFull(photo.photoUrl, photoView)
    }

    override fun showError() {
        errorMessageView.isVisible = true
    }

    companion object {
        const val EXTRA_PHOTO_ID = "extra_photo_id"

        fun newInstance(photoId: String): ViewerFragment {
            return ViewerFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_PHOTO_ID, photoId)
                }
            }
        }
    }
}