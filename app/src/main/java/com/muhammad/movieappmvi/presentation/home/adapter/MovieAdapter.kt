package com.muhammad.movieappmvi.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muhammad.movieappmvi.R
import com.muhammad.movieappmvi.databinding.ItemMovieGridBinding
import com.muhammad.movieappmvi.databinding.ItemMovieLinearBinding
import com.muhammad.movieappmvi.domain.model.Movie

class MovieAdapter(
    private val onFavoriteClick: (Movie) -> Unit,
    private val onMovieClick: (Movie) -> Unit,
    isGrid: Boolean
) : PagingDataAdapter<Movie, RecyclerView.ViewHolder>(DiffCallback()) {

    private var layoutType: MovieLayoutType =
        if (isGrid) MovieLayoutType.GRID else MovieLayoutType.LINEAR

    fun setLayoutType(isGrid: Boolean) {
        this.layoutType = if (isGrid) MovieLayoutType.GRID else MovieLayoutType.LINEAR
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return layoutType.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return if (viewType == MovieLayoutType.GRID.ordinal) {
            val binding = ItemMovieGridBinding.inflate(layoutInflater, parent, false)
            GridViewHolder(binding)
        } else {
            val binding = ItemMovieLinearBinding.inflate(layoutInflater, parent, false)
            LinearViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = getItem(position) ?: return

        when (holder) {
            is GridViewHolder -> holder.bind(movie)
            is LinearViewHolder -> holder.bind(movie)
        }
    }

    inner class GridViewHolder(private val binding: ItemMovieGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.tvMovieTitle.text = movie.title
            binding.tvReleaseDate.text = movie.releaseDate
            Glide.with(binding.ivMoviePoster)
                .load(movie.posterPath)
                .into(binding.ivMoviePoster)

            binding.ivFav.setImageResource(
                if (movie.isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_unfavorite
            )

            binding.ivFav.setOnClickListener {
                onFavoriteClick(movie)
            }
            binding.root.setOnClickListener {
                onMovieClick(movie)
            }
        }
    }

    inner class LinearViewHolder(private val binding: ItemMovieLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.tvMovieTitle.text = movie.title
            binding.tvReleaseDate.text = movie.releaseDate
            Glide.with(binding.ivMoviePoster)
                .load(movie.posterPath)
                .into(binding.ivMoviePoster)

            binding.ivFav.setImageResource(
                if (movie.isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_unfavorite
            )

            binding.ivFav.setOnClickListener {
                onFavoriteClick(movie)
            }

            binding.root.setOnClickListener {
                onMovieClick(movie)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }
}