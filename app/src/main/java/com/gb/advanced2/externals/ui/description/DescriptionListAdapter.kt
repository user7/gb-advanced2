package com.gb.advanced2.externals.ui.description

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gb.advanced2.databinding.DescriptionListItemBinding
import com.gb.advanced2.entities.Meaning
import timber.log.Timber

class DescriptionListAdapter :
    ListAdapter<Meaning, DescriptionListAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(val vb: DescriptionListItemBinding) : RecyclerView.ViewHolder(vb.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DescriptionListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = super.getItem(position)
        Timber.d("!! load: $item")
        holder.vb.image.load(item.imageUrl)
        holder.vb.desc.text = item.desc
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Meaning>() {
        override fun areItemsTheSame(a: Meaning, b: Meaning) = a == b
        override fun areContentsTheSame(a: Meaning, b: Meaning) = a == b
    }
}