package com.example.movieapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.AppExecutors;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.response.MovieSearchResponse;
import com.example.movieapp.utils.Credentials;
import com.example.movieapp.utils.MovieApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {


    private static MovieApiClient instance;

    private MutableLiveData<List<MovieModel>> mMovies;
    private RetrieveMoviesRunnable retrieveMoviesRunnable;


    private MutableLiveData<List<MovieModel>> mMoviesPopular;
    private RetrieveMoviesRunnablePopular retrieveMoviesPopular;




    public static MovieApiClient getInstance(){
        if(instance==null){
            instance = new MovieApiClient();
        }
        return  instance;
    }

    private MovieApiClient(){
        mMovies=new MutableLiveData<>();
        mMoviesPopular=new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }

    public LiveData<List<MovieModel>> getPopularMovies(){
        return mMoviesPopular;
    }

    public void searchMoviesApi(String query,int pageNumber){
        if(retrieveMoviesRunnable!=null){
            retrieveMoviesRunnable=null;
        }
        retrieveMoviesRunnable =  new RetrieveMoviesRunnable(query,pageNumber);

        final Future myHandler = AppExecutors.getInstance().netWorkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().netWorkIO().schedule(new Runnable() {
            @Override
            public void run() {
            myHandler.cancel(true);
            }
        },4000, TimeUnit.MILLISECONDS);
    }

    public void searchPopularMovies(int pageNumber){
        if(retrieveMoviesPopular!=null){
            retrieveMoviesPopular=null;
        }
        retrieveMoviesPopular =  new RetrieveMoviesRunnablePopular(pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().netWorkIO().submit(retrieveMoviesPopular);

        AppExecutors.getInstance().netWorkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler2.cancel(true);
            }
        },4000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMoviesRunnable implements Runnable{

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest=false;
        }

        @Override
        public void run() {
            try {
                Response response = getMovies(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (pageNumber == 1) {
                        mMovies.postValue(list);
                    }
                    else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }

                }else{
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error code " + error);
                    mMovies.postValue(null);
                }
            }catch (IOException e){
                e.printStackTrace();
                mMovies.postValue(null);
            }


        }

        private Call<MovieSearchResponse> getMovies(String query,int pageNumber){
            return Servicy.getInstance().getJSONApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    String.valueOf(pageNumber)
            );
        }
        private void cancelRequest(){
            Log.v("Tag","Cancelling Search Request");
            cancelRequest=true;
        }


    }

    private class RetrieveMoviesRunnablePopular implements Runnable{

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnablePopular(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest=false;
        }

        @Override
        public void run() {
            try {
                Response response = getPopular(pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (pageNumber == 1) {
                        mMoviesPopular.postValue(list);
                    }
                    else {
                        List<MovieModel> currentMovies = mMoviesPopular.getValue();
                        currentMovies.addAll(list);
                        mMoviesPopular.postValue(currentMovies);
                    }

                }else{
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error code " + error);
                    mMoviesPopular.postValue(null);
                }
            }catch (IOException e){
                e.printStackTrace();
                mMoviesPopular.postValue(null);
            }


        }

        private Call<MovieSearchResponse> getPopular(int pageNumber){
            return Servicy.getInstance().getJSONApi().getPopular(
                    Credentials.API_KEY,
                    String.valueOf(pageNumber)
            );
        }
        private void cancelRequest(){
            Log.v("Tag","Cancelling Search Request");
            cancelRequest=true;
        }


    }

}


