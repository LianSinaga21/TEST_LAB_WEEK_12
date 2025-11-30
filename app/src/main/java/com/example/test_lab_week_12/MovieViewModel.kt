package com.example.test_lab_week_12

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private lateinit var repository: MovieRepository

    val popularMovies: LiveData<List<Movie>>
        get() = repository.movies

    val error: LiveData<String>
        get() = repository.error

    fun setRepository(repository: MovieRepository) {
        this.repository = repository
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            repository.fetchMovies()
        }
    }
}
