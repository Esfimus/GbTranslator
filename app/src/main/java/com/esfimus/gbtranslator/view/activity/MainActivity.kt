package com.esfimus.gbtranslator.view.activity

import com.esfimus.gbtranslator.view.adapter.RecyclerAdapter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.esfimus.gbtranslator.R
import com.esfimus.gbtranslator.databinding.ActivityMainBinding
import com.esfimus.gbtranslator.model.data.AppState
import com.esfimus.gbtranslator.model.data.DataModel
import com.esfimus.gbtranslator.view.fragment.SearchDialogFragment
import com.esfimus.gbtranslator.viewmodel.MainInteractor
import com.esfimus.gbtranslator.viewmodel.MainViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

const val BOTTOM_SHEET_TAG = "shit"

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var ui: ActivityMainBinding
    override lateinit var model: MainViewModel
    private var adapter: RecyclerAdapter? = null
    private val onListItemClickListener: RecyclerAdapter.OnListItemClickListener =
        object : RecyclerAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)

        model = viewModelFactory.create(MainViewModel::class.java)
        model.subscribe().observe(this@MainActivity) { renderData(it) }

        ui.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    model.getData(searchWord, true)
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
            model.getData("welcome", true)
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