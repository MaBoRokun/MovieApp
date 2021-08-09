package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.adapter.MovieRecycleView;
import com.example.movieapp.adapter.OnMovieListener;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.viewmodels.MovieListViewModel;

import java.util.List;



public class MovieListActivity extends AppCompatActivity implements OnMovieListener {
private MovieListViewModel movieList;
private RecyclerView recyclerView;
private MovieRecycleView movieRecycleAdapter;
private CheckBox adult;

boolean isPopular =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        SetupSearchView();

        recyclerView=findViewById(R.id.recyclerView);

        movieList= new ViewModelProvider(this).get(MovieListViewModel.class);

        cfg_recycle();
        ObserverAnyChanges();

        ObserverPopularMovies();
        movieList.searchMoviePopular(1);

        adult=findViewById(R.id.adult);

        adult.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });

    }

    private void ObserverPopularMovies() {
        movieList.getPopularMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels !=null){
                    for(MovieModel movieModel: movieModels){

                        movieRecycleAdapter.setmMovies(movieModels);

                    }
                }
            }
        });
    }


    private void ObserverAnyChanges(){
        movieList.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels !=null){
                    for(MovieModel movieModel: movieModels){

                        movieRecycleAdapter.setmMovies(movieModels);

                    }
                }
            }
        });
    }



    private void SetupSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieList.searchMovieApi(query,1);
                isPopular=false;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    movieList.searchMoviePopular(1);
                }
                return false;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    private void cfg_recycle(){
        movieRecycleAdapter = new MovieRecycleView(this);
        recyclerView.setAdapter(movieRecycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){
                    if(isPopular){
                        movieList.NextPopularPage();
                    }else{
                        movieList.SearchNextPage();
                    }
                }
            }
        });

    }



    @Override
    public void onMovieClick(int position) {

    Intent intent = new Intent(this,MovieDetailActivity.class);
    intent.putExtra("movie",movieRecycleAdapter.getSelectedMovie(position));
    startActivity(intent);

    }

    @Override
    public void onCategoryClick(String category) {

    }
}