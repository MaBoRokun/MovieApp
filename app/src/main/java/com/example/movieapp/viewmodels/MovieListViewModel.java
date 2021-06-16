package com.example.movieapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.repository.MovieRepository;
import com.example.movieapp.utils.MovieApi;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    private MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository=MovieRepository.getInstance();
    }
    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }
    public LiveData<List<MovieModel>> getPopularMovies(){
        return movieRepository.getPopularMovies();
    }

    public void searchMovieApi(String query,int pageNumber){
        movieRepository.searchMovieApi(query,pageNumber);
    }

    public void searchMoviePopular(int pageNumber){
        movieRepository.searchPopularApi(pageNumber);
    }

    public void SearchNextPage(){
        movieRepository.SearchNextPage();
    }
    public void NextPopularPage(){
        movieRepository.NextPopularPage();
    }
}
