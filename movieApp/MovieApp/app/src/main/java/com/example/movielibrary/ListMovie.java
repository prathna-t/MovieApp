package com.example.movielibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.movielibrary.provider.Movie;
import com.example.movielibrary.provider.MovieViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class ListMovie extends AppCompatActivity {

    ArrayList<String> dataSource;
    //RecyclerAdapter adapter;
    MovieAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private MovieViewModel movieViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movie);

        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager


        adapter =new MovieAdapter();
        recyclerView.setAdapter(adapter);

        // initialize
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.getAllMovie().observe(this, newData -> {
            adapter.setMovie(newData);
            adapter.notifyDataSetChanged();
        });

//        Intent intent = getIntent();
        //Get the bundle
//        Bundle bundle = getIntent().getExtras();
//
//        //Extract the data…
//        String myTitle = bundle.getString("title");
//        String myYear = bundle.getString("year");
//        String myCountry = bundle.getString("country");
//        String myGenre = bundle.getString("genre");
//        String myCost = bundle.getString("cost");
//        String myKeywords = bundle.getString("keyword");

//        ArrayList<String> ar1=getIntent().getExtras().getStringArrayList("arrayList");
//        MovieAdapter<String> arrayAdapter = new MovieAdapter<String>(this,android.R.layout.simple_list_item_1, ar1);
//        recyclerView.setAdapter(arrayAdapter);
//        ArrayList<String> myList = (ArrayList<String>) getIntent().getExtras().getStringArrayList("arrayList");


//        Gson gson = new Gson();
//        Intent i = getIntent();
//        String dsStr = i.getStringExtra( "movie");
//        Type type = new TypeToken<ArrayList<Item>>(){}.getType();
//        dataSource = gson.fromJson(dsStr, type);
//        adapter =new RecyclerAdapter(dataSource);
//        recyclerView.setAdapter(adapter);

    }



}