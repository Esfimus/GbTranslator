package com.esfimus.gbtranslator.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import com.esfimus.gbtranslator.R
import com.esfimus.gbtranslator.databinding.LoadingLayoutBinding
import com.esfimus.gbtranslator.network.OnlineLiveData
import com.esfimus.gbtranslator.view.fragment.AlertDialogFragment
import com.esfimus.gbtranslator.view.interactor.Interactor
import com.esfimus.gbtranslator.view.viewmodel.BaseViewModel
import com.esfimus.model.data.AppState
import com.esfimus.model.data.DataModel
import org.koin.androidx.scope.ScopeActivity

private const val DIALOG_FRAGMENT_TAG = "dialog fragment"

abstract class BaseActivity<T: AppState, I: Interactor<T>> : ScopeActivity() {

    private lateinit var ui: LoadingLayoutBinding
    abstract val model: BaseViewModel<T>
    protected var isNetworkAvailable: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        subscribeToNetworkChange()
    }

    override fun onResume() {
        super.onResume()
        ui = LoadingLayoutBinding.inflate(layoutInflater)
        if (!isNetworkAvailable && isDialogNull()) showNoInternetConnectionDialog()
    }

    protected fun renderData(appState: T) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                appState.data?.let {
                    if (it.isEmpty()) {
                        showAlertDialog(
                            getString(R.string.dialogTitleSorry),
                            getString(R.string.emptyServerResponse)
                        )
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                with (ui) {
                    if (appState.progress != null) {
                        progressBarHorizontal.visibility = View.VISIBLE
                        progressBarRound.visibility = View.GONE
                        progressBarHorizontal.progress = appState.progress!!
                    } else {
                        progressBarHorizontal.visibility = View.GONE
                        progressBarRound.visibility = View.VISIBLE
                    }
                }
            }
            is AppState.Error -> {
                showViewWorking()
                showAlertDialog(getString(R.string.error), appState.error.message)
            }
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialogTitleDeviceOffline),
            getString(R.string.dialogMessageDeviceOffline)
        )
    }

    private fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message).show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun showViewWorking() {
        ui.loadingFrameLayout.visibility = View.GONE
    }

    private fun showViewLoading() {
        ui.loadingFrameLayout.visibility = View.VISIBLE
    }

    private fun isDialogNull() = supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null

    abstract fun setDataToAdapter(data: List<DataModel>)

    private fun subscribeToNetworkChange() {
        OnlineLiveData(this).observe(this@BaseActivity) {
            isNetworkAvailable = it
            if (!isNetworkAvailable) {
                Toast.makeText(this@BaseActivity, R.string.dialogMessageDeviceOffline, Toast.LENGTH_LONG).show()
            }
        }
    }
}