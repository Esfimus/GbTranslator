package com.esfimus.gbtranslator.view.activity

import com.esfimus.gbtranslator.view.RecyclerAdapter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.esfimus.gbtranslator.R
import com.esfimus.gbtranslator.databinding.ActivityMainBinding
import com.esfimus.gbtranslator.model.data.AppState
import com.esfimus.gbtranslator.model.data.DataModel
import com.esfimus.gbtranslator.presenter.MainPresenter
import com.esfimus.gbtranslator.presenter.Presenter
import com.esfimus.gbtranslator.view.ViewData
import com.esfimus.gbtranslator.view.fragment.SearchDialogFragment

const val BOTTOM_SHEET_TAG = "shit"

class MainActivity : BaseActivity<AppState>() {

    private lateinit var ui: ActivityMainBinding
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
                    presenter.getData(searchWord, true)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_TAG)
        }
    }

    override fun createPresenter(): Presenter<AppState, ViewData> = MainPresenter()

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
        with (ui) {
            errorText.text = error ?: getString(R.string.undefinedError)
            reloadButton.setOnClickListener {
                presenter.getData("welcome", true)
            }
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