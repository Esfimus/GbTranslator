package com.esfimus.gbtranslator.view.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.RecyclerView
import com.esfimus.gbtranslator.R
import com.esfimus.gbtranslator.databinding.ActivityMainBinding
import com.esfimus.gbtranslator.view.adapter.MainAdapter
import com.esfimus.gbtranslator.view.adapter.OnListItemClickListener
import com.esfimus.gbtranslator.view.fragment.SearchDialogFragment
import com.esfimus.gbtranslator.view.interactor.MainInteractor
import com.esfimus.gbtranslator.view.viewById
import com.esfimus.gbtranslator.view.viewmodel.MainViewModel
import com.esfimus.model.data.AppState
import com.esfimus.model.data.DataModel
import com.esfimus.model.repository.convertMeaningsToSingleString
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject

private const val BOTTOM_SHEET_TAG = "bottom sheet tag"
private const val DURATION: Long = 3000
private const val INTERVAL: Long = 1000
private const val MOVE: Long = 1000

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    private lateinit var ui: ActivityMainBinding
    override lateinit var model: MainViewModel
    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }
    private val mainRecyclerView by viewById<RecyclerView>(R.id.recyclerview)
    private val searchButton by viewById<FloatingActionButton>(R.id.searchFab)
    private val searchHistoryButton by viewById<FloatingActionButton>(R.id.searchHistoryFab)

    private val fabClickListener: View.OnClickListener =
        View.OnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_TAG)
        }

    private val onListItemClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                startActivity(DescriptionActivity.getIntent(
                    this@MainActivity,
                    data.text,
                    convertMeaningsToSingleString(data.meanings),
                    data.meanings[0].imageUrl
                ))
            }
        }

    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                if (isNetworkAvailable) {
                    model.getData(searchWord, true)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)

        if (mainRecyclerView.adapter != null) {
            throw IllegalStateException("Initiate ViewModel first")
        }
        val viewModel: MainViewModel by inject()
        model = viewModel
        model.subscribe().observe(this@MainActivity) { renderData(it) }

        searchHistoryButton.setOnClickListener {
            startActivity(Intent(this, SearchHistoryActivity::class.java))
        }
        searchButton.setOnClickListener(fabClickListener)
        mainRecyclerView.adapter = adapter

        setSplashScreen()
    }

    private fun setSplashScreen() {
        var hideSplashScreen = false
        object: CountDownTimer(DURATION, INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                hideSplashScreen = true
            }
        }.start()

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object: ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw() = if (hideSplashScreen) {
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    true
                } else false
            }
        )

        splashScreenAnimation()
    }

    private fun splashScreenAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashView ->
                val splashMove = ObjectAnimator.ofFloat(
                    splashView, View.TRANSLATION_Y, 0f, -splashView.height.toFloat()
                )
                splashMove.apply {
                    duration = MOVE
                    doOnEnd { splashView.remove() }
                    start()
                }
            }
        }
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }
}