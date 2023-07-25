package com.example.movieapp.presentation.movie_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.RvItemProdBinding
import com.example.movieapp.domain.model.ProductionCompany


class ProdCompanyAdapter :
  ListAdapter<ProductionCompany, ProdCompanyAdapter.MyViewHolder>(ProdItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding: RvItemProdBinding =
            RvItemProdBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyViewHolder(itemView: View, binding: RvItemProdBinding) :
        RecyclerView.ViewHolder(itemView) {
        private var binding: RvItemProdBinding

        init {
            this.binding = binding
        }

        fun bind(item: ProductionCompany) {
            Glide.with(binding.root.context).load(item.logoPath).into(binding.logoIV)
            binding.nameTV.text = item.name
        }
    }
}

private class ProdItemDiffCallback : DiffUtil.ItemCallback<ProductionCompany>() {
    override fun areItemsTheSame(oldItem: ProductionCompany, newItem: ProductionCompany): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: ProductionCompany,
        newItem: ProductionCompany
    ): Boolean =
        oldItem == newItem
}