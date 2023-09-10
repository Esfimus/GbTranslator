package com.esfimus.gbtranslator.view.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.esfimus.gbtranslator.R

class AlertDialogFragment : AppCompatDialogFragment() {

    companion object {

        private const val TITLE_EXTRA = "title extra"
        private const val MESSAGE_EXTRA = "message extra"

        fun newInstance(title: String?, message: String?): AlertDialogFragment {
            val dialogFragment = AlertDialogFragment()
            val args = Bundle()
            args.putString(TITLE_EXTRA, title)
            args.putString(MESSAGE_EXTRA, message)
            dialogFragment.arguments = args
            return dialogFragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = activity
        var alertDialog = getStubAlertDialog(context!!)
        val args = arguments
        if (args != null) {
            val title = args.getString(TITLE_EXTRA)
            val message = args.getString(MESSAGE_EXTRA)
            alertDialog = getAlertDialog(context, title, message)
        }
        return alertDialog
    }

    private fun getStubAlertDialog(context: Context) = getAlertDialog(context, null, null)

    private fun getAlertDialog(context: Context, title: String?, message: String?): AlertDialog {
        val builder = AlertDialog.Builder(context)
        var finalTitle: String? = context.getString(R.string.dialogTitleStub)
        if (!title.isNullOrBlank()) {
            finalTitle = title
        }
        builder.setTitle(finalTitle)
        if (!message.isNullOrBlank()) {
            builder.setMessage(message)
        }
        builder.setCancelable(true)
        builder.setPositiveButton(R.string.dialogButtonCancel) { dialog, _ -> dialog.dismiss() }
        return builder.create()
    }
}