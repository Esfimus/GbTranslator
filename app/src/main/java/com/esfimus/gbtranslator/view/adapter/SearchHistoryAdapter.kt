package com.esfimus.gbtranslator.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esfimus.gbtranslator.R
import com.esfimus.database.SearchEntity
import com.esfimus.gbtranslator.databinding.RecyclerviewSearchItemBinding

class SearchHistoryAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: List<com.esfimus.database.SearchEntity>
) : RecyclerView.Adapter<SearchHistoryAdapter.RecyclerItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_search_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val ui = RecyclerviewSearchItemBinding.bind(itemView)

        fun bind(data: com.esfimus.database.SearchEntity) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                with (ui) {
                    header.text = data.word
                    itemCard.setOnClickListener { open(data) }
                }
            }
        }
    }

    private fun open(listItem: com.esfimus.database.SearchEntity) {
        onListItemClickListener.onItemClick(listItem)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: com.esfimus.database.SearchEntity)
    }
}