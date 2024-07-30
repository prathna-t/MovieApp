package com.example.movielibrary.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieRepository {

    private MovieDao mMovieDao;  // hold data object
    private LiveData<List<Movie>> mAllMovie; // hole data

    //    constructor, store all the movies in here
    MovieRepository(Application application) {
        MovieDatabase db = MovieDatabase.getDatabase(application);
        mMovieDao = db.movieDao();
        mAllMovie = mMovieDao.getAllMovie(); // get data object
    }

    // latest movie
    LiveData<List<Movie>> getAllMovie() {
        return mAllMovie;
    }

    // insert data to table -> DAO
    void insert(Movie movie) {
        MovieDatabase.databaseWriteExecutor.execute(() -> mMovieDao.addMovie(movie));
    }

    // delete all movies
    void deleteAll(){
        MovieDatabase.databaseWriteExecutor.execute(()->{
            mMovieDao.deleteAllMovie();
        });
    }

}
