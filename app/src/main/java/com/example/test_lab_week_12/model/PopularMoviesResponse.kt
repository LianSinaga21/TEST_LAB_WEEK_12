package com.example.test_lab_week_12

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularMoviesResponse(
    val page: Int,
    val results: List<Movie>
)
