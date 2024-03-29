package com.example.movieapp.response;

import com.example.movieapp.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.List;

public class MovieSearchResponse {
    @SerializedName("total_results")
    @Expose
    private int total_count;

    @SerializedName("results")
    @Expose
    private List<MovieModel> movies;

    @SerializedName("genre_ids")
    private Array genre;

    public Array getGenre() {
        return genre;
    }

    public int getTotal_count(){
        return total_count;
    }

    public  List<MovieModel> getMovies(){
        return  movies;
    }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "total_count=" + total_count +
                ", movies=" + movies +
                '}';
    }

}
