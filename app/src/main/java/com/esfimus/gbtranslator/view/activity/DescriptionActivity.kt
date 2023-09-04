package com.esfimus.gbtranslator.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.esfimus.gbtranslator.databinding.ActivityDescriptionBinding

class DescriptionActivity : AppCompatActivity() {

    private lateinit var ui: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(ui.root)

        setActionbarHomeButtonAsUp()
        ui.refreshScreen.setOnRefreshListener { setData() }
        setData()
    }

    private fun setActionbarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
                descriptionImage.loadCoilImage(imageLink)
                stopRefreshAnimation()
            }
        }
    }

    private fun stopRefreshAnimation() {
        if (ui.refreshScreen.isRefreshing) ui.refreshScreen.isRefreshing = false
    }

    private fun ImageView.loadCoilImage(url: String) {
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val WORD_EXTRA = "6zavmpmv4KDXH5VSyuEDXt4S6lzzVW"
        const val DESCRIPTION_EXTRA = "F1ufVTZosHLEE4d0QffeFbkI5LjMVnn0R"
        const val URL_EXTRA = "Xox92doWKEHmDrvuw9gY9Wdr2dc"

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