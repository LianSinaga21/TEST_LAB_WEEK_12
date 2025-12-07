package com.example.test_lab_week_13

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(private val clickListener: MovieClickListener) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movies = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { clickListener.onMovieClick(movie) }
    }

    // Reset list agar tidak duplikat dan tampil konsisten
    fun addMovies(movieList: List<Movie>) {
        movies.clear()
        movies.addAll(movieList)
        notifyDataSetChanged()
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleText: TextView = itemView.findViewById(R.id.movie_title)
        private val poster: ImageView = itemView.findViewById(R.id.movie_poster)

        fun bind(movie: Movie) {
            titleText.text = movie.title

            val posterUrl = movie.posterPath?.let {
                "https://image.tmdb.org/t/p/w500$it"
            } ?: ""

            Glide.with(itemView.context)
                .load(posterUrl)
                .placeholder(R.mipmap.ic_launcher)   // ✔ tidak error
                .error(R.mipmap.ic_launcher)          // ✔ tidak error
                .into(poster)
        }
    }

    interface MovieClickListener {
        fun onMovieClick(movie: Movie)
    }
}
