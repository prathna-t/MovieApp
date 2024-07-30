package com.example.movielibrary.provider;

import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")   // show that this is the table (table name)
public class Movie {
    public static final String TABLE_NAME = "movies";
    public static final String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey(autoGenerate = true)  //assign each id(s) as key // auto generate the key
    @NonNull
    @ColumnInfo(name = "movieId")  // column name
    private int id;                // unique value to fetch data  // primary key
    @ColumnInfo(name = "MovieTitle")
    private String title;
    @ColumnInfo(name = "MovieYear")
    private String year;
    @ColumnInfo(name = "MovieCountry")
    private String country;
    @ColumnInfo(name = "MovieGenre")
    private String genre;
    @ColumnInfo(name = "MovieCost")
    private String cost;
    @ColumnInfo(name = "MovieKeywords")
    private String keywords;

    public Movie(){

    }


    public Movie(String title, String year, String country, String genre, String cost, String keywords) {
        this.title=title;
        this.year=year;
        this.country=country;
        this.genre=genre;
        this.cost=cost;
        this.keywords=keywords;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getCountry() {
        return country;
    }

    public String getGenre() {
        return genre;
    }

    public String getCost() {
        return cost;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

}
