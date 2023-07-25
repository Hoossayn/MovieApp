package com.example.movieapp.presentation.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.common.Resource
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.domain.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {


    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SearchViewModel by activityViewModels()
    private lateinit var filteredMoviesAdapter: FilteredMoviesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filteredRecViewSetup()
        binding.filterFAB.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_searchFilterFragment)
        }
        sharedViewModel.moviesFilter.observe(viewLifecycleOwner) {
            if (it != null) sharedViewModel.searchMovies()
        }
        sharedViewModel.filteredMovies.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    if (resource.data!!.movies.isNotEmpty()) {
                        binding.filterTipTV.visibility = View.GONE
                    } else {
                        binding.filterTipTV.visibility = View.VISIBLE
                        binding.filterTipTV.text = "No Movies Found!"
                    }
                    filteredMoviesAdapter.submitList(resource.data.movies)
                }

                Resource.Status.LOADING -> {
                }

                Resource.Status.ERROR -> {
                    Log.d("SearchFragment", "${resource.message}")
                }
            }
        }
    }

    private fun filteredRecViewSetup() {
        binding.filteredMoviesRV.layoutManager = GridLayoutManager(requireContext(), 2)
        filteredMoviesAdapter = FilteredMoviesAdapter(object : FilteredMovieItemListener {
            override fun onClick(item: Movie, position: Int) {
                val action =
                    SearchFragmentDirections.actionSearchFragmentToMovieDetailFragment2(item.id)
                findNavController().navigate(action)
            }
        })
        binding.filteredMoviesRV.adapter = filteredMoviesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}