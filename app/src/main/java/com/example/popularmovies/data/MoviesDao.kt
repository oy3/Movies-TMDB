package com.example.popularmovies.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface MoviesDao {


    @Query("SELECT * FROM MoviesEntity")
    fun getAllMovies(): List<MoviesEntity>

    @Insert ( onConflict = OnConflictStrategy . REPLACE )
    fun insertAllMovies(movies: List<MoviesEntity>)
}