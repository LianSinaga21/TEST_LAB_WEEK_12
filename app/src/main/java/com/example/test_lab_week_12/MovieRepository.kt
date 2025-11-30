package com.example.test_lab_week_12

import com.example.test_lab_week_12.Movie   // ⬅️ pastikan import dari model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(private val movieService: MovieService) {

    private val apiKey = "8ab4b03ae84e84cac034f619a2e450ec"

    // Fungsi mengembalikan Flow<List<Movie>> dengan sorting descending popularity
    fun fetchMovies(): Flow<List<Movie>> {
        return flow {
            // Ambil movies dari API
            val movies = movieService.getPopularMovies(apiKey).results

            // Sort descending by popularity
            val sortedMovies = movies.sortedByDescending { it.popularity }

            // Emit hasil yang sudah disortir
            emit(sortedMovies)
        }.flowOn(Dispatchers.IO) // jalankan di thread pool IO
    }
}
