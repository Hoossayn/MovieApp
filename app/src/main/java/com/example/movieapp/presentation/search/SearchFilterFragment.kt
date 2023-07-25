package com.example.movieapp.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.movieapp.R
import com.example.movieapp.common.FilterParams
import com.example.movieapp.databinding.FragmentSearchFilterBinding
import com.example.movieapp.domain.model.MoviesFilter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFilterFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSearchFilterBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchFilterBinding.inflate(layoutInflater, container, false)
        createGenreChips()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFilterData(sharedViewModel.moviesFilter.value)
        binding.resetFilterBtn.setOnClickListener { resetFilter() }
        binding.applyFilterBtn.setOnClickListener {
            sharedViewModel.setFilter(getFilterData())
            dismiss()
        }
    }

    private fun createGenreChips() {
        for (key in FilterParams.GENRES_FILTER.keys) {
            val chip = layoutInflater.inflate(
                R.layout.chip_item,
                binding.genreChipGroup,
                false
            ) as Chip
            chip.text = (key)
            chip.isCheckable = true
            binding.genreChipGroup.addView(chip)
        }
    }

    private fun getFilterData(): MoviesFilter {
        val sortBy = binding.root.findViewById<Chip>(binding.sortByChipGroup.checkedChipId).text
        val genres = mutableListOf<String>()
        for (i in 0 until binding.genreChipGroup.childCount) {
            val chip = binding.genreChipGroup.getChildAt(i) as Chip
            if (chip.isChecked)
                genres.add(chip.text.toString())
        }
        return MoviesFilter(
            sortBy.toString(),
            genres,
            binding.rateRangeSlider.values[0],
            binding.rateRangeSlider.values[1],
            binding.yearRangeSlider.values[0],
            binding.yearRangeSlider.values[1]
        )
    }

    private fun setFilterData(moviesFilter: MoviesFilter?) {
        if (moviesFilter != null) {
            for (i in 0 until binding.sortByChipGroup.childCount) {
                val chip = binding.sortByChipGroup.getChildAt(i) as Chip
                Log.d("SearchFilterFragment", "setFilterData: ${chip.text}")
                if (moviesFilter.sortBy == chip.text.toString())
                    chip.isChecked = true
            }
            for (i in 0 until binding.genreChipGroup.childCount) {
                val chip = binding.genreChipGroup.getChildAt(i) as Chip
                Log.d("SearchFilterFragment", "setFilterData: ${chip.text}")
                if (moviesFilter.genres.contains(chip.text.toString()))
                    chip.isChecked = true
            }
            binding.apply {
                rateRangeSlider.values = listOf(moviesFilter.voteGte, moviesFilter.voteLte)
                yearRangeSlider.values = listOf(moviesFilter.yearGte, moviesFilter.yearLte)
            }
        }
    }

    private fun resetFilter() {
        binding.apply {
            popularityChip.isChecked = true
            genreChipGroup.clearCheck()
            rateRangeSlider.values = listOf(5.0f, 10.0f)
            yearRangeSlider.values = listOf(2000f, 2023f)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}