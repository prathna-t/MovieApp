package com.example.movielibrary.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository mRepository;
    private LiveData<List<Movie>> mAllMovie;

    // get data from respository
    public MovieViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        mAllMovie = mRepository.getAllMovie();
    }

    public LiveData<List<Movie>> getAllMovie() {
        return mAllMovie;
    }

    public void insert(Movie movie) {
        mRepository.insert(movie);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

}
