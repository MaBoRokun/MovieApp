package com.example.movieapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.utils.Credentials;

import java.util.List;

public class MovieRecycleView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;

    private static final int Display_Popular=1;
    private static final int Display_Search=2;



    public MovieRecycleView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,
//                parent,false);
//        return new MovieViewHolder(view,onMovieListener);
        View view = null;
        if(viewType == Display_Search){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,
                parent,false);
            return new MovieViewHolder(view,onMovieListener);
        }
        else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_list_item,
                    parent,false);
            return new PopularViewHolder(view,onMovieListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


//        ((MovieViewHolder)holder).release_date.setText(mMovies.get(position).getRelease_date());
//        ((MovieViewHolder)holder).duration.setText(String.valueOf(mMovies.get(position).getRuntime()));
//
//        ((MovieViewHolder)holder).ratingBar.setRating(mMovies.get(position).getVote_average()/2);
//        Glide.with(holder.itemView.getContext())
//                .load("https://image.tmdb.org/t/p/w500/"+mMovies.get(position).getPoster_path())
//                .into(((MovieViewHolder)holder).imageView);

        int itemViewType = getItemViewType(position);
        if(itemViewType == Display_Search){
            ((MovieViewHolder)holder).title.setText(mMovies.get(position).getTitle());
            ((MovieViewHolder)holder).ratingBar.setRating(mMovies.get(position).getVote_average()/2);
            Glide.with(holder.itemView.getContext())
                .load(Credentials.IMAGE_PATH+mMovies.get(position).getPoster_path())
                .into(((MovieViewHolder)holder).imageView);

        }else {
            ((PopularViewHolder)holder).title.setText(mMovies.get(position).getTitle());
            ((PopularViewHolder)holder).ratingBar.setRating(mMovies.get(position).getVote_average()/2);
            Glide.with(holder.itemView.getContext())
                    .load(Credentials.IMAGE_PATH+mMovies.get(position).getPoster_path())
                    .into(((PopularViewHolder)holder).imageView);

        }


    }

    @Override
    public int getItemCount() {
        if(mMovies !=null) {
            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    public MovieModel getSelectedMovie(int position){
        if(mMovies !=null){
            if(mMovies.size()>0){
                return mMovies.get(position);
            }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(Credentials.POPULAR){
            return  Display_Popular;
        }else{
            return Display_Search;
        }
    }
}
