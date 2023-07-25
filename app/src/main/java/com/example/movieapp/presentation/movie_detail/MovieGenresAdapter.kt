package com.example.movieapp.presentation.movie_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.RvItemGenreBinding


class MovieGenresAdapter :
    ListAdapter<String, MovieGenresAdapter.MyViewHolder>(ItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding: RvItemGenreBinding =
            RvItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyViewHolder(itemView: View, binding: RvItemGenreBinding) :
        RecyclerView.ViewHolder(itemView) {
        private var binding: RvItemGenreBinding

        init {
            this.binding = binding
        }

        fun bind(genre: String) {
            binding.genreTV.text = genre
        }
    }
}
private class ItemDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem

}
