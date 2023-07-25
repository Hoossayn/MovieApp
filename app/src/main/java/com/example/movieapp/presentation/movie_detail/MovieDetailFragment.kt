package com.example.movieapp.presentation.movie_detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.common.Resource
import com.example.movieapp.databinding.FragmentMovieDetailBinding
import com.example.movieapp.domain.model.Cast
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.ProductionCompany
import com.example.movieapp.presentation.home.MovieItemListener
import com.example.movieapp.presentation.home.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var movieGenresAdapter: MovieGenresAdapter
    private lateinit var castAdapter: CastAdapter
    private lateinit var prodCompanyAdapter: ProdCompanyAdapter
    private lateinit var similarMoviesAdapter: MoviesAdapter
    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModelObservers()
        binding.rateMovieTV.setOnClickListener {
            val browserIntent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(movie.imdbUrl)
            }
            startActivity(browserIntent)
        }
        binding.shareTV.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, movie.imdbUrl)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }


    }

    private fun setupViewModelObservers() {
        viewModel.movieDetail.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    movie = resource.data!!
                    bindMovieData(movie)
                    populateProdCompanyRecView(movie.productionCompanies!!)
                }

                Resource.Status.LOADING -> {
                }

                Resource.Status.ERROR -> {
                    Log.d(
                        "MovieDetailFragment",
                        "setupViewModelObservers: ${resource.message}"
                    )
                }
            }
        }
        viewModel.movieCast.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    populateMovieCastRecView(resource.data!!)
                }

                Resource.Status.LOADING -> {
                }

                Resource.Status.ERROR -> {
                    Log.d(
                        "MovieDetailFragment",
                        "setupViewModelObservers: ${resource.message}"
                    )
                }
            }
        }
        viewModel.similarMovies.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    populateSimilarMoviesRecView(resource.data!!.movies)
                }

                Resource.Status.LOADING -> {
                }

                Resource.Status.ERROR -> {
                    Log.d(
                        "MovieDetailFragment",
                        "setupViewModelObservers: ${resource.message}"
                    )
                }
            }

        }
        viewModel.favMovie.observe(viewLifecycleOwner) { favMovie ->
            movie = favMovie
            bindMovieData(favMovie)
        }
        viewModel.isFav.observe(viewLifecycleOwner) { isFav ->
            toggleFavBtn(isFav)
        }
    }

    private fun bindMovieData(movie: Movie) {
        Glide.with(requireContext())
            .load(movie.backdropPath)
            .into(binding.backdropImgView)
        binding.apply {
            titleTV.text = movie.title
            overviewTV.text = movie.overview
            rateTV.text = movie.voteAverage.toString()
        }
        populateGenreRecView(movie.genres)
    }

    private fun populateGenreRecView(genres: List<String>) {
        binding.genreRV.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        movieGenresAdapter = MovieGenresAdapter()
        binding.genreRV.adapter = movieGenresAdapter
        movieGenresAdapter.submitList(genres)
    }

    private fun populateMovieCastRecView(castList: List<Cast>) {
        binding.movieCastRV.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        castAdapter = CastAdapter()
        binding.movieCastRV.adapter = castAdapter
        castAdapter.submitList(castList)
    }

    private fun populateProdCompanyRecView(prodList: List<ProductionCompany>) {
        if (prodList.isNotEmpty()) {
            binding.prodCompanyTV.visibility = View.VISIBLE
            binding.prodCompanyRV.visibility = View.VISIBLE
            binding.prodCompanyRV.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            prodCompanyAdapter = ProdCompanyAdapter()
            binding.prodCompanyRV.adapter = prodCompanyAdapter
            prodCompanyAdapter.submitList(prodList)
        }
    }

    private fun populateSimilarMoviesRecView(movies: List<Movie>) {
        if (movies.isNotEmpty()) {
            binding.similarMoviesTV.visibility = View.VISIBLE
            binding.similarMoviesRV.visibility = View.VISIBLE
            binding.similarMoviesRV.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            similarMoviesAdapter = MoviesAdapter(object : MovieItemListener {
                override fun onClick(item: Movie, position: Int) {
                }
            })
            binding.similarMoviesRV.adapter = similarMoviesAdapter
            similarMoviesAdapter.submitList(movies)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun toggleFavBtn(isFav: Boolean) {
        binding.favProgressBar.visibility = View.GONE
        binding.favToggleBtn.visibility = View.VISIBLE
        if (isFav) {
            binding.favTV.text = "Unfavorite"
            binding.favToggleBtn.isChecked = true
            binding.favToggleBtn.setOnClickListener {
                viewModel.removeMovieFromFav(movie)
            }
        } else {
            binding.favTV.text = "Favorite"
            binding.favToggleBtn.isChecked = false
            binding.favToggleBtn.setOnClickListener {
                viewModel.addMovieToFav(movie)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}