package com.example.popularmovies.ui.activities

import android.arch.persistence.room.Room
import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.example.popularmovies.data.Movies
import com.example.popularmovies.ui.adapters.MoviesAdapter
import com.example.popularmovies.data.MoviesRetriever
import com.example.popularmovies.R
import com.example.popularmovies.data.MoviesDatabase
import com.example.popularmovies.data.MoviesEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    private val TAG = "Main Activity"
    private val moviesRetriever = MoviesRetriever()


    private val callback = object : Callback<Movies> {
        override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
            response.isSuccessful.let {
                val movieList = response.body()?.results ?: emptyList()
                insertIntoDB(movieList)
                recyclerView.adapter = MoviesAdapter(movieList)
            }
        }

        override fun onFailure(call: Call<Movies>, t: Throwable) {
            getFromDB()
            Log.e(TAG, "Problem calling Movies API", t)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        checkConnectivity()
    }

    private fun checkConnectivity() {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        if (!isConnected) {
            getFromDB()
            Toast.makeText(this@MainActivity, "Check network connection", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MainActivity, "connected", Toast.LENGTH_SHORT).show()
            moviesRetriever.getMoviesList(callback)
        }
    }


    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tap back again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    fun insertIntoDB(moviesList: List<MoviesEntity>) {
        val db = Room.databaseBuilder(
            this.applicationContext,
            MoviesDatabase::class.java,
            "Movies_database"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        db.moviesDao().insertAllMovies(moviesList)
    }

    private fun getFromDB() {
        val db = Room.databaseBuilder(
            this.applicationContext,
            MoviesDatabase::class.java,
            "Task_database"
        ).allowMainThreadQueries()
            .build()

        val moviesList: List<MoviesEntity> = db.moviesDao().getAllMovies()
        recyclerView.adapter = MoviesAdapter(moviesList)

    }
}
