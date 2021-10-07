package com.retroblade.hirasawaprod.viewer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.retroblade.hirasawaprod.App
import com.retroblade.hirasawaprod.R
import com.retroblade.hirasawaprod.base.BaseActivity
import com.retroblade.hirasawaprod.content.domain.Photo
import com.retroblade.hirasawaprod.utils.GlideImageHelper
import com.retroblade.hirasawaprod.viewer.di.component.DaggerViewerComponent
import kotlinx.android.synthetic.main.fragment_viewer.*
import moxy.ktx.moxyPresenter
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author m.a.kovalev
 */
class ViewerActivity : BaseActivity(), ViewerView {

    @Inject
    lateinit var presenterProvider: Provider<ViewerPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    private val photoId: String? by lazy { intent.getStringExtra(EXTRA_PHOTO_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (applicationContext as App).appComponent
        DaggerViewerComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)
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
        this.title = ""
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

    companion object {
        const val EXTRA_PHOTO_ID = "extra_photo_id"

        fun createIntent(context: Context, photoId: String): Intent {
            return Intent(context, ViewerActivity::class.java).apply {
                putExtra(EXTRA_PHOTO_ID, photoId)
            }
        }
    }
}