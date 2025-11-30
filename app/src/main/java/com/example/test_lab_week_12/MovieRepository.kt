package com.example.test_lab_week_12

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test_lab_week_12.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val movieService: MovieService) {

    private val apiKey = "8ab4b03ae84e84cac034f619a2e450ec"

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    suspend fun fetchMovies() {
        withContext(Dispatchers.IO) {
            try {
                val response = movieService.getPopularMovies(apiKey)
                _movies.postValue(response.results)
            } catch (e: Exception) {
                _error.postValue("Error: ${e.message ?: "Unknown error"}")
            }
        }
    }
}
