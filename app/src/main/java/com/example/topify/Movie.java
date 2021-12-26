package com.example.topify;

import java.io.Serializable;

public class Movie implements Serializable {
    private String title = "";
    private String overview= "";
    private String image = "";
    private String releaseDate = "";
    private double rating = 0;
    private Number popularity = 0;

    public Movie() {

    }

    public Number getPopularity() {
        return popularity;
    }

    public Movie setPopularity(Number popularity) {
        this.popularity = popularity;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getOverview() {
        return overview;
    }

    public Movie setOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Movie setImage(String image) {
        this.image = image;
        return this;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Movie setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public double getRating() {
        return rating;
    }

    public Movie setRating(double rating) {
        this.rating = rating;
        return this;
    }
}
