package com.esfimus.gbtranslator.view.activity

import com.esfimus.gbtranslator.view.adapter.RecyclerAdapter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.esfimus.gbtranslator.R
import com.esfimus.gbtranslator.databinding.ActivityMainBinding
import com.esfimus.gbtranslator.model.data.AppState
import com.esfimus.gbtranslator.model.data.DataModel
import com.esfimus.gbtranslator.view.fragment.SearchDialogFragment
import com.esfimus.gbtranslator.viewmodel.MainViewModel

const val BOTTOM_SHEET_TAG = "shit"

class MainActivity : BaseActivity<AppState>() {

    private lateinit var ui: ActivityMainBinding
    override val model: MainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }
    private val observer = Observer<AppState> { renderData(it) }
    private var adapter: RecyclerAdapter? = null
    private val onListItemClickListener: RecyclerAdapter.OnListItemClickListener =
        object : RecyclerAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    model.getData(searchWord, true).observe(this@MainActivity, observer)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_TAG)
        }
    }

    override fun renderData(appState: AppState) {
        with (ui) {
            when (appState) {
                is AppState.Success -> {
                    val dataModel = appState.data
                    if (dataModel.isNullOrEmpty()) {
                        showErrorScreen(getString(R.string.emptyServerResponse))
                    } else {
                        showViewSuccess()
                        if (adapter == null) {
                            recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                            recyclerview.adapter =
                                RecyclerAdapter(onListItemClickListener, dataModel)
                        } else {
                            adapter!!.setData(dataModel)
                        }
                    }
                }

                is AppState.Loading -> {
                    showViewLoading()
                    if (appState.progress != null) {
                        progressHorizontal.visibility = View.VISIBLE
                        progressRound.visibility = View.GONE
                        progressHorizontal.progress = appState.progress
                    } else {
                        progressHorizontal.visibility = View.GONE
                        progressRound.visibility = View.VISIBLE
                    }
                }

                is AppState.Error -> {
                    showErrorScreen(appState.error.message)
                }
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        ui.errorText.text = error ?: getString(R.string.undefinedError)
        ui.reloadButton.setOnClickListener {
            model.getData("welcome", true).observe(this, observer)
        }
    }

    private fun showViewSuccess() {
        with (ui) {
            successLayout.visibility = View.VISIBLE
            loadingLayout.visibility = View.GONE
            errorLayout.visibility = View.GONE
        }
    }

    private fun showViewLoading() {
        with (ui) {
            successLayout.visibility = View.GONE
            loadingLayout.visibility = View.VISIBLE
            errorLayout.visibility = View.GONE
        }
    }

    private fun showViewError() {
        with (ui) {
            successLayout.visibility = View.GONE
            loadingLayout.visibility = View.GONE
            errorLayout.visibility = View.VISIBLE
        }
    }
}