package com.example.popularmovies

data class Movies(val results: List<MoviesEntity>)

data class MoviesEntity(
    val id: String,
    val title: String,
    val poster_path: String,
    val overview: String,
    val release_date: String
)
