package com.example.movielibrary.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LoginDao {

    @Query("select * from loginTable")  //fetch data from movie class
    LiveData<List<LoginTable>> getAllList();  // get all the movie in the list

    @Insert
    void signUp(LoginTable loginTable);  // insert movie in the database

    @Query("select * from loginTable where email = :email")
    LoginTable getUserCredentials(String email);

    @Query("delete FROM loginTable")
    void deleteAllData();    // use delete by id

}
