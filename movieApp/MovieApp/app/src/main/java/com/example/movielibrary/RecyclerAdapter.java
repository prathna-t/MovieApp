package com.example.movielibrary;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<Item> movieData= new ArrayList<Item>();

    public RecyclerAdapter(ArrayList<Item> dataSource) {
        this.movieData=dataSource;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleText.setText(movieData.get(position).getTitle());
        holder.yearText.setText(movieData.get(position).getYear());
        holder.countryText.setText(movieData.get(position).getCountry());
        holder.genreText.setText(movieData.get(position).getGenre());
        holder.costText.setText(movieData.get(position).getCost());
        holder.keywordsText.setText(movieData.get(position).getKeywords());
        //holder.USDText.setText(movieData.get(position).getUSD());

        String cost = movieData.get(position).getCost();

        double calc= Integer.valueOf(cost)*0.75;
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleText;
        public TextView yearText;
        public TextView countryText;
        public TextView genreText;
        public TextView costText;
        public TextView keywordsText;
        //public TextView USDText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            titleText = itemView.findViewById(R.id.title_id);
            yearText = itemView.findViewById(R.id.year_id);
            countryText = itemView.findViewById(R.id.country_id);
            genreText = itemView.findViewById(R.id.genre_id);
            costText = itemView.findViewById(R.id.cost_id);
            keywordsText = itemView.findViewById(R.id.keywords_id);
            //USDText = itemView.findViewById(R.id.USD_id);
        }
    }

}
