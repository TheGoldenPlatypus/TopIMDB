package com.example.topify;

import java.util.Comparator;

public class PopularitySorter implements Comparator<Movie> {

    @Override
    public int compare(Movie o1, Movie o2) {
        int o1Pop =(int) o1.getPopularity();
        int o2Pop = (int) o2.getPopularity();

        return Integer.compare(o2Pop,o1Pop);
    }
}
