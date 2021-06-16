package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.utils.Credentials;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView DetailImage;
    private TextView TitleDetails,OverviewDetails;
    private RatingBar RatingDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        DetailImage=findViewById(R.id.detail_movie_image);
        TitleDetails=findViewById(R.id.detail_movie_title);
        OverviewDetails=findViewById(R.id.detail_movie_overview);
        RatingDetails=findViewById(R.id.detail_movie_rating);

        GetDataFromIntent();
    }

    private void GetDataFromIntent() {
        if(getIntent().hasExtra("movie")){
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            Log.v("Tag","Intent movie id" +movieModel.getMovie_id());
            TitleDetails.setText(movieModel.getTitle());
            OverviewDetails.setText(movieModel.getMovie_overview());
            RatingDetails.setRating((movieModel.getVote_average())/2);

            Log.v("Tag","DetailOverview "+movieModel.getMovie_overview());
            Glide.with(this)
                    .load(Credentials.IMAGE_PATH+movieModel.getPoster_path())
                    .into(DetailImage);


        }
    }


}