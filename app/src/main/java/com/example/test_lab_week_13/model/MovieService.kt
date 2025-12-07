package com.example.test_lab_week_13

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): PopularMoviesResponse   // 🔥 ganti MovieResponse → PopularMoviesResponse

    companion object {
        fun create(): MovieService {
            return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(MovieService::class.java)
        }
    }
}
