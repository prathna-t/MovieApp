package com.example.movielibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielibrary.provider.Movie;
import com.example.movielibrary.provider.MovieViewModel;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    private List<Movie> mMovie= new ArrayList<>();
    MovieViewModel movieViewModel;

    public MovieAdapter() {

    }

    public void setMovie(List<Movie> newData) {
        this.mMovie=newData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        holder.titleText.setText(mMovie.get(position).getTitle());
        holder.yearText.setText(mMovie.get(position).getYear());
        holder.countryText.setText(mMovie.get(position).getCountry());
        holder.genreText.setText(mMovie.get(position).getGenre());
        holder.costText.setText(mMovie.get(position).getCost());
        holder.keywordsText.setText(mMovie.get(position).getKeywords());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int year = Integer.parseInt(holder.yearText.getText().toString());
//                movieViewModel.deleteMovieYear(year);
//
//                int cost = Integer.parseInt(holder.costText.getText().toString());
//                movieViewModel.deleteMovieCost(cost);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        if (mMovie == null)
            return 0;
        else
            return mMovie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleText;
        public TextView yearText;
        public TextView countryText;
        public TextView genreText;
        public TextView costText;
        public TextView keywordsText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.title_id);
            yearText = itemView.findViewById(R.id.year_id);
            countryText = itemView.findViewById(R.id.country_id);
            genreText = itemView.findViewById(R.id.genre_id);
            costText = itemView.findViewById(R.id.cost_id);
            keywordsText = itemView.findViewById(R.id.keywords_id);

        }
    }






}
