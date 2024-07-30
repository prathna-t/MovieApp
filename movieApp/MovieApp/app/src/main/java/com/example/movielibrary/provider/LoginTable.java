package com.example.movielibrary.provider;

import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "loginTable")  // show that this is the table (table name)
public class LoginTable {
    public static final String TABLE_NAME = "loginTable";
    public static final String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey (autoGenerate = true)  //assign each id(s) as key // auto generate the key
    @NonNull
    @ColumnInfo(name = "movieId")  // column name
    private int id;                // unique value to fetch data  // primary key
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "password")
    private String password;

    public LoginTable(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
