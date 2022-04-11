package com.gb.advanced2.externals.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gb.advanced2.databinding.SearchListItemBinding
import com.gb.advanced2.entities.Article
import com.gb.advanced2.entities.Meaning
import java.lang.StringBuilder

class SearchListAdapter(private val onItemClick: (Article) -> Unit) :
    ListAdapter<Article, SearchListAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(val vb: SearchListItemBinding) : RecyclerView.ViewHolder(vb.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        SearchListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = super.getItem(position)
        holder.vb.header.text = item.term
        holder.vb.description.text = item.meanings.joinToString(separator = "; ") { it.desc }
        holder.vb.root.setOnClickListener { onItemClick(item) }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(a: Article, b: Article) = a == b
        override fun areContentsTheSame(a: Article, b: Article) = a == b
    }
}