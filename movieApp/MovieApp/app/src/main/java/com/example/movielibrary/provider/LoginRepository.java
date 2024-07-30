package com.example.movielibrary.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LoginRepository {
    private LoginDao loginDao;  // hold data object
    private LiveData<List<LoginTable>> mAllLogin; // hole data

    //    constructor, store all the movies in here
    public LoginRepository(Application application) {
        MovieDatabase db = MovieDatabase.getDatabase(application);
        this.loginDao = db.loginDao();
        mAllLogin = loginDao.getAllList(); // get data object
    }

    // latest movie
    LiveData<List<LoginTable>> getAllList() {
        return mAllLogin;
    }

    // insert data to table -> DAO
    void signUp(LoginTable loginTable) {
        MovieDatabase.databaseWriteExecutor.execute(() -> loginDao.signUp(loginTable));
    }

    // get the detail from database
    LoginTable getUserCredentials(String email) {
        return loginDao.getUserCredentials(email);
    }

    // delete all movies
    void deleteAllData() {
        MovieDatabase.databaseWriteExecutor.execute(() -> loginDao.deleteAllData());
    }
}
