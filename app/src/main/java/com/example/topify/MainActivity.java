package com.example.topify;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView main_LST_movies;
    private TextInputLayout search_EDT_movie;
    private Button main_BTN_sort_pop;
    private Button main_BTN_sort_rate;
    private Button main_BTN_search;
    private  ArrayList<Movie> movies;
    private  ArrayList<Movie> temp;
    private MovieAdapter myAdapter;
    private Bundle bundle;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_LST_movies =  findViewById(R.id.main_LST_movies);
        search_EDT_movie = findViewById(R.id.search_EDT_movie);
        main_BTN_sort_pop = findViewById(R.id.main_BTN_sort_pop);
        main_BTN_sort_rate = findViewById(R.id.main_BTN_sort_rate);
        main_BTN_search = findViewById(R.id.main_BTN_search);
        movies = DataManager.generateMovies();
        temp = DataManager.generateMovies();

        this.bundle = getIntent().getBundleExtra("BUNDLE");


        myAdapter = new MovieAdapter(this,movies);

        main_BTN_sort_pop.setOnClickListener(v -> sort(0));
        main_BTN_sort_rate.setOnClickListener(v -> sort(1));
        main_BTN_search.setOnClickListener(v -> search());

        myAdapter.setMovieItemClickListener((movie, position) -> openMovieActivity(position));

        GetData getData = new GetData();
        getData.execute();




    }


    private void openMovieActivity(int position) {

        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("MOVIE", movies.get(position));
        startActivity(intent);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void search() {
        String input = String.valueOf(search_EDT_movie.getEditText().getText());

        if (input.equals("")){
            fixMoviesList();
            sort(1);
        }

        for (int i =0; i<movies.size(); i++){
            String title = movies.get(i).getTitle();

            if(!title.startsWith(input)){
                temp.add(movies.get(i));
                movies.remove(movies.get(i));
                i=-1;
            }



        }
        putDataIntoRecyclerView();
        search_EDT_movie.getEditText().setText("");

    }

    private void fixMoviesList() {
        for (int i = 0; i < temp.size(); i++) {
            if(!movies.contains(temp.get(i)))
                movies.add(temp.get(i));

        }
        putDataIntoRecyclerView();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sort(int flag){
        if (flag ==0)
            movies.sort(new PopularitySorter());
        else
            movies.sort(new RatingSorter());

        putDataIntoRecyclerView();
    }


    public class GetData extends AsyncTask<String, String, String> {

        public GetData() {
            super();
        }

        @Override
        protected String doInBackground(String... strings) {

            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(String.format(getString(R.string.base_url)));
                    urlConnection = (HttpURLConnection) url.openConnection();



                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i =0; i<jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    Movie movie = new Movie();
                    movie.setTitle(jsonObject1.getString("title"));
                    movie.setOverview(jsonObject1.getString("overview"));
                    movie.setImage(jsonObject1.getString("poster_path"));
                    movie.setReleaseDate(jsonObject1.getString("release_date"));
                    movie.setRating(jsonObject1.getDouble("vote_average"));
                    movie.setPopularity((Number) jsonObject1.getInt("popularity"));
                    movies.add(movie);

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }

            putDataIntoRecyclerView();
        }


    }
    private void putDataIntoRecyclerView( ){

        main_LST_movies.setLayoutManager(new GridLayoutManager(this,2));
        main_LST_movies.setHasFixedSize(true);
        main_LST_movies.setItemAnimator(new DefaultItemAnimator());
        main_LST_movies.setAdapter(myAdapter);



    }

}