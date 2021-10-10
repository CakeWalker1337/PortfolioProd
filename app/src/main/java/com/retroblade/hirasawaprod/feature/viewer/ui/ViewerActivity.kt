package com.retroblade.hirasawaprod.feature.viewer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.retroblade.hirasawaprod.App
import com.retroblade.hirasawaprod.R
import com.retroblade.hirasawaprod.base.BaseActivity
import com.retroblade.hirasawaprod.feature.content.domain.Photo
import com.retroblade.hirasawaprod.feature.viewer.di.component.DaggerViewerComponent
import com.retroblade.hirasawaprod.utils.loadFullImage
import kotlinx.android.synthetic.main.fragment_viewer.*
import moxy.ktx.moxyPresenter
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject
import javax.inject.Provider

/**
 * An activity for displaying interactive image and its details
 */
class ViewerActivity : BaseActivity(), ViewerView {

    @Inject
    lateinit var presenterProvider: Provider<ViewerPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    // providing photo id through intent
    private val photoId: String? by lazy { intent.getStringExtra(EXTRA_PHOTO_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        // component injector must be called before onCreate
        val appComponent = (applicationContext as App).appComponent
        DaggerViewerComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_viewer)

        // setup back button and title for actionbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        this.title = ""

        // set fullscreen activity mode and change system navbar color
        viewerContainer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.navigationBarColor = getColor(R.color.black)

        presenter.loadData(photoId)
    }

    override fun showPhoto(photo: Photo) {
        titleView.text = photo.title
        viewsInfoView.text = photo.viewsCount.toString()
        likesInfoView.text = photo.likesCount.toString()
        uploadDate.text = photo.uploadDate.toString(DateTimeFormat.forPattern("dd/MM/yyyy"))
        photoView.loadFullImage(photo.photoUrl)
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