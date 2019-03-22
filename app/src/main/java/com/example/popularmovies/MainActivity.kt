package com.example.popularmovies

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val TAG = "Main Activity"
    private val moviesRetriever = MoviesRetriever()


    private val callback = object : Callback<Movies> {
        override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
            response.isSuccessful.let {
                val movieList = response.body()?.results ?: emptyList()
                recyclerView.adapter = MoviesAdapter(movieList)
            }
        }

        override fun onFailure(call: Call<Movies>, t: Throwable) {
            Log.e(TAG, "Problem calling Movies API", t)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        checkConnectivity()
//        dummy_movie.setImageURI("http://image.tmdb.org/t/p/w342/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg")
    }

    private fun checkConnectivity() {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        if (!isConnected) {
            Toast.makeText(this@MainActivity, "911..Check network connection", Toast.LENGTH_SHORT).show()
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
}
