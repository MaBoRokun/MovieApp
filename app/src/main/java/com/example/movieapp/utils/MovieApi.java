package com.example.movieapp.utils;

import com.example.movieapp.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {


    //Test response
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie
            (@Query("api_key") String key,
             @Query("query")String query,
             @Query("page")String page);

    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String key,
            @Query("page")String page
    );

    @GET("/3/genre/movie/list")
    Call<MovieSearchResponse> getGenreList(
            @Query("api_key") String key
    );
}
