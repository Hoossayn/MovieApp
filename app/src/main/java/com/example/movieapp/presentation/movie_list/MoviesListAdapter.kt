package com.example.movieapp.presentation.movie_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.RvCardMovieListBinding
import com.example.movieapp.domain.model.Movie


class MoviesListAdapter(private val itemListener: MovieListItemListener) :
    ListAdapter<Movie, MoviesListAdapter.MyViewHolder>(MovieListItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding =
            RvCardMovieListBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyViewHolder(itemView: View, binding: RvCardMovieListBinding) :
        RecyclerView.ViewHolder(itemView) {
        private var binding: RvCardMovieListBinding

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
            Glide.with(binding.root.context).load(item.posterPath)
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_placeholder)
                .into(binding.posterImgView)
            binding.titleTxtView.text = item.title
            binding.rateTV.text = item.voteAverage.toString()
            if (item.voteAverage > 0.0) binding.rateLayout.visibility = View.VISIBLE
            else binding.rateLayout.visibility = View.GONE
        }
    }
}


interface MovieListItemListener {
    fun onClick(item: Movie, position: Int)
}

private class MovieListItemDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem

}