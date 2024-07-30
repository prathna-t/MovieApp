package com.example.movielibrary;

import android.os.Parcel;
import android.os.Parcelable;

public class Item {

    private String TitleName;
    private String YearName;
    private String CountryName;
    private String GenreName;
    private String CostName;
    private String KeywordsName;
    //private String USDName;

    public Item(String title, String year, String country, String genre, String cost, String keywords) {
        this.TitleName = title;
        this.YearName = year;
        this.CountryName = country;
        this.GenreName = genre;
        this.CostName = cost;
        this.KeywordsName = keywords;
        //this.USDName = cost;
    }


    public String getTitle() {
        return TitleName;
    }

    public String getYear() {
        return YearName;
    }

    public String getCountry() {
        return CountryName;
    }

    public String getGenre() {
        return GenreName;
    }

    public String getCost() {
        return CostName;
    }

    public String getKeywords() {
        return KeywordsName;
    }


}
