package com.example.movieapp.presentation.movie_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.common.MoviesListType
import com.example.movieapp.databinding.FragmentMoviesListBinding
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MoviesList
import com.example.movieapp.presentation.home.HomeViewModel

class MoviesListFragment : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    private val sharedHomeViewModel: HomeViewModel by activityViewModels()
    private lateinit var moviesListAdapter: MoviesListAdapter
    private lateinit var currentMoviesList: MoviesList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModelObservers()
        moviesListRecViewSetup()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewModelObservers() {
        sharedHomeViewModel.moviesListType.observe(viewLifecycleOwner) { type ->
            when (type!!) {
                MoviesListType.POPULAR -> {
                    binding.headerTV.text = "Popular Movies"
                    currentMoviesList = sharedHomeViewModel.popularMoviesList.value!!.data!!
                    moviesListAdapter.submitList(currentMoviesList.movies)
                }

                MoviesListType.NOW_PLAYING -> {
                    binding.headerTV.text = "Now Playing Movies"
                    currentMoviesList = sharedHomeViewModel.nowPlayingMoviesList.value!!.data!!
                    moviesListAdapter.submitList(currentMoviesList.movies)
                }

                MoviesListType.UPCOMING -> {
                    binding.headerTV.text = "Upcoming Movies"
                    currentMoviesList = sharedHomeViewModel.upcomingMoviesList.value!!.data!!
                    moviesListAdapter.submitList(currentMoviesList.movies)
                }

                MoviesListType.TOP_RATED -> {
                    binding.headerTV.text = "Top Rated Movies"
                    currentMoviesList = sharedHomeViewModel.topRatedMoviesList.value!!.data!!
                    moviesListAdapter.submitList(currentMoviesList.movies)
                }
            }
        }
    }

    private fun moviesListRecViewSetup() {
        binding.moviesListRV.layoutManager = GridLayoutManager(requireContext(), 3)
        moviesListAdapter = MoviesListAdapter(object : MovieListItemListener {
            override fun onClick(item: Movie, position: Int) {
                val action =
                    MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailFragment(item.id)
                findNavController().navigate(action)
            }
        })
        binding.moviesListRV.adapter = moviesListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}