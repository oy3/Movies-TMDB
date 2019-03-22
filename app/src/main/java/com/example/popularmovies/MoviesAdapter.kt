package com.example.popularmovies

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movies_list.view.*

class MoviesAdapter(private var moviesList: List<MoviesEntity>) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.activity_movies_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = moviesList.size

    override fun onBindViewHolder(holder: MoviesAdapter.ViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var movies: MoviesEntity

        fun bind(movies: MoviesEntity) {
            this.movies = movies
            itemView.movieTitle.text = movies.title
            itemView.movieOverview.text = movies.overview
            itemView.rDate.text = movies.release_date
            val imageUrl = "${BuildConfig.MOVIES_DB_URL}${movies.poster_path}"
            val titleM = movies.title
            Log.e("$titleM,Image url ==========", imageUrl)
            Picasso.with(itemView.context).load(imageUrl)
                .into(itemView.movieImg)
            //itemView.movieImg.setImageURI(imageUrl)

        }
    }
}