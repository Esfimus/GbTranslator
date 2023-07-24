package com.esfimus.gbtranslator.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esfimus.gbtranslator.R
import com.esfimus.gbtranslator.databinding.RecyclerviewItemBinding
import com.esfimus.gbtranslator.model.data.DataModel

class RecyclerAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: List<DataModel>
) : RecyclerView.Adapter<RecyclerAdapter.RecyclerItemViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val ui = RecyclerviewItemBinding.bind(itemView)

        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                with (ui) {
                    header.text = data.text
                    description.text = data.meanings?.get(0)?.translation?.translation
                    itemCard.setOnClickListener { open(data) }
                }
            }
        }
    }

    private fun open(listItem: DataModel) {
        onListItemClickListener.onItemClick(listItem)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }
}