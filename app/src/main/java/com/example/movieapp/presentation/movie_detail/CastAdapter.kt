package com.example.movieapp.presentation.movie_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.RvItemCastBinding
import com.example.movieapp.domain.model.Cast

class CastAdapter :
    ListAdapter<Cast, CastAdapter.MyViewHolder>(CastItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding: RvItemCastBinding =
            RvItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyViewHolder(itemView: View, binding: RvItemCastBinding) :
        RecyclerView.ViewHolder(itemView) {
        private var binding: RvItemCastBinding

        init {
            this.binding = binding
        }

        fun bind(item: Cast) {
            Glide.with(binding.root.context).load(item.profilePath)
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_placeholder)
                .into(binding.profileIV)
            binding.nameTV.text = item.name
        }
    }
}

private class CastItemDiffCallback : DiffUtil.ItemCallback<Cast>() {
    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: Cast,
        newItem: Cast
    ): Boolean =
        oldItem == newItem
}