package com.muhammad.movieappmvi.presentation.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.muhammad.movieappmvi.R
import com.muhammad.movieappmvi.databinding.FragmentDetailsBinding
import com.muhammad.movieappmvi.domain.model.Movie
import com.muhammad.movieappmvi.presentation.details.intent.MovieDetailsIntent
import com.muhammad.movieappmvi.presentation.details.state.MovieDetailsUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MovieDetailsFragment : Fragment() {

    private val viewModel: MovieDetailsViewModel by viewModel()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchMovieDetails()

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun fetchMovieDetails() {
        viewModel.onEvent(MovieDetailsIntent.LoadMovieDetails(args.movieId))

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is MovieDetailsUiState.Loading -> {
                        binding.loadingView.visibility = View.VISIBLE
                    }

                    is MovieDetailsUiState.Success -> {
                        updateMovieDetailsUI(state.movie)
                    }

                    is MovieDetailsUiState.Error -> {
                        binding.loadingView.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }

                    MovieDetailsUiState.Idle -> Unit
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateMovieDetailsUI(movie: Movie) {
        movie.apply {
            binding.run {
                loadingView.visibility = View.GONE
                movieDetailsTitle.text = title
                rating.text = voteAverage.toString()
                description.text = overview
                tvGenres.text = genres
                Glide.with(requireContext()).load(backdropPath).into(movieDetailsPoster)
                addToFavorite.setImageResource(
                    if (movie.isFavorite) R.drawable.ic_favorite
                    else R.drawable.ic_unfavorite
                )
                addToFavorite.setOnClickListener {
                    viewModel.onEvent(MovieDetailsIntent.ToggleFavorite(movie))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}