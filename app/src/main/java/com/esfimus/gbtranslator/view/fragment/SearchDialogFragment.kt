package com.esfimus.gbtranslator.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esfimus.gbtranslator.R
import com.esfimus.gbtranslator.databinding.SearchDialogFragmentBinding
import com.esfimus.gbtranslator.view.viewById
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class SearchDialogFragment : BottomSheetDialogFragment() {

    private var _ui: SearchDialogFragmentBinding? = null
    private val ui get() = _ui!!
    private var onSearchClickListener: OnSearchClickListener? = null
    private val searchButton by viewById<MaterialButton>(R.id.searchButton)

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            searchButton.isEnabled = !ui.inputText.text.isNullOrEmpty()
        }

        override fun afterTextChanged(p0: Editable?) { }
    }

    private val onSearchButtonClickListener = View.OnClickListener {
        onSearchClickListener?.onClick(ui.inputText.text.toString())
        dismiss()
    }

    companion object { fun newInstance() = SearchDialogFragment() }

    fun setOnSearchClickListener(listener: OnSearchClickListener) {
        onSearchClickListener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _ui = SearchDialogFragmentBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with (ui) {
            inputText.addTextChangedListener(textWatcher)
            inputBox.setEndIconOnClickListener { inputText.setText("") }
        }
        searchButton.setOnClickListener(onSearchButtonClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onSearchClickListener = null
        _ui = null
    }

    interface OnSearchClickListener {
        fun onClick(searchWord: String)
    }
}