package com.retroblade.hirasawaprod.viewer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.retroblade.hirasawaprod.R
import com.retroblade.hirasawaprod.base.BaseActivity
import com.retroblade.hirasawaprod.content.domain.Photo
import com.retroblade.hirasawaprod.utils.GlideImageHelper
import kotlinx.android.synthetic.main.fragment_viewer.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.joda.time.format.DateTimeFormat

/**
 * @author m.a.kovalev
 */
class ViewerActivity : BaseActivity(), ViewerView {

    private val photoId: String? by lazy { intent.getStringExtra(EXTRA_PHOTO_ID) }

    @InjectPresenter
    lateinit var presenter: ViewerPresenter

    @ProvidePresenter
    fun providePresenter(): ViewerPresenter = scope.getInstance(ViewerPresenter::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope.inject(this)
        setContentView(R.layout.fragment_viewer)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        viewerContainer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        presenter.loadData(photoId)
        window.navigationBarColor = getColor(R.color.black)
    }

    override fun showPhoto(photo: Photo) {
        titleView.text = photo.title
        viewsInfoView.text = photo.viewsCount.toString()
        likesInfoView.text = photo.likesCount.toString()
        uploadDate.text = photo.uploadDate.toString(DateTimeFormat.forPattern("dd/MM/yyyy"))
        GlideImageHelper.loadFlickrFull(photo.photoUrl, photoView)
    }

    override fun showError() {
        errorMessageView.isVisible = true
    }

    override fun showToastError(message: String) = Unit

    override fun showAlertError(message: String) = Unit


    companion object {
        const val EXTRA_PHOTO_ID = "extra_photo_id"

        fun createIntent(context: Context, photoId: String): Intent {
            return Intent(context, ViewerActivity::class.java).apply {
                putExtra(EXTRA_PHOTO_ID, photoId)
            }
        }
    }
}