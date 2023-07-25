package com.example.movieapp.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.RvCardMovieBackdropBinding
import com.example.movieapp.domain.model.Movie


class BackdropMoviesAdapter(private val itemListener: BackdropItemListener) :
    ListAdapter<Movie, BackdropMoviesAdapter.MyViewHolder>(BackdropItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding =
            RvCardMovieBackdropBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyViewHolder(itemView: View, binding: RvCardMovieBackdropBinding) :
        RecyclerView.ViewHolder(itemView) {
        private var binding: RvCardMovieBackdropBinding

        init {
            this.binding = binding
            itemView.setOnClickListener {
                itemListener.onClick(
                    getItem(adapterPosition),
                    adapterPosition
                )
            }
        }

        fun bind(item: Movie) {
            Glide.with(binding.root.context).load(item.backdropPath)
                .placeholder(R.drawable.backdrop_placeholder)
                .error(R.drawable.backdrop_placeholder)
                .into(binding.backdropImgView)
            binding.titleTxtView.text = item.title
            binding.rateTV.text = item.voteAverage.toString()
            if (item.voteAverage > 0.0) binding.rateLayout.visibility = View.VISIBLE
            else binding.rateLayout.visibility = View.GONE
        }
    }
}


interface BackdropItemListener {
    fun onClick(item: Movie, position: Int)
}

private class BackdropItemDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem

}