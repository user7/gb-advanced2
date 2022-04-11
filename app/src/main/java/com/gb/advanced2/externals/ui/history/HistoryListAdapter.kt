package com.gb.advanced2.externals.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gb.advanced2.databinding.HistoryListItemBinding
import com.gb.advanced2.entities.SearchHistoryRecord

class HistoryListAdapter(private val onClickListener: (SearchHistoryRecord) -> Unit) :
    ListAdapter<SearchHistoryRecord, HistoryListAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(val vb: HistoryListItemBinding) : RecyclerView.ViewHolder(vb.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        HistoryListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = super.getItem(position)
        holder.vb.searchString.text = item.searchQuery
        holder.vb.resultsCount.text = item.resultsCount.toString()
        holder.vb.root.setOnClickListener { onClickListener(item) }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<SearchHistoryRecord>() {
        override fun areItemsTheSame(a: SearchHistoryRecord, b: SearchHistoryRecord) = a == b
        override fun areContentsTheSame(a: SearchHistoryRecord, b: SearchHistoryRecord) = a == b
    }
}