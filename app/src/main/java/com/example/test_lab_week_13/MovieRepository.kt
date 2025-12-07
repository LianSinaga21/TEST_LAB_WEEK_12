package com.example.test_lab_week_13

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepository(
    private val movieService: MovieService,
    private val apiKey: String
) {
    fun fetchMovies(): Flow<List<Movie>> = flow {
        val response = movieService.getPopularMovies(apiKey)
        val movies = response.results ?: emptyList()
        emit(movies)
    }
}
