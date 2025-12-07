package com.example.test_lab_week_13

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieApplication : Application() {

    companion object {
        const val TMDB_API_KEY = "8ab4b03ae84e84cac034f619a2e450ec"   // 🔥 ganti dengan API KEY kamu
    }

    lateinit var movieRepository: MovieRepository

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val movieService = retrofit.create(MovieService::class.java)

        movieRepository = MovieRepository(
            movieService,
            TMDB_API_KEY
        )
    }
}
