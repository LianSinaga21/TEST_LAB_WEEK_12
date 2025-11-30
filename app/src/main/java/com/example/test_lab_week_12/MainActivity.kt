package com.example.test_lab_week_12

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import com.example.test_lab_week_12.Movie

class MainActivity : AppCompatActivity() {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var recyclerView: RecyclerView

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

        recyclerView = findViewById(R.id.movie_list)
        recyclerView.adapter = movieAdapter

        // ViewModel dibuat langsung, karena repository dibuat di ViewModel
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        collectFlows()
    }

    private fun collectFlows() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Collect movies
                launch {
                    movieViewModel.popularMovies.collect { movies ->
                        movieAdapter.addMovies(movies)
                    }
                }

                // Collect errors
                launch {
                    movieViewModel.error.collect { error ->
                        if (error.isNotEmpty()) {
                            Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun openMovieDetails(movie: Movie) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.EXTRA_TITLE, movie.title ?: "")
            putExtra(DetailsActivity.EXTRA_RELEASE, movie.releaseDate ?: "")
            putExtra(DetailsActivity.EXTRA_OVERVIEW, movie.overview ?: "")
            putExtra(DetailsActivity.EXTRA_POSTER, movie.posterPath ?: "")
        }
        startActivity(intent)
    }
}
