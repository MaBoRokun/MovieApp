package com.example.movieapp.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

public class PopularViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

         TextView title,release_date,duration,rating_value;
        ImageView imageView;
        RatingBar ratingBar;

        OnMovieListener onMovieListener;

    public PopularViewHolder(@NonNull View itemView,OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener=onMovieListener;

        title=itemView.findViewById(R.id.movie_title);
//        release_date=itemView.findViewById(R.id.movie_category);
        release_date=itemView.findViewById(R.id.release_date);
        rating_value=itemView.findViewById(R.id.rating_value);
        imageView=itemView.findViewById(R.id.movie_img);
        ratingBar=itemView.findViewById(R.id.rating_bar);

        itemView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        onMovieListener.onMovieClick(getAdapterPosition());

    }
}
