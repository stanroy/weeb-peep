package com.stanroy.weebpeep.presentation.util

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.stanroy.weebpeep.R
import com.stanroy.weebpeep.data.model.Anime
import com.stanroy.weebpeep.databinding.AnimeListItemBinding

class AnimePagingAdapter() : PagingDataAdapter<Anime, AnimePagingAdapter.AnimeViewHolder>(
    diffUtilCallback
) {

    private lateinit var onFabAddClick: (anime: Anime) -> Unit
    private lateinit var onFabRmvClick: (anime: Anime) -> Unit
    private lateinit var onImageClick: (animeUrl: String) -> Unit
    var shouldHideFab = false

    companion object {
        val diffUtilCallback = object : DiffUtil.ItemCallback<Anime>() {
            override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }

    fun setOnFabAddClick(onClick: (anime: Anime) -> Unit) {
        this.onFabAddClick = onClick
    }

    fun setOnFabRmvClick(onClick: (anime: Anime) -> Unit) {
        this.onFabRmvClick = onClick
    }

    fun setOnImageClick(onClick: (animeUrl: String) -> Unit) {
        this.onImageClick = onClick
    }


    inner class AnimeViewHolder(private val binding: AnimeListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: Anime) {
            binding.tvAnimeTitle.text = anime.title
            binding.fabAddToFav.setOnClickListener {
                onFabAddClick(anime)
            }
            binding.fabRmvFromFav.setOnClickListener {
                onFabRmvClick(anime)
            }

            Glide.with(itemView)
                .load(anime.imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.pbAnimeItem.visibility = View.GONE

                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.pbAnimeItem.visibility = View.GONE
                        return false
                    }
                })
                .transition(withCrossFade())
                .into(binding.ivAnimeImg)

            binding.ivAnimeImg.setOnClickListener {
                onImageClick(anime.url)
            }

            if (shouldHideFab) {
                binding.fabAddToFav.visibility = View.INVISIBLE
                binding.fabRmvFromFav.visibility = View.VISIBLE
            } else {
                binding.fabAddToFav.visibility = View.VISIBLE
                binding.fabRmvFromFav.visibility = View.GONE
            }
        }
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding: AnimeListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.anime_list_item,
            parent,
            false
        )
        return AnimeViewHolder(binding)
    }
}