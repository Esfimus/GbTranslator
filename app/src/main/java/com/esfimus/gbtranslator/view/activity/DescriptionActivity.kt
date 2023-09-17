package com.esfimus.gbtranslator.view.activity

import android.content.Context
import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.esfimus.gbtranslator.R
import com.esfimus.gbtranslator.databinding.ActivityDescriptionBinding
import com.esfimus.gbtranslator.network.OnlineLiveData
import com.esfimus.gbtranslator.view.fragment.AlertDialogFragment

class DescriptionActivity : AppCompatActivity() {

    private lateinit var ui: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.refreshScreen.setOnRefreshListener { startLoadingOrShowError() }
        setData()
    }

    private fun setData() {
        val bundle = intent.extras
        with (ui) {
            descriptionTitle.text = bundle?.getString(WORD_EXTRA)
            descriptionText.text = bundle?.getString(DESCRIPTION_EXTRA)
            val imageLink = bundle?.getString(URL_EXTRA)
            if (imageLink.isNullOrBlank()) {
                stopRefreshAnimation()
            } else {
                descriptionImage.loadCoilImage("https:$imageLink", false)
                descriptionMirroredImage.loadCoilImage("https:$imageLink", true)
                stopRefreshAnimation()
            }
        }
    }

    private fun startLoadingOrShowError() {
        OnlineLiveData(this).observe(this@DescriptionActivity) {
            if (it) {
                setData()
            } else {
                AlertDialogFragment.newInstance(
                    getString(R.string.dialogTitleDeviceOffline),
                    getString(R.string.dialogMessageDeviceOffline)
                ).show(
                    supportFragmentManager,
                    DIALOG_FRAGMENT_TAG
                )
                stopRefreshAnimation()
            }
        }
    }

    private fun stopRefreshAnimation() {
        if (ui.refreshScreen.isRefreshing) ui.refreshScreen.isRefreshing = false
    }

    private fun ImageView.loadCoilImage(url: String, isBlurred: Boolean) {
        val imageLoader = ImageLoader.Builder(this.context)
            .components { add(SvgDecoder.Factory()) }
            .build()
        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()
        imageLoader.enqueue(request)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && isBlurred) {
            val blurEffect = RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.DECAL)
            this.setRenderEffect(blurEffect)
        }
    }

    companion object {
        private const val WORD_EXTRA = "word extra"
        private const val DESCRIPTION_EXTRA = "description extra"
        private const val URL_EXTRA = "url extra"
        private const val DIALOG_FRAGMENT_TAG = "dialog fragment"

        fun getIntent(
            context: Context,
            word: String,
            description: String,
            url: String?
        ): Intent = Intent(context, DescriptionActivity::class.java).apply {
            putExtra(WORD_EXTRA, word)
            putExtra(DESCRIPTION_EXTRA, description)
            putExtra(URL_EXTRA, url)
        }
    }
}