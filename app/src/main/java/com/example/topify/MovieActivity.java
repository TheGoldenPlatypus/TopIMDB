package com.example.topify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

public class MovieActivity extends AppCompatActivity {

    private Movie movie;

    public AppCompatImageView list_movie_IMG_image;
    public MaterialTextView list_movie_LBL_title;
    public MaterialTextView list_movie_LBL_overview;
    public MaterialTextView list_movie_LBL_release_date;
    public RatingBar list_movie_RTNG_stars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_movie);

        Intent i = getIntent();
        movie = (Movie) i.getSerializableExtra("MOVIE");

        findViews();

        showMovie();
    }

    private void findViews() {
        list_movie_IMG_image = findViewById(R.id.list_movie_IMG_image);
        list_movie_LBL_title = findViewById(R.id.list_movie_LBL_title);
        list_movie_LBL_overview = findViewById(R.id.list_movie_LBL_overview);
        list_movie_LBL_release_date = findViewById(R.id.list_movie_LBL_release_date);
        list_movie_RTNG_stars = findViewById(R.id.list_movie_RTNG_stars);


    }
    private void showMovie() {
        Glide.with(this).load("https://image.tmdb.org/t/p/original/"+movie.getImage()).into(list_movie_IMG_image);

        list_movie_LBL_title.setText(movie.getTitle());
        list_movie_LBL_overview.setText(movie.getOverview());
        list_movie_LBL_release_date   .setText(movie.getReleaseDate());
        double rating = movie.getRating();
        rating /= 2;
        list_movie_RTNG_stars.setRating((float) rating);

    }
}