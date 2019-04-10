package com.example.popularmovies.data

import retrofit2.Call
import retrofit2.http.GET

interface MoviesApiService {
    @GET("movie/popular?api_key=94c0386b114bfb0ee11d9e449994de1d")
    fun getMovies(): Call<Movies>
}