package com.example.test_lab_week_12

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProvider
import com.example.test_lab_week_12.Movie

class MainActivity : AppCompatActivity() {

    private lateinit var movieViewModel: MovieViewModel

    private val movieAdapter by lazy {
        MovieAdapter(object : MovieAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                openMovieDetails(movie)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.movie_list)
        recyclerView.adapter = movieAdapter

        val movieRepository = (application as MovieApplication).movieRepository

        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        movieViewModel.setRepository(movieRepository)

        observeMovies()
        observeError()
    }

    private fun observeMovies() {
        movieViewModel.popularMovies.observe(this) { movies ->
            movieAdapter.addMovies(movies)
        }
    }

    private fun observeError() {
        movieViewModel.error.observe(this) { message ->
            if (!message.isNullOrEmpty()) println("ERROR: $message")
        }
    }

    private fun openMovieDetails(movie: Movie) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.EXTRA_TITLE, movie.title)
            putExtra(DetailsActivity.EXTRA_RELEASE, movie.releaseDate)
            putExtra(DetailsActivity.EXTRA_OVERVIEW, movie.overview)
            putExtra(DetailsActivity.EXTRA_POSTER, movie.posterPath)
        }
        startActivity(intent)
    }
}

