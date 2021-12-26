package com.example.topify;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Activity activity; // glide requires Context
    private ArrayList<Movie> movies = new ArrayList<>();
    private MovieItemClickListener movieItemClickListener;

    public MovieAdapter(Activity activity, ArrayList<Movie> movies) {
        this.movies = movies;
        this.activity=activity;
    }

    public MovieAdapter setMovieItemClickListener(MovieItemClickListener movieItemClickListener) {
        this.movieItemClickListener = movieItemClickListener;
        return this;
    }

    // each Object is correspond with layout ,in this case - movie_item
    @Override
    public RecyclerView.ViewHolder
    onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    // invoked every time when a need to display an object arisen
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
        Movie movie = getItem(position);

        movieViewHolder.movie_LBL_title.setText(position+1+"."+ "\n" + movie.getTitle());
        movieViewHolder.movie_LBL_overview.setText(movie.getOverview());
        movieViewHolder.movie_LBL_release_date.setText(movie.getReleaseDate());

        double rating = movie.getRating();
        rating /= 2;
        movieViewHolder.movie_RTNG_stars.setRating((float)rating);

        Glide.with(activity).load("https://image.tmdb.org/t/p/original/"+movie.getImage()).into(movieViewHolder.movie_IMG_image);


    }

    // returns list's size
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // return the Movie object in the suitable position (index)
    private Movie getItem(int position) {
        return movies.get(position);
    }

    public interface MovieItemClickListener {
        void movieItemClicked(Movie movie, int position);
    }

    // internal sub-class to hold al the views objects
    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public AppCompatImageView movie_IMG_image;
        public MaterialTextView movie_LBL_title;
        public MaterialTextView movie_LBL_overview;
        public MaterialTextView movie_LBL_release_date;
        public RatingBar movie_RTNG_stars;

        public MovieViewHolder(final View itemView) {
            super(itemView);
            this.movie_IMG_image = itemView.findViewById(R.id.movie_IMG_image);
            this.movie_LBL_title = itemView.findViewById(R.id.movie_LBL_title);
            this.movie_LBL_overview = itemView.findViewById(R.id.movie_LBL_overview);
            this.movie_LBL_release_date = itemView.findViewById(R.id.movie_LBL_release_date);
            this.movie_RTNG_stars = itemView.findViewById(R.id.movie_RTNG_stars);

            itemView.setOnClickListener(v -> movieItemClickListener.movieItemClicked(getItem(getAdapterPosition()),getAdapterPosition()));
        }
    }


    }
