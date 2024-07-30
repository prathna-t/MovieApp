package com.example.movielibrary.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("select * from movies")  //fetch data from movie class
    LiveData<List<Movie>> getAllMovie();  // get all the movie in the list

    @Insert
    void addMovie(Movie movie);  // insert movie in the database

    @Query("delete FROM movies")
    void deleteAllMovie();    // use delete by id

//    @Query("delete from movies where MovieYear = :movieYear")  // pick condition, only when condition match
//    void deleteMovieYear(int movieYear);  // delete movie
//
//    @Query("delete from movies where MovieCost = :movieCost")
//    void deleteMovieCost(int movieCost);
}
