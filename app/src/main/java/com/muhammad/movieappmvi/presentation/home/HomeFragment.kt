package com.muhammad.movieappmvi.presentation.home

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhammad.movieappmvi.R
import com.muhammad.movieappmvi.databinding.FragmentHomeBinding
import com.muhammad.movieappmvi.domain.model.Movie
import com.muhammad.movieappmvi.presentation.home.adapter.MovieAdapter
import com.muhammad.movieappmvi.presentation.home.adapter.MovieLoadStateAdapter
import com.muhammad.movieappmvi.presentation.home.intent.HomeIntent
import com.muhammad.movieappmvi.presentation.home.state.HomeUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class HomeFragment : Fragment() {
    private var recyclerViewState: Parcelable? = null

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecycler()
        observeUiState()
        observeLayoutType()
        handleIntents()
    }

    private fun setupRecycler() {
        movieAdapter = MovieAdapter(
            ::onFavoriteClick,
            ::onMovieClick,
            isGrid = viewModel.isGrid.value
        )

        binding.rvMovies.adapter = movieAdapter.withLoadStateFooter(
            footer = MovieLoadStateAdapter { movieAdapter.retry() }
        )
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is HomeUiState.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is HomeUiState.Success -> {
                        binding.progressBar.isGone = true
                        movieAdapter.submitData(state.movies)
                        recyclerViewState?.let {
                            binding.rvMovies.layoutManager?.onRestoreInstanceState(it)
                        }
                    }

                    is HomeUiState.Error -> {
                        binding.progressBar.isGone = true
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> {}
                }
            }
        }
    }

    private fun observeLayoutType() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isGrid.collect { isGrid ->
                val layoutManager = if (isGrid) {
                    GridLayoutManager(requireContext(), 2)
                } else {
                    LinearLayoutManager(requireContext())
                }
                binding.rvMovies.layoutManager = layoutManager
                movieAdapter.setLayoutType(isGrid)

                val iconRes = if (isGrid) {
                    R.drawable.ic_list
                } else {
                    R.drawable.ic_grid
                }
                binding.ivLayoutToggle.setImageResource(iconRes)
            }
        }
    }

    private fun handleIntents() {
        binding.ivLayoutToggle.setOnClickListener {
            viewModel.onIntent(HomeIntent.ChangeLayout(!viewModel.isGrid.value))
        }
        viewModel.onIntent(HomeIntent.FetchMovies)
    }

    private fun onFavoriteClick(movie: Movie) {
        viewModel.onIntent(HomeIntent.ToggleFavorite(movie))
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteToggled.collect { movieId ->
                movieId?.let {
                    movieAdapter.refresh()
                }
            }
        }
    }

    private fun onMovieClick(movie: Movie) {
        val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(movie.id)
        findNavController().navigate(action)
    }

    override fun onPause() {
        super.onPause()
        recyclerViewState = binding.rvMovies.layoutManager?.onSaveInstanceState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}