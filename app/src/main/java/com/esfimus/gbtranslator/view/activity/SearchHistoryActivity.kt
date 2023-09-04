package com.esfimus.gbtranslator.view.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.esfimus.gbtranslator.database.SearchEntity
import com.esfimus.gbtranslator.databinding.ActivitySearchHistoryBinding
import com.esfimus.gbtranslator.model.data.AppState
import com.esfimus.gbtranslator.view.adapter.SearchHistoryAdapter
import com.esfimus.gbtranslator.view.interactor.MainInteractor
import com.esfimus.gbtranslator.view.viewmodel.DatabaseViewModel
import com.esfimus.gbtranslator.view.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchHistoryActivity : BaseActivity<AppState, MainInteractor>() {

    private lateinit var ui: ActivitySearchHistoryBinding
    override lateinit var model: MainViewModel

    private val db: DatabaseViewModel by lazy { ViewModelProvider(this)[DatabaseViewModel::class.java] }
    private val onListItemClickListener: SearchHistoryAdapter.OnListItemClickListener =
        object : SearchHistoryAdapter.OnListItemClickListener {
            override fun onItemClick(data: SearchEntity) {
                model.getData(data.word, true)
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivitySearchHistoryBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val viewModel: MainViewModel by viewModel()
        model = viewModel

        db.searchHistoryLive.observe(this@SearchHistoryActivity) {
            val searchAdapter = SearchHistoryAdapter(onListItemClickListener, it)
            ui.recyclerview.apply {
                layoutManager = LinearLayoutManager(this@SearchHistoryActivity)
                adapter = searchAdapter
            }
        }
    }

    override fun renderData(appState: AppState) {}
}