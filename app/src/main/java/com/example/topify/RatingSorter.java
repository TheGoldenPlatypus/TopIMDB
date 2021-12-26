package com.example.topify;

import java.util.Comparator;

public class RatingSorter implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {

        return Double.compare(o2.getRating(),o1.getRating());

    }
}
