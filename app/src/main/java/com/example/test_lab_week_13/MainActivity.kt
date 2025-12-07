package com.example.test_lab_week_13

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.test_lab_week_13.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        // 🎯 Perbaikan: ubah dari LinearLayoutManager ke GridLayoutManager (2 kolom)
        binding.movieRecycler.layoutManager = GridLayoutManager(this, 2)

        binding.movieRecycler.adapter = movieAdapter
        binding.viewModel = movieViewModel
        binding.lifecycleOwner = this
    }

    private fun openMovieDetails(movie: Movie) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.EXTRA_TITLE, movie.title.orEmpty())
            putExtra(DetailsActivity.EXTRA_RELEASE, movie.releaseDate.orEmpty())
            putExtra(DetailsActivity.EXTRA_OVERVIEW, movie.overview.orEmpty())
            putExtra(DetailsActivity.EXTRA_POSTER, movie.posterPath.orEmpty())
        }
        startActivity(intent)
    }
}
