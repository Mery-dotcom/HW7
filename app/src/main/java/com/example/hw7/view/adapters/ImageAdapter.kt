package com.example.hw7.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hw7.databinding.ItemImageBinding
import com.example.hw7.model.models.ImageResponse

class ImageAdapter(
    private val clickListener: (ImageResponse.Hit) -> Unit,
    private val longClickListener: (ImageResponse.Hit) -> Unit
): ListAdapter<ImageResponse.Hit, ImageAdapter.ImageViewHolder>(ImageDiffCallback()) {

    class ImageViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.binding.apply {
            val item = getItem(position)
            Glide.with(holder.itemView)
                .load(item.largeImageURL)
                .into(holder.binding.image)
        }
    }

    class ImageDiffCallback: DiffUtil.ItemCallback<ImageResponse.Hit>() {
        override fun areItemsTheSame(oldItem: ImageResponse.Hit, newItem: ImageResponse.Hit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageResponse.Hit, newItem: ImageResponse.Hit): Boolean {
            return oldItem == newItem
        }
    }
}