package com.esfimus.gbtranslator.view.activity

import android.os.Bundle
import com.esfimus.gbtranslator.databinding.ActivitySearchHistoryBinding
import com.esfimus.gbtranslator.view.adapter.OnListItemClickListener
import com.esfimus.gbtranslator.view.adapter.SearchHistoryAdapter
import com.esfimus.gbtranslator.view.interactor.SearchHistoryInteractor
import com.esfimus.gbtranslator.view.viewmodel.SearchHistoryViewModel
import com.esfimus.model.data.AppState
import com.esfimus.model.data.DataModel
import org.koin.android.ext.android.inject

class SearchHistoryActivity : BaseActivity<AppState, SearchHistoryInteractor>() {

    private lateinit var ui: ActivitySearchHistoryBinding
    override lateinit var model: SearchHistoryViewModel
    private val adapter: SearchHistoryAdapter by lazy { SearchHistoryAdapter(onListItemClickListener) }

    private val onListItemClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                model.getData(data.text, true)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivitySearchHistoryBinding.inflate(layoutInflater)
        setContentView(ui.root)

        if (ui.recyclerview.adapter != null) {
            throw IllegalStateException("Initiate ViewModel first")
        }
        val viewModel: SearchHistoryViewModel by inject()
        model = viewModel
        model.subscribe().observe(this@SearchHistoryActivity) { renderData(it) }

        ui.recyclerview.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }
}