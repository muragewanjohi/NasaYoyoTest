package com.test.myapplication.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.test.myapplication.R
import com.test.myapplication.data.NasaImageDetails
import com.test.myapplication.databinding.ItemNasaPhotoBinding

class NasaImagesAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<NasaImageDetails, NasaImagesAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    inner class PhotoViewHolder(private val binding: ItemNasaPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(photo: NasaImageDetails) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.href)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imagePic)

                imageTitle.text = photo.title
                imageDateCreated.text = photo.dateCreated
                imageCenter.text = photo.center
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {

        val binding = ItemNasaPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(photo: NasaImageDetails)
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<NasaImageDetails>() {
            override fun areItemsTheSame(oldItem: NasaImageDetails, newItem: NasaImageDetails) =
                oldItem.href == newItem.href

            override fun areContentsTheSame(oldItem: NasaImageDetails, newItem: NasaImageDetails) =
                oldItem == newItem
        }
    }
}